package com.hovto.chepai.model;
/**
 * 成品月统计VO
 * @author Administrator
 *
 */
public class StatisticsFinishedProducts {
	private String place;
	private String typename;
	private int amount;
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
