package com.hovto.chepai.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.FlowType;

@Component
public class FlowTypeDao {

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public void add(FlowType flowType) {
		sessionFactory.getCurrentSession().save(flowType);
	}
	
	public void delete(FlowType flowType) {
		sessionFactory.getCurrentSession().delete(this.find(flowType));
	}
	
	public void modify(FlowType flowType) {
		sessionFactory.getCurrentSession().update(flowType);
	}
	
	public FlowType find(FlowType flowType) {
		return (FlowType) sessionFactory.getCurrentSession().get(FlowType.class, flowType.getId());
	}
	
	@SuppressWarnings("unchecked")
	public List<FlowType> findList() {
		return sessionFactory.getCurrentSession().createQuery("select f from FlowType f").list();
	}
	
}
