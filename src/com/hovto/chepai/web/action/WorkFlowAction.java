package com.hovto.chepai.web.action;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.BigBatchNumber;
import com.hovto.chepai.model.FlowType;
import com.hovto.chepai.model.Operator;
import com.hovto.chepai.model.SmallnessBatchNumber;
import com.hovto.chepai.model.TaskAllocation;
import com.hovto.chepai.model.Users;
import com.hovto.chepai.model.WorkFlow;
import com.hovto.chepai.server.BigBatchNumberServer;
import com.hovto.chepai.server.SmallnessBatchNumberServer;
import com.hovto.chepai.server.TaskAllocationServer;
import com.hovto.chepai.server.UserServer;
import com.hovto.chepai.server.WorkFlowServer;
import com.hovto.chepai.tool.FlowTypeUtils;
import com.hovto.chepai.tool.Page;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class WorkFlowAction extends ActionSupport {

	@Resource
	private WorkFlowServer workFlowServer;
	@Resource
	private UserServer userServer;
	@Resource
	private TaskAllocationServer taskAllocationServer;
	@Resource
	private SmallnessBatchNumberServer smallnessBatchNumberServer;
	@Resource
	private BigBatchNumberServer bigBatchNumberServer;
	
	private Page page = new Page();
	private WorkFlow workFlow;
	private List<WorkFlow> workFlows;
	private List<Users> users;
	private int flowTypeId;
	private String date;
	private String place;
	public int[] smallnessBatchNumberIds;
	private String numberPlate;
	private String bigNo;
	private int plateType;
	private BigBatchNumber bigBatchNumber;
	private int boxnumber;
	private int smallnessBatchNumberId;
	@Resource
	private FlowTypeUtils flowTypeUtils;
	
	private SmallnessBatchNumber smallnessBatchNumber;
	private List<SmallnessBatchNumber> smallnessBatchNumbers;
	private List<FlowType> flowTypes;
	private int[] goFlows;
	private int[] mergers;
	private int isDeliverGoods;
	private int bigBatchNumberId;
	private int precedence;
	private List<TaskAllocation> taskAllocations;
	private List<TaskAllocation> changeTasks;
	private List<Operator> operatores;
	private String[] operators;
	private int state;
	private Date sendTime;
	private int countbox;
	private int notsendbox;
	private int notfinishedbox;
	private int[] bigBatchNumberIds;
	private int boxtype;
	
	


	/*	*//**
	 * 查询已经下发任务的流程
	 * @return
	 *//*
	public String list() {
		users = userServer.findByFlowType(flowTypeId);
		workFlows = workFlowServer.findOrderByUsers(flowTypeUtils.getFlowType(flowTypeId), page, plateType);


		for(int i=0;i<workFlows.size();i++){
			String userName=this.loadUserName(workFlows.get(i).getSmallnessBatchNumber().getOperator());
			workFlows.get(i).getSmallnessBatchNumber().setOperator(userName);
		}

		List<String> operators = taskAllocationServer.findByFlowType(flowTypeId);
		for(int i=0; i<operators.size(); i++) {
			for(int j=0; j<users.size(); j++) {
				
				if((operators.get(i).indexOf(users.get(j).getId() + "")) >= 0) {
					users.remove(j);
					j--;
				}
			}
		}
		return this.loadFlow();
	}*/
	/**
	 * 查询已经下发任务的流程
	 * @return
	 */
	public String list() {
		users = userServer.findByFlowType(flowTypeId);
		workFlows = workFlowServer.findOrderByUsers(flowTypeUtils.getFlowType(flowTypeId), page, plateType);
		
		for(int i=0;i<workFlows.size();i++){
			String userName=this.loadUserName(workFlows.get(i).getSmallnessBatchNumber().getOperator());
			workFlows.get(i).getSmallnessBatchNumber().setOperator(userName);
		}
		
		List<String> operators = taskAllocationServer.findByFlowType(flowTypeId);
		for(int i=0; i<operators.size(); i++) {
			for(int j=0; j<users.size(); j++) {
				if((operators.get(i).indexOf(",")) == -1){
					if(operators.get(i).equals(users.get(j).getId()+"")){
						users.remove(j);
						j--;
					}
				}
				else if((operators.get(i).indexOf(users.get(j).getId() + "")) >= 0) {
					users.remove(j);
					j--;
				}
			}
		}
		return this.loadFlow();
	}
	
	/**
	 * 获取用户名称
	 * @param ids
	 * @return
	 */
	public String loadUserName(String ids){
		List<Users> users = userServer.findAll();
		String userName="";
		String[] userIds=ids.split(",");
		Map map=new HashMap();
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
	
	/**
	 * 根据条件查询流程
	 * @return
	 */
	public String findByConditionInput() {
		return "findByConditionInput";
	}
	
	/**
	 * 根据条件查询流程
	 * @return
	 */
	public String findByCondition() {
		if((bigNo != null && !bigNo.trim().equals("")) || (numberPlate != null && !numberPlate.trim().equals(""))) {
			if(bigNo != null && !bigNo.trim().equals("")) {
				bigBatchNumber = bigBatchNumberServer.findByBigBatchNumber(bigNo);
				return "findByBigNo";
			} else {
				workFlows = workFlowServer.findByNumberPlate(numberPlate);
			}
		} else {
			if(flowTypeId == 0) workFlows = workFlowServer.findByCondition(date,flowTypeId,place,page);
			else workFlows = workFlowServer.findByCondition(date,flowTypeUtils.getFlowType(flowTypeId),place, page);
		}
		return "findByConditionInput";
	}
	
	/**
	 * 重新质检
	 */
	public String updateWorkFlow(){
		try {
			if(smallnessBatchNumberIds!=null){
				for(int i=0;i<smallnessBatchNumberIds.length;i++){
					//根据子批号查询出当前流程状态
					WorkFlow workFlow=workFlowServer.findBySbatchNumberId(smallnessBatchNumberIds[i]);
						workFlow.setCurrentFlow(new FlowType(5));
						workFlow.setNextFlow(new FlowType(7));
						workFlowServer.update(workFlow);//修改流程状态
						//查询出小批号分配的数据
						List<TaskAllocation> taskAllocationList=taskAllocationServer.findBySBatchNumberAndFlowTypeAndStatus(smallnessBatchNumberIds[i], flowTypeId,0);
						for(int j=0;j<taskAllocationList.size();j++){
							TaskAllocation taskAllocation=taskAllocationList.get(j);
							//修改任务分配的完成状态
							taskAllocation.setStatus(1);
							taskAllocationServer.update(taskAllocation);//修改任务完成情况
						}
						
					SmallnessBatchNumber smallnessBatchNumber=smallnessBatchNumberServer.findById(smallnessBatchNumberIds[i]);
					smallnessBatchNumber.setIsDistribute(2);
					smallnessBatchNumberServer.update(smallnessBatchNumber);//修改子批号任务分发状态
					
					//查询出在质检流程的操作人
					List<TaskAllocation> taskList=taskAllocationServer.findBySBatchNumberAndFlowTypeAndStatus(smallnessBatchNumberIds[i],5,1);
					if(taskList.size()>0){
						TaskAllocation tc=taskList.get(0);
						TaskAllocation taskAllocation=new TaskAllocation(tc.getSmallnessBatchNumber(),tc.getFlowType(),tc.getAllocationTime(),tc.getOperator(),0);
						taskAllocationServer.add(taskAllocation);//指定从新质检的人员
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.list();
	}
	
	/**
	 * 修改箱号
	 * @return
	 */
	public String modifyBox(){
		if(boxnumber==0){
			throw new ChePaiException("箱号不能为0！<a href='WorkFlow!findByConditionInput.action'>返回操作</a>");
		}
		if(boxtype!=1 && boxtype!=2 && boxtype!=3){
			throw new ChePaiException("请选择箱子颜色！<a href='WorkFlow!findByConditionInput.action'>返回操作</a>");
		}
		if(smallnessBatchNumberId ==0){
			throw new ChePaiException("未知的小批号！<a href='WorkFlow!findByConditionInput.action'>返回操作</a>");
		}
		if(!smallnessBatchNumberServer.modifyBox(smallnessBatchNumberId, boxnumber, boxtype)){
			throw new ChePaiException("修改失败！<a href='WorkFlow!findByConditionInput.action'>返回操作</a>");
		}
		ActionContext.getContext().put("message", "修改成功!<br/><a href='WorkFlow!findByConditionInput.action'>返回操作</a>");
		return "message";
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
	public WorkFlow getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(WorkFlow workFlow) {
		this.workFlow = workFlow;
	}
	
	public void findUsersAll(){
		users=userServer.findAll();
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
	public int getFlowTypeId() {
		return flowTypeId;
	}

	public void setFlowTypeId(int flowTypeId) {
		this.flowTypeId = flowTypeId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}

	public String getNumberPlate() {
		return numberPlate;
	}

	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}
	public int getPlateType() {
		return plateType;
	}

	public void setPlateType(int plateType) {
		this.plateType = plateType;
	}

	public String getBigNo() {
		return bigNo;
	}

	public void setBigNo(String bigNo) {
		this.bigNo = bigNo;
	}

	public BigBatchNumber getBigBatchNumber() {
		return bigBatchNumber;
	}

	public void setBigBatchNumber(BigBatchNumber bigBatchNumber) {
		this.bigBatchNumber = bigBatchNumber;
	}

	public int getBoxnumber() {
		return boxnumber;
	}

	public void setBoxnumber(int boxnumber) {
		this.boxnumber = boxnumber;
	}


	public int[] getSmallnessBatchNumberIds() {
		return smallnessBatchNumberIds;
	}

	public void setSmallnessBatchNumberIds(int[] smallnessBatchNumberIds) {
		this.smallnessBatchNumberIds = smallnessBatchNumberIds;
	}

	public int getSmallnessBatchNumberId() {
		return smallnessBatchNumberId;
	}

	public void setSmallnessBatchNumberId(int smallnessBatchNumberId) {
		this.smallnessBatchNumberId = smallnessBatchNumberId;
	}
	
	
	public BigBatchNumberServer getBigBatchNumberServer() {
			return bigBatchNumberServer;
		}

		public void setBigBatchNumberServer(BigBatchNumberServer bigBatchNumberServer) {
			this.bigBatchNumberServer = bigBatchNumberServer;
		}

		public FlowTypeUtils getFlowTypeUtils() {
			return flowTypeUtils;
		}

		public void setFlowTypeUtils(FlowTypeUtils flowTypeUtils) {
			this.flowTypeUtils = flowTypeUtils;
		}

		public SmallnessBatchNumber getSmallnessBatchNumber() {
			return smallnessBatchNumber;
		}

		public void setSmallnessBatchNumber(SmallnessBatchNumber smallnessBatchNumber) {
			this.smallnessBatchNumber = smallnessBatchNumber;
		}

		public List<SmallnessBatchNumber> getSmallnessBatchNumbers() {
			return smallnessBatchNumbers;
		}

		public void setSmallnessBatchNumbers(
				List<SmallnessBatchNumber> smallnessBatchNumbers) {
			this.smallnessBatchNumbers = smallnessBatchNumbers;
		}

		public List<FlowType> getFlowTypes() {
			return flowTypes;
		}

		public void setFlowTypes(List<FlowType> flowTypes) {
			this.flowTypes = flowTypes;
		}

		public int[] getGoFlows() {
			return goFlows;
		}

		public void setGoFlows(int[] goFlows) {
			this.goFlows = goFlows;
		}

		public int[] getMergers() {
			return mergers;
		}

		public void setMergers(int[] mergers) {
			this.mergers = mergers;
		}

		public int getIsDeliverGoods() {
			return isDeliverGoods;
		}

		public void setIsDeliverGoods(int isDeliverGoods) {
			this.isDeliverGoods = isDeliverGoods;
		}

		public int getBigBatchNumberId() {
			return bigBatchNumberId;
		}

		public void setBigBatchNumberId(int bigBatchNumberId) {
			this.bigBatchNumberId = bigBatchNumberId;
		}

		public int getPrecedence() {
			return precedence;
		}

		public void setPrecedence(int precedence) {
			this.precedence = precedence;
		}

		public List<TaskAllocation> getTaskAllocations() {
			return taskAllocations;
		}

		public void setTaskAllocations(List<TaskAllocation> taskAllocations) {
			this.taskAllocations = taskAllocations;
		}

		public List<TaskAllocation> getChangeTasks() {
			return changeTasks;
		}

		public void setChangeTasks(List<TaskAllocation> changeTasks) {
			this.changeTasks = changeTasks;
		}

		public List<Operator> getOperatores() {
			return operatores;
		}

		public void setOperatores(List<Operator> operatores) {
			this.operatores = operatores;
		}

		public String[] getOperators() {
			return operators;
		}

		public void setOperators(String[] operators) {
			this.operators = operators;
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public Date getSendTime() {
			return sendTime;
		}

		public void setSendTime(Date sendTime) {
			this.sendTime = sendTime;
		}

		public int getCountbox() {
			return countbox;
		}

		public void setCountbox(int countbox) {
			this.countbox = countbox;
		}

		public int getNotsendbox() {
			return notsendbox;
		}

		public void setNotsendbox(int notsendbox) {
			this.notsendbox = notsendbox;
		}

		public int getNotfinishedbox() {
			return notfinishedbox;
		}

		public void setNotfinishedbox(int notfinishedbox) {
			this.notfinishedbox = notfinishedbox;
		}

		public int[] getBigBatchNumberIds() {
			return bigBatchNumberIds;
		}

		public void setBigBatchNumberIds(int[] bigBatchNumberIds) {
			this.bigBatchNumberIds = bigBatchNumberIds;
		}

		public int getBoxtype() {
			return boxtype;
		}

		public void setBoxtype(int boxtype) {
			this.boxtype = boxtype;
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
