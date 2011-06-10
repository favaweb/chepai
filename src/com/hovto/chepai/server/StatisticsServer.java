package com.hovto.chepai.server;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.FinishedProductDao;
import com.hovto.chepai.dao.InputRegisterDao;
import com.hovto.chepai.dao.MaterialDao;
import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.MonthfinishedProduct;
import com.hovto.chepai.model.StatisticsFinishedProducts;
import com.hovto.chepai.model.StatisticsInput;
import com.hovto.chepai.model.StatisticsMaterial;
import com.sun.jmx.snmp.Timestamp;

/**
 * 统计业务层类
 * 
 * @author Administrator
 * 
 */
@Component
@Transactional
public class StatisticsServer {
	@Resource
	private InputRegisterDao inputRegisterDao;
	@Resource
	private FinishedProductDao finishedProductDao;
	@Resource
	private MaterialDao materialDao;

	/**
	 * 废品统计
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<StatisticsMaterial> findMeterails(Date startDate, Date endDate) {
		String sql = "select count(*) as amount,min(dm.dayNight) as daynight,npt.typename from DisuseMaterial dm "
				+ "join NumberPlateType npt on npt.id = dm.numberPlateType ";
		Calendar c = Calendar.getInstance();
		java.sql.Date end = null;
		if (endDate == null) {
			sql += "where dm.operateTime >= ? ";
		}
		// 不为空 结束时间+1天
		else {
			c.setTime(endDate);
			c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
			endDate = c.getTime();
			end = new java.sql.Date(endDate.getTime());
			sql += "where dm.operateTime >= ? and dm.operateTime < ? ";
		}
		sql +=  "group by dm.dayNight , npt.typename order by npt.typename ";
		return materialDao.findStatisticsList(sql,new java.sql.Date(startDate.getTime()),end);
	}

	/**
	 * 成品发货统计-查询所有地区集合
	 * 
	 * @return
	 */
	public List<String> findPlaceList(int ordertype) {
		return finishedProductDao.findPlaceList(ordertype);
	}

	/**
	 * 成品发货统计（按副）
	 * 
	 * @param startDate
	 * @param endDate
	 * @param orderType
	 *            1，自选、补制 2，号段
	 * @return
	 */
	public List<StatisticsFinishedProducts> findisDeliverGoods(Date startDate,
			Date endDate, int orderType) {
		// 拼sql
		String sql = "select  count(*) as amount,sn.place,min(npt.typename) as typename from NumberPlate np "
				+ "join SmallnessBatchNumber sn on np.smallnessBatchNumber = sn.id "
				+ "join NumberPlateType npt on npt.id = np.numberPlateType "
				+ "join BigBatchNumber bn on bn.id = sn.bigBatchNumber "
				+ "where sn.isDeliverGoods = 2 and (bn.ordertype = ";
		// 如果为自选、补制
		if (orderType == 1)
			sql += "1 or bn.ordertype =2) ";
		// 如果为号段
		else if (orderType == 2)
			sql += "3) ";

			
		Calendar c = Calendar.getInstance();
		java.sql.Date end = null;
		if (endDate == null) {
			sql += "and sn.sendTime >= ? ";
		}
		// 不为空 结束时间+1天
		else {
			c.setTime(endDate);
			c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
			endDate = c.getTime();
			end = new java.sql.Date(endDate.getTime());
			sql += "and sn.sendTime >= ? and sn.sendTime < ? ";
		}


		sql += "group by sn.place , np.numberPlateType order by sn.place";

		return finishedProductDao.findAmountList(sql, new java.sql.Date(
				startDate.getTime()), end,
				orderType);

	}
	/**
	 * 成品发货统计(按箱)
	 * 
	 * @param startDate
	 * @param endDate
	 * @param orderType
	 *            1，自选、补制 2，号段
	 * @return
	 */
	public List<StatisticsFinishedProducts> findisDeliverGoodsbybox(Date startDate,
			Date endDate, int orderType) {
		// 拼sql
		String sql = "select  count(*) as amount,sn.place,sn.place as typename from SmallnessBatchNumber sn "
			+ "join BigBatchNumber bn on bn.id = sn.bigBatchNumber "
			+ "where sn.isDeliverGoods = 2 and (bn.ordertype = ";
		// 如果为自选、补制
		if (orderType == 1)
			sql += "1 or bn.ordertype =2) ";
		// 如果为号段
		else if (orderType == 2)
			sql += "3) ";
		
		
		Calendar c = Calendar.getInstance();
		java.sql.Date end = null;
		if (endDate == null) {
			sql += "and sn.sendTime >= ? ";
		}
		// 不为空 结束时间+1天
		else {
			c.setTime(endDate);
			c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
			endDate = c.getTime();
			end = new java.sql.Date(endDate.getTime());
			sql += "and sn.sendTime >= ? and sn.sendTime < ? ";
		}
		
		
		sql += "group by sn.place , sn.numberPlateType order by sn.place";
		System.out.println(sql);
		return finishedProductDao.findAmountList(sql, new java.sql.Date(
				startDate.getTime()), end,
				orderType);
		
	}

