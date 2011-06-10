package com.hovto.chepai.model;


public class Stats {

	private int marType;
	private int stats;
	
	public int getMarType() {
		return marType;
	}
	public void setMarType(int marType) {
		this.marType = marType;
	}
	public int getStats() {
		return stats;
	}
	public void setStats(int stats) {
		this.stats = stats;
	}
	public Stats(int marType, int stats) {
		super();
		this.marType = marType;
		this.stats = stats;
	}
	public Stats() {
		super();
	}

	
	
	
}
