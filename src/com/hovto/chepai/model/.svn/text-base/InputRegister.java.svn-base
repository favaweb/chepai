package com.hovto.chepai.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 出库记录
 * @author maodi
 *
 */

@Entity
public class InputRegister {

	private int id;
	private SemifinishedProductType semifinishedProductType;
	private int reductionAmount;
	private String reductionNumber;
	//领料用途：1正常生产2优先制作及重新补制3补牌
	private int purpose;
	private String remark;
	//1入库2出库
	private int type;
	private Date createDate;
	//规格
	private String standard;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getReductionAmount() {
		return reductionAmount;
	}
	public void setReductionAmount(int reductionAmount) {
		this.reductionAmount = reductionAmount;
	}
	public String getReductionNumber() {
		return reductionNumber;
	}
	public void setReductionNumber(String reductionNumber) {
		this.reductionNumber = reductionNumber;
	}
	public int getPurpose() {
		return purpose;
	}
	public void setPurpose(int purpose) {
		this.purpose = purpose;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}

	
}
