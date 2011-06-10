package com.hovto.chepai.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.privilege.Roler;

@Component
public class RolerDao {

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public void add(Roler roler) {
		sessionFactory.getCurrentSession().save(roler);
	}
	
	public void delete(Roler roler) {
		sessionFactory.getCurrentSession().delete(this.find(roler));
	}
	
	public void modify(Roler roler) {
		sessionFactory.getCurrentSession().update(roler);
	}
	
	public Roler find(Roler roler) {
		return (Roler) sessionFactory.getCurrentSession().get(Roler.class, roler.getId());
	}
	
	@SuppressWarnings("unchecked")
	public List<Roler> findList() {
		return sessionFactory.getCurrentSession().createQuery("select r from Roler r").list();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
