package com.hovto.chepai.server;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.InputRegisterDao;
import com.hovto.chepai.dao.ReserveDao;
import com.hovto.chepai.dao.SemifinishedProductTypeDao;
import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.InputRegister;
import com.hovto.chepai.model.Reserve;
import com.hovto.chepai.tool.Page;

@Component
@Transactional
public class InputRegisterServer {
	@Resource
	private InputRegisterDao inputRegisterDao;
	@Resource
	private ReserveDao reserveDao;
	@Resource
	private SemifinishedProductTypeDao semifinishedProductTypeDao;

	/**
	 * ����һ����/�����¼
	 * 
	 * @param enterRegister
	 * @return
	 */
	public boolean add(InputRegister inputRegister) {
		int typeId = // ��ȡ���Ʒ����
		semifinishedProductTypeDao.find(
				inputRegister.getSemifinishedProductType()).getId();
		// ��ȡ�����͵Ŀ���¼
		Reserve reserve = reserveDao.findbytypeId(typeId);
		if (inputRegister.getType() == 1) { // 1Ϊ���,���������
			reserve.setRemainder(reserve.getRemainder()
					+ inputRegister.getReductionAmount());
		} else if (inputRegister.getType() == 2) {// 2Ϊ����,�����ȥ��Ӧ
			if (reserve.getRemainder() < inputRegister.getReductionAmount()) {
				throw new ChePaiException("��治�㣡<a href='javascript:history.go(-1);'>����</a>");
			}
			reserve.setRemainder(reserve.getRemainder()
					- inputRegister.getReductionAmount());
		}
		// �޸Ŀ���¼
		reserveDao.modify(reserve);
		// ��ӳ�/����¼
		inputRegisterDao.add(inputRegister);

		return true;
	}

	/**
	 * �޸�һ������¼
	 * 
	 * @param inputRegister
	 * @return
	 */
	public boolean update(InputRegister inputRegister,
			InputRegister reinputRegister) {

			int retypeId = // ԭ���Ʒ����
				semifinishedProductTypeDao.find(
						reinputRegister.getSemifinishedProductType()).getId();
			Reserve reserve = reserveDao.findbytypeId(retypeId);
			int recount =reinputRegister.getReductionAmount();
			int newcount = inputRegister.getReductionAmount();
			//�鿴�Ƿ��治��
			if(inputRegister.getType()==1) //�������
			{
				if((newcount<recount) && (recount-newcount)>reserve.getRemainder()){
					throw new ChePaiException("��治�㣡<a href='javascript:history.go(-1);'>����</a>");
				}
				reserve.setRemainder(reserve.getRemainder()-recount+newcount);//��ȥԭ������������������
			}
			else if(inputRegister.getType() ==2){  //��������
				if((newcount>recount) && (newcount-recount)>reserve.getRemainder()){
					throw new ChePaiException("��治�㣡<a href='javascript:history.go(-1);'>����</a>");
				}
				reserve.setRemainder(reserve.getRemainder()+recount-newcount);//����ԭ����������ȥ�³�����
			}
			reserveDao.modify(reserve);
			reinputRegister.setPurpose(inputRegister.getPurpose());
			reinputRegister.setReductionAmount(inputRegister.getReductionAmount());
			reinputRegister.setReductionNumber(inputRegister.getReductionNumber());
			reinputRegister.setRemark(inputRegister.getRemark());
			reinputRegister.setSemifinishedProductType(inputRegister.getSemifinishedProductType());
			
			inputRegisterDao.modify(reinputRegister);
			
		
		return true;

	}

	/**
	 * ���ҵ�������¼
	 * 
	 * @param inputRegister
	 * @return ��ѯʧ�ܻ��޼�¼�򷵻�null
	 */
	public InputRegister find(InputRegister inputRegister) {
		try {
			return inputRegisterDao.find(inputRegister);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ɾ��һ������¼
	 * 
	 * @param inputRegister
	 * @return
	 */
	public boolean delete(InputRegister inputRegister) {
		try {
			int typeId = // ��ȡ���Ʒ����
			semifinishedProductTypeDao.find(
					inputRegister.getSemifinishedProductType()).getId();
			// ��ȡ�����͵Ŀ���¼
			Reserve reserve = reserveDao.findbytypeId(typeId);
			if (inputRegister.getType() == 1) { // 1Ϊ���,�����ȥ
				reserve.setRemainder(reserve.getRemainder()
						- inputRegister.getReductionAmount());
			} else if (inputRegister.getType() == 2) {// 2Ϊ����,�������
				reserve.setRemainder(reserve.getRemainder()
						+ inputRegister.getReductionAmount());
			}
			// �޸Ŀ���¼
			reserveDao.modify(reserve);
			inputRegisterDao.delete(inputRegister);
			reserve = null;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	
	/**
	 * ��������ҳ���س�����¼�ϼ�
	 * @param inputRegister
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<InputRegister> findList(InputRegister inputRegister,Date startDate,Date endDate,Page page){
		try{
 		String hql  = "select p from InputRegister p ";
		if(startDate != null || inputRegister != null){
			if(startDate != null){
				if(hql.indexOf("where")==-1) hql+="where ";
				if(endDate == null){
					hql += "p.createDate >= :startDate ";
				}else{
					Calendar c = Calendar.getInstance();
					c.setTime(endDate);
					c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
					endDate = c.getTime();
					hql += "p.createDate >= :startDate and p.createDate < :endDate ";
				}
			}
			if(inputRegister.getReductionNumber() != null && !inputRegister.getReductionNumber().trim().equals("")){
				if(hql.indexOf("where")==-1) hql+="where ";
				else if(startDate!=null){
					hql += "and  ";
				}
				hql +="p.reductionNumber = :reductionNumber ";
			}
			if(inputRegister.getSemifinishedProductType().getId() != 0){
				if(hql.indexOf("where")==-1) hql+="where ";
				else if(startDate!=null || !inputRegister.getReductionNumber().equals("")){
					hql += "and ";
				}
				hql += "p.semifinishedProductType = :semifinishedProductType ";
			}
			if(inputRegister.getType() != 0){
				if(hql.indexOf("where")==-1) hql+="where ";
				else if(startDate!=null || !inputRegister.getReductionNumber().equals("") || inputRegister.getSemifinishedProductType().getId() != 0){
					hql += "and ";
				}
				hql += "p.type = :type ";
			}
			String hql1 = hql.replaceFirst("p", "count(*)");
			int count = inputRegisterDao.countList(hql1, startDate, endDate, inputRegister);
			page.setLastPage(count);
			return inputRegisterDao.findListbyHQL(hql, startDate, endDate, inputRegister,page);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}



	
}
