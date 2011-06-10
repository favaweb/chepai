package com.hovto.chepai.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.BatchNumberMergeDao;
import com.hovto.chepai.dao.BigBatchNumberDao;
import com.hovto.chepai.dao.FlowTypeDao;
import com.hovto.chepai.dao.NumberPlateDao;
import com.hovto.chepai.dao.OperatorDao;
import com.hovto.chepai.dao.RemakesDao;
import com.hovto.chepai.dao.SmallnessBatchNumberDao;
import com.hovto.chepai.dao.WorkFlowDao;
import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.BatchNumberMerge;
import com.hovto.chepai.model.BigBatchNumber;
import com.hovto.chepai.model.FlowType;
import com.hovto.chepai.model.NumberPlate;
import com.hovto.chepai.model.Operator;
import com.hovto.chepai.model.SmallnessBatchNumber;
import com.hovto.chepai.model.TaskAllocation;
import com.hovto.chepai.model.WorkFlow;
import com.hovto.chepai.tool.Page;

@Component
@Transactional
public class WorkFlowServer {

	@Resource
	private WorkFlowDao workFlowDao;
	@Resource
	private FlowTypeDao flowTypeDao;
	@Resource
	private SmallnessBatchNumberDao smallnessBatchNumberDao;
	@Resource
	private IndividualOutputServer individualOutputServer;
	@Resource
	private BatchNumberMergeDao batchNumberMergeDao;
	@Resource
	private OperatorDao operatorDao;
	@Resource
	private TaskAllocationServer taskAllocationServer;
	@Resource
	private BigBatchNumberDao batchNumberDao;
	@Resource
	private NumberPlateDao numberPlateDao;
	@Resource
	private RemakesDao remakesDao;
	public void modify(WorkFlow workFlow) {
		workFlowDao.modify(workFlow);
	}
	
