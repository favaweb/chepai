package com.hovto.chepai.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Á÷³Ì±í
 * @author maodi
 *
 */
@Entity
public class WorkFlow {
	
	private int id;
	private BigBatchNumber bigBatchNumber;
	private SmallnessBatchNumber smallnessBatchNumber;
	private FlowType currentFlow;
	private FlowType nextFlow;
	
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
	
	@ManyToOne
	@JoinColumn(name="currentFlow")
	public FlowType getCurrentFlow() {
		return currentFlow;
	}
	public void setCurrentFlow(FlowType currentFlow) {
		this.currentFlow = currentFlow;
	}
	
	@ManyToOne
	@JoinColumn(name="nextFlow")
	public FlowType getNextFlow() {
		return nextFlow;
	}
	public void setNextFlow(FlowType nextFlow) {
		this.nextFlow = nextFlow;
	}

}
