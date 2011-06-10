package com.hovto.chepai.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hovto.chepai.privilege.Privilege;
import com.hovto.chepai.server.PrivilegeServer;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class PrivilegeAction extends ActionSupport {

	@Resource
	private PrivilegeServer privilegeServer;
	private Privilege privilege;
	private List<Privilege> privileges;
	
	public String addInput() {
		return "addInput";
	}
	
	
	public String add() {
		privilegeServer.add(privilege);
		ActionContext.getContext().put("message", "权限添加成功");
		return "message";
	}

	public String list(){
		privileges = privilegeServer.findList();
		return "list";
	}
	
	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
