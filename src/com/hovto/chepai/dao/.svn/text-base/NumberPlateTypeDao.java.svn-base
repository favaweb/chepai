package com.hovto.chepai.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.NumberPlateType;


@Component
public class NumberPlateTypeDao {
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	public void add(NumberPlateType numberPlateType) {
		sessionFactory.getCurrentSession().save(numberPlateType);
	}
	
	public void delete(NumberPlateType numberPlateType) {
		sessionFactory.getCurrentSession().delete(this.find(numberPlateType));
	}
	
	public void modify(NumberPlateType numberPlateType) {
		sessionFactory.getCurrentSession().update(numberPlateType);
	}
	
	public NumberPlateType find(NumberPlateType numberPlateType) {
		return (NumberPlateType) sessionFactory.getCurrentSession().get(NumberPlateType.class, numberPlateType.getId());
	}
	public NumberPlateType findByName(NumberPlateType numberPlateType){
		return (NumberPlateType) sessionFactory.getCurrentSession().get(NumberPlateType.class, numberPlateType.getTypeName());
	}
	@SuppressWarnings("unchecked")
	public List<NumberPlateType> findList() {
		return sessionFactory.getCurrentSession().createQuery("select p from NumberPlateType p").list();
	}
	
	public List<NumberPlateType> findByType(int type){
		List<NumberPlateType> list= sessionFactory.getCurrentSession().createQuery("select p from NumberPlateType p where p.semifinishedProductTypes.type =:type").setParameter("type", type).list();
		return list;
	}
}
