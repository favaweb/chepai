package com.hovto.chepai.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
/**
 *´óÅúºÅ
 * @author maodi
 *
 */
@Entity
public class BigBatchNumber {

	private int id;
	private String bigBattchNumber;
	private int orderType;
	private String place;
	private String otherName;
	private String beginSegmentNumber;
	private String endSegmentNumber;
	private int amount;
	private Date createTime;
	private Date sendTime;
	private int precedence;
	private int isDeliverGoods;
	private NumberPlateType numberPlateType;
	
	public BigBatchNumber(){}
	
	public BigBatchNumber(int id){
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
	public String getBigBattchNumber() {
		return bigBattchNumber;
	}
	public void setBigBattchNumber(String bigBattchNumber) {
		this.bigBattchNumber = bigBattchNumber;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getOtherName() {
		return otherName;
	}
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	public String getBeginSegmentNumber() {
		return beginSegmentNumber;
	}
	public void setBeginSegmentNumber(String beginSegmentNumber) {
		this.beginSegmentNumber = beginSegmentNumber;
	}
	public String getEndSegmentNumber() {
		return endSegmentNumber;
	}
	public void setEndSegmentNumber(String endSegmentNumber) {
		this.endSegmentNumber = endSegmentNumber;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getPrecedence() {
		return precedence;
	}
	public void setPrecedence(int precedence) {
		this.precedence = precedence;
	}

	public int getIsDeliverGoods() {
		return isDeliverGoods;
	}

	public void setIsDeliverGoods(int isDeliverGoods) {
		this.isDeliverGoods = isDeliverGoods;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@ManyToOne
	@JoinColumn(name="numberPlateType")
	public NumberPlateType getNumberPlateType() {
		return numberPlateType;
	}

	public void setNumberPlateType(NumberPlateType numberPlateType) {
		this.numberPlateType = numberPlateType;
	}

	
	
	
	
	
	
}
