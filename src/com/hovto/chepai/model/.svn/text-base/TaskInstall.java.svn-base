package com.hovto.chepai.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *	任务设置实体Bean,它主要用于管理员对各个岗位的任务分配进行限制。
 *	当组长分配任务时,如果超过了管理员设置的大小，则以管理员的为准，
 *	如果管理员没有进行分配，则默认最大为5批次。
 *	管理员设置的限制批数只有当天有效，每天进行批次清空。
 * 
 * @author 胡建国
 *
 */
@Entity
public class TaskInstall {
	private int id;
	/**流程*/
	private FlowType flowType;
	
	/**限制数:设置此值后，当前流程最多只能获取的上线以此字段为准*/
	private int number;
	
	/**默认为5批*/
	private int defaultNumber;
	
	/**设置时间：当前时间，系统生成*/
	private Date installDate;

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="flowType")
	public FlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(FlowType flowType) {
		this.flowType = flowType;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getDefaultNumber() {
		return defaultNumber;
	}

	public void setDefaultNumber(int defaultNumber) {
		this.defaultNumber = defaultNumber;
	}

	public Date getInstallDate() {
		return installDate;
	}

	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}
	
	

}
