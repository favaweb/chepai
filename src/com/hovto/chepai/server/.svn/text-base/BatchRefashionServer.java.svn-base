package com.hovto.chepai.server;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.BatchRefashionDao;
import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.BatchRefashion;
import com.hovto.chepai.model.SmallnessBatchNumber;
import com.hovto.chepai.model.TaskAllocation;

@Component
@Transactional
public class BatchRefashionServer {
	@Resource
	private BatchRefashionDao batchRefashionDao;
	@Resource
	private SmallnessBatchNumberServer smallnessBatchNumberServer;
	@Resource
	private TaskAllocationServer taskAllocationServer;
	
	public boolean save(BatchRefashion batchRefashion){
		try {
			batchRefashionDao.add(batchRefashion);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public BatchRefashion findBySmallId(int smallId) {
		return batchRefashionDao.findBySmallId(smallId);
	}
	
	public void delete(BatchRefashion batchRefashion) {
		batchRefashionDao.delete(batchRefashion);
	}
	
	public boolean update(BatchRefashion batchRefashion){
		try {
			batchRefashionDao.modify(batchRefashion);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public BatchRefashion findById(int id){
		return batchRefashionDao.find(new BatchRefashion(id));
	}
	public List<BatchRefashion> findAll(){
			return batchRefashionDao.findList();
	}
	public List<BatchRefashion> findByStatus(int status) {
		return batchRefashionDao.findByStatus(status);
	}

	public void back(int[] batchRefashIds) {
		if(batchRefashIds!=null){
			for(int i=0;i<batchRefashIds.length;i++){
				BatchRefashion batchRefashion = this.findById(batchRefashIds[i]);
				SmallnessBatchNumber smallnessBatchNumber=batchRefashion.getSmallnessBatchNumber();
				smallnessBatchNumber.setIsDistribute(2);
				smallnessBatchNumberServer.update(smallnessBatchNumber);//把需要重制的子批号变为整批补制
				
				this.delete(batchRefashion);
				
				List<TaskAllocation> taskList=taskAllocationServer.findBackRemake(smallnessBatchNumber.getId());
				for(int j=0;j<taskList.size();j++){
					TaskAllocation taskAllocation=taskList.get(j);
					taskAllocation.setStatus(0);
					taskAllocationServer.update(taskAllocation);
				}
			}
		} else
			throw new ChePaiException("请选择要回退的小批号!");
	}
}
