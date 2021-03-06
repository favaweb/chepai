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
import com.hovto.chepai.dao.NumberPlateDao;
import com.hovto.chepai.dao.OperatorDao;
import com.hovto.chepai.dao.SmallnessBatchNumberDao;
import com.hovto.chepai.dao.UserDao;
import com.hovto.chepai.dao.WorkFlowDao;
import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.BatchNumberMerge;
import com.hovto.chepai.model.BigBatchNumber;
import com.hovto.chepai.model.FlowType;
import com.hovto.chepai.model.NumberPlate;
import com.hovto.chepai.model.Operator;
import com.hovto.chepai.model.SmallnessBatchNumber;
import com.hovto.chepai.model.Users;
import com.hovto.chepai.model.WorkFlow;
import com.hovto.chepai.tool.Page;

@Component
@Transactional
public class SmallnessBatchNumberServer {
	@Resource
	private SmallnessBatchNumberDao smallnessBatchNumberDao;
	@Resource
	private WorkFlowDao workFlowDao;
	@Resource
	private BatchNumberMergeDao batchNumberMergeDao;
	@Resource
	private NumberPlateDao numberPlateDao;
	@Resource
	private BigBatchNumberDao bigBatchNumberDao;
	@Resource
	private OperatorDao operatorDao;
	@Resource
	private UserDao userDao;

