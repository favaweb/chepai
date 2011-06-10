package com.hovto.chepai.server;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.RemakesDao;
import com.hovto.chepai.model.NumberPlate;
import com.hovto.chepai.model.Remakes;

@Component
@Transactional
public class RemakesServer {
	@Resource
	private RemakesDao remakesDao;
	
	public boolean add(Remakes remakes){
		try {
			remakesDao.add(remakes);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean updateRemakes(Remakes remakes){
		try {
			remakesDao.modify(remakes);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public List<NumberPlate> findBySmallnessBatchNumber(int smallnessBatchNumberId){
		return remakesDao.findBySmallnessBatchNumber(smallnessBatchNumberId);
	}
	public List<Remakes> findResultRemakes(int smallnessBatchNumberId){
		return remakesDao.findResultRemakes(smallnessBatchNumberId);
	}
	
	public void delete(Remakes remakes) {
		remakesDao.delete(remakes);
	}
	
	public Remakes findBySbatchNumberIdAndlicensePlateNumber(int smallnessBatchNumberId,int licensePlateNumberId){
		Remakes remakes=null;
		List<Remakes> remakesList=remakesDao.findBySbatchNumberIdAndlicensePlateNumber(smallnessBatchNumberId, licensePlateNumberId);
		if(remakesList.size()>0){
			remakes=(Remakes)remakesList.get(0);
		}
		return remakes;
	}
	
	/**
	 * 查询出全部没有质检完的重制车牌
	 * @return
	 */
	public List<Remakes> findByState(){
		return remakesDao.findByState();
	}
	/**
	 * 根据补牌id查找数据
	 * @param id
	 * @return
	 */
	public Remakes findById(int id){
		return remakesDao.find(new Remakes(id));
	}
	
	public List<Remakes> findByPlateId(int plateId){
		return remakesDao.findByPlateId(plateId);
	}
	
	public List<Remakes> queryList(){
		return remakesDao.queryList();
	}
	public boolean findSmallIsRemakes(int smallId) {
		long exit = remakesDao.findSmallIsRemakes(smallId);
		if(exit <= 0) 
			return false;
		return true;
	}
	
	
	
	
	
	
	
}
