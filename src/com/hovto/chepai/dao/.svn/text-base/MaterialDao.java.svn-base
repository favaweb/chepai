package com.hovto.chepai.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.Material;
import com.hovto.chepai.model.StatisticsMaterial;

@Component
public class MaterialDao {
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	public void add(Material material) {
		sessionFactory.getCurrentSession().save(material);
	}
	
	public void delete(Material material) {
		sessionFactory.getCurrentSession().delete(this.find(material));
	}
	
	public void modify(Material material) {
		sessionFactory.getCurrentSession().update(material);
	}
	
	public Material find(Material material) {
		return (Material) sessionFactory.getCurrentSession().get(Material.class, material.getId());
	}
	
	@SuppressWarnings("unchecked")
	public List<Material> findList() {
		return sessionFactory.getCurrentSession().createQuery("select p from Material p").list();
	}
	
	/**
	 * ·ÏÆ·Í³¼Æ
	 * @param sql
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public List<StatisticsMaterial> findStatisticsList(String sql,Date startDate, Date endDate){
		Connection conn = sessionFactory.getCurrentSession().connection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<StatisticsMaterial> meterials = new ArrayList<StatisticsMaterial>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, startDate);
			if(endDate!=null)
			pstmt.setDate(2, endDate);
			rs = pstmt.executeQuery();
			while(rs.next()){
				StatisticsMaterial one = new StatisticsMaterial();
				one.setTypename(rs.getString("typename"));
				one.setDaynight(rs.getInt("daynight"));
				one.setAmount(rs.getInt("amount"));
				meterials.add(one);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return meterials;
	}
}