	public void save() {
		SmallnessBatchNumber smallnessBatchNumber = new SmallnessBatchNumber();
		smallnessBatchNumberDao.add(smallnessBatchNumber);
	}
	public SmallnessBatchNumber findById(int id){
		return smallnessBatchNumberDao.find(new SmallnessBatchNumber(id));
	}
	public boolean add(SmallnessBatchNumber sBatchNumber) {
		try {
			smallnessBatchNumberDao.add(sBatchNumber);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean update(SmallnessBatchNumber smallnessBatchNumber){
		try {
			smallnessBatchNumberDao.modify(smallnessBatchNumber);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 查询需要下发的任务
	 * @param makeStatus 制作状态 默认0还未制作  1制作中 2完成
	 * @return
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<SmallnessBatchNumber> findByMakeStatus(int bigBatchNumberId, Page page) {
		return smallnessBatchNumberDao.findByMakeStatus(bigBatchNumberId,page);
	}
	/**
	 * 根据箱号查询小批号记录
	 * @return
	 */
	public long findByBoxNumber(int boxNumber,int bType) {
		return smallnessBatchNumberDao.findByBoxNumber(boxNumber, bType);
	}
	/**
	 * 任务下发
	 * @param goFlows 下发的子批号
	 */
	public void goFlows(int[] goFlows) {
		if (goFlows != null && goFlows.length != 0) {
			for (int i = 0; i < goFlows.length; i++) {
				
				//修改子批号的制作状态:0待制1制作中2完成
				SmallnessBatchNumber sbn = smallnessBatchNumberDao
						.find(new SmallnessBatchNumber(goFlows[i]));
				sbn.setMakeStatus(1);
				sbn.setIsDistribute(1);
				smallnessBatchNumberDao.modify(sbn);
				
				//往流程表里面插入一条数据
				WorkFlow workFlow = new WorkFlow();
				workFlow.setSmallnessBatchNumber(sbn);
				workFlow.setBigBatchNumber(sbn.getBigBatchNumber());
				workFlow.setCurrentFlow(new FlowType(1));
				workFlow.setNextFlow(new FlowType(2));
				workFlowDao.add(workFlow);
			}
		} else 
			throw new ChePaiException("请选择要下发的任务!<br/><a href='javascript:window.history.go(-1);'>返回操作</a>");
	}

	/**
	 * 查询可以合并的小批号
	 * @param page 
	 * @return
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<SmallnessBatchNumber> findMerger(Page page) {
		return smallnessBatchNumberDao.findMerger();
	}


	/**
	 * 合并小批号
	 * @param mergers 小批号id
	 */
	public void merger(int[] mergers) {
		if(mergers == null || mergers.length == 0) 
			throw new ChePaiException("请选择要合并的小批号<br/><a href='javascript:window.history.go(-1);'>返回操作</a>");
		
		//合并后的车牌总数量
		int account = 0;
		//小批号类型 1补制、2自选、3号段
		int orderType = 0;
		//地区
		String place = null;
		SmallnessBatchNumber sbn = null;
		
		//循环找出要合并的小批号,判断他们是否属于同一类型, 如果符合, 将他们的 是否可用设置为 1无效
		for (int i = 0; i < mergers.length; i++) {
			sbn = smallnessBatchNumberDao
					.find(new SmallnessBatchNumber(mergers[i]));
			if(i == 0) {
				orderType = sbn.getOrderType();
				place = sbn.getPlace();
			} else {
				if(sbn.getOrderType() != orderType || !place.equals(sbn.getPlace())) {
					throw new ChePaiException("不是同一地区或者不是同一车牌类型的批号不能合并<br/><a href='javascript:window.history.go(-1);'>返回操作</a>");
				} else {
					orderType = sbn.getOrderType();
					place = sbn.getPlace();
				}
			}
			//设置小批号无效
			sbn.setIsValid(1);
			smallnessBatchNumberDao.modify(sbn);
			account += sbn.getBigBatchNumber().getAmount();
		}
		
		if(account >= 50) 
			throw new ChePaiException("合并车牌总数不能超过50个!<br/><a href='javascript:window.history.go(-1);'>返回操作</a>");
		
		//生成新的子批号
		SmallnessBatchNumber current = new SmallnessBatchNumber();
		current.setSmallnessBatchNumber("HB-"+System.currentTimeMillis());
		current.setOrderType(sbn.getOrderType());
		current.setPlace(sbn.getPlace());
		current.setOtherName(sbn.getOtherName());
		current.setAmount(account);
		current.setDateTime(new Date());
		current.setPrecedence(sbn.getPrecedence());
		//设置 小批号的 状态为 2合并
		current.setIsRemakes(2);
		current.setBoxNumber(sbn.getBoxNumber());
		current.setMakeStatus(sbn.getMakeStatus());
		//设置流程 状态 0默认1未发 2已发 3补制中 4整批重制
		current.setIsDistribute(1);
		current = smallnessBatchNumberDao.add(current);
		
		//循环往合并表 添加数据
		for (int i = 0; i < mergers.length; i++) {
			sbn = smallnessBatchNumberDao
					.find(new SmallnessBatchNumber(mergers[i]));
			
			//将合并的车牌 与 新生成的子批号对应...
			List<NumberPlate> nps = numberPlateDao.findBySmallnessBatchId(sbn.getId());
			//重新生成车牌,将其对应到小批号
			for(NumberPlate n : nps) {
				NumberPlate np = new NumberPlate();
				np.setBigBatchNumber(n.getBigBatchNumber());
				np.setSmallnessBatchNumber(current);
				np.setBusinessDepartment(n.getBusinessDepartment());
				np.setLicensePlateNumber(n.getLicensePlateNumber());
				np.setNumberPlateType(n.getNumberPlateType());
				np.setOrderNumber(n.getOrderNumber());
				numberPlateDao.add(np);
			}
			
			//往合并表里添加数据
			BatchNumberMerge bnm = new BatchNumberMerge();
			bnm.setSmallnessBatchNumber(current);
			bnm.setBigBatchNumber(sbn.getBigBatchNumber());
			batchNumberMergeDao.add(bnm);
		}
	} 
	
	/**
	 * 查询是否发货
	 * @param date
	 * @param place
	 * @param isDeliverGoods
	 * @return
	 */
	public List<SmallnessBatchNumber> findIsDeliverGoods(String date, String place, int isDeliverGoods) {
		String sql = "select s from SmallnessBatchNumber s where s.makeStatus = 2 and s.isValid = 0 ";
		Date findDate = null;
		Date nextDate = null;
		
		if(date != null && !date.trim().equals("")) {
			try {
				findDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
				sql += " and s.dateTime > :findDate and s.dateTime < :nextDate ";
				
				Calendar c = Calendar.getInstance();
				c.setTime(findDate);
				c.set(Calendar.DATE,c.get(Calendar.DATE) + 1);
				
				nextDate = c.getTime();
				
			} catch (ParseException e) {
				throw new ChePaiException("时间类型不对<br/><a href='javascript:window.history.go(-1);'>返回操作</a>");
			}
		}
		
		if(place != null && !"".equals(place)) {
			sql += " and s.place = :place";
		}
		if(isDeliverGoods == 0){
			sql += " and s.isDeliverGoods = 1 order by s.precedence, s.dateTime ";
		} else {
			sql += " and s.isDeliverGoods = :isDeliverGoods order by s.precedence, s.dateTime ";
		}
		
		return smallnessBatchNumberDao.findIsDeliverGoods(sql, findDate, nextDate, place, isDeliverGoods);
	}

	/**
	 * 修改子批号跟 大批号的 发货状态
	 * @param mergers 子批号
	 * @param state 1为修改成发货状态，2为取消发货状态，并去掉发货时间
	 */
	
	public int changeIsDeliverGoods(int[] mergers,int state) {
		
		if(mergers == null || mergers.length == 0) throw new ChePaiException("请选择要发货的批号<br/><a href='javascript:window.history.go(-1);'>返回操作</a>");
		
		int bigBatchNumber = 0;
		//更改子批号的发货状态
		for(int i=0; i<mergers.length; i++) {
			SmallnessBatchNumber sbn = smallnessBatchNumberDao.find(new SmallnessBatchNumber(mergers[i]));
			sbn.setIsDeliverGoods(state);
			if(state==1){
				sbn.setSendTime(null);
			}else{
				sbn.setSendTime(new Date());
			}
			smallnessBatchNumberDao.modify(sbn);
			bigBatchNumber = sbn.getBigBatchNumber().getId();
		}
		long count = smallnessBatchNumberDao.isDeliverGoodsCount(bigBatchNumber);
		if(count <= 0) {
			BigBatchNumber temp = bigBatchNumberDao.find(new BigBatchNumber(bigBatchNumber));
			temp.setIsDeliverGoods(state);
			if(state==1){
				temp.setSendTime(null);
			}else{
				temp.setSendTime(new Date());
			}
			bigBatchNumberDao.modify(temp);
		}
		
		return bigBatchNumber;
	}
	
	/**
	 * 修改小批号和大批号的发货时间
	 * @param smallBatch 需要修改的小批号
	 * @param date 修改后的时间
	 * @return
	 */
	public int modifySendTime(int smallBatch,Date sendTime){
		int bigBatchNumber = 0;
		SmallnessBatchNumber sbn = smallnessBatchNumberDao.find(new SmallnessBatchNumber(smallBatch));
		sbn.setSendTime(sendTime);
		smallnessBatchNumberDao.modify(sbn);
		bigBatchNumber = sbn.getBigBatchNumber().getId();
		BigBatchNumber temp = bigBatchNumberDao.find(new BigBatchNumber(bigBatchNumber));
		temp.setSendTime(sendTime);
		bigBatchNumberDao.modify(temp);
		return bigBatchNumber;
	}


	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<SmallnessBatchNumber> findIsDeliverByBig(int bigBatchNumberId, Page page) {
		return smallnessBatchNumberDao.findIsDeliverByBig(bigBatchNumberId, page);
	}
	
	public List<SmallnessBatchNumber> findByBig(int bigBatchNumberId){
		return smallnessBatchNumberDao.findIsDeliverByBig(bigBatchNumberId);
	}
	
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<SmallnessBatchNumber> findIsFaHuo(String plateNo) {
		List<NumberPlate> numberPlates = numberPlateDao.findIsFaHuo(plateNo.trim());
		if(numberPlates == null) throw new ChePaiException("没有该车牌号码,确定输入无误?<br/><a href='javascript:window.history.go(-1);'>返回操作</a>");
		
		List<SmallnessBatchNumber> smalls = null;
		//如果是正常的车牌号码
		if(numberPlates.size() == 1) {
			smalls = new ArrayList<SmallnessBatchNumber>();
			smalls.add(numberPlates.get(0).getSmallnessBatchNumber());
			return smalls;
		} else {
			smalls = new ArrayList<SmallnessBatchNumber>();
			for(NumberPlate n : numberPlates) {
				if(n.getSmallnessBatchNumber().getBigBatchNumber() != null) {
					smalls.add(n.getSmallnessBatchNumber());
				}
			}
			return smalls;
		}
	}
	
	
	public List<Operator> findFlowOperator(int smallnessBatchNumberId) {
		List<Operator> operator = operatorDao.findBySmallBatchId(smallnessBatchNumberId);
		if(operator == null || operator.size() == 0) {
			SmallnessBatchNumber s = smallnessBatchNumberDao.find(new SmallnessBatchNumber(smallnessBatchNumberId));
			SmallnessBatchNumber temp = null;
			if(s.getBigBatchNumber() != null)
				temp = batchNumberMergeDao.findByBigBatch(s.getBigBatchNumber().getId());
			if(temp != null) {
				int smallId = batchNumberMergeDao.findByBigBatch(s.getBigBatchNumber().getId()).getId();
				operator = operatorDao.findBySmallBatchId(smallId);
			}
		}
		for(Operator o : operator) {
			List<Users> users = new ArrayList<Users>();
			String[] userIds = o.getOperater().split(",");
			for(String uid : userIds) {
				Users u = userDao.find(new Users(Integer.parseInt(uid)));
				users.add(u);
			}
			o.setUsers(users);
		}
		return operator;
	}
	public List<SmallnessBatchNumber> findHeBin(Page page) {
		return smallnessBatchNumberDao.findHeBin(page);
	}
	
	
	public List<SmallnessBatchNumber> findFlowByBig(int bigBatchNumberId, Page page) {
		return smallnessBatchNumberDao.findFlowByBig(bigBatchNumberId, page);
	}
	
	/**
	 * 查询箱数
	 * @param statu 0返回总数，1返回未发货数量,2返回未完成数量
	 * @return
	 */
	public int CountSmall(int statu,int id){
		String hql = "select count(*) from SmallnessBatchNumber sn where sn.bigBatchNumber.id="+id;
		if(statu==1){
			hql += " and sn.isDeliverGoods <> 2";
		}else if(statu==2){
			hql += " and sn.isDeliverGoods = 0";
		}
		
		return smallnessBatchNumberDao.countSmall(hql);
	}
	
	
	public SmallnessBatchNumber findSmallnessBatchNumberbyBid(int bid){
		return  smallnessBatchNumberDao.findBybigBatchNumber(bid).get(0);
	}
	
	/**
	 * 修改箱号
	 * @param smallid 小批号ID
	 * @param boxnumber 箱号
	 * @param boxType 箱号颜色
	 * @return
	 */
	public boolean modifyBox(int smallid,int boxnumber,int boxType){
		try {
			if(this.findByBoxNumber(boxnumber, boxType) > 0){
				return false;
			}
			SmallnessBatchNumber small = this.findById(smallid);
			small.setBoxNumber(boxnumber);
			small.setBoxNumberType(boxType);
			smallnessBatchNumberDao.modify(small);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
