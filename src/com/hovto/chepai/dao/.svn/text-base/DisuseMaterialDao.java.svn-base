package com.hovto.chepai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.DisuseMaterial;
import com.hovto.chepai.model.NumberPlate;
import com.hovto.chepai.model.Stats;
import com.hovto.chepai.tool.Page;


@Component
public class DisuseMaterialDao {
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	@Resource
	private NumberPlateDao numberPlateDao;
	public void add(DisuseMaterial disuseMaterial) {
		sessionFactory.getCurrentSession().save(disuseMaterial);
	}
	
	public void delete(DisuseMaterial disuseMaterial) {
		sessionFactory.getCurrentSession().delete(this.find(disuseMaterial));
	}
	
	public void modify(DisuseMaterial disuseMaterial) {
		sessionFactory.getCurrentSession().update(disuseMaterial);
	}
	
	public DisuseMaterial find(DisuseMaterial disuseMaterial) {
		return (DisuseMaterial) sessionFactory.getCurrentSession().get(DisuseMaterial.class, disuseMaterial.getId());
	}
	/**
	 * 统计个人废牌数量
	 * @param output	人员id
	 * @param biginTime	开始时间
	 * @param endTime 结束时间
	 * @return List<IndividualOutput>
	 */
	@SuppressWarnings("unchecked")
	public List<DisuseMaterial> findByUserId(int userId,String biginTime,String endTime) {
		String sql="select p from DisuseMaterial p where 1=1";
		if(!biginTime.equals("")&&biginTime!=null){
			sql+=" and p.operateTime>'"+biginTime+"'";
		}
		if(!endTime.equals("")&&endTime!=null){
			sql+=" and p.operateTime<'"+endTime+"'";
		}
		if(userId>0){
			sql+=" and p.operator like '%"+userId+"%'";
		}
		return sessionFactory.getCurrentSession().createQuery(sql).list();
	}
	/**
	 * 统计某段时间某个人某个流程的废牌数量
	 * @param folwTypeId
	 * @param userId
	 * @param biginTime
	 * @param endTime
	 * @return
	 */
	/*@SuppressWarnings("unchecked")
	public List<DisuseMaterial> findByFolwTypeOutput(int flowTypeId,int userId,String biginTime,String endTime) {
		String sql="SELECT min(id) as id,min(bigBatchNumber) as bigBatchNumber,min(flowType) as flowType,min(numberPlateType) as numberPlateType,min(operateTime) as operateTime,min(smallnessBatchNumber) as smallnessBatchNumber,d.numberPlate,d.marType,count(*) as amount,d.operator from DisuseMaterial d where 1=1";
		if(flowTypeId>0){
			sql+=" and d.flowType.id="+flowTypeId;
		}
		if(!biginTime.equals("")&&biginTime!=null){
			sql+=" and d.operateTime>'"+biginTime+"'";
		}
		if(!endTime.equals("")&&endTime!=null){
			sql+=" and d.operateTime<'"+endTime+"'";
		}
		if(userId>0){
			sql+=" and d.operator like '%"+userId+"%'";
		}
		sql+=" group by numberPlate,marType,operator";
		return sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(DisuseMaterial.class).list();
	}*/
	@SuppressWarnings("unchecked")
	public List<DisuseMaterial> findByFolwTypeOutput(String hql,Page page) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setMaxResults(page.getPageSize());
		query.setFirstResult((page.getCurrentPage()-1)*page.getPageSize());
		return query.list();
	}
	
	public int findCountByFolwTypeOutput(String hql,Page page){
		hql = hql.replaceFirst("d", "count(*)");
		return Integer.parseInt(sessionFactory.getCurrentSession().createQuery(hql).uniqueResult().toString());
	}
	
	//旧
	/*public List<DisuseMaterial> findByFolwTypeOutput(int flowTypeId,int userId,String biginTime,String endTime) {
		Connection conn = sessionFactory.getCurrentSession().connection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<DisuseMaterial> dmList = new ArrayList<DisuseMaterial>();
		try {
			String sql="SELECT min(n.licensePlateNumber) as licensePlateNumber,min(d.id) as did,min(d.bigBatchNumber) as bigBatchNumber,min(d.flowType) as flowType,min(d.numberPlateType) as numberPlateType,min(d.operateTime) as operateTime,min(d.smallnessBatchNumber) as smallnessBatchNumber,d.operator ,d.numberPlate,d.marType,count(*) as amount from DisuseMaterial d  left join NumberPlate n on(n.id = d.numberPlate) where 1=1";
			if(flowTypeId>0){
				sql+=" and d.flowType="+flowTypeId;
			}
			if(!biginTime.equals("")&&biginTime!=null){
				sql+=" and d.operateTime>'"+biginTime+"'";
			}
			if(!endTime.equals("")&&endTime!=null){
				sql+=" and d.operateTime<'"+endTime+"'";
			}
			if(userId>0){
				sql+=" and d.operator like '%"+userId+"%'";
			}
			sql+=" group by numberPlate,marType,d.operator";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				DisuseMaterial disuseMaterial = new DisuseMaterial();
				
				NumberPlate n = new NumberPlate(rs.getInt("numberPlate"));
				n.setLicensePlateNumber(rs.getString("licensePlateNumber"));
				
				disuseMaterial.setNumberPlate(n);
				disuseMaterial.setMarType(rs.getInt("marType"));
				disuseMaterial.setAmount(rs.getInt("amount"));
				disuseMaterial.setOperator(rs.getString("operator"));
				dmList.add(disuseMaterial);
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
		return dmList;
	}*/
	
	@SuppressWarnings("unchecked")
	public List<DisuseMaterial> findList() {
		return sessionFactory.getCurrentSession().createQuery("select p from DisuseMaterial p").list();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Stats> stats(java.sql.Date begin, java.sql.Date end) {
		Connection conn = sessionFactory.getCurrentSession().connection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Stats> statses = new ArrayList<Stats>();
		try {
			pstmt = conn.prepareStatement("select marType as marType, count(*) as stats from DisuseMaterial where operateTime >= ? and operateTime < ? group by marType ");
			pstmt.setDate(1, begin);
			pstmt.setDate(2, end);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Stats s = new Stats();
				s.setMarType(rs.getInt("marType"));
				s.setStats(rs.getInt("stats"));
				statses.add(s);
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
		return statses;
	}
	
	/**
	 * 统计
	 * @param hql Sql语句
	 * @param biginDate 开始时间
 	 * @param endDate 结束时间
	 * @param flowTypeId 流程id
	 * @param marType 废牌类型
	 * @param userId 用户id 
	 * @return List
	 */
	public List<DisuseMaterial> statBySql(String hql,String biginDate,String endDate,int flowTypeId,int marType,String operator){
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if(biginDate!=null && endDate!=null){
			query.setParameter("biginDate", biginDate);
			query.setParameter("endDate", endDate);
		}
		if(flowTypeId>0){
			query.setParameter("flowTypeId", flowTypeId);
		}
		if(marType>0){
			query.setParameter("marType", marType);
		}
		if(operator!=null&&!"".equals(operator)){
			query.setParameter("userId","'%"+operator+"%'");
		}
		return query.list();
	}

	public void delBySmallandPlate(int smallId, int numberPlateId) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete from DisuseMaterial d where d.smallnessBatchNumber.id = :smallId and d.numberPlate.id = :plateId");
		query.setParameter("smallId", smallId);
		query.setParameter("plateId", numberPlateId);
		query.executeUpdate();
	}

	public List<DisuseMaterial> findBySmallandPlate(int smallId,
			int numberPlateId) {
		Query query = sessionFactory.getCurrentSession().createQuery("select d from DisuseMaterial d where d.smallnessBatchNumber.id = :smallId and d.numberPlate.id = :plateId order by d.id desc");
		query.setParameter("smallId", smallId);
		query.setParameter("plateId", numberPlateId);
		return query.list();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
