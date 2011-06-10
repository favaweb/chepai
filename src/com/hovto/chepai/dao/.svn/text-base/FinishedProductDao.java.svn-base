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

import com.hovto.chepai.model.MonthfinishedProduct;
import com.hovto.chepai.model.StatisticsFinishedProducts;

@Component
public class FinishedProductDao {
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * 成品发货统计
	 * @param sql
	 * @param startDate
	 * @param endDate
	 * @param orderType
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public List<StatisticsFinishedProducts> findAmountList(String sql,Date startDate,Date endDate,int orderType){
		Connection conn = sessionFactory.getCurrentSession().connection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<StatisticsFinishedProducts> sfps = new ArrayList<StatisticsFinishedProducts>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, startDate);
			if(endDate != null){
				pstmt.setDate(2, endDate);
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				StatisticsFinishedProducts statisticsFinishedProducts = new StatisticsFinishedProducts();
				statisticsFinishedProducts.setAmount(rs.getInt("amount"));
				statisticsFinishedProducts.setPlace(rs.getString("place"));
				statisticsFinishedProducts.setTypename(rs.getString("typename"));
				sfps.add(statisticsFinishedProducts);
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
		return sfps;
	}
	
	/**
	 * 成品发货统计-查询有数据的类型集合
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public List<String> findTypeList(String sql,Date startDate,Date endDate,int orderType){
		Connection conn = sessionFactory.getCurrentSession().connection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> types = new ArrayList<String>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, startDate);
			if(endDate != null){
				pstmt.setDate(2, endDate);
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				String one = null;
				one = rs.getString("typename");
				types.add(one);
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
		return types;
	}
	
	/**
	 * 成品发货统计-查询所有地区集合
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public List<String> findPlaceList(int ordertype){
		String sql = "select sn.place from SmallnessBatchNumber sn where sn.place is not null ";
		if(ordertype==1){
			sql += "and (ordertype=1 or ordertype=2) ";
		}else if(ordertype==2){
			sql += "and ordertype=3 ";
		}
		sql += "group by sn.place";
		Connection conn = sessionFactory.getCurrentSession().connection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> places = new ArrayList<String>();
		try{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				String one = null;
				one = rs.getString("place");
				places.add(one);
			}
		}catch(Exception e){
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
		return places;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 成品月统计（旧）
	 * @param startDate
	 * @param endDate
	 * @param orderType
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public List<MonthfinishedProduct> findfinishedProductByMonth(Date startDate,Date endDate,int orderType ){
		String sql = this.selectSql(orderType);
		Connection conn = sessionFactory.getCurrentSession().connection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MonthfinishedProduct> monfinishedProducts = new ArrayList<MonthfinishedProduct>();
		try{
			pstmt = conn.prepareStatement(sql);
			for(int i=1;i<=12;i++){
				if(i%2==1){
					pstmt.setDate(i, startDate);
				}else
				pstmt.setDate(i, endDate);
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				MonthfinishedProduct m = new MonthfinishedProduct();
				m.setPlace(rs.getString("place"));
				m.setDa(rs.getInt("da"));
				m.setGua(rs.getInt("gua"));
				m.setJiao(rs.getInt("jiao"));
				m.setMo(rs.getInt("mo"));
				m.setNong(rs.getInt("nong"));
				m.setXiao(rs.getInt("xiao"));
				monfinishedProducts.add(m);
			}
		}catch(java.sql.SQLException e){
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
		return monfinishedProducts;
	}
	

	
	@SuppressWarnings("unused")
	private String selectSql(int orderType){
		String sql = null;
		if(orderType == 0){
			sql ="select place,(select sum(amount) from SmallnessBatchNumber as sbn left join NumberPlateType as np on sbn.numberPlateType=np.id  where typeName='大型汽车号牌' and datetime >= ? and datetime < ? "
				+ "and sbn.place=s.place) as 'da' ,(select sum(amount) from SmallnessBatchNumber as sbn left join NumberPlateType as np on sbn.numberPlateType=np.id  where typeName='小型汽车号牌' and datetime >= ? and datetime < ? "
				+ "and sbn.place=s.place) as 'xiao',(select sum(amount) from SmallnessBatchNumber as sbn left join NumberPlateType as np on sbn.numberPlateType=np.id  where typeName='普通摩托车号牌' and datetime >= ? and datetime < ? "
				+ "and sbn.place=s.place) as 'mo',(select sum(amount) from SmallnessBatchNumber as sbn left join NumberPlateType as np on sbn.numberPlateType=np.id  where typeName='农用运输车' and datetime >= ? and datetime < ? "
				+ "and sbn.place=s.place) as 'nong',(select sum(amount) from SmallnessBatchNumber as sbn left join NumberPlateType as np on sbn.numberPlateType=np.id  where typeName='挂车号牌' and datetime >= ? and datetime < ? "
				+ "and sbn.place=s.place) as 'gua',(select sum(amount) from SmallnessBatchNumber as sbn left join NumberPlateType as np on sbn.numberPlateType=np.id  where typeName='教练汽车' and datetime >= ? and datetime < ? "
				+ "and sbn.place=s.place) as 'jiao' from SmallnessBatchNumber as s where place is not null  group by place ";
		}
		else if(orderType == 1){
			sql ="select place,(select sum(amount) from SmallnessBatchNumber as sbn left join NumberPlateType as np on sbn.numberPlateType=np.id  where typeName='大型汽车号牌' and datetime >= ? and datetime < ? and (orderType =1 or orderType = 2)  "
				+ "and sbn.place=s.place) as 'da' ,(select sum(amount) from SmallnessBatchNumber as sbn left join NumberPlateType as np on sbn.numberPlateType=np.id  where typeName='小型汽车号牌' and datetime >= ? and datetime < ? and (orderType =1 or orderType = 2)   "
				+ "and sbn.place=s.place) as 'xiao',(select sum(amount) from SmallnessBatchNumber as sbn left join NumberPlateType as np on sbn.numberPlateType=np.id  where typeName='普通摩托车号牌' and datetime >= ? and datetime < ? and (orderType =1 or orderType = 2)  "
				+ "and sbn.place=s.place) as 'mo',(select sum(amount) from SmallnessBatchNumber as sbn left join NumberPlateType as np on sbn.numberPlateType=np.id  where typeName='农用运输车' and datetime >= ? and datetime < ? and (orderType =1 or orderType = 2)   "
				+ "and sbn.place=s.place) as 'nong',(select sum(amount) from SmallnessBatchNumber as sbn left join NumberPlateType as np on sbn.numberPlateType=np.id  where typeName='挂车号牌' and datetime >= ? and datetime < ? and (orderType =1 or orderType = 2)   "
				+ "and sbn.place=s.place) as 'gua',(select sum(amount) from SmallnessBatchNumber as sbn left join NumberPlateType as np on sbn.numberPlateType=np.id  where typeName='教练汽车' and datetime >= ? and datetime < ? and (orderType =1 or orderType = 2)   "
				+ "and sbn.place=s.place) as 'jiao' from SmallnessBatchNumber as s where place is not null  group by place ";
		}else if(orderType ==2){
			sql ="select place,(select sum(amount) from SmallnessBatchNumber as sbn left join NumberPlateType as np on sbn.numberPlateType=np.id  where typeName='大型汽车号牌' and datetime >= ? and datetime < ? and orderType =3 "
				+ "and sbn.place=s.place) as 'da' ,(select sum(amount) from SmallnessBatchNumber as sbn left join NumberPlateType as np on sbn.numberPlateType=np.id  where typeName='小型汽车号牌' and datetime >= ? and datetime < ? and orderType =3  "
				+ "and sbn.place=s.place) as 'xiao',(select sum(amount) from SmallnessBatchNumber as sbn left join NumberPlateType as np on sbn.numberPlateType=np.id  where typeName='普通摩托车号牌' and datetime >= ? and datetime < ?  and orderType =3 "
				+ "and sbn.place=s.place) as 'mo',(select sum(amount) from SmallnessBatchNumber as sbn left join NumberPlateType as np on sbn.numberPlateType=np.id  where typeName='农用运输车' and datetime >= ? and datetime < ? and orderType =3  "
				+ "and sbn.place=s.place) as 'nong',(select sum(amount) from SmallnessBatchNumber as sbn left join NumberPlateType as np on sbn.numberPlateType=np.id  where typeName='挂车号牌' and datetime >= ? and datetime < ? and orderType =3  "
				+ "and sbn.place=s.place) as 'gua',(select sum(amount) from SmallnessBatchNumber as sbn left join NumberPlateType as np on sbn.numberPlateType=np.id  where typeName='教练汽车' and datetime >= ? and datetime < ? and orderType =3  "
				+ "and sbn.place=s.place) as 'jiao' from SmallnessBatchNumber as s where place is not null  group by place ";
		}
		return sql;
	}
}
