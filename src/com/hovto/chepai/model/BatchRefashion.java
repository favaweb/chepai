package com.hovto.chepai.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 整批重制表
 * @author maodi
 *
 */
@Entity
public class BatchRefashion {
	private int id;
	private SmallnessBatchNumber smallnessBatchNumber;
	private BigBatchNumber bigBatchNumber;
	private SmallnessBatchNumber refashionSmallnessBatchNumber;
	private int status;
	
	public BatchRefashion(){}
	public BatchRefashion(int id){
		this.id=id;
	}
	public BatchRefashion(int id,SmallnessBatchNumber smallnessBatchNumber,BigBatchNumber bigBatchNumber,SmallnessBatchNumber refashionSmallnessBatchNumber,int status){
		this.id=id;
		this.smallnessBatchNumber=smallnessBatchNumber;
		this.bigBatchNumber=bigBatchNumber;
		this.refashionSmallnessBatchNumber=refashionSmallnessBatchNumber;
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
	@JoinColumn(name="bigBatchNumber")
	public BigBatchNumber getBigBatchNumber() {
		return bigBatchNumber;
	}
	public void setBigBatchNumber(BigBatchNumber bigBatchNumber) {
		this.bigBatchNumber = bigBatchNumber;
	}
	@ManyToOne
	@JoinColumn(name="refashionSmallnessBatchNumber")
	public SmallnessBatchNumber getRefashionSmallnessBatchNumber() {
		return refashionSmallnessBatchNumber;
	}
	public void setRefashionSmallnessBatchNumber(
			SmallnessBatchNumber refashionSmallnessBatchNumber) {
		this.refashionSmallnessBatchNumber = refashionSmallnessBatchNumber;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
