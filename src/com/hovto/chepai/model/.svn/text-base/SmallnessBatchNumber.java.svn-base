package com.hovto.chepai.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * 小批号
 * @author maodi
 *
 */
@Entity
public class SmallnessBatchNumber {

	private int id;
	private BigBatchNumber bigBatchNumber;
	private String smallnessBatchNumber;
	private int orderType;
	private String place;
	private String otherName;
	private int amount;
	private Date dateTime;
	private Date sendTime;
	private int precedence;
	private int isRemakes;
	private int boxNumber;
	/**箱号各类:1白2黄3蓝*/
	private int boxNumberType;
	private int makeStatus;
	private int isDistribute;
	private int isDeliverGoods;
	private int isValid;
	private String operator;
	private NumberPlateType numberPlateType;
	
	public SmallnessBatchNumber(){}
	
	public SmallnessBatchNumber(int id){
		this.id=id;
	}
	public SmallnessBatchNumber(BigBatchNumber bigBatchNumber,String smallnessBatchNumber,int orderType,String place,String otherName,int amount,Date dateTime,int precedence,int isRemakes,int boxNumber,int makeStatus,int isDistribute,int isDeliverGoods,int isValid,NumberPlateType numberPlateType){
		this.bigBatchNumber=bigBatchNumber;
		this.smallnessBatchNumber=smallnessBatchNumber;
		this.orderType=orderType;
		this.place=place;
		this.otherName=otherName;
		this.amount=amount;
		this.dateTime=dateTime;
		this.precedence=precedence;
		this.isRemakes=isRemakes;
		this.boxNumber=boxNumber;
		this.makeStatus=makeStatus;
		this.isDistribute=isDistribute;
		this.isDeliverGoods=isDeliverGoods;
		this.isValid=isValid;
		this.numberPlateType = numberPlateType;
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
	public String getSmallnessBatchNumber() {
		return smallnessBatchNumber;
	}
	public void setSmallnessBatchNumber(String smallnessBatchNumber) {
		this.smallnessBatchNumber = smallnessBatchNumber;
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public int getPrecedence() {
		return precedence;
	}
	public void setPrecedence(int precedence) {
		this.precedence = precedence;
	}
	public int getIsRemakes() {
		return isRemakes;
	}
	public void setIsRemakes(int isRemakes) {
		this.isRemakes = isRemakes;
	}
	public int getBoxNumber() {
		return boxNumber;
	}
	public void setBoxNumber(int boxNumber) {
		this.boxNumber = boxNumber;
	}
	public int getMakeStatus() {
		return makeStatus;
	}
	public void setMakeStatus(int makeStatus) {
		this.makeStatus = makeStatus;
	}

	public int getIsDistribute() {
		return isDistribute;
	}

	public void setIsDistribute(int isDistribute) {
		this.isDistribute = isDistribute;
	}

	public int getIsDeliverGoods() {
		return isDeliverGoods;
	}

	public void setIsDeliverGoods(int isDeliverGoods) {
		this.isDeliverGoods = isDeliverGoods;
	}

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}
	
	@Transient
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getBoxNumberType() {
		return boxNumberType;
	}

	public void setBoxNumberType(int boxNumberType) {
		this.boxNumberType = boxNumberType;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SmallnessBatchNumber other = (SmallnessBatchNumber) obj;
		if (id != other.id)
			return false;
		return true;
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
