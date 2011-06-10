package com.hovto.chepai.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * 废牌记录
 * @author maodi
 *
 */
@Entity
public class DisuseMaterial {
	
	private int id;
	private BigBatchNumber bigBatchNumber;
	private SmallnessBatchNumber smallnessBatchNumber;
	private FlowType flowType;
	private String operator;
	private Date operateTime;
	//损坏类型：1压错2半成品3机器模具4试机5样牌
	private int marType;
	private NumberPlateType numberPlateType;
	private NumberPlate numberPlate;
	private int amount;
	private SemifinishedProductType semifinishedProductType;
	//标记 该废牌是 白班产生 还是夜班 产生//1为 白班 0为 夜班
	private int dayNight;
	
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
	@JoinColumn(name="flowType")
	public FlowType getFlowType() {
		return flowType;
	}
	public void setFlowType(FlowType flowType) {
		this.flowType = flowType;
	}
	
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public int getMarType() {
		return marType;
	}
	public void setMarType(int marType) {
		this.marType = marType;
	}
	
	@ManyToOne
	@JoinColumn(name="numberPlateType")
	public NumberPlateType getNumberPlateType() {
		return numberPlateType;
	}
	public void setNumberPlateType(NumberPlateType numberPlateType) {
		this.numberPlateType = numberPlateType;
	}
	
	@ManyToOne
	@JoinColumn(name="numberPlate")
	public NumberPlate getNumberPlate() {
		return numberPlate;
	}
	public void setNumberPlate(NumberPlate numberPlate) {
		this.numberPlate = numberPlate;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Transient
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@ManyToOne
	@JoinColumn(name="semifinishedProductType")
	public SemifinishedProductType getSemifinishedProductType() {
		return semifinishedProductType;
	}
	public void setSemifinishedProductType(
			SemifinishedProductType semifinishedProductType) {
		this.semifinishedProductType = semifinishedProductType;
	}
	public int getDayNight() {
		return dayNight;
	}
	public void setDayNight(int dayNight) {
		this.dayNight = dayNight;
	}

}
