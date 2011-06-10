package com.hovto.chepai.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.BatchNumberMerge;
import com.hovto.chepai.model.SmallnessBatchNumber;

@Component
public class BatchNumberMergeDao {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	public void add(BatchNumberMerge batchNumberMerge) {
		sessionFactory.getCurrentSession().save(batchNumberMerge);
	}

	/**
	 * 根据合并的小批号 查看 所有合并的打批号
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BatchNumberMerge> findBySmallnessBatchNumber(int id) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"select b from BatchNumberMerge b where b.smallnessBatchNumber.id = :id").setParameter("id", id)
				.list();
	}

	
	public SmallnessBatchNumber findByBigBatch(int bigId) {
		BatchNumberMerge bm = ((BatchNumberMerge)sessionFactory.getCurrentSession().createQuery("select b from BatchNumberMerge b where b.bigBatchNumber.id = :id").setParameter("id", bigId).uniqueResult());
		if(bm == null) return null;
		else return bm.getSmallnessBatchNumber();
	}
	
	
	
	
	
	
	
	
	
	
	
}
