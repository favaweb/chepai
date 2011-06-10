package com.hovto.chepai.server;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.PrivilegeDao;
import com.hovto.chepai.privilege.Privilege;

@Component
@Transactional
public class PrivilegeServer {

	@Resource
	private PrivilegeDao privilegeDao;
	
	public void add(Privilege privilege) {
		privilegeDao.add(privilege);
	}
	
	public void delete(Privilege privilege) {
		privilegeDao.delete(privilege);
	}
	
	public void modify(Privilege privilege) {
		privilegeDao.modify(privilege);
	}
	
	public Privilege find(Privilege privilege) {
		return privilegeDao.find(privilege);
	}
	
	public List<Privilege> findList() {
		return privilegeDao.findList();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
