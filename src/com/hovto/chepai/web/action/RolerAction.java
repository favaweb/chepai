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
		ActionContext.getContext().put("message", "��ɫ��ӳɹ�<a href='Roler!list.action'>���ز���</a>");
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
		ActionContext.getContext().put("message", "��ɫȨ���޸İ��ɹ�!<br/><a href='Roler!list.action'>���ز���</a>");
		return "message";
	}
	
	public String delete() {
		roler = rolerServer.find(roler);
		if(roler.getUsers().size()>0){
			throw new ChePaiException("ɾ��ʧ�ܣ���ȷ��û���κ��û�Ϊ�ý�ɫ��<a href='Roler!list.action'>���ز���</a>");
		}else{
			rolerServer.delete(roler);
			ActionContext.getContext().put("message", "��ɫɾ���ɹ�<a href='Roler!list.action'>����</a>");
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
