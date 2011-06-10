package com.hovto.chepai.server;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.SmallnessBatchNumberDao;
import com.hovto.chepai.dao.TaskAllocationDao;
import com.hovto.chepai.dao.TaskInstallDao;
import com.hovto.chepai.dao.WorkFlowDao;
import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.FlowType;
import com.hovto.chepai.model.SmallnessBatchNumber;
import com.hovto.chepai.model.TaskAllocation;
import com.hovto.chepai.model.TaskInstall;
import com.hovto.chepai.model.WorkFlow;

@Component
@Transactional
public class TaskAllocationServer {
	@Resource
	private TaskAllocationDao taskAllocationDao;
	@Resource
	private SmallnessBatchNumberDao smallnessBatchNumberDao;
	@Resource
	private WorkFlowDao workFlowDao;
	@Resource
	private TaskInstallDao taskInstallDao;
	
	/**
	 * �����û�id���������̲�ѯ�������������
	 * @param userId
	 * @param flowTypeId
	 * @return
	 */
	public long findByStatus(String userId,int flowTypeId){
		return taskAllocationDao.findByStatus(userId,flowTypeId);
	}
	/**
	 * �����û� Id��ѯ �û�δ��ɵ�����
	 * @param userId
	 * @return
	 */
	public long findByStatus(String userId){
		return taskAllocationDao.findByStatus(userId);
	}
	
	
	public void add(TaskAllocation taskAllocation){
		taskAllocationDao.add(taskAllocation);
	}
	
