package com.hovto.chepai.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 流程类型
 * @author maodi
 *
 */
@Entity
public class FlowType {

	private int id;
	private String typeName;
	
	public FlowType(int id) {
		super();
		this.id = id;
	}
	public FlowType(){
	}
	
	public FlowType(int id,String typeNume){
		this.id=id;
		this.typeName=typeNume;
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
	
}
