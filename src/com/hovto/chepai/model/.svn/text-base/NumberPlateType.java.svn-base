package com.hovto.chepai.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * 车牌类型
 * @author maodi
 *
 */
@Entity
public class NumberPlateType {

	private int id;
	private String typeName;
	//代码
	private String code;
	//类型：1大车类型2小型汽车类型3摩托车类型4低速车类型5临时行驶类型
	private int type;
	private int number;
	private List<SemifinishedProductType> semifinishedProductTypes;
	
	public NumberPlateType() {}
	
	public NumberPlateType(int id) {
		super();
		this.id = id;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	@ManyToMany
	@JoinTable(
			name="numberPlateType_semifinishedProductType",
			joinColumns={@JoinColumn(name="numberPlateTypeId")},
			inverseJoinColumns={@JoinColumn(name="semifinishedProductTypeId")}
	)
	public List<SemifinishedProductType> getSemifinishedProductTypes() {
		return semifinishedProductTypes;
	}
	public void setSemifinishedProductTypes(
			List<SemifinishedProductType> semifinishedProductTypes) {
		this.semifinishedProductTypes = semifinishedProductTypes;
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
		final NumberPlateType other = (NumberPlateType) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
