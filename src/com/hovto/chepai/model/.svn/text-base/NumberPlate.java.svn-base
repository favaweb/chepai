package com.hovto.chepai.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * ³µÅÆ
 * @author maodi
 *
 */
@Entity
public class NumberPlate {
	
	private int id;
	private SmallnessBatchNumber smallnessBatchNumber;
	private BigBatchNumber bigBatchNumber;
	private BusinessDepartment businessDepartment;
	private NumberPlateType numberPlateType;
	private int orderNumber;
	private String licensePlateNumber;
	
	public NumberPlate(){}
	
	public NumberPlate(int id){
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
	@JoinColumn(name="smallnessBatchNumber")
	public SmallnessBatchNumber getSmallnessBatchNumber() {
		return smallnessBatchNumber;
	}
	public void setSmallnessBatchNumber(SmallnessBatchNumber smallnessBatchNumber) {
		this.smallnessBatchNumber = smallnessBatchNumber;
	}
	
	@ManyToOne
	@JoinColumn(name="businessDepartment")
	public BusinessDepartment getBusinessDepartment() {
		return businessDepartment;
	}
	public void setBusinessDepartment(BusinessDepartment businessDepartment) {
		this.businessDepartment = businessDepartment;
	}
	
	@ManyToOne
	@JoinColumn(name="numberPlateType")
	public NumberPlateType getNumberPlateType() {
		return numberPlateType;
	}
	public void setNumberPlateType(NumberPlateType numberPlateType) {
		this.numberPlateType = numberPlateType;
	}
	
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}
	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
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
