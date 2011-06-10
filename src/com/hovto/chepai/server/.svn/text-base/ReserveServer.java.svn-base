package com.hovto.chepai.server;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.ReserveDao;
import com.hovto.chepai.model.InputRegister;
import com.hovto.chepai.model.Reserve;


@Component
@Transactional
public class ReserveServer {
	@Resource
	private ReserveDao reserveDao;

	/**
	 * 增加一个库存记录
	 * @param enterRegister
	 * @return
	 */
	public boolean add(Reserve seserve){
		try{
			reserveDao.add(seserve);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 修改一条库存记录（入库、出库）
	 * @param inputRegister
	 * @return
	 */
	public boolean modify(Reserve seserve ,InputRegister inputRegister){
		try{
			reserveDao.modify(seserve);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 查找单条库存记录
	 * @param seserve
	 * @return 查询失败或无记录则返回null
	 */
	public Reserve find(Reserve seserve){
		try{
			return reserveDao.find(seserve);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除一条库存记录
	 * @param seserve
	 * @return
	 */
	public boolean delete(Reserve seserve){
		try{
			reserveDao.delete(seserve);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 返回所有库存记录
	 * @return
	 */
	public List<Reserve> findList(){
		try{
			return reserveDao.findList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
