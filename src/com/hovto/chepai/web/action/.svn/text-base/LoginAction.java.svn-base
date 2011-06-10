package com.hovto.chepai.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.Users;
import com.hovto.chepai.privilege.Privilege;
import com.hovto.chepai.server.UserServer;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class LoginAction extends ActionSupport implements SessionAware {

	@Resource
	private UserServer userServer;
	private Users user;
	private List<Privilege> onePrivileges = new ArrayList<Privilege>();
	private List<Privilege> twoPrivileges = new ArrayList<Privilege>();
	private List<Privilege>	threePrivileges = new ArrayList<Privilege>();
	private List<Privilege> fourPrivileges = new ArrayList<Privilege>();
	private List<Privilege> fivePrivileges = new ArrayList<Privilege>();
	
	private Map<String, Object> session;
	public String login() {
		userServer.login(user, session);
		return "index";
	}
	
	public String loginInput() {
		return "loginInput";
	}
	
	public String getUserPrivileges() {
		userServer.getUserPrivileges(session,onePrivileges,twoPrivileges,threePrivileges,fourPrivileges,fivePrivileges);
		return "getUserPrivileges";
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public List<Privilege> getOnePrivileges() {
		return onePrivileges;
	}

	public void setOnePrivileges(List<Privilege> onePrivileges) {
		this.onePrivileges = onePrivileges;
	}

	public List<Privilege> getTwoPrivileges() {
		return twoPrivileges;
	}

	public void setTwoPrivileges(List<Privilege> twoPrivileges) {
		this.twoPrivileges = twoPrivileges;
	}

	public List<Privilege> getThreePrivileges() {
		return threePrivileges;
	}

	public void setThreePrivileges(List<Privilege> threePrivileges) {
		this.threePrivileges = threePrivileges;
	}

	public List<Privilege> getFourPrivileges() {
		return fourPrivileges;
	}

	public void setFourPrivileges(List<Privilege> fourPrivileges) {
		this.fourPrivileges = fourPrivileges;
	}

	public List<Privilege> getFivePrivileges() {
		return fivePrivileges;
	}

	public void setFivePrivileges(List<Privilege> fivePrivileges) {
		this.fivePrivileges = fivePrivileges;
	}

	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}

	
	
	
	
	
	
	
	
	
	
	
	
}
