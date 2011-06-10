package com.hovto.chepai.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.InputRegister;
import com.hovto.chepai.model.StatisticsInput;
import com.hovto.chepai.tool.Page;

@Component
public class InputRegisterDao {
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public void add(InputRegister inputRegister) {
		sessionFactory.getCurrentSession().save(inputRegister);
	}
	
	public void delete(InputRegister inputRegister) {
		sessionFactory.getCurrentSession().delete(this.find(inputRegister));
	}
	
	public void modify(InputRegister inputRegister) {
		sessionFactory.getCurrentSession().update(inputRegister);
	}
	
	public InputRegister find(InputRegister inputRegister) {
		return (InputRegister) sessionFactory.getCurrentSession().get(InputRegister.class, inputRegister.getId());
	}
	
	@SuppressWarnings("unchecked")
	public List<InputRegister> findList() {
		return sessionFactory.getCurrentSession().createQuery("select p from InputRegister p").list();
	}
	
	/**
	 * 根据出/入库查询
	 * @param purpose
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InputRegister> findListbyPurpose(int type){
		return sessionFactory.getCurrentSession().createQuery("select p from InputRegister p where p.type= :type").setParameter("type", type).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<InputRegister> findListbyhql(String hql){
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<InputRegister> findListbyIn(String hql,Date startDate,Date endDate,int typeid){
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if(startDate!=null && endDate!=null){
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
		}
		if(typeid!=0){
			query.setParameter("typeid", typeid);
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<InputRegister> findListbyOut(String hql,Date startDate,Date endDate,int typeid,int purpose,String reductionNumber){
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if(startDate!=null && endDate!=null){
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
		}
		if(typeid!=0){
			query.setParameter("typeid", typeid);
		}
		if(purpose!=0){
			query.setParameter("purpose", purpose);
		}
		if(!reductionNumber.trim().equals("") && reductionNumber!=null){
			query.setParameter("reductionNumber", reductionNumber);
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<StatisticsInput> findListbySQL(String sql){
		return sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(StatisticsInput.class).list();
	}
	
	
	/**
	 * 查询出入库存当天合集统计
	 * @param sql
	 * @param startDate
	 * @param endDate
	 * @param typeid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<StatisticsInput> findListbySQL(String sql, Date startDate, Date endDate, int typeid){
		Query query=sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(StatisticsInput.class);
		if(startDate != null && endDate != null){
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
		}
		if(typeid != 0){
			query.setParameter("typeid", typeid);
		}
		return query.list();
	}
	
	

	
	/**
	 * 按条件分页查询入库明细
	 * @param hql
	 * @param startDate
	 * @param endDate
	 * @param inputRegister
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InputRegister> findListbyHQL(String hql,Date startDate,Date endDate,InputRegister inputRegister,Page page){
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if(startDate != null){
			query.setParameter("startDate", startDate);
			if(endDate !=null)
			query.setParameter("endDate", endDate);
		}
		if(inputRegister != null){
			if(inputRegister.getType()!= 0){
				query.setParameter("type", inputRegister.getType());
			}
			if(!inputRegister.getReductionNumber().equals("")){
				query.setParameter("reductionNumber", inputRegister.getReductionNumber());
			}
			if(inputRegister.getSemifinishedProductType().getId()!=0){
				query.setParameter("semifinishedProductType",inputRegister.getSemifinishedProductType());
			}
		}
		query.setMaxResults(page.getPageSize());
		query.setFirstResult((page.getCurrentPage()-1)*page.getPageSize());
		return query.list();
	}
	
	
	/**
	 * 计算条件查询结果的总数
	 * @param hql
	 * @param startDate
	 * @param endDate
	 * @param inputRegister
	 * @return
	 */
	public int countList(String hql,Date startDate,Date endDate,InputRegister inputRegister){
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if(startDate != null){
			query.setParameter("startDate", startDate);
			if(endDate !=null)
			query.setParameter("endDate", endDate);
		}
		if(inputRegister != null){
			if(inputRegister.getType()!= 0){
				query.setParameter("type", inputRegister.getType());
			}
			if(!inputRegister.getReductionNumber().equals("")){
				query.setParameter("reductionNumber", inputRegister.getReductionNumber());
			}
			if(inputRegister.getSemifinishedProductType().getId()!=0){
				query.setParameter("semifinishedProductType",inputRegister.getSemifinishedProductType());
			}
		}
		return Integer.parseInt(query.list().get(0).toString());
		
		
	}

}
