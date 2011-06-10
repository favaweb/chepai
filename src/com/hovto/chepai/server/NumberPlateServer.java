package com.hovto.chepai.server;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hovto.chepai.dao.BatchNumberMergeDao;
import com.hovto.chepai.dao.NumberPlateDao;
import com.hovto.chepai.dao.WorkFlowDao;
import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.BatchNumberMerge;
import com.hovto.chepai.model.BigBatchNumber;
import com.hovto.chepai.model.BusinessDepartment;
import com.hovto.chepai.model.FlowType;
import com.hovto.chepai.model.NumberPlate;
import com.hovto.chepai.model.NumberPlateType;
import com.hovto.chepai.model.SmallnessBatchNumber;
import com.hovto.chepai.model.TypeStats;
import com.hovto.chepai.model.WorkFlow;
import com.hovto.chepai.tool.parseExcel;
import com.hovto.chepai.web.action.ExcelUtils;

@Component
@Transactional
public class NumberPlateServer {
	@Resource
	private NumberPlateDao numberPlateDao;
	@Resource
	private SmallnessBatchNumberServer sBatchNumberServer;
	@Resource
	private BatchNumberMergeDao BatchNumberMergeDao;
	@Resource
	private WorkFlowDao workFlowDao;
	@Resource
	private ExcelUtils excelUtils;
	@Resource
	private BigBatchNumberServer bigBatchNumberServer;

