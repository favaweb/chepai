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

import com.hovto.chepai.model.BigBatchNumber;
import com.hovto.chepai.model.FlowType;
import com.hovto.chepai.model.NumberPlateType;
import com.hovto.chepai.model.SmallnessBatchNumber;
import com.hovto.chepai.model.WorkFlow;
import com.hovto.chepai.tool.Page;

@Component
public class WorkFlowDao {

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public void add(WorkFlow workFlow) {
		sessionFactory.getCurrentSession().save(workFlow);
	}
	
	public void modify(WorkFlow workFlow) {
		sessionFactory.getCurrentSession().update(workFlow);
	}
	
	public WorkFlow find(WorkFlow workFlow) {
		return (WorkFlow) sessionFactory.getCurrentSession().get(WorkFlow.class, workFlow.getId());
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkFlow> findList(int id) {
		return sessionFactory.getCurrentSession()
		.createQuery("select w from WorkFlow w where w.currentFlow.id = :id and w.smallnessBatchNumber.isValid=0 and w.smallnessBatchNumber.isDeliverGoods=0 ORDER BY smallnessBatchNumber.precedence,smallnessBatchNumber.dateTime")
		.setParameter("id", id).list();
	}
	/**
	 * 根据流程查询出已经下发的任务
	 * @return w.smallnessBatchNumber.isDistribute!=1 and
	 */
	@SuppressWarnings("unchecked")
	public List<WorkFlow> queryList(int id){
		return sessionFactory.getCurrentSession()
		.createQuery("select w from WorkFlow w where w.currentFlow.id = :id and w.smallnessBatchNumber.isValid=0 and w.smallnessBatchNumber.isDeliverGoods=0 ORDER BY smallnessBatchNumber.precedence,smallnessBatchNumber.dateTime")
		.setParameter("id", id).list();
	}
	@SuppressWarnings("unchecked")
	public List<WorkFlow> findByIsDistribute(int id) {
		return sessionFactory.getCurrentSession()
		.createQuery("select w from WorkFlow w where w.currentFlow.id = :id and w.smallnessBatchNumber.isValid=0 and w.smallnessBatchNumber.isDistribute=1 and w.smallnessBatchNumber.isDeliverGoods=0 ORDER BY smallnessBatchNumber.precedence,smallnessBatchNumber.dateTime")
		.setParameter("id", id).list();
	}
	/**
	 * 根据 小批号id查找流程数据
	 * @param smallnessBatchNumberId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WorkFlow> findBySbatchNumberId(int smallnessBatchNumberId){
		return sessionFactory.getCurrentSession()
		.createQuery("select w from WorkFlow w where w.smallnessBatchNumber.id = :id")
		.setParameter("id", smallnessBatchNumberId).list();
	}
	
	
	
	/**
	 * 根据 流程id 查询 未发合并 跟补牌 的流程号
	 */
	@SuppressWarnings("unchecked")
	public List<WorkFlow> queryHB(int flowType){
		String sql="select w from WorkFlow w where w.currentFlow.id="+flowType+" and w.smallnessBatchNumber.isDistribute=1 and w.smallnessBatchNumber.isRemakes = 2 and isRemakes <> 2 ";
		List<WorkFlow> list=sessionFactory.getCurrentSession().createQuery(sql).setFirstResult(0).setMaxResults(1).list();
		return list;
	}
	
	
	/**
	 * 查询出补制的 流程
	 * @param flowType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WorkFlow> queryByOrderType(int flowType, int orderType, int sum){
		String sql="select w from WorkFlow w where w.currentFlow.id="+flowType+" and w.smallnessBatchNumber.isDistribute=1 ";
		if(orderType == 1) 
			sql += "and w.smallnessBatchNumber.orderType=" + orderType;
		else 
			sql += "and w.smallnessBatchNumber.orderType != 1";
		sql += " order by w.smallnessBatchNumber.precedence";
		List<WorkFlow> list=sessionFactory.getCurrentSession().createQuery(sql).setFirstResult(0).setMaxResults(sum).list();
		return list;
	}
	
	/**
	 * 根据车牌类型查询流程
	 * @param flowType
	 * @param plateType
	 * @param sum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WorkFlow> queryPlateType(int flowType, int plateType, int sum){
		String sql="select w from WorkFlow w where w.currentFlow.id="+flowType+" and w.smallnessBatchNumber.isDistribute=1 and w.smallnessBatchNumber.orderType = 3 ";
		if(plateType == 1) 
			sql += "and w.smallnessBatchNumber.numberPlateType.type=" + plateType;
		else 
			sql += "and w.smallnessBatchNumber.numberPlateType.id = " + plateType;
		sql += " order by w.smallnessBatchNumber.precedence";
		List<WorkFlow> list=sessionFactory.getCurrentSession().createQuery(sql).setFirstResult(0).setMaxResults(sum).list();
		return list;
	}
	
	
	@SuppressWarnings("deprecation")
	public List<WorkFlow> queryFanYa(int flowType, int sum){
		Connection conn = sessionFactory.getCurrentSession().connection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<WorkFlow> workFlows = new ArrayList<WorkFlow>();
		try {
			String sql =  "select top " + sum + " w.id as 'wid', s.id as 'sid', s.amount as 'amount', s.boxNumber as 'boxNumber', s.boxNumberType as 'boxNumberType', s.dateTime as 'dateTime',s.isDeliverGoods as 'isDeliverGoods', s.isRemakes as 'isRemakes',s.isValid as 'isValid', s.makeStatus as 'makeStatus',s.orderType as 'orderType',s.otherName as 'otherName', s.place as 'place', s.precedence as 'precedence', s.sendTime as 'sendTime', s.smallnessBatchNumber as 'smallnessBatchNumber',s.bigBatchNumber as 'bigBatchNumber', s.numberPlateType as 'numberPlateType', s.isDistribute as 'sisDistribute' from workFlow w join SmallnessBatchNumber s on(w.smallnessBatchNumber = s.id) left join NumberPlate np on(s.numberPlateType = np.id) " +  
						  "left join numberPlateType npt on(np.numberPlateType = npt.id) " +  
						  "where s.orderType != 3 and w.currentFlow = " + flowType + " and s.isDistribute = 1 " +  
						  "or s.isRemakes = 0 and w.currentFlow = " + flowType + " and s.isDistribute = 1 " +  
					      "or s.numberPlateType != 9 and npt.type != 1 and w.currentFlow = " + flowType + " and s.isDistribute = 1 " +  
						  "order by s.precedence, s.dateTime";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				WorkFlow w = new WorkFlow();
				w.setId(rs.getInt("wid"));
				
				SmallnessBatchNumber s = new SmallnessBatchNumber();
				s.setId(rs.getInt("sid"));
				s.setAmount(rs.getInt("amount"));
				s.setBoxNumber(rs.getInt("boxNumber"));
				s.setBoxNumberType(rs.getInt("boxNumberType"));
				s.setDateTime(rs.getTimestamp("dateTime"));
				s.setIsDeliverGoods(rs.getInt("isDeliverGoods"));
				s.setIsRemakes(rs.getInt("isRemakes"));
				s.setIsDistribute(rs.getInt("sisDistribute"));
				s.setIsValid(rs.getInt("isValid"));
				s.setMakeStatus(rs.getInt("makeStatus"));
				s.setOrderType(rs.getInt("orderType"));
				s.setOtherName(rs.getString("otherName"));
				s.setPlace(rs.getString("place"));
				s.setPrecedence(rs.getInt("precedence"));
				s.setSendTime(rs.getTimestamp("sendTime"));
				s.setSmallnessBatchNumber(rs.getString("smallnessBatchNumber"));
				if(rs.getObject("bigBatchNumber") != null)
					s.setBigBatchNumber(new BigBatchNumber(rs.getInt("bigBatchNumber")));
				if(rs.getObject("numberPlateType") != null)
					s.setNumberPlateType(new NumberPlateType(rs.getInt("numberPlateType")));
				w.setSmallnessBatchNumber(s);
				workFlows.add(w);
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
		return workFlows;
	}

	/**
	 * 根据 条件查询 流程
	 * @param findDate
	 * @param nextDate
	 * @param flowType
	 * @param place
	 * @param sql
	 * @param countSql
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WorkFlow> findByCondition(Date findDate, Date nextDate, int flowType,
			String place, String sql, String countSql, Page page) {
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		Query countQuery = sessionFactory.getCurrentSession().createQuery(countSql);
		if(findDate != null) {
			query.setParameter("findDate", findDate);
			countQuery.setParameter("findDate", findDate);
		}
		if(nextDate != null) {
			query.setParameter("nextDate", nextDate);
			countQuery.setParameter("nextDate", nextDate);
		}
		if(flowType != 0) {
			query.setParameter("flowType", flowType);
			countQuery.setParameter("flowType", flowType);
		}
		if(place != null && !"".equals(place)) {
			query.setParameter("place", place);
			countQuery.setParameter("place", place);
		}
		page.setLastPage(Integer.parseInt(countQuery.uniqueResult().toString()));
		
		query.setMaxResults(page.getPageSize());
		query.setFirstResult((page.getCurrentPage()-1) * page.getPageSize());
		return query.list();
	}
	
	
	/**
	 * 根据 流程id 优先级别 随机获取
	 * @param flowTypeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WorkFlow> queryByFlowTypeAndPrecedence(int flowTypeId, int sum){
		String sql="select top " + sum + " a.id,a.bigBatchNumber,a.precedence,a.currentFlow,a.nextFlow,a.smallnessBatchNumber from (select top " + sum + " wf.id,wf.bigBatchNumber,wf.currentFlow,wf.nextFlow,wf.smallnessBatchNumber,SBN.precedence from WorkFlow as wf left join SmallnessBatchNumber as SBN on wf.smallnessBatchNumber=SBN.id where wf.currentFlow="+flowTypeId+" and SBN.isValid=0 and SBN.isDistribute=1 and SBN.isDeliverGoods=0 order by SBN.precedence,newid()) a";
		List<WorkFlow> list=sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(WorkFlow.class).list();
		return list;
	}
	
	

	/**
	 * 根据 流程Id 查询数据,并且按照操作人排序
	 * @param id 流程Id
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public List<WorkFlow> findOrderByUsers(int id, Page page) {
		Connection conn = sessionFactory.getCurrentSession().connection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmtCount = null;
		ResultSet rs = null;
		ResultSet rsCount = null;
		List<WorkFlow> workFlows = new ArrayList<WorkFlow>();
		try {
			int resultCount = 0;
			
			if(page.getLastPage() <= 1) {
				String sqlCount = "select count(*) from WorkFlow w join TaskAllocation t on(w.smallnessBatchNumber = t.smallnessBatchNumber) join SmallnessBatchNumber s on(s.id = w.smallnessBatchNumber) left join BigBatchNumber b on(b.id = w.bigBatchNumber) where w.currentFlow = ? and s.isValid = 0  and t.status !=1 and t.flowType = ?";
				pstmtCount = conn.prepareStatement(sqlCount);
				pstmtCount.setInt(1, id);
				pstmtCount.setInt(2, id);
				rsCount = pstmtCount.executeQuery();
				if(rsCount.next()) {
					resultCount = rsCount.getInt(1);
					page.setLastPage(resultCount);
				}
			} else {
				resultCount = page.getLastPage() * page.getPageSize();
			}
			
			String sql = 
				"select top " + page.getPageSize() + " * from (" + 
				"	select top " + resultCount + "  w.id as 'wid',w.nextFlow,s.id as 'sid',s.boxNumberType,s.smallnessBatchNumber,s.isRemakes,s.orderType,s.place,s.precedence,s.dateTime,s.boxNumber,s.isDistribute,b.id as 'bid',b.bigBattchNumber,t.operator from WorkFlow w join TaskAllocation t on(w.smallnessBatchNumber = t.smallnessBatchNumber) join SmallnessBatchNumber s on(s.id = w.smallnessBatchNumber) left join BigBatchNumber b on(b.id = w.bigBatchNumber) where w.currentFlow = " + id + " and s.isValid = 0  and t.status !=1 and t.flowType = " + id + " order by t.operator,s.precedence,s.dateTime,w.id" + 
				") a where a.wid not in (" + 
				"	select top " + (page.getCurrentPage()-1) * page.getPageSize() + " b.wid from(" + 
				"		select top " + resultCount + " w.id as 'wid' from WorkFlow w join TaskAllocation t on(w.smallnessBatchNumber = t.smallnessBatchNumber) join SmallnessBatchNumber s on(s.id = w.smallnessBatchNumber) left join BigBatchNumber b on(b.id = w.bigBatchNumber) where w.currentFlow = " + id + " and s.isValid = 0  and t.status !=1 and t.flowType = " + id + " order by t.operator,s.precedence,s.dateTime,w.id" + 
				"	) b " + 
				")";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				WorkFlow w = new WorkFlow();
				w.setId(rs.getInt("wid"));
				w.setNextFlow(new FlowType(rs.getInt("nextFlow")));
				
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
				s.setOperator(rs.getString("operator"));
				
				w.setSmallnessBatchNumber(s);
				
				if(rs.getObject("bid") != null) {
					BigBatchNumber b = new BigBatchNumber();
					b.setId(rs.getInt("bid"));
					b.setBigBattchNumber(rs.getString("bigBattchNumber"));
					w.setBigBatchNumber(b);
				}
				
				workFlows.add(w);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(rsCount != null) rsCount.close();
				if(pstmtCount != null) pstmtCount.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return workFlows;
	}
	/**
	 * 根据 流程Id 查询数据,并且按照操作人排序
	 * @param id 流程Id
	 * @return
	 *//*
	@SuppressWarnings("deprecation")
	public List<WorkFlow> findOrderByUsers(int id, Page page) {
		Connection conn = sessionFactory.getCurrentSession().connection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmtCount = null;
		ResultSet rs = null;
		ResultSet rsCount = null;
		List<WorkFlow> workFlows = new ArrayList<WorkFlow>();
		try {
			int resultCount = 0;
			
			if(page.getLastPage() <= 1) {
				String sqlCount = "select count(*) from WorkFlow w join TaskAllocation t on(w.smallnessBatchNumber = t.smallnessBatchNumber) join SmallnessBatchNumber s on(s.id = w.smallnessBatchNumber) left join BigBatchNumber b on(b.id = w.bigBatchNumber) where w.currentFlow = ? and s.isValid = 0 and s.isDeliverGoods = 0 and t.status !=1 and t.flowType = ?";
				pstmtCount = conn.prepareStatement(sqlCount);
				pstmtCount.setInt(1, id);
				pstmtCount.setInt(2, id);
				rsCount = pstmtCount.executeQuery();
				if(rsCount.next()) {
					resultCount = rsCount.getInt(1);
					page.setLastPage(resultCount);
				}
			} else {
				resultCount = page.getLastPage() * page.getPageSize();
			}
			
			String sql = 
				"select top " + page.getPageSize() + " * from (" + 
				"	select top " + resultCount + "  w.id as 'wid',w.nextFlow,s.id as 'sid',s.boxNumberType,s.smallnessBatchNumber,s.isRemakes,s.orderType,s.place,s.precedence,s.dateTime,s.boxNumber,s.isDistribute,b.id as 'bid',b.bigBattchNumber,t.operator from WorkFlow w join TaskAllocation t on(w.smallnessBatchNumber = t.smallnessBatchNumber) join SmallnessBatchNumber s on(s.id = w.smallnessBatchNumber) left join BigBatchNumber b on(b.id = w.bigBatchNumber) where w.currentFlow = " + id + " and s.isValid = 0 and s.isDeliverGoods = 0 and t.status !=1 and t.flowType = " + id + " order by t.operator,s.precedence,s.dateTime,w.id" + 
				") a where a.wid not in (" + 
				"	select top " + (page.getCurrentPage()-1) * page.getPageSize() + " b.wid from(" + 
				"		select top " + resultCount + " w.id as 'wid' from WorkFlow w join TaskAllocation t on(w.smallnessBatchNumber = t.smallnessBatchNumber) join SmallnessBatchNumber s on(s.id = w.smallnessBatchNumber) left join BigBatchNumber b on(b.id = w.bigBatchNumber) where w.currentFlow = " + id + " and s.isValid = 0 and s.isDeliverGoods = 0 and t.status !=1 and t.flowType = " + id + " order by t.operator,s.precedence,s.dateTime,w.id" + 
				"	) b " + 
				")";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				WorkFlow w = new WorkFlow();
				w.setId(rs.getInt("wid"));
				w.setNextFlow(new FlowType(rs.getInt("nextFlow")));
				
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
				s.setOperator(rs.getString("operator"));
				
				w.setSmallnessBatchNumber(s);
				
				if(rs.getObject("bid") != null) {
					BigBatchNumber b = new BigBatchNumber();
					b.setId(rs.getInt("bid"));
					b.setBigBattchNumber(rs.getString("bigBattchNumber"));
					w.setBigBatchNumber(b);
				}
				
				workFlows.add(w);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(rsCount != null) rsCount.close();
				if(pstmtCount != null) pstmtCount.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return workFlows;
	}*/
	
	@SuppressWarnings("deprecation")
	public List<WorkFlow> findFanYa(int id, Page page, int plateType) {
		Connection conn = sessionFactory.getCurrentSession().connection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmtCount = null;
		ResultSet rs = null;
		ResultSet rsCount = null;
		List<WorkFlow> workFlows = new ArrayList<WorkFlow>();
		try {
			int resultCount = 0;
			
			if(page.getLastPage() <= 1) {
				String sqlCount = "select count(*) from WorkFlow w join TaskAllocation t on(w.smallnessBatchNumber = t.smallnessBatchNumber) join SmallnessBatchNumber s on(s.id = w.smallnessBatchNumber) left join BigBatchNumber b on(b.id = w.bigBatchNumber) left join NumberPlateType npt on(npt.id = s.numberPlateType) where w.currentFlow = ? and s.isValid = 0 and s.isDeliverGoods = 0 and t.status !=1 and t.flowType = ? and s.orderType = 3 ";
				if(plateType == 1) {
					sqlCount += " and npt.type = ?";
				} else {
					sqlCount += " and s.numberPlateType = ?";
				}
				pstmtCount = conn.prepareStatement(sqlCount);
				pstmtCount.setInt(1, id);
				pstmtCount.setInt(2, id);
				pstmtCount.setInt(3, plateType);
				rsCount = pstmtCount.executeQuery();
				if(rsCount.next()) {
					resultCount = rsCount.getInt(1);
					page.setLastPage(resultCount);
				}
			} else {
				resultCount = page.getLastPage() * page.getPageSize();
			}
			
			String sql = 
				"select top " + page.getPageSize() + " * from (" + 
				"	select top " + resultCount + "  w.id as 'wid',w.nextFlow,s.id as 'sid',s.boxNumberType,s.smallnessBatchNumber,s.isRemakes,s.orderType,s.place,s.precedence,s.dateTime,s.boxNumber,s.isDistribute,b.id as 'bid',b.bigBattchNumber,t.operator from WorkFlow w join TaskAllocation t on(w.smallnessBatchNumber = t.smallnessBatchNumber) join SmallnessBatchNumber s on(s.id = w.smallnessBatchNumber) left join BigBatchNumber b on(b.id = w.bigBatchNumber) left join NumberPlateType npt on(npt.id = s.numberPlateType) where w.currentFlow = " + id + " and s.isValid = 0 and s.isDeliverGoods = 0 and t.status !=1 and t.flowType = " + id;
			if(plateType == 1) {
				sql += " and npt.type = ?";
			} else {
				sql += " and s.numberPlateType = ?";
			}
			
			sql += " order by t.operator,s.precedence,s.dateTime,w.id" + 
				") a where a.wid not in (" + 
				"	select top " + (page.getCurrentPage()-1) * page.getPageSize() + " b.wid from(" + 
				"		select top " + resultCount + " w.id as 'wid' from WorkFlow w join TaskAllocation t on(w.smallnessBatchNumber = t.smallnessBatchNumber) join SmallnessBatchNumber s on(s.id = w.smallnessBatchNumber) left join BigBatchNumber b on(b.id = w.bigBatchNumber) left join NumberPlateType npt on(npt.id = s.numberPlateType) where w.currentFlow = " + id;
			if(plateType == 1) {
				sql += " and npt.type = ?";
			} else {
				sql += " and s.numberPlateType = ?";
			}
			
			sql += " and s.isValid = 0 and s.isDeliverGoods = 0 and t.status !=1 and t.flowType = " + id + " order by t.operator,s.precedence,s.dateTime,w.id" + 
				"	) b " + 
				")";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, plateType);
			pstmt.setInt(2, plateType);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				WorkFlow w = new WorkFlow();
				w.setId(rs.getInt("wid"));
				w.setNextFlow(new FlowType(rs.getInt("nextFlow")));
				
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
				s.setOperator(rs.getString("operator"));
				
				w.setSmallnessBatchNumber(s);
				
				if(rs.getObject("bid") != null) {
					BigBatchNumber b = new BigBatchNumber();
					b.setId(rs.getInt("bid"));
					b.setBigBattchNumber(rs.getString("bigBattchNumber"));
					w.setBigBatchNumber(b);
				}
				
				workFlows.add(w);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(rsCount != null) rsCount.close();
				if(pstmtCount != null) pstmtCount.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return workFlows;
	}
	
	@SuppressWarnings("deprecation")
	public List<WorkFlow> findFanYa(int id, Page page) {
		Connection conn = sessionFactory.getCurrentSession().connection();
System.out.println("connection:\t" + conn.getClass().getName()); 
		PreparedStatement pstmt = null;
		PreparedStatement pstmtCount = null;
		ResultSet rs = null;
		ResultSet rsCount = null;
		List<WorkFlow> workFlows = new ArrayList<WorkFlow>();
		try {
			int resultCount = 0;
			
			if(page.getLastPage() <= 1) {
				String sqlCount = "select count(*) from WorkFlow w join TaskAllocation t on(w.smallnessBatchNumber = t.smallnessBatchNumber) join SmallnessBatchNumber s on(s.id = w.smallnessBatchNumber) left join BigBatchNumber b on(b.id = w.bigBatchNumber) left join NumberPlateType npt on(npt.id = s.numberPlateType) where w.currentFlow = ? and s.isValid = 0 and s.isDeliverGoods = 0 and t.status !=1 and t.flowType = ? and npt.id != 9 and npt.type != 1 ";
				sqlCount += " or w.currentFlow = ? and s.isValid = 0 and s.isDeliverGoods = 0 and t.status !=1 and t.flowType = ? and s.orderType != 3 ";
				pstmtCount = conn.prepareStatement(sqlCount);
				pstmtCount.setInt(1, id);
				pstmtCount.setInt(2, id);
				pstmtCount.setInt(3, id);
				pstmtCount.setInt(4, id);
				rsCount = pstmtCount.executeQuery();
				if(rsCount.next()) {
					resultCount = rsCount.getInt(1);
					page.setLastPage(resultCount);
				}
			} else {
				resultCount = page.getLastPage() * page.getPageSize();
			}
			
			String sql = 
				"select top " + page.getPageSize() + " * from (" + 
				"	select top " + resultCount + "  w.id as 'wid',w.nextFlow,s.id as 'sid',s.boxNumberType,s.smallnessBatchNumber,s.isRemakes,s.orderType,s.place,s.precedence,s.dateTime,s.boxNumber,s.isDistribute,b.id as 'bid',b.bigBattchNumber,t.operator from WorkFlow w join TaskAllocation t on(w.smallnessBatchNumber = t.smallnessBatchNumber) join SmallnessBatchNumber s on(s.id = w.smallnessBatchNumber) left join BigBatchNumber b on(b.id = w.bigBatchNumber) left join NumberPlateType npt on(npt.id = s.numberPlateType) where w.currentFlow = " + id + " and s.isValid = 0  and t.status !=1 and t.flowType = " + id;
			sql += " and npt.id != 9 and npt.type != 1 ";
			sql += " or w.currentFlow = " + id + " and s.isValid = 0  and t.status !=1 and t.flowType = " + id;
			sql += " and s.orderType != 3";
			sql += " order by t.operator,s.precedence,s.dateTime,w.id" + 
			") a where a.wid not in (" + 
			"	select top " + (page.getCurrentPage()-1) * page.getPageSize() + " b.wid from(" + 
			"		select top " + resultCount + " w.id as 'wid' from WorkFlow w join TaskAllocation t on(w.smallnessBatchNumber = t.smallnessBatchNumber) join SmallnessBatchNumber s on(s.id = w.smallnessBatchNumber) left join BigBatchNumber b on(b.id = w.bigBatchNumber) left join NumberPlateType npt on(npt.id = s.numberPlateType) where w.currentFlow = " + id + " and s.isValid = 0  and t.status !=1 and t.flowType = " + id;
			sql += " and npt.id != 9 and npt.type != 1 ";
			sql += " or w.currentFlow = " + id + " and s.isValid = 0  and t.status !=1 and t.flowType = " + id;
			sql += " and s.orderType != 3";
			sql += " order by t.operator,s.precedence,s.dateTime,w.id" + 
			"	) b " + 
			")";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				WorkFlow w = new WorkFlow();
				w.setId(rs.getInt("wid"));
				w.setNextFlow(new FlowType(rs.getInt("nextFlow")));
				
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
				s.setOperator(rs.getString("operator"));
				
				w.setSmallnessBatchNumber(s);
				
				if(rs.getObject("bid") != null) {
					BigBatchNumber b = new BigBatchNumber();
					b.setId(rs.getInt("bid"));
					b.setBigBattchNumber(rs.getString("bigBattchNumber"));
					w.setBigBatchNumber(b);
				}
				
				workFlows.add(w);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(rsCount != null) rsCount.close();
				if(pstmtCount != null) pstmtCount.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return workFlows;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
