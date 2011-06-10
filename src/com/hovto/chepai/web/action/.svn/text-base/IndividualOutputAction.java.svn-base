package com.hovto.chepai.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.FlowType;
import com.hovto.chepai.model.IndividualOutput;
import com.hovto.chepai.model.OutputStats;
import com.hovto.chepai.model.Users;
import com.hovto.chepai.server.FlowTypeServer;
import com.hovto.chepai.server.IndividualOutputServer;
import com.hovto.chepai.server.UserServer;
@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class IndividualOutputAction {
	@Resource
	private IndividualOutputServer individualOutputServer;
	@Resource
	private UserServer userServer;
	@Resource
	private FlowTypeServer flowTypeServer;
	
	private List<IndividualOutput> outputList;
	private int userId;
	private int flowTypeId;
	private String biginTime;
	private String endTime;
	private List<Users> users;
	private List<FlowType> flowTypes;
	private List<OutputStats> outputStatses;
	
	public String findUsersAll(){
		users=userServer.findAll();
		this.findFlowTypeAll();
		return "list";
	}
	public void findFlowTypeAll(){
		flowTypes=flowTypeServer.findList();
	}
	public String outputStat(){
		try {
			outputList=individualOutputServer.findByFolwTypeOutput(flowTypeId, userId, biginTime, endTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.findUsersAll();
		return "list";
	}
	
	/**
	 * 产量统计
	 * @return
	 */
	public String findOutputStatsInput() {
		return "findOutputStatsInput";
	}
	public String findOutputStats() {
		outputStatses = individualOutputServer.findOutputStats(biginTime, endTime);
		return "findOutputStatsInput";
	}
	
	/**
	 * 个人产量统计
	 * @return
	 */
	public String findPersonStatsInput() {
		return "findPersonStatsInput";
	}
	public String findPersonStats() {
		outputStatses = individualOutputServer.findPersonStats(biginTime, endTime);
		return "findPersonStatsInput";
	}
	
	
	
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public int getFlowTypeId() {
		return flowTypeId;
	}

	public void setFlowTypeId(int flowTypeId) {
		this.flowTypeId = flowTypeId;
	}

	public List<IndividualOutput> getOutputList() {
		return outputList;
	}

	public void setOutputList(List<IndividualOutput> outputList) {
		this.outputList = outputList;
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
	public List<OutputStats> getOutputStatses() {
		return outputStatses;
	}
	public void setOutputStatses(List<OutputStats> outputStatses) {
		this.outputStatses = outputStatses;
	}
}
