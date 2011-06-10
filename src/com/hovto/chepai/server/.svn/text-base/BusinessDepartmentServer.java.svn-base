package com.hovto.chepai.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.BusinessDepartmentDao;
import com.hovto.chepai.model.BusinessDepartment;

@Component
@Transactional
public class BusinessDepartmentServer {
	@Resource
	private BusinessDepartmentDao bDepartmentDao;
	
	public List findAll(){
		return bDepartmentDao.findList();
	}
	public Map queryAll(){
		List list=bDepartmentDao.findList();
		Map map=new HashMap();
		for(int i=0;i<list.size();i++){
			BusinessDepartment bDepartment=(BusinessDepartment)list.get(i);
			map.put(bDepartment.getDepartment(),bDepartment.getId());
		}
		return map;
	}
	public boolean add(BusinessDepartment bDepartment){
		try {
			bDepartmentDao.add(bDepartment);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<BusinessDepartment> findList(){
		return bDepartmentDao.findList();
	}
}
