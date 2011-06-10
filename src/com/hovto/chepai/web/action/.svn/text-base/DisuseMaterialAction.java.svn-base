package com.hovto.chepai.web.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.DisuseMaterial;
import com.hovto.chepai.model.FlowType;
import com.hovto.chepai.model.NumberPlate;
import com.hovto.chepai.model.NumberPlateType;
import com.hovto.chepai.model.SemifinishedProductType;
import com.hovto.chepai.model.Stats;
import com.hovto.chepai.model.TaskAllocation;
import com.hovto.chepai.model.Users;
import com.hovto.chepai.server.DisuseMaterialServer;
import com.hovto.chepai.server.FlowTypeServer;
import com.hovto.chepai.server.NumberPlateServer;
import com.hovto.chepai.server.NumberPlateTypeServer;
import com.hovto.chepai.server.SemifinishedProductTypeServer;
import com.hovto.chepai.server.TaskAllocationServer;
import com.hovto.chepai.server.UserServer;
import com.hovto.chepai.tool.Page;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class DisuseMaterialAction {
	@Resource
	private DisuseMaterialServer disuseMaterialServer;
	@Resource
	private NumberPlateServer numberPlateServer;
	@Resource
	private UserServer userServer;
	@Resource
	private FlowTypeServer flowTypeServer;
	@Resource
	private TaskAllocationServer taskAllocationServer;
	@Resource
	private NumberPlateTypeServer numberPlateTypeServer;
	@Resource
	private SemifinishedProductTypeServer semifinishedProductTypeServer;
	
	private int[] numberPlateIds;
	private int marType;
	private DisuseMaterial disuseMaterial;
	
	private List<DisuseMaterial> disuseMaterialList;
	private List<SemifinishedProductType> semifinishedProductTypes;
	private List<Stats> statses;
	private int userId;
	private int flowTypeId;
	private String biginTime;
	private String endTime;
	private List<Users> users;
	private List<FlowType> flowTypes;
	private List<NumberPlateType> numberPlateTypes;
	public int smallnessBatchNumberId;
	public List<DisuseMaterial> dmList;
	private int amount;
	private int plateType;
	private Page page;
	private int[] frontNumberIds;//出错前牌号
	private int[] offsideNumberIds;//出错后牌号
	
	/**
	 * 反压记录 废牌
	 * 
	 * @return
	 */
	public String saveFeeNumberId(){
		try {
			TaskAllocation taskAllocation= taskAllocationServer.findBySBatchNumberAndFlowType(smallnessBatchNumberId, flowTypeId);
			
			List<SemifinishedProductType> frontSP=semifinishedProductTypeServer.findByType(2);
			Map<Integer,Integer> frontMap=new HashMap<Integer,Integer>();
			for(int i=0;i<frontSP.size();i++){
				for(int j=0;j<frontSP.get(i).getNumberPlateTypes().size();j++){
					frontMap.put(frontSP.get(i).getNumberPlateTypes().get(j).getId(), frontSP.get(i).getId());
				}
			}
			
			List<SemifinishedProductType> offsideSP=semifinishedProductTypeServer.findByType(1);
			Map<Integer,Integer> offsideMap=new HashMap<Integer,Integer>();
			for(int i=0;i<offsideSP.size();i++){
				for(int j=0;j<offsideSP.get(i).getNumberPlateTypes().size();j++){
					offsideMap.put(offsideSP.get(i).getNumberPlateTypes().get(j).getId(), offsideSP.get(i).getId());
				}
			}
			
			//添加反压前牌费牌记录
			this.addFor(frontNumberIds,frontMap,offsideMap, taskAllocation);
			//添加反压后牌费牌记录 
			this.addFor(offsideNumberIds, offsideMap,frontMap, taskAllocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String url = "废牌添加成功!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId;
		if(plateType != 0) 
			url += "&plateType=" + plateType;
		url += "'>返回操作</a>";
		ActionContext.getContext().put("message", url);
		return "message";
	}
	
	
	public void addFor(int[] numberIds,Map<Integer,Integer> map,Map<Integer,Integer> mapElse,TaskAllocation taskAllocation){
		//记录错误前牌
		if(numberIds!=null){
			for(int i=0;i<numberIds.length;i++){
				NumberPlate numberPlate=numberPlateServer.findById(numberIds[i]);
				DisuseMaterial disuseMaterial=new DisuseMaterial();
				disuseMaterial.setBigBatchNumber(numberPlate.getSmallnessBatchNumber().getBigBatchNumber());
				disuseMaterial.setSmallnessBatchNumber(numberPlate.getSmallnessBatchNumber());
				disuseMaterial.setNumberPlate(numberPlate);
				disuseMaterial.setNumberPlateType(numberPlate.getNumberPlateType());
				disuseMaterial.setOperateTime(new Date());
				disuseMaterial.setFlowType(taskAllocation.getFlowType());
				disuseMaterial.setMarType(marType);
				disuseMaterial.setOperator(taskAllocation.getOperator());
				
				if(taskAllocation.getOperator() != null) {
					Users u = userServer.find(new Users(Integer.parseInt(taskAllocation.getOperator().split(",")[0])));
					disuseMaterial.setDayNight(u.getDayNight());
				}
				
				//判断 是否 包含 前牌或后牌
				if(map.containsKey(numberPlate.getNumberPlateType().getId())) {
					disuseMaterial.setSemifinishedProductType(new SemifinishedProductType(map.get(numberPlate.getNumberPlateType().getId())));
				} else if(mapElse.containsKey(numberPlate.getNumberPlateType().getId())) {
					disuseMaterial.setSemifinishedProductType(new SemifinishedProductType(mapElse.get(numberPlate.getNumberPlateType().getId())));
				}
				
				disuseMaterialServer.add(disuseMaterial);
			}
		}
	}
	public String findUsersAll(){
		users=userServer.findAll();
		this.findFlowTypeAll();
		return "list";
	}
	public void findFlowTypeAll(){
		flowTypes=flowTypeServer.findList();
	}
	
	public String findByFlowType(){
		if(biginTime==null || biginTime.trim().equals("") ){
			throw new ChePaiException("请选择开始日期！<a href='DisuseMaterial!findUsersAll.action'>返回</a>");
		}
		try {
			this.findUsersAll();
			if(page == null) page = new Page();
			disuseMaterialList=disuseMaterialServer.findByFolwTypeOutput(flowTypeId, userId, biginTime, endTime,page);
			for(int i=0;i<disuseMaterialList.size();i++){
				String userName=this.loadUserName(disuseMaterialList.get(i).getOperator());
				disuseMaterialList.get(i).setOperator(userName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	//总统计
	public String ledgerStats(){
		disuseMaterialServer.ledgerStats(biginTime, endTime);
		return "findOutputStatsInput";
	}
	public String save(){
		try {
			if(numberPlateIds.length>0){
				TaskAllocation taskAllocation= taskAllocationServer.findBySBatchNumberAndFlowType(smallnessBatchNumberId, flowTypeId);
				for(int i=0;i<numberPlateIds.length;i++){
					NumberPlate numberPlate=numberPlateServer.findById(numberPlateIds[i]);
					DisuseMaterial disuseMaterial=new DisuseMaterial();
					disuseMaterial.setBigBatchNumber(numberPlate.getSmallnessBatchNumber().getBigBatchNumber());
					disuseMaterial.setSmallnessBatchNumber(numberPlate.getSmallnessBatchNumber());
					disuseMaterial.setNumberPlate(numberPlate);
					disuseMaterial.setNumberPlateType(numberPlate.getNumberPlateType());
					disuseMaterial.setOperateTime(new Date());
					disuseMaterial.setFlowType(taskAllocation.getFlowType());
					disuseMaterial.setOperator(taskAllocation.getOperator());
					disuseMaterial.setMarType(marType);
					disuseMaterialServer.add(disuseMaterial);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ActionContext.getContext().put("message", "废牌添加成功!<br/><a href='WorkFlow!list.action?flowTypeId=" + flowTypeId + "'>返回操作</a>");
		return "message";
	}
	
	//获取用户名称
	public String loadUserName(String ids){
		String userName="";
		if(ids!=null&&!"".equals(ids)){
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
		}
		return userName;
	}
	
	public String stats() {
		if(biginTime != null && endTime != null)
			statses = disuseMaterialServer.stats(biginTime, endTime);
		return "stats";
	}

	public String addInput() {
		numberPlateTypes = numberPlateTypeServer.findAll();
		semifinishedProductTypes = semifinishedProductTypeServer.findList();
		return "addInput";
	}
	
	public String add() {
		if(biginTime == null || biginTime.equals("")) throw new ChePaiException("时间不允许为空!");
		else {
			try {
				disuseMaterial.setOperateTime(new SimpleDateFormat("yyyy-MM-dd").parse(biginTime));
			} catch (ParseException e) {
				throw new ChePaiException("时间格式不正确!");
			}
		}
		
		if(amount == 0) throw new ChePaiException("添加数量不允许为空!<br/><a href='javascript:window.history.go(-1);'>返回操作</a>");
		
		for(int i=0; i<amount; i++) {
			DisuseMaterial dm = new DisuseMaterial();
			dm.setNumberPlateType(disuseMaterial.getNumberPlateType());
			dm.setSemifinishedProductType(disuseMaterial.getSemifinishedProductType());
			dm.setOperateTime(disuseMaterial.getOperateTime());
			dm.setMarType(disuseMaterial.getMarType());
			disuseMaterialServer.add(dm);
		}
		
		ActionContext.getContext().put("message", "废牌添加成功!<br/><a href='javascript:window.history.go(-1);'>返回操作</a>");
		return "message";
	}
	
	public int[] getNumberPlateIds() {
		return numberPlateIds;
	}

	public void setNumberPlateIds(int[] numberPlateIds) {
		this.numberPlateIds = numberPlateIds;
	}

	public int getMarType() {
		return marType;
	}

	public void setMarType(int marType) {
		this.marType = marType;
	}

	public List<DisuseMaterial> getDisuseMaterialList() {
		return disuseMaterialList;
	}

	public void setDisuseMaterialList(List<DisuseMaterial> disuseMaterialList) {
		this.disuseMaterialList = disuseMaterialList;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getFlowTypeId() {
		return flowTypeId;
	}

	public void setFlowTypeId(int flowTypeId) {
		this.flowTypeId = flowTypeId;
	}

	public String getBiginTime() {
		return biginTime;
	}

	public void setBiginTime(String biginTime) {
		this.biginTime = biginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

	public List<FlowType> getFlowTypes() {
		return flowTypes;
	}

	public void setFlowTypes(List<FlowType> flowTypes) {
		this.flowTypes = flowTypes;
	}
	public DisuseMaterial getDisuseMaterial() {
		return disuseMaterial;
	}
	public void setDisuseMaterial(DisuseMaterial disuseMaterial) {
		this.disuseMaterial = disuseMaterial;
	}
	public List<Stats> getStatses() {
		return statses;
	}
	public void setStatses(List<Stats> statses) {
		this.statses = statses;
	}
	public int getSmallnessBatchNumberId() {
		return smallnessBatchNumberId;
	}
	public void setSmallnessBatchNumberId(int smallnessBatchNumberId) {
		this.smallnessBatchNumberId = smallnessBatchNumberId;
	}
	public List<NumberPlateType> getNumberPlateTypes() {
		return numberPlateTypes;
	}
	public void setNumberPlateTypes(List<NumberPlateType> numberPlateTypes) {
		this.numberPlateTypes = numberPlateTypes;
	}
		public List<DisuseMaterial> getDmList() {
		return dmList;
	}
	public void setDmList(List<DisuseMaterial> dmList) {
		this.dmList = dmList;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int[] getFrontNumberIds() {
		return frontNumberIds;
	}
	public void setFrontNumberIds(int[] frontNumberIds) {
		this.frontNumberIds = frontNumberIds;
	}
	public int[] getOffsideNumberIds() {
		return offsideNumberIds;
	}
	public void setOffsideNumberIds(int[] offsideNumberIds) {
		this.offsideNumberIds = offsideNumberIds;
	}


	public List<SemifinishedProductType> getSemifinishedProductTypes() {
		return semifinishedProductTypes;
	}


	public void setSemifinishedProductTypes(
			List<SemifinishedProductType> semifinishedProductTypes) {
		this.semifinishedProductTypes = semifinishedProductTypes;
	}


	public int getPlateType() {
		return plateType;
	}


	public void setPlateType(int plateType) {
		this.plateType = plateType;
	}


	public Page getPage() {
		return page;
	}


	public void setPage(Page page) {
		this.page = page;
	}
	
}
