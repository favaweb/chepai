package com.hovto.chepai.server;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.IndividualOutputDao;
import com.hovto.chepai.dao.NumberPlateDao;
import com.hovto.chepai.dao.OperatorDao;
import com.hovto.chepai.dao.RemakesDao;
import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.FlowType;
import com.hovto.chepai.model.IndividualOutput;
import com.hovto.chepai.model.NumberPlate;
import com.hovto.chepai.model.Operator;
import com.hovto.chepai.model.OutputStats;
import com.hovto.chepai.model.SmallnessBatchNumber;
import com.hovto.chepai.model.Users;

@Component
@Transactional
public class IndividualOutputServer {
	@Resource
	private IndividualOutputDao individualOutputDao;
	@Resource
	private OperatorDao operatorDao;
	@Resource
	private NumberPlateDao numberPlateDao;
	@Resource
	private RemakesDao remakesDao;
	
	/**
	 * 
	 * @param FlowType
	 * @param smallnessBatchNumber
	 * @return
	 */
	public boolean save(FlowType flowType,SmallnessBatchNumber smallnessBatchNumber){
		try {
			//根据小批号 流程类型 查询到 对应流程的操作人
			List<Operator> operatorList=operatorDao.findBySBatchNumberId(smallnessBatchNumber.getId(),flowType.getId());
			Operator operator=null;
			if(operatorList.size()>0){
				double quantity=this.loadOutput(flowType, smallnessBatchNumber);//获取使用半成品数量
				operator=operatorList.get(0);
				String users=operator.getOperater();
				String[] userIds=users.split(",");
				for(int i=0;i<userIds.length;i++){
					IndividualOutput individualOutput=new IndividualOutput();
					
					individualOutput.setFlowType(flowType);//
					individualOutput.setSmallnessBatchNumber(smallnessBatchNumber);//
					//individualOutput.setBigBatchNumber(bigBatchNumber)//
					individualOutput.setProductTime(new Date());
					individualOutput.setUser(new Users(Integer.parseInt(userIds[i])));
					individualOutput.setOutput(Math.round(quantity/userIds.length * 100.0) / 100.0);
					individualOutputDao.add(individualOutput);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 统计 小批号在 当前流程的 产量
	 * @param flowType
	 * @param smallnessBatchNumber
	 * @return
	 */
	public double loadOutput(FlowType flowType,SmallnessBatchNumber smallnessBatchNumber){
		double quantity=0.0;
		List<NumberPlate> numberPlateList=null;
		if(smallnessBatchNumber.getIsRemakes()==1){//当前为补制时在补牌表里查车牌
			numberPlateList=remakesDao.findFinish(smallnessBatchNumber.getId());
		}else{
			numberPlateList=numberPlateDao.findBySmallnessBatchId(smallnessBatchNumber.getId());
		}
		if(flowType.getId()==2||flowType.getId()==4){//判断流程是洗牌，正压
			for(int i=0;i<numberPlateList.size();i++){
				NumberPlate numberPlate=numberPlateList.get(i);
				//判断是否为大型汽车号牌和摩托车
				if(numberPlate.getNumberPlateType().getType()== 2){
					quantity+=numberPlate.getNumberPlateType().getNumber();
				}
			}
		}else{
			for(int i=0;i<numberPlateList.size();i++){
				NumberPlate numberPlate=numberPlateList.get(i);
				quantity+=numberPlate.getNumberPlateType().getNumber();
			}
		}
		return quantity;
	}
	/**
	 * 统计个人产量
	 * @param output	人员id
	 * @param biginTime	开始时间
	 * @param endTime 结束时间
	 * @return List<IndividualOutput>
	 */
	public List<IndividualOutput> findByUserId(int userId,String biginTime,String endTime) {
		if(biginTime==null||"".equals(biginTime)){
			try {
				SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
				Calendar   c   =   Calendar.getInstance(); 
				c.set(c.YEAR,   c.get(Calendar.DAY_OF_YEAR)); 
				c.set(c.MONTH,  c.get(Calendar.DAY_OF_MONTH)-1); 
				biginTime=sf.format( c.getActualMinimum(c.DAY_OF_MONTH));
				endTime=sf.format(c.getActualMaximum(c.DAY_OF_MONTH));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return individualOutputDao.findByUserId(userId, biginTime, endTime);
	}
	public List<IndividualOutput> findByFolwTypeOutput(int flowTypeId,int userId,String biginTime,String endTime) {
		/*if(biginTime==null||"".equals(biginTime)){
			try {
				SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
				Calendar   c   =   Calendar.getInstance(); 
				c.set(c.YEAR,   new Date().getYear()); 
				c.set(c.MONTH,   new Date().getMonth()-1); 
				biginTime=sf.format( c.getActualMinimum(c.DAY_OF_MONTH));
				endTime=sf.format(c.getActualMaximum(c.DAY_OF_MONTH));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		return individualOutputDao.findByFolwTypeOutput(flowTypeId, userId, biginTime, endTime);
	}
	
	
	/**
	 * 根据时间段 查询流程产量统计
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<OutputStats> findOutputStats(String biginTime, String endTime) {
		if(biginTime == null || biginTime.equals("")) throw new ChePaiException("统计的开始时间不允许为空<br/><a href='javascript:window.history.go(-1);'>返回操作</a>");
		
		java.sql.Date begin = null;
		java.sql.Date tempDate = null;
		java.sql.Date end = null;
		try {
			begin = java.sql.Date.valueOf(biginTime);
			
			if(endTime != null && !endTime.equals("")) {
				tempDate = java.sql.Date.valueOf(endTime);
				
				Calendar c = Calendar.getInstance();
				c.setTime(tempDate);
				c.set(Calendar.DATE,c.get(Calendar.DATE) + 1);
				
				end = new java.sql.Date(c.getTime().getTime());
			}
		} catch (Exception e) {
			throw new ChePaiException("时间类型不对<br/><a href='javascript:window.history.go(-1);'>返回操作</a>");
		}
		return individualOutputDao.findOutputStats(begin, end);
	}
	
	public List<OutputStats> findPersonStats(String biginTime, String endTime) {
		if(biginTime == null ||  biginTime.equals("")) throw new ChePaiException("统计的开始时间不允许为空<br/><a href='javascript:window.history.go(-1);'>返回操作</a>");
		
		java.sql.Date begin = null;
		java.sql.Date tempDate = null;
		java.sql.Date end = null;
		try {
			begin = java.sql.Date.valueOf(biginTime);
			
			if(endTime != null && !endTime.equals("")) {
				tempDate = java.sql.Date.valueOf(endTime);
				
				Calendar c = Calendar.getInstance();
				c.setTime(tempDate);
				c.set(Calendar.DATE,c.get(Calendar.DATE) + 1);
				
				end = new java.sql.Date(c.getTime().getTime());
			}
		} catch (Exception e) {
			throw new ChePaiException("时间类型不对<br/><a href='javascript:window.history.go(-1);'>返回操作</a>");
		}
		return individualOutputDao.findPersonStats(begin, end);

	}

	public void delBySmallAndFlow(int smallId, int flow) {
		individualOutputDao.delBySmallAndFlow(smallId, flow);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
