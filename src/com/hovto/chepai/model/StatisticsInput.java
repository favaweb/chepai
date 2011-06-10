package com.hovto.chepai.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 出入库统计类型
 */
@Entity
public class StatisticsInput {
	
	private Integer id;
	//日期
	private Date date;
	//类型名称
	private SemifinishedProductType type;
	//规格
	private String standard;
	//入库数量
	private Integer inCount;
	//入库数量
	private Integer outCount;
	
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getInCount() {
		return inCount;
	}
	public void setInCount(Integer inCount) {
		this.inCount = inCount;
	}
	public Integer getOutCount() {
		return outCount;
	}
	public void setOutCount(Integer outCount) {
		this.outCount = outCount;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	@ManyToOne
	@JoinColumn(name="typeid")
	public SemifinishedProductType getType() {
		return type;
	}
	public void setType(SemifinishedProductType type) {
		this.type = type;
	}
}
