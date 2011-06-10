package com.hovto.chepai.dao;

import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import com.hovto.chepai.model.InputRegister;
import com.hovto.chepai.model.Reserve;
import com.hovto.chepai.model.SemifinishedProductType;

@Component
public class ReserveDao {
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public void add(Reserve reserve) {
		sessionFactory.getCurrentSession().save(reserve);
	}
	
	public void delete(Reserve reserve) {
		sessionFactory.getCurrentSession().delete(this.find(reserve));
	}
	
	public void modify(Reserve reserve) {
		sessionFactory.getCurrentSession().update(reserve);
	}
	
	public Reserve find(Reserve reserve) {
		return (Reserve) sessionFactory.getCurrentSession().get(Reserve.class, reserve.getId());
	}
	
	/**
	 * 根据类型ID查找
	 * @param typeId
	 * @return
	 */
	public Reserve findbytypeId(int id){
		return (Reserve) sessionFactory.getCurrentSession().createQuery("select p from Reserve p where p.semifinishedProductType.id = :id").setParameter("id", id).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Reserve> findList() {
		return sessionFactory.getCurrentSession().createQuery("select p from Reserve p").list();
	}
}
