package com.hovto.chepai.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * ¿â´æ
 */
@Entity
public class Reserve {
	
	private int id;
	private SemifinishedProductType semifinishedProductType;
	private int remainder;
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
	public int getRemainder() {
		return remainder;
	}
	public void setRemainder(int remainder) {
		this.remainder = remainder;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}

}
