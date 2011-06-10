package com.hovto.chepai.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * °ë³ÉÆ·
 * @author maodi
 *
 */
@Entity
public class Material {
	
	private int id;
	private int total;
	private String standard;
	
	public Material(){}
	
	public Material(int id,int total,String standard){
		this.id=id;
		this.total=total;
		this.standard=standard;
	}
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}

}
