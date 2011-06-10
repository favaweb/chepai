package com.hovto.chepai.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * 任务分配
 * @author maodi
 *
 */
@Entity
public class TaskAllocation {

	private int id;
	private SmallnessBatchNumber smallnessBatchNumber;
	private FlowType flowType;
	private String operator;
	private Date allocationTime;
	//0未完成 1完成 2重制
	private int status;
	private String names;
	
	public TaskAllocation(){}
	public TaskAllocation(int id){
		this.id=id;
	}
	public TaskAllocation(SmallnessBatchNumber smallnessBatchNumber,FlowType flowType,Date allocationTime, String operator,int status){
		this.smallnessBatchNumber=smallnessBatchNumber;
		this.flowType=flowType;
		this.operator=operator;
		this.allocationTime=allocationTime;
		this.status=status;
	}
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="smallnessBatchNumber")
	public SmallnessBatchNumber getSmallnessBatchNumber() {
		return smallnessBatchNumber;
	}
	public void setSmallnessBatchNumber(SmallnessBatchNumber smallnessBatchNumber) {
		this.smallnessBatchNumber = smallnessBatchNumber;
	}
	
	@ManyToOne
	@JoinColumn(name="flowType")
	public FlowType getFlowType() {
		return flowType;
	}
	public void setFlowType(FlowType flowType) {
		this.flowType = flowType;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getAllocationTime() {
		return allocationTime;
	}
	public void setAllocationTime(Date allocationTime) {
		this.allocationTime = allocationTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Transient
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	
}
