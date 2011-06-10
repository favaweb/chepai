package com.hovto.chepai.server;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.NumberPlateTypeDao;
import com.hovto.chepai.model.NumberPlateType;


@Component
@Transactional
public class NumberPlateTypeServer {
	@Resource
	private NumberPlateTypeDao plateTypeDao;
	
	public List findAll(){
		return plateTypeDao.findList();
	}
	public boolean add(NumberPlateType numberPlateType){
		try {
			plateTypeDao.add(numberPlateType);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	//根据id查找名称
	public NumberPlateType findById(NumberPlateType numberPlateType){
		return plateTypeDao.find(numberPlateType);
	}
	public Map queryAll(){
		List list=plateTypeDao.findList();
		Map map=new HashMap();
		for(int i=0;i<list.size();i++){
			NumberPlateType numberPlateType=(NumberPlateType)list.get(i);
			map.put(numberPlateType.getTypeName(), numberPlateType.getId());
		}
		return map;
	}
	public List<NumberPlateType> findByType(int type){
		return plateTypeDao.findByType(type);
	}
	
	
}
