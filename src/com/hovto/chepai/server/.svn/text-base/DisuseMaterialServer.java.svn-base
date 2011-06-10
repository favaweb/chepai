package com.hovto.chepai.server;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.DisuseMaterialDao;
import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.DisuseMaterial;
import com.hovto.chepai.model.Stats;
import com.hovto.chepai.tool.Page;

@Component
@Transactional
public class DisuseMaterialServer {
	@Resource
	private DisuseMaterialDao disuseMaterialDao;
	
	public boolean add(DisuseMaterial disuseMaterial){
		try {
			disuseMaterialDao.add(disuseMaterial);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	

	public void delete(DisuseMaterial d) {
		disuseMaterialDao.delete(d);
	}
	
	public void delBySmallandPlate(int smallId, int numberPlateId) {
		disuseMaterialDao.delBySmallandPlate(smallId, numberPlateId);
	}
	
	public List<DisuseMaterial> findBySmallandPlate(int smallId, int numberPlateId) {
		return disuseMaterialDao.findBySmallandPlate(smallId, numberPlateId);
	}
	/*	public List<DisuseMaterial> findByFolwTypeOutput(int flowTypeId,int userId,String biginTime,String endTime) {
		return disuseMaterialDao.findByFolwTypeOutput(flowTypeId, userId, biginTime, endTime);
	}*/
	
	
	
	@SuppressWarnings("deprecation")
	public List<DisuseMaterial> findByFolwTypeOutput(int flowTypeId,int userId,String biginTime,String endTime,Page page) throws ParseException {
		java.util.Date date = null; 
		String hql = "select d from DisuseMaterial d ";
		if(endTime == null || endTime.trim().equals("")){
			date = new java.util.Date();
			endTime = date.toLocaleString();
		}
		System.out.println(biginTime);
		hql += " where d.operateTime > '"+biginTime + "' and d.operateTime <'" + endTime+"'";
		
		if(flowTypeId>0){
			hql+=" and d.flowType="+flowTypeId;
		}
		if(userId>0){
			hql+=" and d.operator like '%"+userId+"%'";
		}
		page.setLastPage(disuseMaterialDao.findCountByFolwTypeOutput(hql, page));
		return disuseMaterialDao.findByFolwTypeOutput(hql,page);
	}
	public List<Stats> stats(String biginTime, String endTime) {
		if(biginTime == null || endTime == null || biginTime.equals("") || endTime.equals("")) throw new ChePaiException("统计的开始时间跟结束时间不允许为空");
		
		Date begin = null;
		Date tempDate = null;
		Date end = null;
		try {
			begin = Date.valueOf(biginTime);
			tempDate = Date.valueOf(endTime);
			
			Calendar c = Calendar.getInstance();
			c.setTime(tempDate);
			c.set(Calendar.DATE,c.get(Calendar.DATE) + 1);
			end = new Date(c.getTime().getTime());
			
		} catch (Exception e) {
			throw new ChePaiException("时间类型不对");
		}
		List<Stats> statses =  disuseMaterialDao.stats(begin, end);
		int count = 0;
		List<Stats> temp = new ArrayList<Stats>();
		for(Stats s : statses) {
			if(s.getMarType() == 1) 
				count += s.getStats();
			else if(s.getMarType() == 3)
				count += s.getStats();
			else 
				temp.add(s);
		}
		if(count != 0)
			temp.add(new Stats(1, count));
		return temp;
	}
	
	/*public List<DisuseMaterial> ledgerStats1(String biginDate,String endDate,int flowTypeId,int marType,String operator){
		String hql="select count(*) as amount,flowType,marType from dbo.DisuseMaterial where 1=1";
		if(biginDate!=null&&!"".equals(biginDate)){
			hql+=" and operateTime>:biginDate and operateTime<:endDate";
			if(endDate==null||endDate.equals("")){
				endDate=(new java.util.Date()).toString();
			}
		}
		if(flowTypeId>0){
			hql+=" and flowType=:flowTypeId";
		}
		if(marType>0){
			hql+=" and marType=:marType";
		}
		if(operator!=null&&!"".equals(operator)){
			hql+=" and operator=:operator";
		}
		hql+=" group by flowType,marType";
		return disuseMaterialDao.statBySql(hql, biginDate, endDate, flowTypeId, marType, operator);
	}*/
	public List<DisuseMaterial> ledgerStats(String biginDate,String endDate){
		String hql="select count(*) as amount,flowType,marType from dbo.DisuseMaterial where 1=1";
		if(biginDate!=null&&!"".equals(biginDate)){
			hql+=" and operateTime>:biginDate and operateTime<:endDate";
			if(endDate==null||endDate.equals("")){
				endDate=(new java.util.Date()).toString();
			}
		}
		hql+=" group by flowType,marType";
		return disuseMaterialDao.statBySql(hql, biginDate, endDate, 0, 0, null);
	}
	
}
