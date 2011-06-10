package com.hovto.chepai.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * 半成品类型
 * @author maodi
 *
 */

@Entity
public class SemifinishedProductType {

	private int id;
	private String typeName;
	private String standard;
	private List<NumberPlateType> numberPlateTypes;
	/**该字段为了标识半成品的前后牌：1前牌2后牌3前后牌（3即是前牌又是后牌）*/
	private Integer type;
	
	public SemifinishedProductType(){}
	public SemifinishedProductType(int id){
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
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	
	@ManyToMany(mappedBy="semifinishedProductTypes",fetch=FetchType.EAGER)
	public List<NumberPlateType> getNumberPlateTypes() {
		return numberPlateTypes;
	}
	public void setNumberPlateTypes(List<NumberPlateType> numberPlateTypes) {
		this.numberPlateTypes = numberPlateTypes;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

	
	
}