	public WorkFlow find(WorkFlow workFlow) {
		return workFlowDao.find(workFlow);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkFlow> findList(int id) {
		return workFlowDao.findList(id);
	}
	@SuppressWarnings("unchecked")
	public List<WorkFlow> queryList(int id){
		return workFlowDao.queryList(id);
	}
	
	/**
	 * 根据 流程Id 查询数据,并且按照操作人排序
	 * @param id 流程Id
	 * @return
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<WorkFlow> findOrderByUsers(int id, Page page, int plateType) {
		if(id == 1) {
			if(plateType != 0) {
				return workFlowDao.findFanYa(id, page, plateType);
			} else {
				return workFlowDao.findFanYa(id, page);
			}
		} else {
			return workFlowDao.findOrderByUsers(id,page);
		}
	}
	
	public boolean update(WorkFlow workFlow){
		try {
			workFlowDao.modify(workFlow);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public WorkFlow findBySbatchNumberId(int smallnessBatchNumberId){
		WorkFlow workFlow=null;
		try {
			List<WorkFlow> list=workFlowDao.findBySbatchNumberId(smallnessBatchNumberId);//根据子批号id查找出流程状态
			if(list.size()>0){
				workFlow=(WorkFlow)list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workFlow;
	}
	
	
	/**
	 * 反压和滚油提交流程修改
	 * @param smallnessBatchNumberId
	 * @param boxNumber
	 * @param isRemakes
	 */
	public void counterpressureUpdate(int smallnessBatchNumberId,String boxNumber,String boxType,int isRemakes){
		WorkFlow directionWorkFlow=new WorkFlow();
		try {
			SmallnessBatchNumber smallnessBatchNumber=smallnessBatchNumberDao.find(new SmallnessBatchNumber(smallnessBatchNumberId));
			if(smallnessBatchNumber != null && boxNumber!=null&&!"".equals(boxNumber)){//判断箱号是否为0不为0则是反压流程提交添加箱号
				smallnessBatchNumber.setBoxNumberType(Integer.parseInt(boxType));
				smallnessBatchNumber.setBoxNumber(Integer.parseInt(boxNumber));
				smallnessBatchNumberDao.modify(smallnessBatchNumber);//修改箱号
			}
			//设置小批号
			directionWorkFlow.setSmallnessBatchNumber(smallnessBatchNumber);
			
			List<NumberPlate> numberPlateList=new ArrayList<NumberPlate>(0);
			if(smallnessBatchNumber.getIsRemakes()==1){//判断为补制
				//找出 车牌类型为 小型汽车类型的 车牌
				numberPlateList=remakesDao.queryBySbId(smallnessBatchNumberId);
			}else{
				//找出 车牌类型为 小型汽车类型的 车牌
				numberPlateList=numberPlateDao.findByNumberTypeAndSbatchId(smallnessBatchNumberId);//查询出小批号类所有的小型车牌
			}
			directionWorkFlow=this.findBySbatchNumberId(smallnessBatchNumberId);
			int flowId=directionWorkFlow.getCurrentFlow().getId();
			//判断大于0则有小型汽车 进入洗牌或正压流程
			if(numberPlateList.size()>0){
				directionWorkFlow.setCurrentFlow(new FlowType((directionWorkFlow.getCurrentFlow().getId())+1));
				directionWorkFlow.setNextFlow(new FlowType((directionWorkFlow.getNextFlow().getId())+1));
			}else{//否则则进入滚油或质检流程
				directionWorkFlow.setCurrentFlow(new FlowType((directionWorkFlow.getCurrentFlow().getId())+2));
				directionWorkFlow.setNextFlow(new FlowType((directionWorkFlow.getNextFlow().getId())+2));
			}
			//添加操作人 跟产量
			this.addOperatorAndindividualOutput(flowId, smallnessBatchNumber);
			
			//修改流程状态
			workFlowDao.modify(directionWorkFlow);
			//子批号表改为待发
			smallnessBatchNumber.setIsDistribute(1);
			smallnessBatchNumberDao.modify(smallnessBatchNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//洗牌和正压提交流程修改
	public void shuffleAndBarotropyUpdate(int smallnessBatchNumberId){
		try {
			WorkFlow directionWorkFlow=this.findBySbatchNumberId(smallnessBatchNumberId);
			SmallnessBatchNumber smallnessBatchNumber=smallnessBatchNumberDao.find(new SmallnessBatchNumber(smallnessBatchNumberId));
			if(directionWorkFlow!=null){
				int flowId=directionWorkFlow.getCurrentFlow().getId();
				directionWorkFlow.setCurrentFlow(new FlowType((directionWorkFlow.getCurrentFlow().getId())+1));
				directionWorkFlow.setNextFlow(new FlowType((directionWorkFlow.getNextFlow().getId())+1));
				
				//添加个人产量和操作人修改任务分配完成情况
				this.addOperatorAndindividualOutput(flowId, smallnessBatchNumber);
				workFlowDao.modify(directionWorkFlow);//修改流程状态
				smallnessBatchNumber.setIsDistribute(1);//子批号表改为待发
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据小批号 更改下一个流程状态
	 * status 是否补制
	 * @param smallnessBatchNumberId
	 * @return
	 */
	public boolean updateBySbatchNumberId(int smallnessBatchNumberId, int status){
		WorkFlow directionWorkFlow=new WorkFlow();
		try {
			SmallnessBatchNumber smallnessBatchNumber=smallnessBatchNumberDao.find(new SmallnessBatchNumber(smallnessBatchNumberId));
			/*if(boxNumber!=null&&!"".equals(boxNumber)){//判断箱号是否为0不为0则是反压流程提交添加箱号
				smallnessBatchNumber.setBoxNumber(Integer.parseInt(boxNumber));
				smallnessBatchNumberDao.modify(smallnessBatchNumber);
			}*/
			
			if(smallnessBatchNumber!=null){
				directionWorkFlow.setSmallnessBatchNumber(smallnessBatchNumber);
			}
			//查询出当前流程数据
			/*List<WorkFlow> list=workFlowDao.findBySbatchNumberId(smallnessBatchNumberId);//根据子批号id查找出流程状态
			workFlow=(WorkFlow)list.get(0);*/
			directionWorkFlow=this.findBySbatchNumberId(smallnessBatchNumberId);
			int flowId=directionWorkFlow.getCurrentFlow().getId();
			if(directionWorkFlow!=null){
				
				if(directionWorkFlow.getCurrentFlow().getId()<8) {//判断当前流程id是否小于最后流程id
					//设置好 当前流程 和下一个流程
					directionWorkFlow.setCurrentFlow(new FlowType((directionWorkFlow.getCurrentFlow().getId())+1));
					if(directionWorkFlow.getNextFlow().getId()<8){//判断下一流程是否为最后流程
						directionWorkFlow.setNextFlow(new FlowType((directionWorkFlow.getNextFlow().getId())+1));
					}
					
					
					//添加个人产量和操作人修改任务分配完成情况
					if(status == 0)
						this.addOperatorAndindividualOutput(flowId, smallnessBatchNumber);
					else
						this.addOperatorAndindividualOutputRepeat(flowId, smallnessBatchNumber);
						
				
				
					TaskAllocation taskAllocation=taskAllocationServer.findBySBatchNumberAndFlowType(smallnessBatchNumberId, 7);
					if(taskAllocation!=null){//判断是否为总质检返回子批号为null则不是
						directionWorkFlow.setCurrentFlow(new FlowType(7));
						directionWorkFlow.setNextFlow(new FlowType(8));
					}
					
					workFlowDao.modify(directionWorkFlow);//修改流程状态
					smallnessBatchNumber.setIsDistribute(1);//子批号表改为待发
					smallnessBatchNumberDao.modify(smallnessBatchNumber);
					
					
					//如果是总质检
					if(flowId==7){
						smallnessBatchNumber.setMakeStatus(2);//制作状态：3完成
						smallnessBatchNumber.setBoxNumber(0);//箱号改为0
						smallnessBatchNumber.setBoxNumberType(0);
						smallnessBatchNumber.setIsDeliverGoods(1);//是否发货：1未发（默认）2已发
						smallnessBatchNumberDao.modify(smallnessBatchNumber);
						
						
						if(smallnessBatchNumber.getIsRemakes()==2){//判断是否为合并
							//根据合并小批号查询合并数据
							List<BatchNumberMerge> batchNumberMergeList=batchNumberMergeDao.findBySmallnessBatchNumber(smallnessBatchNumber.getId());
							for(int i=0;i<batchNumberMergeList.size();i++){
								BatchNumberMerge batchNumberMerge=(BatchNumberMerge)batchNumberMergeList.get(i);
								List<SmallnessBatchNumber> sbatchNumberList=smallnessBatchNumberDao.findBybigBatchNumber(batchNumberMerge.getBigBatchNumber().getId());
								for(int j=0;j<sbatchNumberList.size();j++){
									SmallnessBatchNumber sBatchNumber=(SmallnessBatchNumber)sbatchNumberList.get(j);
									sBatchNumber.setMakeStatus(2);
									sBatchNumber.setIsDeliverGoods(1);
									smallnessBatchNumberDao.modify(sBatchNumber);
									
									BigBatchNumber bigBatchNumber=sBatchNumber.getBigBatchNumber();
									bigBatchNumber.setIsDeliverGoods(1);
									batchNumberDao.modify(bigBatchNumber);//修改大批号 发货状态
								}
							}
						} else {
							BigBatchNumber bigBatchNumber=smallnessBatchNumber.getBigBatchNumber();
							bigBatchNumber.setIsDeliverGoods(1);
							batchNumberDao.modify(bigBatchNumber);//修改大批号 发货状态
						}
						
					}
					
					
					
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<WorkFlow> findByCondition(String date, int flowType, String place, Page page) {
		
		String sql = "select w from WorkFlow w where w.currentFlow != 8 and w.smallnessBatchNumber.makeStatus = 1 and w.smallnessBatchNumber.isValid = 0 ";
		String countSql = "select count(w) from WorkFlow w where w.currentFlow != 8 and w.smallnessBatchNumber.makeStatus = 1 and w.smallnessBatchNumber.isValid = 0 ";
		Date findDate = null;
		Date nextDate = null;
		
		if(date != null && !date.trim().equals("")) {
			try {
				findDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
				sql += " and w.smallnessBatchNumber.dateTime >= :findDate and w.smallnessBatchNumber.dateTime < :nextDate ";
				countSql += " and w.smallnessBatchNumber.dateTime >= :findDate and w.smallnessBatchNumber.dateTime < :nextDate";
				
				Calendar c = Calendar.getInstance();
				c.setTime(findDate);
				c.set(Calendar.DATE,c.get(Calendar.DATE) + 1);
				
				nextDate = c.getTime();
				
			} catch (ParseException e) {
				throw new ChePaiException("时间类型不对");
			}
		}
		
		
		if(flowType != 0) {
			sql += " and w.currentFlow.id = :flowType ";
			countSql += " and w.currentFlow.id = :flowType ";
		}
		if(place != null && !"".equals(place)) {
			sql += " and w.smallnessBatchNumber.place = :place";
			countSql += " and w.smallnessBatchNumber.place = :place";
		}
		
		return workFlowDao.findByCondition(findDate, nextDate, flowType, place, sql, countSql, page);
	}
	
	/**
	 * 添加个人产量和操作人修改任务分配完成情况
	 */
	public void addOperatorAndindividualOutput(int flowId,SmallnessBatchNumber smallnessBatchNumber){
		//根据小批号和流程类型查询出未完成的分配情况
		List<TaskAllocation> taskAllocationList=taskAllocationServer.findBySBatchNumberAndFlowTypeAndStatus(smallnessBatchNumber.getId(), flowId,0);
		//添加操作人
		Operator operator=new Operator();
		operator.setFlowType(new FlowType(flowId));
		operator.setOperater(taskAllocationList.get(taskAllocationList.size()-1).getOperator());
		operator.setSmallnessBatchNumber(smallnessBatchNumber);
		operator.setBigBatchNumber(smallnessBatchNumber.getBigBatchNumber());
		operatorDao.add(operator);//添加操作人
		
		//添加个人产量
		individualOutputServer.save(new FlowType(flowId), smallnessBatchNumber);

		//修改任务状态
		for(int j=0;j<taskAllocationList.size();j++){
			TaskAllocation taction=taskAllocationList.get(j);
			if(taction.getStatus()!=1){
				//修改任务分配的完成状态
				taction.setStatus(1);
				taskAllocationServer.update(taction);
			}
		}
	}
	
	/**
	 * 添加个人产量和操作人修改任务分配完成情况
	 */
	public void addOperatorAndindividualOutputRepeat(int flowId,SmallnessBatchNumber smallnessBatchNumber){
		//根据小批号和流程类型查询出未完成的分配情况
		List<TaskAllocation> taskAllocationList=taskAllocationServer.findBySBatchNumberAndFlowTypeAndStatus(smallnessBatchNumber.getId(), flowId,1);
		//添加操作人
		Operator operator=new Operator();
		operator.setFlowType(new FlowType(flowId));
		operator.setOperater(taskAllocationList.get(taskAllocationList.size()-1).getOperator());
		operator.setSmallnessBatchNumber(smallnessBatchNumber);
		operator.setBigBatchNumber(smallnessBatchNumber.getBigBatchNumber());
		operatorDao.add(operator);//添加操作人
		
		//添加个人产量
		individualOutputServer.save(new FlowType(flowId), smallnessBatchNumber);
		
		//修改任务状态
		for(int j=0;j<taskAllocationList.size();j++){
			TaskAllocation taction=taskAllocationList.get(j);
			if(taction.getStatus()!=1){
				//修改任务分配的完成状态
				taction.setStatus(1);
				taskAllocationServer.update(taction);
			}
		}
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<WorkFlow> findBySmallNo(String smallBatchNumber) {
		SmallnessBatchNumber s = smallnessBatchNumberDao.findBySmallNo(smallBatchNumber);
		if(s != null) {
			//如果小批号是 合并批号
			if(s.getIsValid() == 1) {
				SmallnessBatchNumber temp = batchNumberMergeDao.findByBigBatch(s.getBigBatchNumber().getId());
				return workFlowDao.findBySbatchNumberId(temp.getId());
			} else {
				return workFlowDao.findBySbatchNumberId(s.getId());
			}
		} else 
			throw new ChePaiException("没有该小批号,确定输入无误?<br/><a href='javascript:window.history.go(-1);'>返回操作</a>");
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<WorkFlow> findByNumberPlate(String numberPlate) {
		List<NumberPlate> numberPlates = numberPlateDao.findByNo(numberPlate.trim());
		if(numberPlates == null) throw new ChePaiException("没有该车牌号码,确定输入无误?<br/><a href='javascript:window.history.go(-1);'>返回操作</a>");
		
		//如果是正常的车牌号码
		if(numberPlates.size() == 1) {
			return workFlowDao.findBySbatchNumberId(numberPlates.get(0).getSmallnessBatchNumber().getId());
		} else { //如果该车牌号码 在合并批号里面
			for(NumberPlate n : numberPlates) {
				if(n.getSmallnessBatchNumber().getIsRemakes() == 2 || (n.getSmallnessBatchNumber().getIsValid() == 0)) {
					return workFlowDao.findBySbatchNumberId(n.getSmallnessBatchNumber().getId());
				}
			}
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
