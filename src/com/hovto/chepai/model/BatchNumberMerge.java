package com.hovto.chepai.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * ÅúºÅºÏ²¢
 * @author maodi
 *
 */
@Entity
public class BatchNumberMerge {
	
	private int id;
	private SmallnessBatchNumber smallnessBatchNumber;
	private BigBatchNumber bigBatchNumber;
	
	public BatchNumberMerge(){}
	public BatchNumberMerge(SmallnessBatchNumber smallnessBatchNumber,BigBatchNumber bigBatchNumber){
		this.smallnessBatchNumber=smallnessBatchNumber;
		this.bigBatchNumber=bigBatchNumber;
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
	@JoinColumn(name="bigBatchNumber")
	public BigBatchNumber getBigBatchNumber() {
		return bigBatchNumber;
	}
	public void setBigBatchNumber(BigBatchNumber bigBatchNumber) {
		this.bigBatchNumber = bigBatchNumber;
	}

}
