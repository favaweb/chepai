package com.hovto.chepai.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 个人产量
 * @author maodi
 *
 */
@Entity
public class IndividualOutput {

	private int id;
	private BigBatchNumber bigBatchNumber;
	private SmallnessBatchNumber smallnessBatchNumber;
	private double output;
	private Date productTime;
	private FlowType flowType;
	private Users user;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="bigBatchNumber")
	public BigBatchNumber getBigBatchNumber() {
		return bigBatchNumber;
	}
	public void setBigBatchNumber(BigBatchNumber bigBatchNumber) {
		this.bigBatchNumber = bigBatchNumber;
	}
	
	@ManyToOne
	@JoinColumn(name="smallnessBatchNumber")
	public SmallnessBatchNumber getSmallnessBatchNumber() {
		return smallnessBatchNumber;
	}
	public void setSmallnessBatchNumber(SmallnessBatchNumber smallnessBatchNumber) {
		this.smallnessBatchNumber = smallnessBatchNumber;
	}
	public double getOutput() {
		return output;
	}
	public void setOutput(double output) {
		this.output = output;
	}
	public Date getProductTime() {
		return productTime;
	}
	public void setProductTime(Date productTime) {
		this.productTime = productTime;
	}
	
	@ManyToOne
	@JoinColumn(name="flowType")
	public FlowType getFlowType() {
		return flowType;
	}
	public void setFlowType(FlowType flowType) {
		this.flowType = flowType;
	}
	
	@ManyToOne
	@JoinColumn(name="userId")
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	
	
}
