package com.hovto.chepai.web.action;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.FlowType;
import com.hovto.chepai.model.Operator;
import com.hovto.chepai.model.SmallnessBatchNumber;
import com.hovto.chepai.model.TaskAllocation;
import com.hovto.chepai.model.Users;
import com.hovto.chepai.server.FlowTypeServer;
import com.hovto.chepai.server.SmallnessBatchNumberServer;
import com.hovto.chepai.server.TaskAllocationServer;
import com.hovto.chepai.server.UserServer;
import com.hovto.chepai.tool.Page;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class SmallnessBatchNumberAction extends ActionSupport {

	@Resource
	private SmallnessBatchNumberServer smallnessBatchNumberServer;
	@Resource
	private FlowTypeServer flowTypeServer;
	@Resource
	private UserServer userServer;
	
	@Resource
	private TaskAllocationServer taskAllocationServer;
	private Page page = new Page();
	private SmallnessBatchNumber smallnessBatchNumber;
	private List<SmallnessBatchNumber> smallnessBatchNumbers;
	private List<FlowType> flowTypes;
	private int[] goFlows;
	private int[] mergers;
	private String date;
	private String place;
	private int isDeliverGoods;
	private int bigBatchNumberId;
	private int smallnessBatchNumberId;
	private int precedence;
	private int flowTypeId;
	private List<TaskAllocation> taskAllocations;
	private List<TaskAllocation> changeTasks;
	private List<Operator> operatores;
	private List<Users> users;
	private String[] operators;
	private int state;
	private Date sendTime;
	private int countbox;
	private int notsendbox;
	private int notfinishedbox;
	private int[] bigBatchNumberIds;
	private int boxnumber;
	private int boxtype;
	private String bigNo;
	private String numberPlate;







	public String upgradelevle(){
		SmallnessBatchNumber smallnessBatchNumber=smallnessBatchNumberServer.findById(smallnessBatchNumberId);
		if(smallnessBatchNumber!=null){
			if(smallnessBatchNumber.getPrecedence() > 1) {
				smallnessBatchNumber.setPrecedence(precedence-1);
				smallnessBatchNumberServer.update(smallnessBatchNumber);
			}
		}
		ActionContext.getContext().put("message", "批号升级成功!<br/><a href='WorkFlow!findByConditionInput.action'>返回操作</a>");
		return "message";
	}
	
	/**
	 * 查询需要下发的任务
	 * @return
	 */
	public String findByMakeStatus() {
		smallnessBatchNumbers = smallnessBatchNumberServer.findByMakeStatus(bigBatchNumberId, page);
		return "findByMakeStatus";
	}
	
	public String findHeBin() {
		smallnessBatchNumbers = smallnessBatchNumberServer.findHeBin(page);
		return "findHeBin";
	}
	
	public String goFlows() {
		smallnessBatchNumberServer.goFlows(goFlows);
		ActionContext.getContext().put("message", "任务下发成功!<br/><a href='SmallnessBatchNumber!findByMakeStatus.action?bigBatchNumberId=" + bigBatchNumberId + "'>返回操作</a>");
		return "message";
	}
	
	/**
	 * 查询可以合并的小批号
	 * @return
	 */
	public String findMerger() {
		smallnessBatchNumbers = smallnessBatchNumberServer.findMerger(page);
		return "findMerger";
	}
	
	/**
	 * 合并小批号
	 * @return
	 */
	public String merger() {
		smallnessBatchNumberServer.merger(mergers);
		ActionContext.getContext().put("message", "大批号合并成功!<br/><a href='SmallnessBatchNumber!findMerger.action'>返回操作</a>");
		return "message";
	}
	
	public String changeIsDeliverGoods() {
		if(!(state==1||state==2)){
			throw new ChePaiException("操作失败！");
		}
		int bigBatchNumber = smallnessBatchNumberServer.changeIsDeliverGoods(mergers,state);
		if(state==1)
			ActionContext.getContext().put("message", "取消发货状态成功!<br/><a href='SmallnessBatchNumber!findIsDeliverByBig.action?bigBatchNumberId=" + bigBatchNumber + "'>返回操作</a>");
		else if(state==2)
			ActionContext.getContext().put("message", "发货成功!<br/><a href='SmallnessBatchNumber!findIsDeliverByBig.action?bigBatchNumberId=" + bigBatchNumber + "'>返回操作</a>");
			
		return "message";
	}
	
	public String changeIsDeliverGoodsbyBigList(){
		for(int i=0;i<bigBatchNumberIds.length;i++){
			smallnessBatchNumbers = smallnessBatchNumberServer.findByBig(bigBatchNumberIds[i]);
			int[] smallid = new int[smallnessBatchNumbers.size()];
			for(int j=0;j<smallnessBatchNumbers.size();j++){
				smallid[j] = smallnessBatchNumbers.get(j).getId();
			}
			if(smallid.length!=0)
				smallnessBatchNumberServer.changeIsDeliverGoods(smallid,2);
		}
		ActionContext.getContext().put("message", "发货成功!<br/><a href='BigBatchNumber!findIsDeliverGoods.action'>返回操作</a>");
		return "message";
	}
	
	public String modifySendtime(){
		System.out.println(sendTime.toLocaleString());
		int bigBatchNumber = smallnessBatchNumberServer.modifySendTime(smallnessBatchNumber.getId(), sendTime);
		ActionContext.getContext().put("message", "修改成功!<br/><a href='SmallnessBatchNumber!findIsDeliverByBig.action?bigBatchNumberId=" + bigBatchNumber + "'>返回操作</a>");
		return "message";
	}
	/**
	 * 仓库
	 * @return
	 */
	public String findIsDeliverByBig() {
		countbox=smallnessBatchNumberServer.CountSmall(0,bigBatchNumberId);
		notsendbox=smallnessBatchNumberServer.CountSmall(1,bigBatchNumberId);
		notfinishedbox = smallnessBatchNumberServer.CountSmall(2,bigBatchNumberId);
		smallnessBatchNumbers = smallnessBatchNumberServer.findIsDeliverByBig(bigBatchNumberId,page);
		return "findIsDeliverByBig";
	}
	
	/**
	 * 查询统计
	 * @return
	 */
	public String selectIsDeliverByBig() {
		countbox=smallnessBatchNumberServer.CountSmall(0,bigBatchNumberId);
		notsendbox=smallnessBatchNumberServer.CountSmall(1,bigBatchNumberId);
		notfinishedbox = smallnessBatchNumberServer.CountSmall(2,bigBatchNumberId);
		smallnessBatchNumbers = smallnessBatchNumberServer.findIsDeliverByBig(bigBatchNumberId,page);
		return "selectIsDeliverByBig";
	}
	
	/**
	 * 任务调配查询
	 * @return
	 */
	public String queryPrepareWork() {
		flowTypes = flowTypeServer.findList();
		return "queryPrepareWork";
	}
	
	public String queryPrepareOpeartor() {
		flowTypes = flowTypeServer.findList();
		users = userServer.findByFlowType(flowTypeId);
		taskAllocations = taskAllocationServer.queryPrepareOpeartor(flowTypeId);
		for(int i=0; i<taskAllocations.size(); i++) {
			String names = this.loadUserName(taskAllocations.get(i).getOperator());
			taskAllocations.get(i).setNames(names);
		}
		return "queryPrepareWork";
	}
	
	public String findPrepareWork() {
		users = userServer.findByFlowType(flowTypeId);
		
		taskAllocations = taskAllocationServer.findPrepareWork(flowTypeId,operators,0);
		changeTasks = taskAllocationServer.findPrepareWork(flowTypeId,operators,1);
		
		if(taskAllocations != null && taskAllocations.size() != 0) {
			String names = this.loadUserName(taskAllocations.get(0).getOperator());
			taskAllocations.get(0).setNames(names);
		}
		
		if(changeTasks != null && changeTasks.size() != 0) {
			String names = this.loadUserName(changeTasks.get(0).getOperator());
			changeTasks.get(0).setNames(names);
		}
		return "findPrepareWork";
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
	
	//获取用户名称
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
	 * 查询历史记录，各个流程操作人
	 * @return
	 */
	public String findFlowOperator() {
		flowTypes = flowTypeServer.findList();
		operatores = smallnessBatchNumberServer.findFlowOperator(smallnessBatchNumberId);
		return "findFlowOperator";
	}
	
	public String findFlowByBig() {
		smallnessBatchNumbers = smallnessBatchNumberServer.findFlowByBig(bigBatchNumberId, page);
		return "findFlowByBig";
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
	public int getSmallnessBatchNumberId() {
		return smallnessBatchNumberId;
	}
	public void setSmallnessBatchNumberId(int smallnessBatchNumberId) {
		this.smallnessBatchNumberId = smallnessBatchNumberId;
	}
	public int getPrecedence() {
		return precedence;
	}
	public void setPrecedence(int precedence) {
		this.precedence = precedence;
	}
	public int getFlowTypeId() {
		return flowTypeId;
	}
	public void setFlowTypeId(int flowTypeId) {
		this.flowTypeId = flowTypeId;
	}
	public List<FlowType> getFlowTypes() {
		return flowTypes;
	}
	public void setFlowTypes(List<FlowType> flowTypes) {
		this.flowTypes = flowTypes;
	}
	public List<TaskAllocation> getTaskAllocations() {
		return taskAllocations;
	}
	public void setTaskAllocations(List<TaskAllocation> taskAllocations) {
		this.taskAllocations = taskAllocations;
	}
	public List<Users> getUsers() {
		return users;
	}
	public void setUsers(List<Users> users) {
		this.users = users;
	}
	public List<TaskAllocation> getChangeTasks() {
		return changeTasks;
	}
	public void setChangeTasks(List<TaskAllocation> changeTasks) {
		this.changeTasks = changeTasks;
	}
	public String[] getOperators() {
		return operators;
	}
	public void setOperators(String[] operators) {
		this.operators = operators;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<Operator> getOperatores() {
		return operatores;
	}

	public void setOperatores(List<Operator> operatores) {
		this.operatores = operatores;
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

	public int getBoxnumber() {
		return boxnumber;
	}

	public void setBoxnumber(int boxnumber) {
		this.boxnumber = boxnumber;
	}

	public int getBoxType() {
		return boxtype;
	}

	public void setBoxType(int boxType) {
		this.boxtype = boxType;
	}
	
	public String getBigNo() {
		return bigNo;
	}

	public void setBigNo(String bigNo) {
		this.bigNo = bigNo;
	}
	
	

	public String getNumberPlate() {
		return numberPlate;
	}

	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}
	
	
	
	
	
	
	
	
}
