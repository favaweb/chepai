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
import com.hovto.chepai.model.NumberPlate;
import com.hovto.chepai.tool.Page;

@Component
public class BigBatchNumberDao {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	
	
	public void add(BigBatchNumber bigBatchNumber) {
		sessionFactory.getCurrentSession().save(bigBatchNumber);
	}

	public void delete(BigBatchNumber bigBatchNumber) {
		sessionFactory.getCurrentSession().delete(this.find(bigBatchNumber));
	}

	public void modify(BigBatchNumber bigBatchNumber) {
		sessionFactory.getCurrentSession().update(bigBatchNumber);
	}

	public BigBatchNumber find(BigBatchNumber bigBatchNumber) {
		return (BigBatchNumber) sessionFactory.getCurrentSession().get(
				BigBatchNumber.class, bigBatchNumber.getId());
	}

	@SuppressWarnings("unchecked")
	public List<BigBatchNumber> findList() {
		return sessionFactory.getCurrentSession().createQuery(
				"select p from BigBatchNumber p").list();
	}

	public BigBatchNumber findByBigBatchNumber(String bigBatchNumber) {
		return (BigBatchNumber) sessionFactory
				.getCurrentSession()
				.createQuery(
						"select p from BigBatchNumber p where bigBattchNumber=:bigBattchNumber")
				.setParameter("bigBattchNumber", bigBatchNumber).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<BigBatchNumber> findIsDeliverGoods(String sql, String countSql,
			Date findDate, Date nextDate, String place, int isDeliverGoods,
			String bigNo, Page page) {
		Query countQuery = sessionFactory.getCurrentSession().createQuery(
				countSql);
		if (findDate != null) {
			countQuery.setParameter("startDate", findDate);
		}
		if (nextDate != null) {
			countQuery.setParameter("endDate", nextDate);
		}
		if (place != null && !"".equals(place)) {
			countQuery.setParameter("place", place);
		}
		if (isDeliverGoods != 0) {
			countQuery.setParameter("isDeliverGoods", isDeliverGoods);
		}
		if (bigNo != null && !"".equals(bigNo)) {
			countQuery.setParameter("bigNo", bigNo);
		}
		page
				.setLastPage(Integer.parseInt(countQuery.uniqueResult()
						.toString()));

		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		if (findDate != null)
			query.setParameter("startDate", findDate);
		if (nextDate != null)
			query.setParameter("endDate", nextDate);
		if (place != null && !"".equals(place))
			query.setParameter("place", place);
		if (isDeliverGoods != 0)
			query.setParameter("isDeliverGoods", isDeliverGoods);
		if (bigNo != null && !"".equals(bigNo))
			query.setParameter("bigNo", bigNo);

		query.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		List<BigBatchNumber> list = null;
		try {
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public String findDepartment(int id) {
		String sql = "select min(bd.department) from BusinessDepartment bd "
				+ "join NumberPlate np on np.businessDepartment = bd.id "
				+ "join BigBatchNumber bn on bn.id = np.bigBatchNumber "
				+ "where bn.id = " + id;
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		String one = (String) query.uniqueResult();
		return one;
	}
	
	@SuppressWarnings("deprecation")
	public List<BigBatchNumber> findByMakeStatus(Page page) {
		Connection conn = sessionFactory.getCurrentSession().connection();
		PreparedStatement pstmt = null;
		PreparedStatement pstmtCount = null;
		ResultSet rs = null;
		ResultSet rsCount = null;
		List<BigBatchNumber> bigBatchNumbers = new ArrayList<BigBatchNumber>();
		try {
			
			if(page.getLastPage() <= 1) {
				String sqlCount = "select count(*) from smallnessBatchNumber p full join BigbatchNumber b on(b.id = p.bigBatchNumber) where p.makeStatus = 0 and p.isValid = 0 and p.isRemakes = 0 group by p.bigBatchNumber";
				pstmtCount = conn.prepareStatement(sqlCount);
				rsCount = pstmtCount.executeQuery();
				if(rsCount.next()) {
					page.setLastPage(rsCount.getInt(1));
				}
			}
			
			String sql = 
				"select top " + page.getPageSize() + " * from (" + 
				"	select min(b.precedence) as precedence,min(b.amount) as amount,min(b.place) place,min(b.orderType) as orderType, p.bigBatchNumber as bid, min(b.bigBattchNumber) as bigBatchNumber,min(b.createTime) as createTime" + 
				"	from smallnessBatchNumber p full join BigbatchNumber b on(b.id = p.bigBatchNumber) " + 
				"	where p.makeStatus = 0 and p.isValid = 0 and p.isRemakes = 0 group by p.bigBatchNumber" + 
				
				") a where a.bid not in " + 
				"	(" + 
				"	 select top " + (page.getCurrentPage()-1) * page.getPageSize() + " b.bid from " + 
				"		(" + 
				"			select p.bigBatchNumber as bid" + 
				"			from smallnessBatchNumber p full join BigbatchNumber b on(b.id = p.bigBatchNumber) " + 
				"			where p.makeStatus = 0 and p.isValid = 0 and p.isRemakes = 0 group by p.bigBatchNumber" + 
				"		) b" + 
				"	)";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BigBatchNumber b = new BigBatchNumber();
				b.setId(rs.getInt("bid"));
				b.setPlace(rs.getString("place"));
				b.setOrderType(rs.getInt("orderType"));
				b.setBigBattchNumber(rs.getString("bigBatchNumber"));
				b.setCreateTime(rs.getDate("createTime"));
				b.setAmount(rs.getInt("amount"));
				b.setPrecedence(rs.getInt("precedence"));
				bigBatchNumbers.add(b);
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
		return bigBatchNumbers;
	}
	
	
}
