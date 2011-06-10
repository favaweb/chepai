package com.hovto.chepai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.NumberPlate;
import com.hovto.chepai.model.TypeStats;

@Component
public class NumberPlateDao {
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public void add(NumberPlate numberPlate) {
		sessionFactory.getCurrentSession().save(numberPlate);
	}
	
	public void delete(NumberPlate numberPlate) {
		sessionFactory.getCurrentSession().delete(this.find(numberPlate));
	}
	
	public void modify(NumberPlate numberPlate) {
		sessionFactory.getCurrentSession().update(numberPlate);
	}
	
	public NumberPlate find(NumberPlate numberPlate) {
		return (NumberPlate) sessionFactory.getCurrentSession().get(NumberPlate.class, numberPlate.getId());
	}
	
	/**
	 * 根据小批号 查询所有车牌号码
	 * @param id 小批号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<NumberPlate> findBySmallnessBatchId(int id) {
		return sessionFactory.getCurrentSession().createQuery("select p from NumberPlate p where p.smallnessBatchNumber.id = :id").setParameter("id", id).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<NumberPlate> findByBigBatchId(int id) {
		return sessionFactory.getCurrentSession().createQuery("select p from NumberPlate p where p.bigBatchNumber.id = :id").setParameter("id", id).list();
	}
	
	/**
	 * 根据小批号查询出小型车的车牌
	 * @param smallnessBatchNumberId
	 * @return
	 */
	public List<NumberPlate> findByNumberTypeAndSbatchId(int smallnessBatchNumberId){
		return sessionFactory.getCurrentSession().createQuery("select p from NumberPlate p where p.smallnessBatchNumber.id = :id and p.numberPlateType.type=2").setParameter("id", smallnessBatchNumberId).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<NumberPlate> findList() {
		return sessionFactory.getCurrentSession().createQuery("select p from NumberPlate p").list();
	}

	@SuppressWarnings("deprecation")
	public List<TypeStats> typeStats(String date) {
		Connection conn = sessionFactory.getCurrentSession().connection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<TypeStats> typeStatses = new ArrayList<TypeStats>();
		String sql = " select spt.id,min(standard) as standard,min(typeName) as typeName,sum(totle) as totle,max(temps.number) as number from SemifinishedProductType spt " + 
					"left join numberPlateType_semifinishedProductType  npt_spt on spt.id=npt_spt.semifinishedProductTypeId " + 
					"left join (select numberPlateType,count(licensePlateNumber) as totle from NumberPlate  " + 
					"where exists (select 1 from taskAllocation where CONVERT(varchar(10), allocationTime, 23) =? " +  
					"and NumberPlate.smallnessBatchNumber=taskAllocation.smallnessBatchNumber and flowType != 1 " + 
					"group by  smallnessBatchNumber) group by numberPlateType)as npt on npt_spt.numberPlateTypeId=npt.numberPlateType " + 
					"left join (select spt.id,count(spt.id) number from DisuseMaterial as DM " + 
					"left join SemifinishedProductType as SPT on dm.semifinishedProductType=spt.id " + 
					"where CONVERT(varchar(10), operateTime, 23) =? group by spt.id) as temps on temps.id=spt.id " + 
					"group by spt.id";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,date);
			pstmt.setString(2,date);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				TypeStats ts = new TypeStats();
				ts.setId(rs.getInt("id"));
				ts.setStandard(rs.getString("standard"));
				ts.setTypeName(rs.getString("typeName"));
				ts.setTotle(rs.getInt("totle"));
				ts.setNumber(rs.getInt("number"));
				typeStatses.add(ts);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return typeStatses;
	}

	/**
	 * 根据车牌号码 查询
	 * @param numberPlate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<NumberPlate> findByNo(String numberPlate) {
		return sessionFactory.getCurrentSession().createQuery("select p from NumberPlate p where p.smallnessBatchNumber.isDeliverGoods = 0 and p.licensePlateNumber = :plateNo").setParameter("plateNo", numberPlate).list();
	}
	/**
	 * 根据车牌号码 查询
	 * @param numberPlate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<NumberPlate> findIsFaHuo(String numberPlate) {
		return sessionFactory.getCurrentSession().createQuery("select p from NumberPlate p where p.smallnessBatchNumber.isDeliverGoods != 0 and p.licensePlateNumber = :plateNo").setParameter("plateNo", numberPlate).list();
	}
	
	
	

	
}
