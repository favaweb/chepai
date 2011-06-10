package com.hovto.chepai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.BigBatchNumber;
import com.hovto.chepai.model.FlowType;
import com.hovto.chepai.model.SmallnessBatchNumber;
import com.hovto.chepai.model.TaskAllocation;
import com.hovto.chepai.model.WorkFlow;


@Component
public class TaskAllocationDao {
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	public void add(TaskAllocation taskAllocation) {
		sessionFactory.getCurrentSession().save(taskAllocation);
	}
	
	public void delete(TaskAllocation taskAllocation) {
		sessionFactory.getCurrentSession().delete(this.find(taskAllocation));
	}
	
	public void modify(TaskAllocation taskAllocation) {
		sessionFactory.getCurrentSession().update(taskAllocation);
	}
	
	public TaskAllocation find(TaskAllocation taskAllocation) {
		return (TaskAllocation) sessionFactory.getCurrentSession().get(TaskAllocation.class, taskAllocation.getId());
	}
	@SuppressWarnings("unchecked")
	public List<TaskAllocation> findList() {
		return sessionFactory.getCurrentSession().createQuery("select p from TaskAllocation p").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TaskAllocation> findBySBatchNumberAndFlowType(int sBatchNumberId,int flowTypeId){
		String sql="select p from TaskAllocation p where 1=1";
		if(sBatchNumberId>0){
			sql+=" and p.smallnessBatchNumber.id="+sBatchNumberId;
		}
		if(flowTypeId>0){
			sql+=" and p.flowType.id="+flowTypeId;
		}
		List<TaskAllocation> list=sessionFactory.getCurrentSession().createQuery(sql).list();
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<TaskAllocation> queryBySBatchNumberAndFlowType(int sBatchNumberId,int flowTypeId){
		String sql="select p from TaskAllocation p where 1=1";
		if(sBatchNumberId>0){
			sql+=" and p.smallnessBatchNumber.id="+sBatchNumberId;
		}
		if(flowTypeId>0){
			sql+=" and p.flowType.id="+flowTypeId;
		}
		List<TaskAllocation> list=sessionFactory.getCurrentSession().createQuery(sql).list();
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<TaskAllocation> findBySBatchNumberAndFlowTypeAndStatus(int sBatchNumberId,int flowTypeId,int status){
		String sql="select p from TaskAllocation p where 1=1";
		if(status>=0){
			sql+=" and status="+status;
		}
		if(sBatchNumberId>0){
			sql+=" and p.smallnessBatchNumber.id="+sBatchNumberId;
		}
		if(flowTypeId>0){
			sql+=" and p.flowType.id="+flowTypeId;
		}
		List<TaskAllocation> list=sessionFactory.getCurrentSession().createQuery(sql).list();
		return list;
	}
	/**
	 * 根据用户id和所在流程查询出他分配的条数
	 * @param userId
	 * @param flowTypeId
	 * @return
	 */
	public long findByStatus(String userId,int flowTypeId){
		String sql="select count(p) from TaskAllocation p where status=0 and p.smallnessBatchNumber.isDistribute <> 5 and p.flowType.id="+flowTypeId+" and operator like '%"+userId+"%'";
		if(flowTypeId ==5)//若为质检，则忽略正在补制中的
			sql="select count(p) from TaskAllocation p  where p.status=0 and p.smallnessBatchNumber.isDistribute <> 3 and p.flowType.id="+flowTypeId+" and p.operator like '%"+userId+"%'";
//			sql="select count(p) from TaskAllocation p join SmallnessBatchNumber s on s.id = p.SmallnessBatchNumber where p.status=0 and s.isDistribute <> 3 and p.flowType.id="+flowTypeId+" and p.operator like '%"+userId+"%'";
		return (Long)sessionFactory.getCurrentSession().createQuery(sql).uniqueResult();
	}
	
	/**
	 * 根据用户 Id查询 用户未完成的任务
	 * @param userId
	 * @return
	 */
	public long findByStatus(String userId){
		String sql="select count(p) from TaskAllocation p where status=0 and operator='"+userId+"'";
		return (Long)sessionFactory.getCurrentSession().createQuery(sql).uniqueResult();
	}
	
	
	public List<TaskAllocation> findBySBatchNumberId(int smallnessBatchNumberId){
		return sessionFactory.getCurrentSession().createQuery("select p from TaskAllocation p where status=0 and p.smallnessBatchNumber.id=:smallnessBatchNumberId").setParameter("smallnessBatchNumberId", smallnessBatchNumberId).list();
	}
	
	
	public List<TaskAllocation> findBackRemake(int smallnessBatchNumberId){
		return sessionFactory.getCurrentSession().createQuery("select p from TaskAllocation p where status=2 and p.smallnessBatchNumber.id=:smallnessBatchNumberId").setParameter("smallnessBatchNumberId", smallnessBatchNumberId).list();
	}
	
	@SuppressWarnings("deprecation")
	public List<TaskAllocation> queryPrepareOpeartor(int flowTypeId) {
		Connection conn = sessionFactory.getCurrentSession().connection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<TaskAllocation> TaskAllocations = new ArrayList<TaskAllocation>();
		try {
			String sql = "select t.operator from WorkFlow w join TaskAllocation t on(w.smallnessBatchNumber = t.smallnessBatchNumber) join SmallnessBatchNumber s on(s.id = w.smallnessBatchNumber) left join BigBatchNumber b on(b.id = w.bigBatchNumber) where w.currentFlow = ? and s.isValid = 0 and s.isDeliverGoods = 0 and t.flowType = ? group by t.operator";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, flowTypeId);
			pstmt.setInt(2, flowTypeId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				TaskAllocation t = new TaskAllocation();
				t.setOperator(rs.getString("operator"));
				TaskAllocations.add(t);
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
		return TaskAllocations;
	}

	@SuppressWarnings("deprecation")
	public List<TaskAllocation> findPrepareWork(int flowTypeId, String operator) {
		Connection conn = sessionFactory.getCurrentSession().connection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<TaskAllocation> TaskAllocations = new ArrayList<TaskAllocation>();
		try {
			String sql = "select t.id as 'tid',s.id as 'sid',s.isRemakes,s.boxNumberType,s.smallnessBatchNumber,s.orderType,s.place,s.precedence,s.dateTime,s.boxNumber,s.isDistribute,b.id as 'bid',b.bigBattchNumber from WorkFlow w join TaskAllocation t on(w.smallnessBatchNumber = t.smallnessBatchNumber) join SmallnessBatchNumber s on(s.id = w.smallnessBatchNumber) left join BigBatchNumber b on(b.id = w.bigBatchNumber) where w.currentFlow = ? and s.isValid = 0 and s.isDeliverGoods = 0 and t.flowType = ? and t.operator = ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, flowTypeId);
			pstmt.setInt(2, flowTypeId);
			pstmt.setString(3, operator);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				TaskAllocation t = new TaskAllocation();
				t.setId(rs.getInt("tid"));
				t.setOperator(operator);
				
				SmallnessBatchNumber s = new SmallnessBatchNumber();
				s.setId(rs.getInt("sid"));
				s.setSmallnessBatchNumber(rs.getString("smallnessBatchNumber"));
				s.setOrderType(rs.getInt("orderType"));
				s.setPlace(rs.getString("place"));
				s.setPrecedence(rs.getInt("precedence"));
				s.setDateTime(rs.getDate("dateTime"));
				s.setBoxNumber(rs.getInt("boxNumber"));
				s.setBoxNumberType(rs.getInt("boxNumberType"));
				s.setIsRemakes(rs.getInt("isRemakes"));
				s.setIsDistribute(rs.getInt("isDistribute"));
				
				if(rs.getObject("bid") != null) {
					BigBatchNumber b = new BigBatchNumber();
					b.setId(rs.getInt("bid"));
					b.setBigBattchNumber(rs.getString("bigBattchNumber"));
					s.setBigBatchNumber(b);
				}
				
				t.setSmallnessBatchNumber(s);				
				TaskAllocations.add(t);
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
		return TaskAllocations;
	}

	/**
	 * 根据 流程Id 查询出 该流程未完成的人的 Id
	 * @param flowType
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public List<String> findByFlowType(String sql, int flowType) {
		Connection conn = sessionFactory.getCurrentSession().connection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> operators = new ArrayList<String>();
		try {
			pstmt = conn.prepareStatement(sql);
			if(flowType != 1 && flowType != 4)
				pstmt.setInt(1, flowType);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				operators.add(rs.getString("operator"));
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
		return operators;
	}
	
	
	
	
	
}