	/**
	 * 成品发货统计-查询有数据的类型集合
	 * 
	 * @return
	 */
	public List<String> findTypesList(Date startDate, Date endDate,
			int orderType) {
		// 拼sql
		String sql = "select  min(npt.typename) as typename from NumberPlate np "
				+ "join SmallnessBatchNumber sn on np.smallnessBatchNumber = sn.id "
				+ "join NumberPlateType npt on npt.id = np.numberPlateType "
				+ "join BigBatchNumber bn on bn.id = sn.bigBatchNumber "
				+ "where sn.isDeliverGoods = 2 and (bn.ordertype = ";
		// 如果为自选、补制
		if (orderType == 1)
			sql += "1 or bn.ordertype =2) ";
		// 如果为号段
		else if (orderType == 2)
			sql += "3) ";

		Calendar c = Calendar.getInstance();
		java.sql.Date end = null;
		
		if (endDate == null) {
			sql += "and sn.sendTime >= ? ";
		}
		// 不为空 结束时间+1天
		else {
			c.setTime(endDate);
			c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
			endDate = c.getTime();
			end = new java.sql.Date(endDate.getTime());
			sql += "and sn.sendTime >= ? and sn.sendTime < ? ";
		}
		sql += "group by np.numberPlateType";
		return finishedProductDao.findTypeList(sql, new java.sql.Date(startDate
				.getTime()), end, orderType);
	}