	public NumberPlateDao getNumberPlateDao() {
		return numberPlateDao;
	}
	public void update(NumberPlate numberPlate){
		numberPlateDao.modify(numberPlate);
	}
	public void setNumberPlateDao(NumberPlateDao numberPlateDao) {
		this.numberPlateDao = numberPlateDao;
	}
	public NumberPlate findById(int numberPlateId){
		return numberPlateDao.find(new NumberPlate(numberPlateId));
	}
	/**
	 * 
	 * @param list ��������
	 * @param bigBatchNumber ������
	 */
	public boolean save(List list,BigBatchNumber bigBatchNumber){
		try {
			SmallnessBatchNumber sBatchNumber=new SmallnessBatchNumber();
			for(int i=0;i<list.size();i++){
				Map map=(Map)list.get(i);
				if(i==0){
					int amount=this.loadAmount(list, map, i);
					//����С����
					sBatchNumber=this.addSmallnessBatchNumber(map, bigBatchNumber,amount );
				}else if(!map.get("numberSegmentId").equals(((Map)list.get(i-1)).get("numberSegmentId"))){
					int amount=this.loadAmount(list, map, i);
					//����С����
					sBatchNumber=this.addSmallnessBatchNumber(map, bigBatchNumber,amount);
				}
				NumberPlate numberPlate=new NumberPlate();
				numberPlate.setOrderNumber(Integer.parseInt(map.get("orderNumber").toString()));
				BusinessDepartment businessDepartment=new BusinessDepartment();
				businessDepartment.setId(Integer.parseInt(map.get("businessDepartment").toString()));
				numberPlate.setBusinessDepartment(businessDepartment);
				NumberPlateType numberPlateType=new NumberPlateType();
				numberPlateType.setId(Integer.parseInt(map.get("licensePlateType").toString()));
				numberPlate.setNumberPlateType(numberPlateType);
				numberPlate.setLicensePlateNumber(map.get("number").toString());
				numberPlate.setSmallnessBatchNumber(sBatchNumber);
				numberPlate.setBigBatchNumber(bigBatchNumber);
				numberPlateDao.add(numberPlate);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	public int loadAmount(List list,Map map,int i){
		int amount=0;
		for(int j=list.size()-1;j>=i;j--){
			Map maps=(Map)list.get(j);
			if(map.get("numberSegmentId").toString().trim().equals(maps.get("numberSegmentId").toString().trim())){
				amount++;
			}
		}
		return amount;
	}
	//���һ��������
	public SmallnessBatchNumber addSmallnessBatchNumber(Map map,BigBatchNumber bigBatchNumber,int amount){
		SmallnessBatchNumber sBatchNumber=new SmallnessBatchNumber();
		try {
			sBatchNumber.setBigBatchNumber(bigBatchNumber);
			sBatchNumber.setOrderType(bigBatchNumber.getOrderType());
			sBatchNumber.setPlace(bigBatchNumber.getPlace());
			sBatchNumber.setDateTime(bigBatchNumber.getCreateTime());
			sBatchNumber.setOtherName(map.get("otherName").toString());
			sBatchNumber.setPrecedence(bigBatchNumber.getPrecedence());
			sBatchNumber.setAmount(amount);
			sBatchNumber.setNumberPlateType(bigBatchNumber.getNumberPlateType());
			if(amount>45&&bigBatchNumber.getOrderType()!=3){
				sBatchNumber.setMakeStatus(1);
				sBatchNumber.setIsDistribute(1);
			}
			sBatchNumber.setSmallnessBatchNumber(map.get("numberSegmentId").toString());
			boolean is=sBatchNumberServer.add(sBatchNumber);
			if(is){
				//�����̱��������һ������
				if(amount>45&&bigBatchNumber.getOrderType()!=3){//���ǺŶεĶ���45���Զ��·�����
					WorkFlow workFlow = new WorkFlow();
					workFlow.setSmallnessBatchNumber(sBatchNumber);
					workFlow.setBigBatchNumber(sBatchNumber.getBigBatchNumber());
					workFlow.setCurrentFlow(new FlowType(1));
					workFlow.setNextFlow(new FlowType(2));
					workFlowDao.add(workFlow);
				}
				return sBatchNumber;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//���һ�����ƺ���
	public boolean add(Map map,SmallnessBatchNumber sBatchNumber){
		NumberPlate numberPlate=new NumberPlate();
		try {
			numberPlate.setSmallnessBatchNumber(sBatchNumber);
				BusinessDepartment businessDepartment=new BusinessDepartment();
				businessDepartment.setId(Integer.parseInt(map.get("businessDepartment").toString()));
				NumberPlateType numberPlateType=new NumberPlateType();
				numberPlateType.setId(Integer.parseInt(map.get("licensePlateType").toString()));
			numberPlate.setBusinessDepartment(businessDepartment);
			numberPlate.setNumberPlateType(numberPlateType);
			numberPlate.setLicensePlateNumber(map.get("number").toString());
			numberPlate.setOrderNumber(Integer.parseInt(map.get("orderNumber").toString()));
			numberPlateDao.add(numberPlate);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<NumberPlate> findBySmallnessBatchId(int id) {
		return numberPlateDao.findBySmallnessBatchId(id);
	}

	public void findByHBSmallnessBatchId(int id, List<NumberPlate> numberPlates) {
		List<BatchNumberMerge> batchNumberMerges = BatchNumberMergeDao.findBySmallnessBatchNumber(id);
		for(BatchNumberMerge bnm : batchNumberMerges) {
			for(NumberPlate n :numberPlateDao.findByBigBatchId(bnm.getBigBatchNumber().getId())) {
				numberPlates.add(n);
			}
		}
	}
	
	public List<NumberPlate> findByBigBatchId(int id){
		return numberPlateDao.findByBigBatchId(id);
		
	}
	
	/**
	 * ͳ�Ƶ���� ��������
	 * @return
	 */
	public List<TypeStats> typeStats(String dateTime) {
		Date date = null;
		if(dateTime == null || dateTime.equals("")) 
			date = new Date(System.currentTimeMillis());
		else {
			try {
				date = Date.valueOf(dateTime);
			} catch (Exception e) {
				throw new ChePaiException("ʱ���ʽ����!<a href='javascript:window.history.go(-1);'>���ز���</a>");
			}
		}
			 
		return numberPlateDao.typeStats(new SimpleDateFormat("yyyy-MM-dd").format(date));
	}
	
	public void importFile(File file, String fileName, String area,
			String orderType, Map departmentList, Map numberTypeList) {
		List result = excelUtils.readExcel(file, fileName, area, orderType, departmentList, numberTypeList);
		if(result==null){
			throw new ChePaiException("�ظ�����!<a href='javascript:window.history.go(-1);'>���ز���</a>");
		}
		
		BigBatchNumber bigBatchNumber=new BigBatchNumber();//����������bean
		//��ȡ����
		bigBatchNumber.setBigBattchNumber(fileName.substring(0,fileName.indexOf(".")));
		bigBatchNumber.setBeginSegmentNumber(((Map)result.get(0)).get("number").toString());//��ʼ�Ŷ�
		bigBatchNumber.setEndSegmentNumber(((Map)result.get(result.size()-1)).get("number").toString());//�����Ŷ�)
		bigBatchNumber.setAmount(result.size());//����;
		bigBatchNumber.setPlace(area);//��ַ
		bigBatchNumber.setOrderType(orderTypeConvert(orderType));//���ͣ�3�ŶΣ�2��ѡ��1����
		bigBatchNumber.setOtherName(((Map)result.get(0)).get("otherName").toString());//��������
		int precedence=2;
		if(orderType.equals("�Ŷ�")){
			precedence=3;//�Ŷ�Ĭ��
			bigBatchNumber.setNumberPlateType(new NumberPlateType(Integer.parseInt(((Map)result.get(0)).get("licensePlateType").toString())));
			excelUtils.setSame(true);
		} else if(excelUtils.isSame()) {
			bigBatchNumber.setNumberPlateType(new NumberPlateType(Integer.parseInt(((Map)result.get(0)).get("licensePlateType").toString())));
			excelUtils.setSame(true);
		} else {
			bigBatchNumber.setNumberPlateType(new NumberPlateType(18));
			excelUtils.setSame(true);
		}
		bigBatchNumber.setPrecedence(precedence);
		bigBatchNumber.setCreateTime(new java.util.Date());//����ʱ��
		boolean is=bigBatchNumberServer.add(bigBatchNumber);
		if(is){
			this.save(result, bigBatchNumber);
		}
		
	}
	/**
	 * ���������ļ�
	 * @param orderTypes
	 * @param remoteIp
	 * @param departmentList
	 * @param numberTypeList
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	public String importFiles(String[] orderTypes, String remoteIp,
			Map departmentList, Map numberTypeList) throws IOException {
		List<File> realFiles = new ArrayList<File>();//�ļ�����
		String tofilepath = null;
		String filename = null;
		if(orderTypes != null && remoteIp != null && !remoteIp.equals("")) {
			for(String orderType : orderTypes) {
				String parentPath = "\\\\" + remoteIp.trim() + "\\�������ݰ�\\" + orderType;
				File parentFile = new File(parentPath);
				if(parentFile.isDirectory()) {
					
					for(String area : parentFile.list()) {
						String areaPath = parentPath + "\\" + area;
						File areaFile = new File(areaPath);
						System.out.println("----------����:\t" + area);
						for(String fileName : areaFile.list()) {
							//ReadExcel.readExcel(areaPath + "\\" + fileName, System.out);
							tofilepath = ServletActionContext.getRequest().getRealPath("\\excel\\Import\\parse")+"\\" + fileName;
							filename = areaPath + "\\" + fileName;
							
							File file = parseExcel.toExcel(filename, tofilepath);
							realFiles.add(file);
							System.out.println("������:\t" + fileName); 
							long startTime = System.currentTimeMillis();
							this.importFile(file, fileName, area, orderType, departmentList, numberTypeList);
							long entTime = System.currentTimeMillis();
							System.out.println("����ʱ��:\t" + (entTime - startTime));
						}
					}
					
					
				} else
					throw new ChePaiException("��������IP����!������ȷ�����ݰ��Ƿ��ڸ�IP��ַ��!<a href='NumberPlate!importInput.action'>���ز���</a>");
			}
		}
		
		for(File f : realFiles) {
			try {
				boolean isDelete = f.delete();
			} catch (Exception e) {
				return "���ݵ���ɹ�����������û��ɾ�������ֶ�ɾ��!";
			}
		}
		return null;
		
	}
	
	public int orderTypeConvert(String orderType){
		if(orderType.equals("����")){
			return 1;
		}else if(orderType.equals("��ѡ")){
			return 2;
		}else if(orderType.equals("�Ŷ�")){
			return 3;
		}
		throw new ChePaiException("�޷�ʶ��������");
	}
	
	
	
	
	
	
}
