package com.hovto.chepai.model;

/**
 *产量统计
 */
public class OutputStats {

	private int flowTypeId;
	private String flowTypeName;
	private String userName;
	private int output;
	
	
	public int getFlowTypeId() {
		return flowTypeId;
	}
	public void setFlowTypeId(int flowTypeId) {
		this.flowTypeId = flowTypeId;
	}
	public String getFlowTypeName() {
		return flowTypeName;
	}
	public void setFlowTypeName(String flowTypeName) {
		this.flowTypeName = flowTypeName;
	}
	public int getOutput() {
		return output;
	}
	public void setOutput(int output) {
		this.output = output;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
