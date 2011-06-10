package com.hovto.chepai.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.BatchRefashion;


@Component
public class BatchRefashionDao {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	public void add(BatchRefashion batchRefashion) {
		sessionFactory.getCurrentSession().save(batchRefashion);
	}
	
	public void delete(BatchRefashion batchRefashion) {
		sessionFactory.getCurrentSession().delete(this.find(batchRefashion));
	}
	
	public void modify(BatchRefashion batchRefashion) {
		sessionFactory.getCurrentSession().update(batchRefashion);
	}
	
	public BatchRefashion find(BatchRefashion batchRefashion) {
		return (BatchRefashion) sessionFactory.getCurrentSession().get(BatchRefashion.class, batchRefashion.getId());
	}
	
	@SuppressWarnings("unchecked")
	public List<BatchRefashion> findList() {
		return sessionFactory.getCurrentSession().createQuery("select u from BatchRefashion u").list();
	}
	
	public BatchRefashion findBySmallId(int smallId) {
		return (BatchRefashion) sessionFactory.getCurrentSession().createQuery("select u from BatchRefashion u where u.smallnessBatchNumber.id = :smallId").setParameter("smallId", smallId).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<BatchRefashion> findByStatus(int status) {
		return sessionFactory.getCurrentSession().createQuery("select u from BatchRefashion u where status=:status").setParameter("status",status).list();
	}
}
