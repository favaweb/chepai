package com.hovto.chepai.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.NumberPlate;
import com.hovto.chepai.model.Remakes;



@Component
public class RemakesDao {
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	public void add(Remakes remakes) {
		sessionFactory.getCurrentSession().save(remakes);
	}
	
	public void delete(Remakes remakes) {
		sessionFactory.getCurrentSession().delete(this.find(remakes));
	}
	
	public void modify(Remakes remakes) {
		sessionFactory.getCurrentSession().update(remakes);
	}
	
	public Remakes find(Remakes remakes) {
		return (Remakes) sessionFactory.getCurrentSession().get(Remakes.class, remakes.getId());
	}
	@SuppressWarnings("unchecked")
	public List<Remakes> queryList(){
		return sessionFactory.getCurrentSession().createQuery("select p from Remakes p where p.state=0").list();
	}
	/**
	 * 根据小批号id查询车牌数据
	 * @param smallnessBatchNumberId 小批号Id
	 * @return 车牌数据集合List
	 */
	@SuppressWarnings("unchecked")
	public List<NumberPlate> findBySmallnessBatchNumber(int smallnessBatchNumberId){
		return sessionFactory.getCurrentSession().createQuery("select p.numberPlate from Remakes p where p.state=1 and p.smallnessBatchNumber.id=:smallnessBatchNumberId").setParameter("smallnessBatchNumberId", smallnessBatchNumberId).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<NumberPlate> findFinish(int smallnessBatchNumberId){
		return sessionFactory.getCurrentSession().createQuery("select p.numberPlate from Remakes p where p.state=2 and p.smallnessBatchNumber.id=:smallnessBatchNumberId").setParameter("smallnessBatchNumberId", smallnessBatchNumberId).list();
	}
	/**
	 * 根据小批号id查询车牌数据
	 * @param smallnessBatchNumberId 小批号Id
	 * @return 车牌数据集合List
	 */
	@SuppressWarnings("unchecked")
	public List<Remakes> findBySBatchNumberId(int smallnessBatchNumberId){
		return sessionFactory.getCurrentSession().createQuery("select p from Remakes p where p.state=1 and p.smallnessBatchNumber.id=:smallnessBatchNumberId").setParameter("smallnessBatchNumberId", smallnessBatchNumberId).list();
	}
	/**
	 * 根据重制批号id和车牌id查询重制数据
	 * @param smallnessBatchNumberId 重制批号id
	 * @param licensePlateNumberId 车牌id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Remakes> findBySbatchNumberIdAndlicensePlateNumber(int smallnessBatchNumberId,int licensePlateNumberId){
		return sessionFactory.getCurrentSession().createQuery("select p from Remakes p where p.state=1 and p.numberPlate.id=:licensePlateNumberId and p.smallnessBatchNumber.id=:smallnessBatchNumberId").setParameter("licensePlateNumberId", licensePlateNumberId).setParameter("smallnessBatchNumberId", smallnessBatchNumberId).list();
	}
	/**
	 * 
	 * @param smallnessBatchNumberId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Remakes> findResultRemakes(int smallnessBatchNumberId){
		return sessionFactory.getCurrentSession().createQuery("select p from Remakes p where p.state!=2 and p.smallnessBatchNumber.id=:smallnessBatchNumberId").setParameter("smallnessBatchNumberId", smallnessBatchNumberId).list();
	}
	/**
	 * 查询出全部没有质检完的重制车牌
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Remakes> findByState() {
		return sessionFactory.getCurrentSession().createQuery("select p from Remakes p where p.state!=2").list();
	}
	
	/**
	 * //找出 车牌类型为 小型汽车类型的 车牌
	 * @param smallnessBatchNumberId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<NumberPlate> queryBySbId(int smallnessBatchNumberId){
		List<Remakes> remakesList=sessionFactory.getCurrentSession().createQuery("select p from Remakes p where p.numberPlate.numberPlateType.type=2 and p.smallnessBatchNumber.id=:smallnessBatchNumberId").setParameter("smallnessBatchNumberId", smallnessBatchNumberId).list();
		List<NumberPlate> numberPlateList = null;
		if(remakesList!=null){
			numberPlateList=new ArrayList<NumberPlate>(0);
			for(int i=0;i<remakesList.size();i++){
				numberPlateList.add(remakesList.get(i).getNumberPlate());
			}
		}
		return numberPlateList;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Remakes> findList() {
		return sessionFactory.getCurrentSession().createQuery("select p from Remakes p").list();
	}

	@SuppressWarnings("unchecked")
	public long findSmallIsRemakes(int smallId) {
		return (Long) sessionFactory.getCurrentSession().createQuery("select count(p) from Remakes p where p.smallnessBatchNumber.id = :smallId and p.state < 2").setParameter("smallId", smallId).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Remakes> findByPlateId(int plateId) {
		return sessionFactory.getCurrentSession().createQuery("select p from Remakes p where p.numberPlate.id = :plateId order by p.id desc ").setParameter("plateId", plateId).list();
	}
	
	
}
