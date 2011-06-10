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
	 * ��ѯ��Ҫ�·�������
	 * @param makeStatus ����״̬ Ĭ��0��δ����  1������ 2���
	 * @return
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<SmallnessBatchNumber> findByMakeStatus(int bigBatchNumberId, Page page) {
		return smallnessBatchNumberDao.findByMakeStatus(bigBatchNumberId,page);
	}
	/**
	 * ������Ų�ѯС���ż�¼
	 * @return
	 */
	public long findByBoxNumber(int boxNumber,int bType) {
		return smallnessBatchNumberDao.findByBoxNumber(boxNumber, bType);
	}
	/**
	 * �����·�
	 * @param goFlows �·���������
	 */
	public void goFlows(int[] goFlows) {
		if (goFlows != null && goFlows.length != 0) {
			for (int i = 0; i < goFlows.length; i++) {
				
				//�޸������ŵ�����״̬:0����1������2���
				SmallnessBatchNumber sbn = smallnessBatchNumberDao
						.find(new SmallnessBatchNumber(goFlows[i]));
				sbn.setMakeStatus(1);
				sbn.setIsDistribute(1);
				smallnessBatchNumberDao.modify(sbn);
				
				//�����̱��������һ������
				WorkFlow workFlow = new WorkFlow();
				workFlow.setSmallnessBatchNumber(sbn);
				workFlow.setBigBatchNumber(sbn.getBigBatchNumber());
				workFlow.setCurrentFlow(new FlowType(1));
				workFlow.setNextFlow(new FlowType(2));
				workFlowDao.add(workFlow);
			}
		} else 
			throw new ChePaiException("��ѡ��Ҫ�·�������!<br/><a href='javascript:window.history.go(-1);'>���ز���</a>");
	}

	/**
	 * ��ѯ���Ժϲ���С����
	 * @param page 
	 * @return
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<SmallnessBatchNumber> findMerger(Page page) {
		return smallnessBatchNumberDao.findMerger();
	}


	/**
	 * �ϲ�С����
	 * @param mergers С����id
	 */
	public void merger(int[] mergers) {
		if(mergers == null || mergers.length == 0) 
			throw new ChePaiException("��ѡ��Ҫ�ϲ���С����<br/><a href='javascript:window.history.go(-1);'>���ز���</a>");
		
		//�ϲ���ĳ���������
		int account = 0;
		//С�������� 1���ơ�2��ѡ��3�Ŷ�
		int orderType = 0;
		//����
		String place = null;
		SmallnessBatchNumber sbn = null;
		
		//ѭ���ҳ�Ҫ�ϲ���С����,�ж������Ƿ�����ͬһ����, �������, �����ǵ� �Ƿ��������Ϊ 1��Ч
		for (int i = 0; i < mergers.length; i++) {
			sbn = smallnessBatchNumberDao
					.find(new SmallnessBatchNumber(mergers[i]));
			if(i == 0) {
				orderType = sbn.getOrderType();
				place = sbn.getPlace();
			} else {
				if(sbn.getOrderType() != orderType || !place.equals(sbn.getPlace())) {
					throw new ChePaiException("����ͬһ�������߲���ͬһ�������͵����Ų��ܺϲ�<br/><a href='javascript:window.history.go(-1);'>���ز���</a>");
				} else {
					orderType = sbn.getOrderType();
					place = sbn.getPlace();
				}
			}
			//����С������Ч
			sbn.setIsValid(1);
			smallnessBatchNumberDao.modify(sbn);
			account += sbn.getBigBatchNumber().getAmount();
		}
		
		if(account >= 50) 
			throw new ChePaiException("�ϲ������������ܳ���50��!<br/><a href='javascript:window.history.go(-1);'>���ز���</a>");
		
		//�����µ�������
		SmallnessBatchNumber current = new SmallnessBatchNumber();
		current.setSmallnessBatchNumber("HB-"+System.currentTimeMillis());
		current.setOrderType(sbn.getOrderType());
		current.setPlace(sbn.getPlace());
		current.setOtherName(sbn.getOtherName());
		current.setAmount(account);
		current.setDateTime(new Date());
		current.setPrecedence(sbn.getPrecedence());
		//���� С���ŵ� ״̬Ϊ 2�ϲ�
		current.setIsRemakes(2);
		current.setBoxNumber(sbn.getBoxNumber());
		current.setMakeStatus(sbn.getMakeStatus());
		//�������� ״̬ 0Ĭ��1δ�� 2�ѷ� 3������ 4��������
		current.setIsDistribute(1);
		current = smallnessBatchNumberDao.add(current);
		
		//ѭ�����ϲ��� ��������
		for (int i = 0; i < mergers.length; i++) {
			sbn = smallnessBatchNumberDao
					.find(new SmallnessBatchNumber(mergers[i]));
			
			//���ϲ��ĳ��� �� �����ɵ������Ŷ�Ӧ...
			List<NumberPlate> nps = numberPlateDao.findBySmallnessBatchId(sbn.getId());
			//�������ɳ���,�����Ӧ��С����
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
			
			//���ϲ�������������
			BatchNumberMerge bnm = new BatchNumberMerge();
			bnm.setSmallnessBatchNumber(current);
			bnm.setBigBatchNumber(sbn.getBigBatchNumber());
			batchNumberMergeDao.add(bnm);
		}
	} 
	
	/**
	 * ��ѯ�Ƿ񷢻�
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
				throw new ChePaiException("ʱ�����Ͳ���<br/><a href='javascript:window.history.go(-1);'>���ز���</a>");
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
	 * �޸������Ÿ� �����ŵ� ����״̬
	 * @param mergers ������
	 * @param state 1Ϊ�޸ĳɷ���״̬��2Ϊȡ������״̬����ȥ������ʱ��
	 */
	
	public int changeIsDeliverGoods(int[] mergers,int state) {
		
		if(mergers == null || mergers.length == 0) throw new ChePaiException("��ѡ��Ҫ����������<br/><a href='javascript:window.history.go(-1);'>���ز���</a>");
		
		int bigBatchNumber = 0;
		//���������ŵķ���״̬
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
	 * �޸�С���źʹ����ŵķ���ʱ��
	 * @param smallBatch ��Ҫ�޸ĵ�С����
	 * @param date �޸ĺ��ʱ��
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
		if(numberPlates == null) throw new ChePaiException("û�иó��ƺ���,ȷ����������?<br/><a href='javascript:window.history.go(-1);'>���ز���</a>");
		
		List<SmallnessBatchNumber> smalls = null;
		//����������ĳ��ƺ���
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
	 * ��ѯ����
	 * @param statu 0����������1����δ��������,2����δ�������
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
	 * �޸����
	 * @param smallid С����ID
	 * @param boxnumber ���
	 * @param boxType �����ɫ
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