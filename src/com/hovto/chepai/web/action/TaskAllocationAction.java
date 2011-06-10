package com.hovto.chepai.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.Operator;
import com.hovto.chepai.model.Users;
import com.hovto.chepai.model.WorkFlow;
import com.hovto.chepai.server.SmallnessBatchNumberServer;
import com.hovto.chepai.server.TaskAllocationServer;
import com.hovto.chepai.server.UserServer;
import com.hovto.chepai.server.WorkFlowServer;
import com.hovto.chepai.tool.FlowTypeUtils;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class TaskAllocationAction {
	@Resource
	private TaskAllocationServer taskAllocationServer;
	@Resource
	private SmallnessBatchNumberServer smallnessBatchNumberServer;
	@Resource
	private UserServer userServer;
	@Resource
	private WorkFlowServer workFlowServer;
	@Resource
	private FlowTypeUtils flowTypeUtils;
	
	private String[] select;
	private int sum;
	private int flowTypeId;//流程id
	private String boxNumber;//箱号
	private int smallBatchId;
	private List<Operator> operators;
	private List<WorkFlow> workFlows;
	private List<Users> users;
	private int[] tasks;
	private int[] cTasks;
	private String taskNames;
	private String cTaskNames;
	private int machine;
	private int plateType;
	
	public String save(){
		try {
			/*
			 * if(flowTypeId==1){
				String result=this.boxNumberIsExist();//判断箱号是否以用
				if(result!=null&&!"".equals(result)){
					String[] results=result.split(",");
					if(results.length>0){
						for(int i=0;i<results.length;i++){
							message+=results[i]+"号箱";
						}
						message+="重复.";
					}
				}
			}*/
			
			//判断是否有用户还有车牌未完成
			for(int i=0;i<select.length;i++){
				if(select[i].equals("0")) continue;
				long result = 0L;
				//如果是正压或反压
				if(flowTypeId==1||flowTypeId==4){
					result = taskAllocationServer.findByStatus(select[i]);
				}else{
					result = taskAllocationServer.findByStatus(select[i],flowTypeId); //如果是其他流程
				}
				if(result > 0) {
					ActionContext.getContext().put("message","该员工还有未完成的任务,请重新选择!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>返回操作</a>");
					return "message";
				}
			}
			
			
			//如果用户没有任何任务, 设置好 operator
			String operater="";
			for(int i=0;i<select.length;i++){
				if(!select[i].equals("0")){
					if(operater.equals("")||operater==null){
						operater=select[i];
					}else{
						operater+=","+select[i];
					}
				}
			}
			/*taskAllocationServer.save(operater, sum,flowTypeId,boxNumber);*/
			//添加 任务分配
			if(flowTypeId == 1) {
				taskAllocationServer.saveFanYa(operater, sum, flowTypeId, plateType);
			} else {
				taskAllocationServer.save(operater, sum,flowTypeId,machine);
			}
			
			
			/*
			users = userServer.findByFlowType(flowTypeId);
			workFlows = workFlowServer.findList(flowTypeUtils.getFlowType(flowTypeId));
			for(int i=0;i<workFlows.size();i++){
				List<TaskAllocation> taskAllocationList=taskAllocationServer.findBySBatchNumberId(workFlows.get(i).getSmallnessBatchNumber().getId());
					if(taskAllocationList.size()>0){
						String userName=this.loadUserName(taskAllocationList.get(0).getOperator());
						workFlows.get(i).getSmallnessBatchNumber().setOperator(userName);
					}
				}
			*/
				
			String url = "任务分发成功!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId;
			if(plateType != 0) 
				url += "&plateType=" + plateType;
			url += "'>返回操作</a>";
			ActionContext.getContext().put("message", url);
			return "message";
		} catch (Exception e) {
			e.printStackTrace();
		}
		ActionContext.getContext().put("message", "任务分发失败!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>返回操作</a>");
		return "message";
	}
	
	
	public String loadUserName(String ids){
		String userName="";
		String[] userIds=ids.split(",");
		Map<Integer,String> map=new HashMap<Integer,String>();
		for(int i=0;i<users.size();i++){
			map.put(users.get(i).getId(), users.get(i).getName());
		}
		for(int i=0;i<userIds.length;i++){
			boolean is=map.containsKey(Integer.parseInt(userIds[i]));
			if(is){
				userName+=map.get(Integer.parseInt(userIds[i]))+",";
			}
		}
		if(userName.endsWith(",")){
			 return userName.substring(0, userName.length()-1);
			}
		return userName;
	}
	
	
	public String loadFlow(){
		if(flowTypeId==1){
			return "Counterpressure";
		}else if(flowTypeId==2){
			return "Shuffle";
		}else if(flowTypeId==3){
			return "Stitchingoil";
		}else if(flowTypeId==4){
			return "Barotropy";
		}else if(flowTypeId==5){
			return "Llnite";
		}else if(flowTypeId==6){
			return "Imprinter";
		}else if(flowTypeId==7){
			return "AlwaysLlnite";
		}else{
			return null;
		}
	}
	/**
	 * 根据用户id获取用户信息
	 * @param id
	 * @return
	 */
	public String loadUserName(int id){
		if(id > 0)
			return userServer.find(new Users(id)).getName();
		return null;
	}
	
	/**
	 * 判断箱号是否重复
	 * @return
	 
	public String boxNumberIsExist(){
		String[] boxNumbers=boxNumber.split(",");
		String result="";
			for(int i=0;i<boxNumbers.length;i++){
				List<SmallnessBatchNumber> sBatchNumberList=smallnessBatchNumberServer.findByBoxNumber(Integer.parseInt(boxNumbers[i].toString()));
				if(sBatchNumberList.size()>0){
					SmallnessBatchNumber smallnessBatchNumber=(SmallnessBatchNumber)sBatchNumberList.get(0);
					result+=smallnessBatchNumber.getBoxNumber()+",";
				}
			}
			if(result.endsWith(",")){
			 return result.substring(0, result.length()-1);
			}
		return result;
	}
	*/
	
	public String changeTask() {
		taskAllocationServer.changeTask(tasks,cTasks,taskNames,cTaskNames);
		ActionContext.getContext().put("message", "任务交换成功!<br/><a href='SmallnessBatchNumber!queryPrepareWork.action'>返回操作</a>");
		return "message";
	}
	
	
	
	public String[] getSelect() {
		return select;
	}
	public void setSelect(String[] select) {
		this.select = select;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public int getFlowTypeId() {
		return flowTypeId;
	}
	public void setFlowTypeId(int flowTypeId) {
		this.flowTypeId = flowTypeId;
	}
	public String getBoxNumber() {
		return boxNumber;
	}
	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}
	public int getSmallBatchId() {
		return smallBatchId;
	}
	public void setSmallBatchId(int smallBatchId) {
		this.smallBatchId = smallBatchId;
	}
	public List<Operator> getOperators() {
		return operators;
	}
	public void setOperators(List<Operator> operators) {
		this.operators = operators;
	}
	public List<WorkFlow> getWorkFlows() {
		return workFlows;
	}
	public void setWorkFlows(List<WorkFlow> workFlows) {
		this.workFlows = workFlows;
	}
	public List<Users> getUsers() {
		return users;
	}
	public void setUsers(List<Users> users) {
		this.users = users;
	}
	public int[] getTasks() {
		return tasks;
	}
	public void setTasks(int[] tasks) {
		this.tasks = tasks;
	}
	public int[] getCTasks() {
		return cTasks;
	}
	public void setCTasks(int[] tasks) {
		cTasks = tasks;
	}
	public String getTaskNames() {
		return taskNames;
	}
	public void setTaskNames(String taskNames) {
		this.taskNames = taskNames;
	}
	public String getCTaskNames() {
		return cTaskNames;
	}
	public void setCTaskNames(String taskNames) {
		cTaskNames = taskNames;
	}


	public int getMachine() {
		return machine;
	}


	public void setMachine(int machine) {
		this.machine = machine;
	}


	public int getPlateType() {
		return plateType;
	}


	public void setPlateType(int plateType) {
		this.plateType = plateType;
	}
}