	/**
	 * 出入库条件统计
	 * 
	 * @param startDate
	 * @param endDate
	 * @param typeid
	 * @return
	 */
	public List<StatisticsInput> find(Date startDate, Date endDate, int typeid) {
		try {
			String sql = "select min(id) as id, createDate as date,semifinishedProductType as typeid,min(standard) as 'standard',"
					+ "(select sum(reductionAmount) from InputRegister where type=1 and createDate=ir.createDate and semifinishedProductType=ir.semifinishedProductType )as 'inCount' ,"
					+ "(select sum(reductionAmount) from InputRegister where type=2 and createDate=ir.createDate and semifinishedProductType=ir.semifinishedProductType )as 'outCount' "
					+ "from InputRegister as ir ";

			if (startDate != null || typeid != 0) {
				sql += "where ";
				if (endDate != null) {
					sql += "createDate >= :startDate and createDate < :endDate ";
				}
				if (endDate == null && startDate != null) {
					Calendar c = Calendar.getInstance();
					c.setTime(startDate);
					c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
					endDate = c.getTime();
					sql += "createDate >= :startDate and createDate < :endDate ";
				}
				if (typeid != 0) {
					if (startDate != null)
						sql += "and ";
					sql += "semifinishedProductType = :typeid ";
				}
			}
			sql += "group by createDate, semifinishedProductType"
					+ " order by createDate";
			;

			return inputRegisterDao.findListbySQL(sql, startDate, endDate,
					typeid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 出入库所有统计
	 * 
	 * @return
	 */
	public List<StatisticsInput> find() {
		List<StatisticsInput> list = null;
		try {
			String sql = "select min(id) as id, createDate as date,semifinishedProductType as typeid,min(standard) as 'standard',"
					+ "(select sum(reductionAmount) from InputRegister where type=1 and createDate=ir.createDate and semifinishedProductType=ir.semifinishedProductType )as 'inCount' ,"
					+ "(select sum(reductionAmount) from InputRegister where type=2 and createDate=ir.createDate and semifinishedProductType=ir.semifinishedProductType )as 'outCount' "
					+ "from InputRegister as ir "
					+ "group by createDate, semifinishedProductType"
					+ " order by createDate";
			list = inputRegisterDao.findListbySQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return list;

	}

	/**
	 * 车牌成品月统计表(旧)
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<MonthfinishedProduct> findfinishedProductByMonth(
			Date startDate, Date endDate, int orderType) {
		try {
			if (startDate != null && endDate != null) {
				return finishedProductDao.findfinishedProductByMonth(
						new java.sql.Date(startDate.getTime()),
						new java.sql.Date(endDate.getTime()), orderType);
			} else
				throw new ChePaiException("请选择月份！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// /**
	// * 统计所有出入库
	// */
	// public List<InputRegister> findStatistics(){
	// String sql ="SELECT min(id) as id, standard,min(purpose) as purpose,
	// SUM(reductionAmount) AS reductionAmount, semifinishedProductType,
	// createDate, type, min(remark) as remark, "
	// + "MIN(reductionNumber) AS reductionNumber FROM InputRegister ir "
	// + "WHERE (type = type) AND (createDate = createDate) AND
	// (semifinishedProductType = semifinishedProductType) "
	// + "GROUP BY createDate, type, semifinishedProductType,standard ";
	// return inputRegisterDao.findStatisticbySql(sql);
	// }
	//	
	//	
	//	
	// /**
	// * 按条件统计出入库记录
	// */
	// public List<InputRegister> findStatistics(Date startDate,Date endDate,int
	// typeid,int semifinishedProductTypeid){
	// String sql ="SELECT min(id) as id, standard,min(purpose) as purpose,
	// SUM(reductionAmount) AS reductionAmount, semifinishedProductType,
	// createDate, type, min(remark) as remark, "
	// + "MIN(reductionNumber) AS reductionNumber FROM InputRegister ir "
	// + "WHERE (type = type) AND (createDate = createDate) AND
	// (semifinishedProductType = semifinishedProductType) "
	// + "GROUP BY createDate, type, semifinishedProductType ";
	//		
	// if(typeid != 0){
	// sql += "(type = :type)";
	// }else{
	// sql += "(type = type)";
	// }
	//		
	// if(startDate == null){
	// sql += "(startDate = :startDate)";
	// }
	//		
	//		
	// if(startDate==null){
	// sql.replaceFirst("(createDate = createDate)", "");
	// }
	// else{
	// if(endDate==null){
	// Calendar c = Calendar.getInstance();
	// c.setTime(startDate);
	// c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
	// endDate = c.getTime();
	// }
	// sql.replaceFirst("(createDate = createDate)", "(createDate >= :startDate
	// and startDate < :endDate)");
	// }
	// if(typeid==0){
	// sql.replaceFirst("(type = type)", "");
	// }else{
	// sql.replaceFirst("(type = type)", "(type = :typeid)");
	// }
	// if(semifinishedProductTypeid == 0){
	// sql.replaceFirst("(semifinishedProductType = semifinishedProductType)",
	// "");
	// }else{
	// sql.replaceFirst("(semifinishedProductType = semifinishedProductType)",
	// "(semifinishedProductType = :semifinishedProductTypeid)");
	// }
	//		
	//		
	// return inputRegisterDao.findStatisticbySql(sql, startDate,
	// endDate,typeid,semifinishedProductTypeid);
	// }

}
