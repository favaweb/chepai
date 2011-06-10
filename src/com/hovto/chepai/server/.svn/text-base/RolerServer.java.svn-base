package com.hovto.chepai.server;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.RolerDao;
import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.privilege.Privilege;
import com.hovto.chepai.privilege.Roler;

@Component
@Transactional
public class RolerServer {

	@Resource
	private RolerDao rolerDao;
	
	public void add(Roler roler) {
		if(roler == null || roler.getName() == null || roler.getName().trim().equals(""))
			throw new ChePaiException("角色名称不允许为空<br/><a href='javascript:window.history.go(-1);'>返回操作</a>");
		rolerDao.add(roler);
	}
	
	public void delete(Roler roler) {
		rolerDao.delete(roler);
	}
	
	public void modify(Roler roler) {
		rolerDao.modify(roler);
	}
	
	public void modifyPrivilege(Roler roler, int[] privileges) {
		roler = rolerDao.find(roler);
		List<Privilege> currentPrivileges = null;
		if(privileges != null) {
			currentPrivileges = new ArrayList<Privilege>();
			for(int i=0; i<privileges.length; i++) {
				currentPrivileges.add(new Privilege(privileges[i]));
			}
		}
		roler.setPrivileges(currentPrivileges);
		rolerDao.modify(roler);
	}
	
	public Roler find(Roler roler) {
		return rolerDao.find(roler);
	}
	
	public List<Roler> findList() {
		return rolerDao.findList();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
