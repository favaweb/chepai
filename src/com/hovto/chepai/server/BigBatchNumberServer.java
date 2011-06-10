package com.hovto.chepai.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.BigBatchNumberDao;
import com.hovto.chepai.dao.SmallnessBatchNumberDao;
import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.BigBatchNumber;
import com.hovto.chepai.model.SmallnessBatchNumber;
import com.hovto.chepai.tool.Page;

@Component
@Transactional
public class BigBatchNumberServer {
	@Resource
	private BigBatchNumberDao bigBatchNumberDao;
	@Resource
	private SmallnessBatchNumberDao smallnessBatchNumberDao;
	@Resource
	private SmallnessBatchNumberServer smallnessBatchNumberServer;
	
	public boolean add(BigBatchNumber bigBatchNumber){
		try {
			bigBatchNumberDao.add(bigBatchNumber);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<BigBatchNumber> findIsDeliverGoods(String bigNo,String startTime, String endTime, String place,
			int isDeliverGoods, Page page) {
		String sql = "select s from BigBatchNumber s ";
		String countSql = "select count(s) from BigBatchNumber s ";
		if(isDeliverGoods == 0){
			sql += " where s.isDeliverGoods = 1 ";
			countSql += " where s.isDeliverGoods = 1 ";
		} else {
			sql += " where s.isDeliverGoods = :isDeliverGoods ";
			countSql += " where s.isDeliverGoods = :isDeliverGoods ";
		}
		Date startDate = null;
		Date endDate = null;
		if(startTime != null && !startTime.trim().equals("")) {
			try {
				startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);
				sql += " and s.sendTime >= :startDate ";
				countSql += " and s.sendTime >= :startDate ";
				
				if(endTime != null && !endTime.trim().equals(""))  {
					sql += " and s.sendTime < :endDate ";
					countSql += " and s.sendTime < :endDate ";
					Date temp = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);
					
					Calendar c = Calendar.getInstance();
					c.setTime(temp);
					c.set(Calendar.DATE,c.get(Calendar.DATE) + 1);
					
					endDate = c.getTime();
				}
				
			} catch (ParseException e) {
				throw new ChePaiException("时间类型不对");
			}
		} 
		
		
		if(place != null && !"".equals(place)) {
			sql += " and s.place = :place ";
			countSql += " and s.place = :place ";
		}
		if(bigNo != null && !"".equals(bigNo)) {
			sql += " and s.bigBattchNumber = :bigNo ";
			countSql += " and s.bigBattchNumber = :bigNo ";
		}
		sql += " order by s.precedence, s.sendTime";
		
		return bigBatchNumberDao.findIsDeliverGoods(sql, countSql, startDate, endDate, place, isDeliverGoods,bigNo,page);
	}
	
	public List<BigBatchNumber> findAll(){
		return bigBatchNumberDao.findList();
	}
	
	public BigBatchNumber findByBigBatchNumber(String bigBatchNumber){
		return bigBatchNumberDao.findByBigBatchNumber(bigBatchNumber);
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<BigBatchNumber> findByMakeStatus(Page page) {
		return bigBatchNumberDao.findByMakeStatus(page);
	}

	public void goFlows(int[] goFlows) {
		for(int bid : goFlows) {
			List<SmallnessBatchNumber> smalls = smallnessBatchNumberDao.findByMakeStatus(bid);
			int[] smallIds = new int[smalls.size()];
			for(int i=0; i<smalls.size(); i++) {
				smallIds[i] = smalls.get(i).getId();
			}
			smallnessBatchNumberServer.goFlows(smallIds);
		}
	}
	
	public BigBatchNumber find(BigBatchNumber bigBatchNumber){
		return bigBatchNumberDao.find(bigBatchNumber);
	}
	
	public String findDepartment(int id){
		try {
			return bigBatchNumberDao.findDepartment(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void upGrade(int bid) {
		BigBatchNumber bigBatch = bigBatchNumberDao.find(new BigBatchNumber(bid));
		List<SmallnessBatchNumber> smalls = smallnessBatchNumberDao.findBybigBatchNumber(bid);
		for(SmallnessBatchNumber s : smalls) {
			if(s.getPrecedence() > 1) 
				s.setPrecedence(s.getPrecedence() - 1);
			smallnessBatchNumberDao.modify(s);
		}
		if(bigBatch.getPrecedence() > 1) 
			bigBatch.setPrecedence(bigBatch.getPrecedence() - 1);
		bigBatchNumberDao.modify(bigBatch);
	}
	
	
	
	
	
	
}
