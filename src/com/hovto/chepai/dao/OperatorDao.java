package com.hovto.chepai.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.Operator;

@Component
public class OperatorDao {
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public void add(Operator operator) {
		sessionFactory.getCurrentSession().save(operator);
	}
	
	public void delete(Operator operator) {
		sessionFactory.getCurrentSession().delete(this.find(operator));
	}
	
	public void modify(Operator operator) {
		sessionFactory.getCurrentSession().update(operator);
	}
	
	public Operator find(Operator operator) {
		return (Operator) sessionFactory.getCurrentSession().get(Operator.class, operator.getId());
	}
	public List<Operator> findBySBatchNumberId(int sbatchNumberId) {
		return sessionFactory.getCurrentSession().createQuery("select p from Operator p where p.smallnessBatchNumber.id=:id").setParameter("id", sbatchNumberId).list();
	}	
	public List<Operator> findBySBatchNumberId(int sbatchNumberId,int flowTypeId) {
		String sql= "select p from Operator p where 1=1";
		if(sbatchNumberId>0){
			sql+=" and p.smallnessBatchNumber.id="+sbatchNumberId;
		}
		if(flowTypeId>0){
			sql+=" and p.flowType.id="+flowTypeId;
		}
		return sessionFactory.getCurrentSession().createQuery(sql).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Operator> findList() {
		return sessionFactory.getCurrentSession().createQuery("select p from Operator p").list();
	}
	public List<Operator> findByOperater(String operater){
		String sql="select max(p.id) as id,max(p.operater) as operater,max(p.bigBatchNumber) as bigBatchNumber, max(p.flowType) as flowType,smallnessBatchNumber from dbo.Operator as p where operater like '%"+operater+"%' group by smallnessBatchNumber";
		return sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Operator.class).list();
	}

	/**
	 * 根据 小批号查询所有流程的操作人
	 * @param smallBatchId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Operator> findBySmallBatchId(int smallBatchId) {
		return sessionFactory.getCurrentSession().createQuery("select o from Operator o where o.smallnessBatchNumber.id = :id").setParameter("id", smallBatchId).list();
	}

	public void delBySmallAndFlow(int smallId, int flow) {
		sessionFactory.getCurrentSession().createQuery("delete from Operator o where o.smallnessBatchNumber.id = :smallId and o.flowType.id = :flow").setParameter("smallId", smallId).setParameter("flow", flow).executeUpdate();
	}
}
