package com.hovto.chepai.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.hovto.chepai.privilege.Roler;

@Entity
public class Users {
	
	private int id;
	private String name;
	private String password;
	
	/**
	 * 用户角色 1组长 2跟单员 3管理员
	 */
	private int roler;
	private int isvalid;
	private String account;
	//true 为男 false 为女
	private boolean sex;
	//1为 白班 0为 夜班
	private int dayNight;
	
	private List<Roler> rolers;
	
	public Users() {}
	
	public Users(int id) {
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany
	@JoinTable(
			name="user_roler",
			joinColumns={@JoinColumn(name="userId")},
			inverseJoinColumns={@JoinColumn(name="rolerId")}
	)
	public List<Roler> getRolers() {
		return rolers;
	}
	public void setRolers(List<Roler> rolers) {
		this.rolers = rolers;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoler() {
		return roler;
	}

	public void setRoler(int roler) {
		this.roler = roler;
	}

	public int getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(int isvalid) {
		this.isvalid = isvalid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public int getDayNight() {
		return dayNight;
	}

	public void setDayNight(int dayNight) {
		this.dayNight = dayNight;
	}

}
