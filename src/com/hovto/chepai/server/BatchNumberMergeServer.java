package com.hovto.chepai.server;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.BatchNumberMergeDao;
import com.hovto.chepai.model.BatchNumberMerge;

@Component
@Transactional
public class BatchNumberMergeServer {

	@Resource
	private BatchNumberMergeDao batchNumberMergeDao;
	
	/**
	 * 根据合并的小批号 查看 所有合并的打批号
	 * @param smallBatchId
	 * @return
	 */
	public List<BatchNumberMerge> findMergerBySmallId(int smallBatchId) {
		return batchNumberMergeDao.findBySmallnessBatchNumber(smallBatchId);
	}
	public void add(BatchNumberMerge batchNumberMerge){
		batchNumberMergeDao.add(batchNumberMerge);
	}
}
