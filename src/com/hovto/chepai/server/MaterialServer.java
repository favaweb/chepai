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
	 * ���ݰ��Ʒid�޸ĳ��Ͱ��Ʒ����
	 * @param id  ���Ʒid
	 * @param total//����
	 * @return
	 */
	public boolean updateTotal(int id,int total){
		try {
			//���ݳ���id���ҳ����Ͱ��Ʒ����
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
	 * �޸İ��Ʒ����
	 * @param map�������Ʒ����
	 * @return
	 */
	public boolean updateMaterial(Map map){
		try {
			List<Material> list=materialDao.findList();
			for(int i=0;i<list.size();i++){
				Material material=(Material)list.get(i);
				boolean str =map.containsKey(material.getId());//���ݳ�������
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
	 * ��ѯ���������۳������Ͳ��޸�������
	 * @param smallnessBatchId ����������
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
				boolean str =map.containsKey(numberPlate.getNumberPlateType().getId());//���ݳ�������
				if(str){//�ж��Ƿ���ڸó�������
					//��ȡ�����͵İ��Ʒ����
					int total=Integer.parseInt(map.get(numberPlate.getNumberPlateType().getId()).toString());
					map.put(numberPlate.getNumberPlateType().getId(), total-1);//�����͵İ��Ʒ-1
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
