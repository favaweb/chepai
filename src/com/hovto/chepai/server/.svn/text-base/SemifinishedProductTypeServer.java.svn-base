package com.hovto.chepai.server;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.hovto.chepai.dao.ReserveDao;
import com.hovto.chepai.dao.SemifinishedProductTypeDao;
import com.hovto.chepai.model.Reserve;
import com.hovto.chepai.model.SemifinishedProductType;


@Component
@Transactional
public class SemifinishedProductTypeServer {
	@Resource
	private SemifinishedProductTypeDao semifinishedProductTypeDao;
	@Resource
	private ReserveDao reserveDao;

	/**
	 * 增加一条半成品类型记录
	 * @param enterRegister
	 * @return
	 */
	public boolean add(SemifinishedProductType semifinishedProductType){
		try{
			Reserve reserve = new Reserve();
			reserve.setSemifinishedProductType(semifinishedProductType);
			reserve.setStandard(semifinishedProductType.getStandard());
			reserveDao.add(reserve);
			semifinishedProductTypeDao.add(semifinishedProductType);
			reserve = null;
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 根据前后牌查找
	 * @param type
	 * @return
	 */
	public List<SemifinishedProductType> findByType(int type){
		return semifinishedProductTypeDao.findByType(type);
	}
	/**
	 * 修改一条半成品类型记录
	 * @param enterRegister
	 * @return
	 */
	public boolean modify(SemifinishedProductType semifinishedProductType){
		try{
			semifinishedProductTypeDao.modify(semifinishedProductType);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 查找单条半成品类型记录
	 * @param enterRegister
	 * @return 查询失败或无记录则返回null
	 */
	public SemifinishedProductType find(SemifinishedProductType semifinishedProductType){
		try{
			return semifinishedProductTypeDao.find(semifinishedProductType);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除一条半成品类型记录
	 * @param enterRegister
	 * @return
	 */
	public boolean delete(SemifinishedProductType semifinishedProductType){
		try{
			semifinishedProductTypeDao.delete(semifinishedProductType);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 返回所有半成品类型记录
	 * @return
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<SemifinishedProductType> findList(){
		try{
			return semifinishedProductTypeDao.findList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
