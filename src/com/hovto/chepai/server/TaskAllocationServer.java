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
	 * 根据用户id和所在流程查询出他分配的条数
	 * @param userId
	 * @param flowTypeId
	 * @return
	 */
	public long findByStatus(String userId,int flowTypeId){
		return taskAllocationDao.findByStatus(userId,flowTypeId);
	}
	/**
	 * 根据用户 Id查询 用户未完成的任务
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
	 * 修改
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
	 * 根据小批号和流程类型查询全部分配情况
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
	 * 根据小批号和流程类型查询出未完成的分配情况
	 * @param sBatchNumberId
	 * @param flowTypeId
	 * @return
	 */
	public List<TaskAllocation> findBySBatchNumberAndFlowTypeAndStatus(int sBatchNumberId,int flowTypeId,int status){
		return taskAllocationDao.findBySBatchNumberAndFlowTypeAndStatus(sBatchNumberId, flowTypeId,status);
	}
	/**
	 * 添加 任务分配
	 * @param userId 操作人id
	 * @param smallnessBatchNumber 子批号对象
	 * @return
	 */
	public boolean addTaskAllocation(String userId,SmallnessBatchNumber smallnessBatchNumber){
		try {
			if(smallnessBatchNumber.getIsDistribute()==2){
				//修改小批号的任务发放状态
				smallnessBatchNumberDao.modify(smallnessBatchNumber);
				
				TaskAllocation taskAllocation=new TaskAllocation();
				taskAllocation.setOperator(userId);
				taskAllocation.setSmallnessBatchNumber(smallnessBatchNumber);
				taskAllocation.setStatus(0);
				taskAllocation.setAllocationTime(new Date());
				List<WorkFlow> list=workFlowDao.findBySbatchNumberId(smallnessBatchNumber.getId());
				if(list.size()>0){
					taskAllocation.setFlowType(new FlowType((list.get(0)).getCurrentFlow().getId()));
					taskAllocationDao.add(taskAllocation);//添加操作人
					
					//修改半成品数量materialServer.findByNumberPlateType(smallnessBatchNumber.getId);
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 给对象分配任务
	 * @param userId 操作人
	 * @param sum 可分配批数
	 * @return
	 */
	public boolean save(String userId,int sum,int workFlowId, int machine){
		try {
			TaskInstall taskInstall = taskInstallDao.find(workFlowId);
			if(sum > taskInstall.getNumber()) sum = taskInstall.getNumber();
			if(sum == 0) sum = taskInstall.getDefaultNumber();
			
		    Map<Integer,SmallnessBatchNumber> mapTest = new HashMap<Integer, SmallnessBatchNumber>();
		    
		    if(workFlowId == 6 && machine == 4) {  //如果是打码...并且是四号机子
		    	//根据 流程id 查询 未发合并 跟补牌 的流程号 非补制类型
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
		    } else if(workFlowId == 6 && machine < 4) { //如果是打码,不是 四号机子
		    	List<WorkFlow> workList = workFlowDao.queryByOrderType(workFlowId, 2, sum);
				for(WorkFlow w : workList) {
					w.getSmallnessBatchNumber().setIsDistribute(2);
		    		mapTest.put(w.getSmallnessBatchNumber().getId(), w.getSmallnessBatchNumber());
		    	}
		    } else {
			    //根据 流程id 查询 未发合并 跟补牌 的流程号 非补制类型
				List<WorkFlow> workList=workFlowDao.queryHB(workFlowId);
			    if(workList.size()>0){
			    	SmallnessBatchNumber sbn = workList.get(0).getSmallnessBatchNumber();
			    	sbn.setIsDistribute(2);
	//		    	sum=sum-1;
			    	mapTest.put(sbn.getId(),sbn );
	//		    	this.addTaskAllocation(userId, workList.get(0).getSmallnessBatchNumber());
			    }
			    
			    //查询出该流程下的待发车牌
				List<WorkFlow> resultFlow=workFlowDao.queryByFlowTypeAndPrecedence(workFlowId, sum);
				int resultLength=resultFlow.size();
	//			if(resultLength>sum){//判断待批结果是否大于待分人数*
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
	 * 反压流程 给对象分配任务
	 * @param userId 操作人
	 * @param sum 可分配批数
	 * @return
	 */
	public boolean saveFanYa(String userId,int sum,int workFlowId, int plateType){
		try {
			TaskInstall taskInstall = taskInstallDao.find(workFlowId);
			if(sum > taskInstall.getNumber()) sum = taskInstall.getNumber();
			if(sum == 0) sum = taskInstall.getDefaultNumber();
			
			Map<Integer,SmallnessBatchNumber> mapTest = new HashMap<Integer, SmallnessBatchNumber>();
			
			if(plateType != 0) {  //如果是
				List<WorkFlow> workList=workFlowDao.queryPlateType(workFlowId, plateType, sum);
				for(WorkFlow w : workList) {
					w.getSmallnessBatchNumber().setIsDistribute(2);
		    		mapTest.put(w.getSmallnessBatchNumber().getId(), w.getSmallnessBatchNumber());
		    	}
			} else {
				//根据 流程id 查询 未发合并 跟补牌 的流程号 非补制类型
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
	 * 根据flowTypeId 查询 所有人
	 * @param flowTypeId
	 * @return
	 */
	public List<TaskAllocation> queryPrepareOpeartor(int flowTypeId) {
		return taskAllocationDao.queryPrepareOpeartor(flowTypeId);
	}
	
	
	public List<TaskAllocation> findPrepareWork(int flowTypeId,
			String[] operators, int index) {
		if(operators == null || operators.length != 2) throw new ChePaiException("请选择要交换任务的人"); 
		
		 List<TaskAllocation> taskAllocations = taskAllocationDao.findPrepareWork(flowTypeId,operators[index]);
		return taskAllocations;
	}
	
	
	
	public void changeTask(int[] tasks, int[] cTasks, String taskNames,
			String cTaskNames) {
		if(tasks == null && cTasks == null)
			throw new ChePaiException("交换任务不允许为空<a href='javascript:window.history.go(-1);'>返回操作</a>");
		
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
	 * 根据 流程Id 查询出 该流程未完成的人的 Id
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
	 * 根据 流程Id 查询出 该流程未完成的人的 Id
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
