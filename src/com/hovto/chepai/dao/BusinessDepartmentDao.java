package com.hovto.chepai.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.BusinessDepartment;

@Component
public class BusinessDepartmentDao {
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	public void add(BusinessDepartment bDepartment) {
		sessionFactory.getCurrentSession().save(bDepartment);
	}
	
	public void delete(BusinessDepartment bDepartment) {
		sessionFactory.getCurrentSession().delete(this.find(bDepartment));
	}
	
	public void modify(BusinessDepartment bDepartment) {
		sessionFactory.getCurrentSession().update(bDepartment);
	}
	
	public BusinessDepartment find(BusinessDepartment bDepartment) {
		return (BusinessDepartment) sessionFactory.getCurrentSession().get(BusinessDepartment.class, bDepartment.getId());
	}
	
	@SuppressWarnings("unchecked")
	public List<BusinessDepartment> findList() {
		return sessionFactory.getCurrentSession().createQuery("select p from BusinessDepartment p").list();
	}
}
