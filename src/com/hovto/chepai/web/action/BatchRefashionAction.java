package com.hovto.chepai.web.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hovto.chepai.dao.WorkFlowDao;
import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.BatchNumberMerge;
import com.hovto.chepai.model.BatchRefashion;
import com.hovto.chepai.model.DisuseMaterial;
import com.hovto.chepai.model.FlowType;
import com.hovto.chepai.model.NumberPlate;
import com.hovto.chepai.model.Remakes;
import com.hovto.chepai.model.SemifinishedProductType;
import com.hovto.chepai.model.SmallnessBatchNumber;
import com.hovto.chepai.model.TaskAllocation;
import com.hovto.chepai.model.Users;
import com.hovto.chepai.model.WorkFlow;
import com.hovto.chepai.server.BatchNumberMergeServer;
import com.hovto.chepai.server.BatchRefashionServer;
import com.hovto.chepai.server.DisuseMaterialServer;
import com.hovto.chepai.server.FlowTypeServer;
import com.hovto.chepai.server.NumberPlateServer;
import com.hovto.chepai.server.RemakesServer;
import com.hovto.chepai.server.SemifinishedProductTypeServer;
import com.hovto.chepai.server.SmallnessBatchNumberServer;
import com.hovto.chepai.server.TaskAllocationServer;
import com.hovto.chepai.server.UserServer;
import com.hovto.chepai.server.WorkFlowServer;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class BatchRefashionAction {
	@Resource
	private BatchRefashionServer batchRefashionServer;
	@Resource
	private SmallnessBatchNumberServer smallnessBatchNumberServer;
	@Resource
	private NumberPlateServer numberPlateServer;
	@Resource
	private WorkFlowServer workFlowServer;
	@Resource
	private TaskAllocationServer taskAllocationServer;
	@Resource
	private BatchNumberMergeServer batchNumberMergeServer;
	@Resource
	private DisuseMaterialServer disuseMaterialServer;
	@Resource
	private FlowTypeServer flowTypeServer;
	@Resource
	private RemakesServer remakesServer;
	@Resource
	private WorkFlowDao workFlowDao;
	@Resource
	private SemifinishedProductTypeServer semifinishedProductTypeServer;
	@Resource
	private UserServer userServer;
	
	
	public SmallnessBatchNumber smallnessBatchNumber;
	public int[] smallnessBatchNumberIds;
	public int[] batchRefashionIds;
	public List<BatchRefashion> batchRefashionList;
	private int flowTypeId;
	public List<FlowType> flowTypes;//完整的流程
	private int errorFlow;//重制错误流程
	private int marType;//废牌类型
	
	public String save(){
		if(smallnessBatchNumberIds!=null){
			for(int i=0;i<smallnessBatchNumberIds.length;i++){
				BatchRefashion batchRefashion=new BatchRefashion();
				SmallnessBatchNumber smallnessBatchNumber=smallnessBatchNumberServer.findById(smallnessBatchNumberIds[i]);
				batchRefashion.setSmallnessBatchNumber(smallnessBatchNumber);
				batchRefashion.setBigBatchNumber(smallnessBatchNumber.getBigBatchNumber());
				batchRefashion.setStatus(1);
				batchRefashionServer.save(batchRefashion);//添加一条整批重制信息
				
				
				//smallnessBatchNumber.setIsValid(1);
				//smallnessBatchNumber.setBoxNumber(0);
				smallnessBatchNumber.setIsDistribute(4);//审核中
				smallnessBatchNumberServer.update(smallnessBatchNumber);//把需要重制的子批号变为整批补制
				List<TaskAllocation> taskList=taskAllocationServer.findBySBatchNumberId(smallnessBatchNumber.getId());
				for(int j=0;j<taskList.size();j++){
					TaskAllocation taskAllocation=taskList.get(j);
					taskAllocation.setStatus(2);
					taskAllocationServer.update(taskAllocation);
				}
			}
			ActionContext.getContext().put("message", "申请重制成功,转入核审区!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>返回操作</a>");
			return "message";
		} else
			throw new ChePaiException("请选择要申请整批重制的小批号!");
	}
	/**
	 * 停制
	 * @return
	 */
	public String stopfor(){
		if(smallnessBatchNumberIds!=null){
			for(int i=0;i<smallnessBatchNumberIds.length;i++){
				SmallnessBatchNumber smallnessBatchNumber=smallnessBatchNumberServer.findById(smallnessBatchNumberIds[i]);
				smallnessBatchNumber.setIsDistribute(5); //停制中
				smallnessBatchNumberServer.update(smallnessBatchNumber);
			}
			
			ActionContext.getContext().put("message", "停制成功!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>返回操作</a>");
			return "message";
		}else{
			throw new ChePaiException("请选择要申请整批重制的小批号!<a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>返回操作</a>");
		}
	}
	
	/**
	 * 取消停制
	 * @return
	 */
	public String celstopfor(){
		if(smallnessBatchNumber!=null){
			smallnessBatchNumber=smallnessBatchNumberServer.findById(smallnessBatchNumber.getId());
			smallnessBatchNumber.setIsDistribute(2); //恢复已发状态
			smallnessBatchNumberServer.update(smallnessBatchNumber);
			ActionContext.getContext().put("message", "操作成功!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>返回操作</a>");
			return "message";
		}else{
			throw new ChePaiException("操作失败!<a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>返回操作</a>");
		}
	}
	
	public String back(){
		batchRefashionServer.back(batchRefashionIds);
		ActionContext.getContext().put("message", "回退成功!<br/><a href='BatchRefashion!findByStatus.action'>返回操作</a>");
		return "message";
	}
	
	//生成废牌记录
	public void addDisuseMaterial(SmallnessBatchNumber sb) {
		try {
			List<SemifinishedProductType> sp= semifinishedProductTypeServer.findList();
			Map<Integer,int[]> map=new HashMap<Integer,int[]>();
			for(int i=0;i<sp.size();i++){
				for(int j=0;j<sp.get(i).getNumberPlateTypes().size();j++){
					if(map.get(sp.get(i).getNumberPlateTypes().get(j).getId()) == null)
						map.put(sp.get(i).getNumberPlateTypes().get(j).getId(), new int[]{sp.get(i).getId()});
					else
						map.put(sp.get(i).getNumberPlateTypes().get(j).getId(), new int[]{map.get(sp.get(i).getNumberPlateTypes().get(j).getId())[0],sp.get(i).getId()});
				}
			}
			
			List<NumberPlate> numberPlateList=numberPlateServer.findBySmallnessBatchId(sb.getId());
			TaskAllocation taskAllocation= taskAllocationServer.findBySBatchNumberAndFlowType(sb.getId(), errorFlow);
			for(int j=0;j<numberPlateList.size();j++){
				NumberPlate numberPlate=numberPlateList.get(j);
				
				int index = 0;
				int typeId = numberPlate.getNumberPlateType().getId();
				for(int i=0;i<numberPlate.getNumberPlateType().getNumber();i++) {
					DisuseMaterial disuseMaterial=new DisuseMaterial();
					disuseMaterial.setBigBatchNumber(numberPlate.getSmallnessBatchNumber().getBigBatchNumber());
					disuseMaterial.setSmallnessBatchNumber(numberPlate.getSmallnessBatchNumber());
					disuseMaterial.setNumberPlate(numberPlate);
					disuseMaterial.setNumberPlateType(numberPlate.getNumberPlateType());
					disuseMaterial.setOperateTime(new Date());
					disuseMaterial.setFlowType(new FlowType(errorFlow));
					disuseMaterial.setMarType(marType);
					disuseMaterial.setOperator(taskAllocation.getOperator());
					if(taskAllocation.getOperator() != null) {
						Users u = userServer.find(new Users(Integer.parseInt(taskAllocation.getOperator().split(",")[0])));
						disuseMaterial.setDayNight(u.getDayNight());
					}
					
					
					if(map.containsKey(typeId)) {
						if(map.get(typeId).length > 1) {
							disuseMaterial.setSemifinishedProductType(new SemifinishedProductType(map.get(typeId)[index]));
							index ++;
						} else {
							disuseMaterial.setSemifinishedProductType(new SemifinishedProductType(map.get(typeId)[index]));
						}
					}
					
					disuseMaterialServer.add(disuseMaterial);
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String findByStatus(){
		batchRefashionList=batchRefashionServer.findByStatus(1);
		return "list";
	}
	
	
	public void updateworkFlow(SmallnessBatchNumber smallnessBatchNumber){
		smallnessBatchNumber.setIsValid(1);//完成前不启用
		smallnessBatchNumber.setBoxNumber(0);//箱号清空
		smallnessBatchNumber.setBoxNumberType(0);
		smallnessBatchNumber.setIsDistribute(4);//补制中
		smallnessBatchNumberServer.update(smallnessBatchNumber);//把需要重制的子批号变为整批补制
		WorkFlow workFlow=workFlowServer.findBySbatchNumberId(smallnessBatchNumber.getId());
		workFlow.setCurrentFlow(new FlowType(8,null));
		workFlow.setNextFlow(new FlowType(8,null));
		workFlowServer.update(workFlow);//改变流程状态
	}
	
	public String update(){
		try {
			if(batchRefashionIds!=null){
				for(int i=0;i<batchRefashionIds.length;i++){
					/*//查询出小批号分配的数据
					List<TaskAllocation> taskAllocationList=taskAllocationServer.findBySBatchNumberId(smallnessBatchNumberIds[i]);
					if(taskAllocationList.size()>0){
						//修改任务分配的完成状态
						TaskAllocation taskAllocation=taskAllocationList.get(0);
						taskAllocation.setStatus(1);
						taskAllocationServer.update(taskAllocation);//修改任务分配情况
					}*/
					
					BatchRefashion batchRefashion=batchRefashionServer.findById(batchRefashionIds[i]);
					SmallnessBatchNumber sb=batchRefashion.getSmallnessBatchNumber();
					
					List<NumberPlate> numberPlateList=numberPlateServer.findBySmallnessBatchId(sb.getId());
					SmallnessBatchNumber refashion=new SmallnessBatchNumber(sb.getBigBatchNumber(),sb.getSmallnessBatchNumber(),sb.getOrderType(),sb.getPlace(),sb.getOtherName(),sb.getAmount(),sb.getDateTime(),1,sb.getIsRemakes(),0,1,1,0,0,sb.getNumberPlateType());
					
					smallnessBatchNumberServer.add(refashion);//重新添加一条子批号
					
					//添加小批号反压流程
					WorkFlow workFlow = new WorkFlow();
					workFlow.setSmallnessBatchNumber(refashion);
					workFlow.setBigBatchNumber(refashion.getBigBatchNumber());
					workFlow.setCurrentFlow(new FlowType(1));
					workFlow.setNextFlow(new FlowType(2));
					workFlowDao.add(workFlow);
					
					this.addDisuseMaterial(sb);//生成废牌记录
					this.updateworkFlow(sb);//改变原小批号状态
					for(int j=0;j<numberPlateList.size();j++){
						NumberPlate numberPlate=(NumberPlate)numberPlateList.get(j);
						numberPlate.setSmallnessBatchNumber(refashion);
						numberPlateServer.update(numberPlate);//改变车牌对应的小批号 
					}

					if(sb.getIsRemakes()==2){//判断是否合并
						List<BatchNumberMerge> batchList=batchNumberMergeServer.findMergerBySmallId(sb.getId());
						for(int j=0;j<batchList.size();j++){
							BatchNumberMerge batchNumberMerge=new BatchNumberMerge(refashion,batchList.get(j).getBigBatchNumber());
							batchNumberMergeServer.add(batchNumberMerge);
						}
					}else if(sb.getIsRemakes()==1){
						List<Remakes> remakesList=remakesServer.findResultRemakes(sb.getId());
							for(int j=0;j<remakesList.size();j++){
								Remakes remakes=remakesList.get(j);
								remakes.setSmallnessBatchNumber(refashion);
								remakesServer.updateRemakes(remakes);
							}
					}
					batchRefashion.setRefashionSmallnessBatchNumber(refashion);//在整批返回表中对应
					batchRefashion.setStatus(2);
					batchRefashionServer.update(batchRefashion);
				}
				ActionContext.getContext().put("message", "重制成功,转入待发区,请重新下发任务!<br/><a href='BatchRefashion!findByStatus.action'>返回操作</a>");
				return "message";
			} else
				throw new ChePaiException("请选择要重制的小批号");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public int[] getSmallnessBatchNumberIds() {
		return smallnessBatchNumberIds;
	}

	public void setSmallnessBatchNumberIds(int[] smallnessBatchNumberIds) {
		this.smallnessBatchNumberIds = smallnessBatchNumberIds;
	}
	public int[] getBatchRefashionIds() {
		return batchRefashionIds;
	}
	public void setBatchRefashionIds(int[] batchRefashionIds) {
		this.batchRefashionIds = batchRefashionIds;
	}
	public List<BatchRefashion> getBatchRefashionList() {
		return batchRefashionList;
	}
	public void setBatchRefashionList(List<BatchRefashion> batchRefashionList) {
		this.batchRefashionList = batchRefashionList;
	}
	public int getFlowTypeId() {
		return flowTypeId;
	}
	public void setFlowTypeId(int flowTypeId) {
		this.flowTypeId = flowTypeId;
	}
	public List<FlowType> getFlowTypes() {
		return flowTypeServer.findList();
	}
	public void setFlowTypes(List<FlowType> flowTypes) {
		this.flowTypes = flowTypes;
	}
	public int getErrorFlow() {
		return errorFlow;
	}
	public void setErrorFlow(int errorFlow) {
		this.errorFlow = errorFlow;
	}
	public int getMarType() {
		return marType;
	}
	public void setMarType(int marType) {
		this.marType = marType;
	}
	public SmallnessBatchNumber getSmallnessBatchNumber() {
		return smallnessBatchNumber;
	}
	public void setSmallnessBatchNumber(SmallnessBatchNumber smallnessBatchNumber) {
		this.smallnessBatchNumber = smallnessBatchNumber;
	}

}
