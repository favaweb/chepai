package com.hovto.chepai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.IndividualOutput;
import com.hovto.chepai.model.OutputStats;


@Component
public class IndividualOutputDao {
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	public void add(IndividualOutput individualOutput) {
		sessionFactory.getCurrentSession().save(individualOutput);
	}
	
	public void delete(IndividualOutput individualOutput) {
		sessionFactory.getCurrentSession().delete(this.find(individualOutput));
	}
	
	public void modify(IndividualOutput individualOutput) {
		sessionFactory.getCurrentSession().update(individualOutput);
	}
	
	public IndividualOutput find(IndividualOutput individualOutput) {
		return (IndividualOutput) sessionFactory.getCurrentSession().get(IndividualOutput.class, individualOutput.getId());
	}
	/**
	 * 统计个人产量
	 * @param output	人员id
	 * @param biginTime	开始时间
	 * @param endTime 结束时间
	 * @return List<IndividualOutput>
	 */
	@SuppressWarnings("unchecked")
	public List<IndividualOutput> findByUserId(int userId,String biginTime,String endTime) {
		String sql="select p from IndividualOutput p where 1=1";
		if(userId>0){
			sql+=" and p.user.id=:userId";
		}
		if(biginTime!=null&&!"".equals(biginTime)){
			sql+=" and p.productTime >'"+biginTime+"'";
		}
		if(endTime!=null&&!"".equals(endTime)){
			sql+=" and p.productTime<'"+endTime+"'";
		}
		return sessionFactory.getCurrentSession().createQuery(sql).setParameter("userId", userId).list();
	}
	/**
	 * 统计某段时间某个人某个流程的产量
	 * @param folwTypeId
	 * @param userId
	 * @param biginTime
	 * @param endTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IndividualOutput> findByFolwTypeOutput(int flowTypeId,int userId,String biginTime,String endTime) {
		String sql="SELECT  min(p.id) as id,min(p.bigBatchNumber) as bigBatchNumber,min(p.productTime) as productTime,min(p.smallnessBatchNumber) as smallnessBatchNumber,p.userId,p.flowType,sum(p.output) as output from individualOutput p where 1=1";
		if(flowTypeId>0){
			sql+=" and p.flowType="+flowTypeId;
		}
		if(userId>0){
			sql+=" and p.userId="+userId;
		}
		if(biginTime!=null&&!"".equals(biginTime)){
			sql+=" and p.productTime >'"+biginTime+"'";
		}
		if(endTime!=null&&!"".equals(endTime)){
			sql+=" and p.productTime<'"+endTime+"'";
		}
		sql+=" group by p.flowType,p.userId";
		List list=sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(IndividualOutput.class).list();
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<IndividualOutput> findList() {
		return sessionFactory.getCurrentSession().createQuery("select p from IndividualOutput p").list();
	}
	
	/**
	 * 根据时间段 查询流程产量统计
	 * @param begin
	 * @param end
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public List<OutputStats> findOutputStats(java.sql.Date begin, java.sql.Date end) {
		Connection conn = sessionFactory.getCurrentSession().connection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OutputStats> OutputStatses = new ArrayList<OutputStats>();
		String sql = "select i.flowType as flowTypeId,min(ft.typeName) as flowTypeName, sum(i.output)as output from IndividualOutput as i left join Users u on i.userId=u.id left join FlowType ft on ft.id=i.flowType where i.productTime >= ? ";
		if(end != null)
			sql += " and i.productTime < ? group by i.flowType";
		else
			sql += " group by i.flowType";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, begin);
			if(end != null)
				pstmt.setDate(2, end);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OutputStats s = new OutputStats();
				s.setFlowTypeId(rs.getInt("flowTypeId"));
				s.setFlowTypeName(rs.getString("flowTypeName"));
				s.setOutput(rs.getInt("output"));
				OutputStatses.add(s);
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
		return OutputStatses;
	}
	
	/**
	 * 根据时间段 查询流程个人产量统计
	 * @param begin
	 * @param end
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public List<OutputStats> findPersonStats(java.sql.Date begin, java.sql.Date end) {
		Connection conn = sessionFactory.getCurrentSession().connection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OutputStats> OutputStatses = new ArrayList<OutputStats>();
		String sql = "select u.id as userId, min(u.name)as userName,sum(i.output)as output from IndividualOutput as i left join Users u on i.userId=u.id where i.productTime >= ? ";
		if(end != null)
			sql += " and i.productTime < ? group by u.id";
		else 
			sql += " group by u.id";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, begin);
			if(end != null)
				pstmt.setDate(2, end);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OutputStats s = new OutputStats();
				s.setUserName(rs.getString("userName"));
				s.setOutput(rs.getInt("output"));
				OutputStatses.add(s);
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
		return OutputStatses;
	}

	public void delBySmallAndFlow(int smallId, int flow) {
		sessionFactory.getCurrentSession().createQuery("delete from IndividualOutput o where o.smallnessBatchNumber.id = :smallId and o.flowType.id = :flow").setParameter("smallId", smallId).setParameter("flow", flow).executeUpdate();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
