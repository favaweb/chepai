package com.hovto.chepai.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.SemifinishedProductType;

@Component
public class SemifinishedProductTypeDao {
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public void add(SemifinishedProductType semifinishedProductType) {
		sessionFactory.getCurrentSession().save(semifinishedProductType);
	}
	
	public void delete(SemifinishedProductType SemifinishedProductType) {
		sessionFactory.getCurrentSession().delete(this.find(SemifinishedProductType));
	}
	
	public void modify(SemifinishedProductType SemifinishedProductType) {
		sessionFactory.getCurrentSession().update(SemifinishedProductType);
	}
	
	public SemifinishedProductType find(SemifinishedProductType semifinishedProductType) {
		return (SemifinishedProductType) sessionFactory.getCurrentSession().get(SemifinishedProductType.class, semifinishedProductType.getId());
	}
	
	public SemifinishedProductType findbyName(String typeName){
		return (SemifinishedProductType) sessionFactory.getCurrentSession().createQuery("select p from SemifinishedProductType p where p.typeName = :typeName").setParameter("typeName", typeName);
	}
	
	@SuppressWarnings("unchecked")
	public List<SemifinishedProductType> findList() {
		return sessionFactory.getCurrentSession().createQuery("select p from SemifinishedProductType p").list();
	}
	
	public List<SemifinishedProductType> findByType(int type){
		return  sessionFactory.getCurrentSession().createQuery("select p from SemifinishedProductType p where p.type != :type").setParameter("type", type).list();
	}
}
