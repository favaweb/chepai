package com.hovto.chepai.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * ¹ıÂËÌõ¼ş
 * @author maodi
 *
 */
@Entity
public class FilterCondition {
	
	private int id;
	private String condition;
	
	@Id@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}

}
