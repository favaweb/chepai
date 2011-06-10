package com.hovto.chepai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.BigBatchNumber;
import com.hovto.chepai.model.FlowType;
import com.hovto.chepai.model.SmallnessBatchNumber;
import com.hovto.chepai.model.WorkFlow;
import com.hovto.chepai.tool.Page;

@Component
public class SmallnessBatchNumberDao {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	public SmallnessBatchNumber add(SmallnessBatchNumber smallnessBatchNumber) {
		sessionFactory.getCurrentSession().save(smallnessBatchNumber);
		return smallnessBatchNumber;
	}
	
	//���ҳ�������������
	public List<SmallnessBatchNumber> findByIsDistribute(){
		return sessionFactory.getCurrentSession().createQuery("select p from SmallnessBatchNumber p where p.isDistribute=1 ORDER BY precedence,dateTime").list();
	}
	public void delete(SmallnessBatchNumber smallnessBatchNumber) {
		sessionFactory.getCurrentSession().delete(
				this.find(smallnessBatchNumber));
	}

	public void modify(SmallnessBatchNumber smallnessBatchNumber) {
		sessionFactory.getCurrentSession().update(smallnessBatchNumber);
	}

	public SmallnessBatchNumber find(SmallnessBatchNumber smallnessBatchNumber) {
		return (SmallnessBatchNumber) sessionFactory.getCurrentSession().get(
				SmallnessBatchNumber.class, smallnessBatchNumber.getId());
	}

