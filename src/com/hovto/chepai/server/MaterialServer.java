package com.hovto.chepai.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.MaterialDao;
import com.hovto.chepai.dao.NumberPlateDao;
import com.hovto.chepai.model.Material;
import com.hovto.chepai.model.NumberPlate;

@Component
@Transactional
public class MaterialServer {
	@Resource
	private MaterialDao materialDao;
	@Resource
	private NumberPlateDao numberPlateDao;
	/**
	 * 根据半成品id修改车型半成品数量
	 * @param id  半成品id
	 * @param total//数量
	 * @return
	 */
	public boolean updateTotal(int id,int total){
		try {
			//根据车型id查找出车型半成品数据
			Material material=materialDao.find(new Material(id,total,null) );
			material.setTotal(total);
			materialDao.modify(material);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 修改半成品数量
	 * @param map分配后半成品数量
	 * @return
	 */
	public boolean updateMaterial(Map map){
		try {
			List<Material> list=materialDao.findList();
			for(int i=0;i<list.size();i++){
				Material material=(Material)list.get(i);
				boolean str =map.containsKey(material.getId());//根据车牌种类
				if(str){
					material.setTotal(Integer.parseInt(map.get(material.getId()).toString()));
					materialDao.modify(material);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 查询出子批号累车牌类型并修改其数量
	 * @param smallnessBatchId 订单子批号
	 * @return
	 */
	public boolean findByNumberPlateType(int smallnessBatchId){
		try {
			List<NumberPlate> result=numberPlateDao.findBySmallnessBatchId(smallnessBatchId);
			List<Material> list=materialDao.findList();
			Map map=new HashMap();
			for(int i=0;i<list.size();i++){
				Material material=(Material)list.get(i);
				map.put(material.getId(), material.getTotal());
			}
			for(int i=0;i<result.size();i++){
				NumberPlate numberPlate=(NumberPlate)result.get(i);
				boolean str =map.containsKey(numberPlate.getNumberPlateType().getId());//根据车牌种类
				if(str){//判断是否存在该车牌类型
					//获取该类型的半成品数量
					int total=Integer.parseInt(map.get(numberPlate.getNumberPlateType().getId()).toString());
					map.put(numberPlate.getNumberPlateType().getId(), total-1);//该类型的半成品-1
				}
			}
			this.updateMaterial(map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}
}
