package com.hovto.chepai.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.privilege.Privilege;
import com.hovto.chepai.privilege.Roler;
import com.hovto.chepai.server.PrivilegeServer;
import com.hovto.chepai.server.RolerServer;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class RolerAction extends ActionSupport {

	@Resource
	private RolerServer rolerServer;
	@Resource
	private PrivilegeServer privilegeServer;
	private List<Roler> rolers;
	private Roler roler;
	private List<Privilege> privileges;
	private int[] modifyPrivileges;
	
	public String add() {
		rolerServer.add(roler);
		ActionContext.getContext().put("message", "角色添加成功<a href='Roler!list.action'>返回操作</a>");
		return "message";
	}
	
	public String list() {
		rolers = rolerServer.findList();
		return "list";
	}
	
	public String modifyPrivilegeInput() {
		privileges = privilegeServer.findList();
		roler = rolerServer.find(roler);
		return "modifyPrivilegeInput";
	}
	
	public String modifyPrivilege() {
		rolerServer.modifyPrivilege(roler, modifyPrivileges);
		ActionContext.getContext().put("message", "角色权限修改啊成功!<br/><a href='Roler!list.action'>返回操作</a>");
		return "message";
	}
	
	public String delete() {
		roler = rolerServer.find(roler);
		if(roler.getUsers().size()>0){
			throw new ChePaiException("删除失败，请确保没有任何用户为该角色。<a href='Roler!list.action'>返回操作</a>");
		}else{
			rolerServer.delete(roler);
			ActionContext.getContext().put("message", "角色删除成功<a href='Roler!list.action'>返回</a>");
		}
		return "message";
	}

	public List<Roler> getRolers() {
		return rolers;
	}

	public void setRolers(List<Roler> rolers) {
		this.rolers = rolers;
	}

	public Roler getRoler() {
		return roler;
	}

	public void setRoler(Roler roler) {
		this.roler = roler;
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

	public int[] getModifyPrivileges() {
		return modifyPrivileges;
	}

	public void setModifyPrivileges(int[] modifyPrivileges) {
		this.modifyPrivileges = modifyPrivileges;
	}

	

	
	
	
	
	
	
	
	
	
	
	
	
}