	@SuppressWarnings("unchecked")
	public List<SmallnessBatchNumber> findList() {
		return sessionFactory.getCurrentSession().createQuery(
				"select p from SmallnessBatchNumber p").list();
	}
	/**
	 * ������Ų�ѯС���ż�¼
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public long findByBoxNumber(int boxNumber,int boxType) {
		return (Long) sessionFactory.getCurrentSession().createQuery("select count(s) from SmallnessBatchNumber s where s.boxNumber=:boxNumber and s.boxNumberType = :boxType").setParameter("boxNumber", boxNumber).setParameter("boxType", boxType).uniqueResult();
	}
	
	
	/**
	 * ��ѯ��Ҫ�·�������
	 * @param makeStatus ����״̬ Ĭ��0��δ����  1������ 2���
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SmallnessBatchNumber> findByMakeStatus(int bigBatchNumber, Page page) {
		if(page.getLastPage() <= 1) {
			Query queryCount = sessionFactory.getCurrentSession().createQuery("select count(p) from SmallnessBatchNumber p where p.makeStatus = 0 and p.isValid = 0 and p.bigBatchNumber.id = :bid");
			queryCount.setParameter("bid", bigBatchNumber);
			page.setLastPage(Integer.parseInt(queryCount.uniqueResult().toString()));
		}
		
		Query query =sessionFactory.getCurrentSession().createQuery("select p from SmallnessBatchNumber p where p.makeStatus = 0 and p.isValid = 0 and p.bigBatchNumber.id = :bid");
		query.setParameter("bid", bigBatchNumber);
		query.setFirstResult((page.getCurrentPage()-1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<SmallnessBatchNumber> findByMakeStatus(int bigBatchNumber) {
		Query query =sessionFactory.getCurrentSession().createQuery("select p from SmallnessBatchNumber p where p.makeStatus = 0 and p.isValid = 0 and p.bigBatchNumber.id = :bid");
		query.setParameter("bid", bigBatchNumber);
		return query.list();
	}
	
	/**
	 * ��ѯ���Ժϲ���С����
	 * ����:
	 *  isRemark=0  0���� 1���� 2�ϲ�
	 *  makeStatus=0 ����״̬ 0����1������2���
	 *  p.bigBatchNumber.amount < 50 �ϲ����ŵĳ�������ҪС��50
	 *  p.isValid = 0 
	 * @param page 
	 */
	@SuppressWarnings("unchecked")
	public List<SmallnessBatchNumber> findMerger(Page page) {
		if(page.getLastPage() <= 1) {
			Query queryCount = sessionFactory.getCurrentSession().createQuery("select count(p) from SmallnessBatchNumber p where p.isRemakes = 0 and p.makeStatus = 0 and p.bigBatchNumber.amount < 50 and p.isValid = 0");
			page.setLastPage(Integer.parseInt(queryCount.uniqueResult().toString()));
		}
		
		Query query =sessionFactory.getCurrentSession().createQuery("select p from SmallnessBatchNumber p where p.isRemakes = 0 and p.makeStatus = 0 and p.bigBatchNumber.amount < 50 and p.isValid = 0");
		query.setFirstResult((page.getCurrentPage()-1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		
		return query.list();
	}
	@SuppressWarnings("unchecked")
	public List<SmallnessBatchNumber> findMerger() {
		Query query =sessionFactory.getCurrentSession().createQuery("select p from SmallnessBatchNumber p where p.isRemakes = 0 and p.makeStatus = 0 and p.bigBatchNumber.amount < 50 and p.isValid = 0 and p.orderType <> 3");
		return query.list();
	}
	/**
	 * ���ݴ����Ų�ѯ����������
	 * @param bigBatchNumberId ������id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SmallnessBatchNumber> findBybigBatchNumber(int bigBatchNumberId) {
		return sessionFactory.getCurrentSession().createQuery("select p from SmallnessBatchNumber p where p.bigBatchNumber.id=:id").setParameter("id", bigBatchNumberId).list();
	}
	
	
	/**
	 * ����������ѯ �Ƿ񷢻�
	 * @param sql
	 * @param findDate
	 * @param nextDate
	 * @param place
	 * @param isDeliverGoods
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SmallnessBatchNumber> findIsDeliverGoods(String sql, Date findDate, Date nextDate, String place, int isDeliverGoods) {
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		if(findDate != null) 
			query.setParameter("findDate", findDate);
		if(nextDate != null)
			query.setParameter("nextDate", nextDate);
		if(place != null && !"".equals(place)) 
			query.setParameter("place", place);
		if(isDeliverGoods != 0) {
			query.setParameter("isDeliverGoods", isDeliverGoods);
		}
		return query.list();
	}

	/**
	 * ���ݴ����Ų�ѯ������
	 * @param bigBatchNumberId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SmallnessBatchNumber> findIsDeliverByBig(int bigBatchNumberId,Page page) {
		Query query = sessionFactory.getCurrentSession().createQuery("select s from SmallnessBatchNumber s where s.makeStatus = 2 and s.isDeliverGoods != 0 and s.bigBatchNumber.id = :id order by s.precedence, s.dateTime, s.isDeliverGoods  ");
		
		if(page.getLastPage() <= 1) {
			Query queryCount = sessionFactory.getCurrentSession().createQuery("select count(s) from SmallnessBatchNumber s where s.makeStatus = 2 and s.isDeliverGoods != 0 and s.bigBatchNumber.id = :id");
			queryCount.setParameter("id", bigBatchNumberId);
			page.setLastPage(Integer.parseInt(queryCount.uniqueResult().toString()));
		}
		query.setParameter("id", bigBatchNumberId);
		query.setFirstResult((page.getCurrentPage() -1) * page.getPageSize()); 
		query.setMaxResults(page.getPageSize());
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<SmallnessBatchNumber> findIsDeliverByBig(int bigBatchNumberId) {
		Query query = sessionFactory.getCurrentSession().createQuery("select sml from SmallnessBatchNumber sml where sml.bigBatchNumber.id = :id and sml.isDeliverGoods=1");
		query.setParameter("id", bigBatchNumberId);
		return query.list();
	}
	
	/**
	 * ���� ������ ��ѯ������ δ��������
	 */
	public long isDeliverGoodsCount(int bigBatchNumberId) {
		Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from SmallnessBatchNumber s where s.isDeliverGoods <= 1 and s.makeStatus <= 1 and s.bigBatchNumber.id = :id ");
		query.setParameter("id", bigBatchNumberId);
		return (Long)query.uniqueResult();
	}

	public SmallnessBatchNumber findBySmallNo(String smallBatchNumber) {
		Query query = sessionFactory.getCurrentSession().createQuery("select s from SmallnessBatchNumber s where s.smallnessBatchNumber = :smallBatchNo");
		query.setParameter("smallBatchNo", smallBatchNumber);
		return (SmallnessBatchNumber) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<SmallnessBatchNumber> findHeBin(Page page) {
		if(page.getLastPage() <= 1) {
			Query queryCount = sessionFactory.getCurrentSession().createQuery("select count(p) from SmallnessBatchNumber p where p.makeStatus = 0 and p.isValid = 0 and p.isRemakes = 2");
			page.setLastPage(Integer.parseInt(queryCount.uniqueResult().toString()));
		}
		
		Query query =sessionFactory.getCurrentSession().createQuery("select p from SmallnessBatchNumber p where p.makeStatus = 0 and p.isValid = 0 and p.isRemakes = 2 order by p.precedence,p.dateTime");
		query.setFirstResult((page.getCurrentPage()-1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		return query.list();
	}

	public List<SmallnessBatchNumber> findFlowByBig(int bigBatchNumberId, Page page) {
		if(page.getLastPage() <= 1) {
			Query queryCount = sessionFactory.getCurrentSession().createQuery("select count(p) from SmallnessBatchNumber p where p.bigBatchNumber.id = :bid and p.isDistribute != 4");
			queryCount.setParameter("bid", bigBatchNumberId);
			page.setLastPage(Integer.parseInt(queryCount.uniqueResult().toString()));
		}
		
		Query query =sessionFactory.getCurrentSession().createQuery("select p from SmallnessBatchNumber p where p.bigBatchNumber.id = :bid and p.isDistribute != 4");
		query.setParameter("bid", bigBatchNumberId);
		query.setFirstResult((page.getCurrentPage()-1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		return query.list();
	}
	
	/**
	 * HQL��������
	 * @param hql
	 * @return
	 */
	public int countSmall(String hql){
		return Integer.parseInt(sessionFactory.getCurrentSession().createQuery(hql).uniqueResult().toString());
	}
	
	
	
	
	
	
	
	
	
	
	
	

}