	public boolean delete(TaskAllocation taskAllocation){
		try {
			taskAllocationDao.delete(taskAllocation);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public List<TaskAllocation> findBySBatchNumberId(int smallnessBatchNumberId){
		return taskAllocationDao.findBySBatchNumberId(smallnessBatchNumberId);
	}
	
	public List<TaskAllocation> findBackRemake(int smallnessBatchNumberId){
		return taskAllocationDao.findBackRemake(smallnessBatchNumberId);
	}
	
	/**
	 * �޸�
	 * @param taskAllocation
	 * @return
	 */
	public boolean update(TaskAllocation taskAllocation){
		try {
			taskAllocationDao.modify(taskAllocation);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * ����С���ź��������Ͳ�ѯȫ���������
	 * @param sBatchNumberId
	 * @param flowTypeId
	 * @return
	 */
	public TaskAllocation findBySBatchNumberAndFlowType(int sBatchNumberId,int flowTypeId){
		TaskAllocation taskAllocation=null;
		try {
			List<TaskAllocation> taskList= taskAllocationDao.findBySBatchNumberAndFlowType(sBatchNumberId, flowTypeId);
			if(taskList.size()>0){
				taskAllocation=taskList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskAllocation;
	}
	public TaskAllocation queryBySBatchNumberAndFlowType(int sBatchNumberId,int flowTypeId){
		TaskAllocation taskAllocation=null;
		try {
			List<TaskAllocation> taskList= taskAllocationDao.queryBySBatchNumberAndFlowType(sBatchNumberId, flowTypeId);
			if(taskList.size()>0){
				taskAllocation=taskList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskAllocation;
	}
	/**
	 * ����С���ź��������Ͳ�ѯ��δ��ɵķ������
	 * @param sBatchNumberId
	 * @param flowTypeId
	 * @return
	 */
	public List<TaskAllocation> findBySBatchNumberAndFlowTypeAndStatus(int sBatchNumberId,int flowTypeId,int status){
		return taskAllocationDao.findBySBatchNumberAndFlowTypeAndStatus(sBatchNumberId, flowTypeId,status);
	}
	/**
	 * ��� �������
	 * @param userId ������id
	 * @param smallnessBatchNumber �����Ŷ���
	 * @return
	 */
	public boolean addTaskAllocation(String userId,SmallnessBatchNumber smallnessBatchNumber){
		try {
			if(smallnessBatchNumber.getIsDistribute()==2){
				//�޸�С���ŵ����񷢷�״̬
				smallnessBatchNumberDao.modify(smallnessBatchNumber);
				
				TaskAllocation taskAllocation=new TaskAllocation();
				taskAllocation.setOperator(userId);
				taskAllocation.setSmallnessBatchNumber(smallnessBatchNumber);
				taskAllocation.setStatus(0);
				taskAllocation.setAllocationTime(new Date());
				List<WorkFlow> list=workFlowDao.findBySbatchNumberId(smallnessBatchNumber.getId());
				if(list.size()>0){
					taskAllocation.setFlowType(new FlowType((list.get(0)).getCurrentFlow().getId()));
					taskAllocationDao.add(taskAllocation);//��Ӳ�����
					
					//�޸İ��Ʒ����materialServer.findByNumberPlateType(smallnessBatchNumber.getId);
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * �������������
	 * @param userId ������
	 * @param sum �ɷ�������
	 * @return
	 */
	public boolean save(String userId,int sum,int workFlowId, int machine){
		try {
			TaskInstall taskInstall = taskInstallDao.find(workFlowId);
			if(sum > taskInstall.getNumber()) sum = taskInstall.getNumber();
			if(sum == 0) sum = taskInstall.getDefaultNumber();
			
		    Map<Integer,SmallnessBatchNumber> mapTest = new HashMap<Integer, SmallnessBatchNumber>();
		    
		    if(workFlowId == 6 && machine == 4) {  //����Ǵ���...�������ĺŻ���
		    	//���� ����id ��ѯ δ���ϲ� ������ �����̺� �ǲ�������
				List<WorkFlow> workList=workFlowDao.queryByOrderType(workFlowId, 1, sum);
				for(WorkFlow w : workList) {
					w.getSmallnessBatchNumber().setIsDistribute(2);
		    		mapTest.put(w.getSmallnessBatchNumber().getId(), w.getSmallnessBatchNumber());
		    	}
				int result = sum - workList.size();
				if(result >= 0) {
					List<WorkFlow> others = workFlowDao.queryByOrderType(workFlowId, 2, result);
					for(WorkFlow w : others) {
						w.getSmallnessBatchNumber().setIsDistribute(2);
			    		mapTest.put(w.getSmallnessBatchNumber().getId(), w.getSmallnessBatchNumber());
			    	}
				}
		    } else if(workFlowId == 6 && machine < 4) { //����Ǵ���,���� �ĺŻ���
		    	List<WorkFlow> workList = workFlowDao.queryByOrderType(workFlowId, 2, sum);
				for(WorkFlow w : workList) {
					w.getSmallnessBatchNumber().setIsDistribute(2);
		    		mapTest.put(w.getSmallnessBatchNumber().getId(), w.getSmallnessBatchNumber());
		    	}
		    } else {
			    //���� ����id ��ѯ δ���ϲ� ������ �����̺� �ǲ�������
				List<WorkFlow> workList=workFlowDao.queryHB(workFlowId);
			    if(workList.size()>0){
			    	SmallnessBatchNumber sbn = workList.get(0).getSmallnessBatchNumber();
			    	sbn.setIsDistribute(2);
	//		    	sum=sum-1;
			    	mapTest.put(sbn.getId(),sbn );
	//		    	this.addTaskAllocation(userId, workList.get(0).getSmallnessBatchNumber());
			    }
			    
			    //��ѯ���������µĴ�������
				List<WorkFlow> resultFlow=workFlowDao.queryByFlowTypeAndPrecedence(workFlowId, sum);
				int resultLength=resultFlow.size();
	//			if(resultLength>sum){//�жϴ�������Ƿ���ڴ�������*
	//				resultLength=sum;
	//			}
				
				for(int i=0;i<resultLength;i++){
					SmallnessBatchNumber sBatchNumber=resultFlow.get(i).getSmallnessBatchNumber();
					if (mapTest.containsKey(sBatchNumber.getId())) {
						continue;
					}
					if (mapTest.size() == sum) {
						break;
					}
					sBatchNumber.setIsDistribute(2);
					mapTest.put(sBatchNumber.getId(), sBatchNumber);
					/*if(boxNumbers!=null&&!"".equals(boxNumbers)){
						sBatchNumber.setBoxNumber(Integer.parseInt(boxNumbers[i]));
					}*/
	//				if(sBatchNumber.getIsDistribute()==2)resultLength++;
	//				this.addTaskAllocation(userId, sBatchNumber);
				}
		    }
			
			for (Integer i : mapTest.keySet()) {
				this.addTaskAllocation(userId, mapTest.get(i));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * ��ѹ���� �������������
	 * @param userId ������
	 * @param sum �ɷ�������
	 * @return
	 */
	public boolean saveFanYa(String userId,int sum,int workFlowId, int plateType){
		try {
			TaskInstall taskInstall = taskInstallDao.find(workFlowId);
			if(sum > taskInstall.getNumber()) sum = taskInstall.getNumber();
			if(sum == 0) sum = taskInstall.getDefaultNumber();
			
			Map<Integer,SmallnessBatchNumber> mapTest = new HashMap<Integer, SmallnessBatchNumber>();
			
			if(plateType != 0) {  //�����
				List<WorkFlow> workList=workFlowDao.queryPlateType(workFlowId, plateType, sum);
				for(WorkFlow w : workList) {
					w.getSmallnessBatchNumber().setIsDistribute(2);
		    		mapTest.put(w.getSmallnessBatchNumber().getId(), w.getSmallnessBatchNumber());
		    	}
			} else {
				//���� ����id ��ѯ δ���ϲ� ������ �����̺� �ǲ�������
				List<WorkFlow> workList=workFlowDao.queryHB(workFlowId);
				if(workList.size()>0){
					SmallnessBatchNumber sbn = workList.get(0).getSmallnessBatchNumber();
					sbn.setIsDistribute(2);
					mapTest.put(sbn.getId(),sbn );
					sum = sum - 1;
				}
				
				List<WorkFlow> fanya = workFlowDao.queryFanYa(workFlowId,sum);
				for(WorkFlow w : fanya) {
					if (mapTest.containsKey(w.getSmallnessBatchNumber().getId())) {
						continue;
					}
					w.getSmallnessBatchNumber().setIsDistribute(2);
		    		mapTest.put(w.getSmallnessBatchNumber().getId(), w.getSmallnessBatchNumber());
		    	}
			}
			
			for (Integer i : mapTest.keySet()) {
				this.addTaskAllocation(userId, mapTest.get(i));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	
	/**
	 * ����flowTypeId ��ѯ ������
	 * @param flowTypeId
	 * @return
	 */
	public List<TaskAllocation> queryPrepareOpeartor(int flowTypeId) {
		return taskAllocationDao.queryPrepareOpeartor(flowTypeId);
	}
	
	
	public List<TaskAllocation> findPrepareWork(int flowTypeId,
			String[] operators, int index) {
		if(operators == null || operators.length != 2) throw new ChePaiException("��ѡ��Ҫ�����������"); 
		
		 List<TaskAllocation> taskAllocations = taskAllocationDao.findPrepareWork(flowTypeId,operators[index]);
		return taskAllocations;
	}
	
	
	
	public void changeTask(int[] tasks, int[] cTasks, String taskNames,
			String cTaskNames) {
		if(tasks == null && cTasks == null)
			throw new ChePaiException("������������Ϊ��<a href='javascript:window.history.go(-1);'>���ز���</a>");
		
		if(tasks != null) {
			for(int i=0; i<tasks.length; i++) {
				TaskAllocation t = taskAllocationDao.find(new TaskAllocation(tasks[i]));
				t.setOperator(cTaskNames);
				taskAllocationDao.modify(t);
			}
		}
		
		if(cTasks != null) {
			for(int i=0; i<cTasks.length; i++) {
				TaskAllocation t = taskAllocationDao.find(new TaskAllocation(cTasks[i]));
				t.setOperator(taskNames);
				taskAllocationDao.modify(t);
			}
		}
		
	}
	
	/**
	 * ���� ����Id ��ѯ�� ������δ��ɵ��˵� Id
	 * @param flowType
	 * @return
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<String> findByFlowType(int flowType) {
		String sql = "select t.operator from TaskAllocation t join SmallnessBatchNumber s on s.id = t.SmallnessBatchNumber where s.isDistribute <> 3 and s.isDistribute <> 5 and t.status = 0 ";
		if(flowType == 1 || flowType == 4)
			sql += " and (t.flowType = 1 or t.flowType = 4) ";
		else
			sql += " and t.flowType = ?";
		return taskAllocationDao.findByFlowType(sql, flowType);
	}
/*	*//**
	 * ���� ����Id ��ѯ�� ������δ��ɵ��˵� Id
	 * @param flowType
	 * @return
	 *//*
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<String> findByFlowType(int flowType) {
		String sql = "select t.operator from TaskAllocation t join SmallnessBatchNumber s on s.id = t.SmallnessBatchNumber where t.status = 0 ";
		if(flowType == 1 || flowType == 4)
			sql += " and t.flowType = 1 or t.flowType = 4 and t.status = 0";
		else
			sql += " and t.flowType = ?";
		sql += " and s.isDistribute <> 3";	
		return taskAllocationDao.findByFlowType(sql, flowType);
	}
*/	
	
	
	
	
	
	
	
	
	
	
}
