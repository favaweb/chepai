package com.hovto.chepai.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.privilege.Privilege;

@Component
public class PrivilegeDao {
   
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public void add(Privilege privilege) {
		sessionFactory.getCurrentSession().save(privilege);
	}
	
	public void delete(Privilege privilege) {
		sessionFactory.getCurrentSession().delete(this.find(privilege));
	}
	
	public void modify(Privilege privilege) {
		sessionFactory.getCurrentSession().update(privilege);
	}
	
	public Privilege find(Privilege privilege) {
		return (Privilege) sessionFactory.getCurrentSession().get(Privilege.class, privilege.getId());
	}
	
	@SuppressWarnings("unchecked")
	public List<Privilege> findList() {
		return sessionFactory.getCurrentSession().createQuery("select p from Privilege p").list();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
