package com.hovto.chepai.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.BusinessDepartment;
import com.hovto.chepai.model.PostAddress;

@Component
public class PostAddressDao {
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	public void add(PostAddress postAddress){
		sessionFactory.getCurrentSession().save(postAddress);
	}
	
	public void modify(PostAddress postAddress){
		sessionFactory.getCurrentSession().update(postAddress);
	}
	
	public PostAddress find(int id){
		return (PostAddress) sessionFactory.getCurrentSession().get(PostAddress.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<PostAddress> findAllPostAddress(){
		return sessionFactory.getCurrentSession().createQuery("from PostAddress pas order by pas.place").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PostAddress> findPostAddressbyDepartment(String department){
		return sessionFactory.getCurrentSession().createQuery("from PostAddress pas where pas.department = :pram").setParameter("pram", department).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PostAddress> findPostAddressbyPlace(String place){
		return sessionFactory.getCurrentSession().createQuery("from PostAddress pas where pas.place = :pram").setParameter("pram", place).list();
	}
}
