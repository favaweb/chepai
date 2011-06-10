package com.hovto.chepai.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.Users;
import com.hovto.chepai.tool.Page;

@Component
public class UserDao {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	public void add(Users user) {
		sessionFactory.getCurrentSession().save(user);
	}

	public void delete(Users user) {
		sessionFactory.getCurrentSession().delete(this.find(user));
	}

	public void modify(Users user) {
		sessionFactory.getCurrentSession().update(user);
	}

	public Users find(Users user) {
		return (Users) sessionFactory.getCurrentSession().get(Users.class,
				user.getId());
	}

	/**
	 * 根据 可以登录的 账户
	 * @param account
	 * @return
	 */
	public Users find(String account) {
		return (Users) sessionFactory.getCurrentSession().createQuery(
				"select u from Users u where u.account = :account and u.roler > 0 and u.isvalid = 1").setParameter(
				"account", account).uniqueResult();
	}
	
	public Users findAccount(String account) {
		return (Users) sessionFactory.getCurrentSession().createQuery(
				"select u from Users u where u.account = :account ").setParameter(
				"account", account).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Users> findList(Page page) {
		Query query = sessionFactory.getCurrentSession().createQuery("select u from Users u ");
		Query queryCount = sessionFactory.getCurrentSession().createQuery("select count(u) from Users u ");
		
		page.setLastPage(Integer.parseInt(queryCount.uniqueResult().toString()));
		
		query.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> findListByCondition(Page page,int roler,int daynight,String username){
		String hql = "select u from Users u ";
		if(roler != -1){
			hql += "where u.roler = "+roler;
		}
		if(daynight != -1){
			if(roler != -1) 
				hql+= " and ";
			else
				hql+= " where ";
			hql += " u.dayNight = "+daynight;
		}
		if(!username.equals("")||username!=null){
			if(roler != -1 || daynight != -1){
				hql += " and ";
			}else
				hql += " where ";
			hql += "u.name like '%"+username+"%'";
		}
		String counthql = hql.replaceFirst("u", "count(u)");
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		Query countQuery = sessionFactory.getCurrentSession().createQuery(counthql);
		
		page.setLastPage(Integer.parseInt(countQuery.uniqueResult().toString()));
		
		query.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		return query.list();
	}
	
	

	/**
	 * 根据权限查询 到所有用户
	 * 
	 * @param findPrivilege
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Users> findByPrivilege(String findPrivilege) {
		String sql = "select * from users as u where exists (select userId from  user_roler ur where exists (select rolerId from roler_privilege as rp where exists ( select 1 from Privilege as p where  p.id=rp.privilegeId and model = :privilege) and ur.rolerId=rolerId) and u.id=ur.userId) and u.roler < 2 and u.isvalid = 1";
		
		
		Date d = new Date();
		
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		if(hour >= 8 && hour < 16) {
			sql += " and u.dayNight = 1";
		} else {
			sql += " and u.dayNight = 0";
		}
		
		return sessionFactory
				.getCurrentSession()
				.createSQLQuery(sql)
				.addEntity(Users.class)
				.setParameter("privilege", findPrivilege).list();
	}
	
	/**
	 * 根据权限查询 所有用户
	 * @param findPrivilege
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Users> findAllPrivilege(String findPrivilege) {
		String sql = "select * from users as u where exists (select userId from  user_roler ur where exists (select rolerId from roler_privilege as rp where exists ( select 1 from Privilege as p where  p.id=rp.privilegeId and model = :privilege) and ur.rolerId=rolerId) and u.id=ur.userId) and u.roler < 2 and u.isvalid = 1";
		
		return sessionFactory
		.getCurrentSession()
		.createSQLQuery(sql)
		.addEntity(Users.class)
		.setParameter("privilege", findPrivilege).list();
	}

	@SuppressWarnings("unchecked")
	public List<Users> findAll() {
		Query query = sessionFactory.getCurrentSession().createQuery("select u from Users u where u.roler < 2 and u.isvalid = 1");
		return query.list();
	}


}
