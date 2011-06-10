package com.hovto.chepai.web.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.Users;
import com.hovto.chepai.privilege.Roler;
import com.hovto.chepai.server.RolerServer;
import com.hovto.chepai.server.UserServer;
import com.hovto.chepai.tool.Page;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class UserAction extends ActionSupport implements SessionAware {

	@Resource
	private UserServer userServer;
	@Resource
	private RolerServer rolerServer;
	private Users user;
	private List<Users> users;
	private List<Roler> rolers;
	private int[] modifyRolers;
	
	private int roler = -1;
	private int daynight = -1;
	private String username = "";
	
	private String nowPassword;
	private String modifyPassword;
	private String confirmPassword;
	private Map<String, Object> session;
	private Page page = new Page();
	
	public String addInput() {
		return "addInput";
	}
	
	public String add() {
		userServer.add(user);
		ActionContext.getContext().put("message", "用户添加成功<br/><a href='User!list.action'>用户列表</a>");
		return "message";
	}

	public String list() {
		users = userServer.findList(page, roler, daynight,username);
		return "list";
	}
	
	public String modifyRolerInput() {
		rolers = rolerServer.findList();
		user = userServer.find(user);
		return "modifyRolerInput";
	}
	
	public String modifyPasswordInput() {
		return "modifyPasswordInput";
	}
	
	public String modifyPassword() {
		userServer.modify(user,nowPassword,modifyPassword,confirmPassword,session);
		ActionContext.getContext().put("message", "密码修改成功,请重新<a href='#'>登录</a>");
		return "message";
	}
	
	public String modifyRoler() {
		userServer.modifyRoler(user, modifyRolers);
		ActionContext.getContext().put("message", "用户角色修改成功!<br/><a href='User!list.action'>返回操作</a>");
		return "message";
	}
	
	public String modifyInfoInput() {
		user = userServer.find(user);
		return "modifyInfoInput";
	}
	
	public String modifyInfo() {
		userServer.modify(user);
		ActionContext.getContext().put("message", "用户信息修改成功!<a href='User!list.action'>返回操作</a>");
		return "message";
	}
	
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

	public List<Roler> getRolers() {
		return rolers;
	}

	public void setRolers(List<Roler> rolers) {
		this.rolers = rolers;
	}

	public int[] getModifyRolers() {
		return modifyRolers;
	}

	public void setModifyRolers(int[] modifyRolers) {
		this.modifyRolers = modifyRolers;
	}

	public String getNowPassword() {
		return nowPassword;
	}

	public void setNowPassword(String nowPassword) {
		this.nowPassword = nowPassword;
	}

	public String getModifyPassword() {
		return modifyPassword;
	}

	public void setModifyPassword(String modifyPassword) {
		this.modifyPassword = modifyPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public int getRoler() {
		return roler;
	}

	public void setRoler(int roler) {
		this.roler = roler;
	}

	public int getDaynight() {
		return daynight;
	}

	public void setDaynight(int daynight) {
		this.daynight = daynight;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}



	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
