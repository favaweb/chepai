package com.hovto.chepai.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 重制表
 * @author maodi
 *
 */
@Entity
public class Remakes {
	
	private int id;
	private SmallnessBatchNumber smallnessBatchNumber;
	private BigBatchNumber bigBatchNumber;
	private NumberPlate numberPlate;
	//1补制作中 2 补制完成 3总质检返回
	private int state;
	
	public Remakes(){}
	
	public Remakes(int id){
		this.id=id;
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
	@JoinColumn(name="bigBatchNumber")
	public BigBatchNumber getBigBatchNumber() {
		return bigBatchNumber;
	}
	public void setBigBatchNumber(BigBatchNumber bigBatchNumber) {
		this.bigBatchNumber = bigBatchNumber;
	}
	
	@ManyToOne
	@JoinColumn(name="numberPlate")
	public NumberPlate getNumberPlate() {
		return numberPlate;
	}
	public void setNumberPlate(NumberPlate numberPlate) {
		this.numberPlate = numberPlate;
	}
	
	@ManyToOne
	@JoinColumn(name="smallnessBatchNumber")
	public SmallnessBatchNumber getSmallnessBatchNumber() {
		return smallnessBatchNumber;
	}
	public void setSmallnessBatchNumber(SmallnessBatchNumber smallnessBatchNumber) {
		this.smallnessBatchNumber = smallnessBatchNumber;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}

}
