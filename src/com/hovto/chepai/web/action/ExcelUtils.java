package com.hovto.chepai.web.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.model.BigBatchNumber;
import com.hovto.chepai.model.BusinessDepartment;
import com.hovto.chepai.model.NumberPlateType;
import com.hovto.chepai.server.BigBatchNumberServer;
import com.hovto.chepai.server.BusinessDepartmentServer;
import com.hovto.chepai.server.NumberPlateTypeServer;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class ExcelUtils {
	@Resource
	private BusinessDepartmentServer businessDepartmentServer;
	@Resource
	private NumberPlateTypeServer numberPlateTypeServer;
	@Resource
	private BigBatchNumberServer bigBatchNumberServer;
	private Map deptMap = null;
	private Map licenseTypeMap=null;
	private boolean isSame = true;
	
	public boolean isExist(String bigBatchNumberId){
		BigBatchNumber bigBatchNumberList= bigBatchNumberServer.findByBigBatchNumber(bigBatchNumberId);
		return bigBatchNumberList == null ? false:true ;
	}
	/**��ȡExcel�ļ�������
	 * @param file  ����ȡ���ļ�
	 * @return
	 */
	public List readExcel(File realFile, String file,Map deptMap,Map licenseTypeMap){
		//ҵ����
		this.deptMap = deptMap;
		//��������
		this.licenseTypeMap=licenseTypeMap;
		
		
		StringBuffer sb = new StringBuffer();
		List resultList = new ArrayList();	//��ȷ����
		List rlList=new ArrayList();//�Ŷη�����ȷ����
		
		//�õ��ļ����� (������)
		String orderName=file.substring(file.lastIndexOf("\\")+1, file.lastIndexOf("."));
		if(this.isExist(orderName))
			throw new ChePaiException("�����ţ�" + orderName + "���Ѿ�����,������ȷ�����������Ƿ���ȷ!<a href='NumberPlate!importInput.action'>���ز���</a>");
		
		
		String[] s=file.split("\\\\");
		//����
		String district=s[s.length-2];
		// ���ͣ��ŶΣ���ѡ������
		String type=s[s.length-3];
		
		
		Workbook wb = null;
		try {
			wb=Workbook.getWorkbook(realFile);
		} catch (BiffException e) {
			e.printStackTrace();
			throw new ChePaiException("Excel�ĵ������ʽ����,������Microsoft soft office Excel���������ļ���ʽ!<a href='NumberPlate!importInput.action'>���ز���</a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(wb==null)
			return null;
		
		//�����Workbook����֮�󣬾Ϳ���ͨ�����õ�Sheet��������������
		Sheet[] sheet = wb.getSheets();
		String otherName=(sheet[0].getRow(0))[0].getContents();//��ȡ��������
		
		
			if(sheet!=null&&sheet.length>0){
				//��ȡ��Ԫ��
				resultList = readCell(sb,sheet,orderName,otherName,resultList);
				/*//��ÿ�����������ѭ��
				for(int i=0;i<sheet.length;i++){
					//�õ���ǰ�����������
					int rowNum = sheet[i].getRows();
					int sumNumber=1001;//������
					String plateType  = null;
					
					//�ӵ����п�ʼ��..����..
					for(int j=2;j<rowNum;j++){
						if(java.lang.Character.isDigit((sheet[0].getRow(j))[0].getContents().charAt(0))){
							Map result = new HashMap();
							//�õ���ǰ�е����е�Ԫ��
							Cell[] cells = sheet[i].getRow(j);
							if(cells!=null&&cells.length>0){
								//��ÿ����Ԫ�����ѭ��
								for(int k=0;k<cells.length;k++){
									//��ȡ��ǰ��Ԫ���ֵ
									String cellValue = cells[k].getContents();
									sb.append(cellValue+"\t");
									
									//���
									if(k==0){
										result.put("orderNumber", cellValue.trim());
									}else if(k==1){ //ҵ����
										boolean str =this.deptMap.containsKey(cellValue.trim().toString());
										if(!str){
											this.loadDept(cellValue);
										}
										result.put("businessDepartment",this.deptMap.get(cellValue.trim()).toString());
									}else if(k==3){
										if(plateType == null) {
											plateType = cellValue.trim();
										} else {
											if(!cellValue.trim().equals(plateType)) 
												isSame = false;
										}
										boolean str=this.licenseTypeMap.containsKey(cellValue.trim().toString());
										if(!str){
											this.loadLicenseType(cellValue);
										}
										result.put("licensePlateType", this.licenseTypeMap.get(cellValue.trim()).toString());
									}else if(k==5){
										result.put("number", cellValue);
									}else{
										result.put(k, cellValue);
									}
									
								}
								result.put("numberSegmentId", orderName+sumNumber);
								result.put("otherName", otherName);
							}
							sb.append("\r\n");
							resultList.add(result);
							if(((j-1)%50)==0){
								sumNumber++;
							}
						}
						sb.append("\r\n");
					}
				}*/
			}
			
			int special = 0;
			//����Ǹ۰���
			if(district.equals("ʡ�����")){
				Map temp = (Map)resultList.get(0);
				if(temp.get(7).toString().indexOf("-")!=-1){
					special = 1;
				}
			}
			
			if(type.equals("�Ŷ�") || special == 1){
				for(int i=0;i<resultList.size();i++){
					Map map=(Map)resultList.get(i);
					if(java.lang.Character.isDigit(map.get("orderNumber").toString().charAt(0))){
						//��ȡҵ����
						String businessDepartment=map.get("businessDepartment").toString();
						//��ȡ��������
						String licensePlateType=map.get("licensePlateType").toString();
						//��ȡ��������
						String filterCondition=map.get(7).toString();
						//��ȡ�����
						String numberSegment=map.get("number").toString();
						
						int space = getSpace(licenseTypeMap,licensePlateType);
						
						//��ȡ����-ǰ�沿��
						String beginNumber=numberSegment.substring(0,numberSegment.lastIndexOf("-")).trim();
						//��ȡ����-���沿��
						String endNumber=numberSegment.substring(numberSegment.lastIndexOf("-")+1,numberSegment.length()).trim();
						char[] beginChar=beginNumber.trim().toCharArray();
						char[] endChar=endNumber.trim().toCharArray();
						String beginInt="";//������պ��뿪ʼ����
						String endInt="";//������պ����������
						int[] status=new int[beginChar.length];//״̬1Ϊ����
						for(int j=0;j<beginChar.length;j++){
							if(java.lang.Character.isDigit(beginChar[j])){//�ж��Ƿ�Ϊ����
								beginInt+=beginChar[j];
								endInt+=endChar[j];
								status[j]=1;
							}else{
								if(beginChar[j]==endChar[j]){
									status[j]=0;
								}else {
									status[j]=2;
								}
							}
						}
						int beginNumberInt=Integer.parseInt(beginInt);
						int endNumberInt=Integer.parseInt(endInt);
						int begin = beginNumberInt % 100;
						if(begin != 1)
							begin = 0;
						
						boolean flag = true;
						String finallyNumber="";//���պ���
						int sumNumber=1001;
						while (flag) {
							finallyNumber="";
							Map mapNumber=new HashMap();
							 for (int j = 0; j < beginChar.length; j++)
	                            {
								 finallyNumber =finallyNumber+beginChar[j];
	                            }
							 //��ȡ�κ�
							 String numString="";
							 for(int j=0;j<beginChar.length;j++){
								 if(java.lang.Character.isDigit(beginChar[j])){
									 numString=numString+beginChar[j];
								 }
							 }
							 //��ȡ���ƺ�2���ֶ�
							 int num=Integer.parseInt(numString.substring(numString.length()-2, numString.length()).trim());
							 if(num==(space+begin)||num==(begin)){
								 if(!numString.equals(beginInt))
									 sumNumber++;
							 }
							 boolean fl=this.filterCondition(filterCondition, finallyNumber);
							 if(fl){//�ж��Ƿ�ͨ����������
								 mapNumber.put("orderNumber", rlList.size()+1);
								 mapNumber.put("businessDepartment", businessDepartment);
								 mapNumber.put("licensePlateType", licensePlateType);
								 mapNumber.put("number", finallyNumber);
								 mapNumber.put("numberSegmentId", orderName+sumNumber);
								 mapNumber.put("otherName", otherName);
								 rlList.add(mapNumber);
							 }
							 fl = !fl;
							 if (finallyNumber.equals(endNumber))
	                         {
	                                flag = false;
	                          }else
	                          {
	                             for (int j =  beginChar.length - 1; j >= 0; j--)
	                             {
	                                 if (status[j] == 1)
	                                 {
	                                     if ('9'!=beginChar[j])
	                                     {                                            
	                                         int ascii = (int)beginChar[j];
	                                         ascii++;
	                                         beginChar[j] = (char)ascii;
	                                         fl=true;
	                                         break;
	                                     }
	                                     else if ('9'==beginChar[j])
	                                     {
	                                    	 beginChar[j] = '0';
	                                         continue;
	                                     }
	                                 }
	                             }
	                             if(!fl){
		                             for (int j = beginChar.length- 1; j >= 0; j--)
		                                 {
		                            	 if (status[j] == 2)
		                                     {
		                                         if (beginChar[j]==endChar[j])
		                                         {
		                                             continue;
		                                         }
		                                         else
		                                         {
		                                             int ascii = (int)beginChar[j];
		                                             ascii++;
		                                             beginChar[j] = (char)ascii;
		                                             break;
		                                         }
		                                     }
		                                 }
									 }
	                          	}
						}
					}
				}
			}else{
				//���ر���Դ���ͷ��ڴ�
				wb.close();
				return resultList;
			}
				//���ر���Դ���ͷ��ڴ�
				wb.close();
				return rlList;
	}
	
	/**��ȡExcel�ļ�������
	 * @param file  ����ȡ���ļ�
	 * @return
	 */
	public List readExcel(File file, String fileName, String area, String orderType,Map deptMap,Map licenseTypeMap){
		//ҵ����
		this.deptMap = deptMap;
		//��������
		this.licenseTypeMap=licenseTypeMap;
		
		
		StringBuffer sb = new StringBuffer();
		List resultList = new ArrayList();	//��ȷ����
		List rlList=new ArrayList();//�Ŷη�����ȷ����
		
		//�õ��ļ����� (������)
		String orderName=fileName.substring(0,fileName.indexOf("."));
		if(this.isExist(orderName))
			throw new ChePaiException("�����ţ�" + orderName + "���Ѿ�����,������ȷ�����������Ƿ���ȷ!<a href='NumberPlate!importInput.action'>���ز���</a>");
		
		//����
		String district=area;
		// ���ͣ��ŶΣ���ѡ������
		String type=orderType;
		
		
		Workbook wb = null;
		try {
			wb=Workbook.getWorkbook(file);
		} catch (BiffException e) {
			e.printStackTrace();
			throw new ChePaiException("Excel�ĵ������ʽ����,������Microsoft soft office Excel���������ļ���ʽ!<a href='NumberPlate!importInput.action'>���ز���</a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(wb==null)
			return null;
		
		//�����Workbook����֮�󣬾Ϳ���ͨ�����õ�Sheet��������������
		Sheet[] sheet = wb.getSheets();
		String otherName=(sheet[0].getRow(0))[0].getContents();//��ȡ��������
		
		
			if(sheet!=null&&sheet.length>0){
				//��ÿ�����������ѭ��
				for(int i=0;i<sheet.length;i++){
					//�õ���ǰ�����������
					int rowNum = sheet[i].getRows();
					int sumNumber=1001;//������
					String plateType  = null;
					resultList = readCell(sb,sheet,orderName,otherName,resultList);
					//�ӵ����п�ʼ��..����..
					/*for(int j=2;j<rowNum;j++){
						if(java.lang.Character.isDigit((sheet[0].getRow(j))[0].getContents().charAt(0))){
							Map result = new HashMap();
							//�õ���ǰ�е����е�Ԫ��
							Cell[] cells = sheet[i].getRow(j);
							if(cells!=null&&cells.length>0){
								//��ÿ����Ԫ�����ѭ��
								for(int k=0;k<cells.length;k++){
									//��ȡ��ǰ��Ԫ���ֵ
									String cellValue = cells[k].getContents();
									sb.append(cellValue+"\t");
									
									//���
									if(k==0){
										result.put("orderNumber", cellValue.trim());
									}else if(k==1){ //ҵ����
										boolean str =this.deptMap.containsKey(cellValue.trim().toString());
										if(!str){
											this.loadDept(cellValue);
										}
										result.put("businessDepartment",this.deptMap.get(cellValue.trim()).toString());
									}else if(k==3){
										if(plateType == null) {
											plateType = cellValue.trim();
										} else {
											if(!cellValue.trim().equals(plateType)) 
												isSame = false;
										}
										boolean str=this.licenseTypeMap.containsKey(cellValue.trim().toString());
										if(!str){
											this.loadLicenseType(cellValue);
										}
										result.put("licensePlateType", this.licenseTypeMap.get(cellValue.trim()).toString());
									}else if(k==5){
										result.put("number", cellValue);
									}else{
										result.put(k, cellValue);
									}
									
								}
								result.put("numberSegmentId", orderName+sumNumber);
								result.put("otherName", otherName);
							}
							sb.append("\r\n");
							resultList.add(result);
							if(((j-1)%50)==0){
								sumNumber++;
							}
						}
						sb.append("\r\n");
					}*/
				}
			}
			
			int special = 0;
			//����Ǹ۰���
			if(district.equals("ʡ�����")){
				Map temp = (Map)resultList.get(0);
				if(temp.get(7).toString().indexOf("-")!=-1){
					special = 1;
				}
			}
			
			if(type.equals("�Ŷ�") || special == 1){
				for(int i=0;i<resultList.size();i++){
					Map map=(Map)resultList.get(i);
					if(java.lang.Character.isDigit(map.get("orderNumber").toString().charAt(0))){
						//��ȡҵ����
						String businessDepartment=map.get("businessDepartment").toString();
						//��ȡ��������
						String licensePlateType=map.get("licensePlateType").toString();
						//��ȡ��������
						String filterCondition=map.get(7).toString();
						//��ȡ�����
						String numberSegment=map.get("number").toString();
						
						int space = getSpace(licenseTypeMap,licensePlateType);
						
						
						//��ȡ����-ǰ�沿��
						String beginNumber=numberSegment.substring(0,numberSegment.lastIndexOf("-")).trim();
						//��ȡ����-���沿��
						String endNumber=numberSegment.substring(numberSegment.lastIndexOf("-")+1,numberSegment.length()).trim();
						char[] beginChar=beginNumber.trim().toCharArray();
						char[] endChar=endNumber.trim().toCharArray();
						String beginInt="";//������պ��뿪ʼ����
						String endInt="";//������պ����������
						int[] status=new int[beginChar.length];//״̬1Ϊ����
						for(int j=0;j<beginChar.length;j++){
							if(java.lang.Character.isDigit(beginChar[j])){//�ж��Ƿ�Ϊ����
								beginInt+=beginChar[j];
								endInt+=endChar[j];
								status[j]=1;
							}else{
								if(beginChar[j]==endChar[j]){
									status[j]=0;
								}else {
									status[j]=2;
								}
							}
						}
						int beginNumberInt=Integer.parseInt(beginInt);
						int endNumberInt=Integer.parseInt(endInt);
						int begin = beginNumberInt % 100;
						if(begin != 1)
							begin = 0;
						boolean flag = true;
						String finallyNumber="";//���պ���
						int sumNumber=1001;
						while (flag) {
							finallyNumber="";
							Map mapNumber=new HashMap();
							 for (int j = 0; j < beginChar.length; j++)
	                            {
								 finallyNumber =finallyNumber+beginChar[j];
	                            }
							 //��ȡ�κ�
							 String numString="";
							 for(int j=0;j<beginChar.length;j++){
								 if(java.lang.Character.isDigit(beginChar[j])){
									 numString=numString+beginChar[j];
								 }
							 }
							 //��ȡ���ƺ�2���ֶ�
							 int num=Integer.parseInt(numString.substring(numString.length()-2, numString.length()).trim());
							 if(num==(space+begin)||num==(begin)){
								 if(!numString.equals(beginInt))
									 sumNumber++;
							 }
							 boolean fl=this.filterCondition(filterCondition, finallyNumber);
							 if(fl){//�ж��Ƿ�ͨ����������
								 mapNumber.put("orderNumber", rlList.size()+1);
								 mapNumber.put("businessDepartment", businessDepartment);
								 mapNumber.put("licensePlateType", licensePlateType);
								 mapNumber.put("number", finallyNumber);
								 mapNumber.put("numberSegmentId", orderName+sumNumber);
								 mapNumber.put("otherName", otherName);
								 rlList.add(mapNumber);
							 }
							 fl = !fl;
							 if (finallyNumber.equals(endNumber))
	                         {
	                                flag = false;
	                          }else
	                          {
	                             for (int j =  beginChar.length - 1; j >= 0; j--)
	                             {
	                                 if (status[j] == 1)
	                                 {
	                                     if ('9'!=beginChar[j])
	                                     {                                            
	                                         int ascii = (int)beginChar[j];
	                                         ascii++;
	                                         beginChar[j] = (char)ascii;
	                                         fl=true;
	                                         break;
	                                     }
	                                     else if ('9'==beginChar[j])
	                                     {
	                                    	 beginChar[j] = '0';
	                                         continue;
	                                     }
	                                 }
	                             }
	                             if(!fl){
		                             for (int j = beginChar.length- 1; j >= 0; j--)
		                                 {
		                            	 if (status[j] == 2)
		                                     {
		                                         if (beginChar[j]==endChar[j])
		                                         {
		                                             continue;
		                                         }
		                                         else
		                                         {
		                                             int ascii = (int)beginChar[j];
		                                             ascii++;
		                                             beginChar[j] = (char)ascii;
		                                             break;
		                                         }
		                                     }
		                                 }
									 }
	                          	}
						}
					}
				}
			}else{
				//���ر���Դ���ͷ��ڴ�
				wb.close();
				return resultList;
			}
				//���ر���Դ���ͷ��ڴ�
				wb.close();
				return rlList;
	}
	
	/**
	 * ��ȡ�������Ħ�г�Ϊ100һС��������Ϊ50һС��
	 * @param map
	 * @return
	 */
	private int getSpace(Map map,String type) {
		for(Object one:map.keySet()){
			if(one != null && one.toString().indexOf("Ħ�г�")!=-1 ){
				if(map.get(one)!= null && map.get(one).toString().equals(type))
					return 100;
			}
		}
		return 50;
	}
	
	/**
	 * 
	 * @param stuts ��������
	 * @param number ���ƺ���
	 * @return ͨ���򷵻��棬��ͨ���򷵻ؼ�
	 * ��һ�ֹ���ȥβ��4
	 * �ڶ��ֹ������ֲ�ȫΪ0
	 * �����ֹ��򣬹���1+����2
	 * 
	 */
	public boolean filterCondition(String stuts,String number){
		boolean flag = true;
        if (stuts.indexOf("1-")!=-1 || stuts.indexOf("3-")!=-1 )  //��һ�ֹ��򡢵����ֹ���
        {
            if (number.substring(number.length()-1, number.length()).equals("4"))
            {
            	flag = false;
            }
        }
        if (stuts.indexOf("2-")!=-1 || stuts.indexOf("3-")!=-1 )  //�ڶ��ֹ��򡢵����ֹ���
        {
/*            char[] numberChar = number.toCharArray();
            for (int i = 0; i < numberChar.length; i++)
            {
                if (java.lang.Character.isDigit(numberChar[i]))
                {
                    int n = (int)numberChar[i];
                    if (n != 0)
                    {
                        flag = false;
                    }
                }
            }*/
        	char temp;
        	for(int i=0;i<number.length();i++){
        		temp = number.charAt(i);
        		if(!java.lang.Character.isDigit(temp)){
        			number = number.replaceFirst(temp+"", "");
        			i--;
        		}
        	}
        	int result = Integer.parseInt(number);
        	if(result==0){
        		flag=false;
        	}
        }
        return flag;
	}
	

	
	public void loadLicenseType(String cellValue){
		NumberPlateType numberPlateType=new NumberPlateType();
		numberPlateType.setTypeName(cellValue.trim().toString());
		numberPlateTypeServer.add(numberPlateType);
		this.licenseTypeMap=numberPlateTypeServer.queryAll();
	}
	
	
	//�������ƻ�ȡid
	public void loadDept(String cellValue){
		BusinessDepartment b = new BusinessDepartment();
		b.setDepartment(cellValue.trim().toString());
		businessDepartmentServer.add(b);
		this.deptMap = businessDepartmentServer.queryAll();
	}

	
	private List readCell(StringBuffer sb,Sheet[] sheet,String orderName,String otherName,List resultList){
		//��ÿ�����������ѭ��
		for(int i=0;i<sheet.length;i++){
			//�õ���ǰ�����������
			int rowNum = sheet[i].getRows();
			int sumNumber=1001;//������
			String plateType  = null;
			
			
			int cellNum_orderNumber =-1; //�����excel�ļ��е�����
			int cellNum_businessDepartment=-1;//ҵ������excel�ļ��е�����
			int cellNum_licensePlateType=-1;//����������excel�ļ��е�����
			int cellNum_number=-1;//���ƺ�����excel�ļ��е�����
			
			//�ӵڶ������б���
			
				Cell[] aCells = sheet[0].getRow(1);
				for(int f=0;f<aCells.length;f++){
					String cellValue = aCells[f].getContents();
					if(cellValue.indexOf("���")!=-1){
						cellNum_orderNumber=f;
					}
					else if(cellValue.indexOf("ҵ����")!=-1){
						cellNum_businessDepartment=f;
					}
					else if(cellValue.indexOf("��������")!=-1){
						cellNum_licensePlateType=f;
					}
					else if(cellValue.indexOf("���ƺ���")!=-1){
						cellNum_number=f;
					}
				}
			
			
			
			//�ӵ����п�ʼ��..����..
			for(int j=2;j<rowNum;j++){
				if(java.lang.Character.isDigit((sheet[0].getRow(j))[0].getContents().charAt(0))){
					Map result = new HashMap();
					//�õ���ǰ�е����е�Ԫ��
					Cell[] cells = sheet[i].getRow(j);
					if(cells!=null&&cells.length>0){
						//��ÿ����Ԫ�����ѭ��
						for(int k=0;k<cells.length;k++){
							//��ȡ��ǰ��Ԫ���ֵ
							String cellValue = cells[k].getContents();
							sb.append(cellValue+"\t");
							
							//���
							if(k==cellNum_orderNumber){
								result.put("orderNumber", cellValue.trim());
							}else if(k==cellNum_businessDepartment){ //ҵ����
								boolean str =this.deptMap.containsKey(cellValue.trim().toString());
								if(!str){
									this.loadDept(cellValue);
								}
								result.put("businessDepartment",this.deptMap.get(cellValue.trim()).toString());
							}else if(k==cellNum_licensePlateType){
								if(cellValue.trim().indexOf("��")!=-1){
									cellValue = cellValue.substring(0, cellValue.trim().indexOf("��"));
								}
								if(plateType == null) {
									plateType = cellValue.trim();
								} else {
									if(!cellValue.trim().equals(plateType)) 
										isSame = false;
								}
								boolean str=this.licenseTypeMap.containsKey(cellValue.trim().toString());
								if(!str){
									this.loadLicenseType(cellValue);
								}
								result.put("licensePlateType", this.licenseTypeMap.get(cellValue.trim()).toString());
							}else if(k==cellNum_number){
								result.put("number", cellValue);
							}else{
								result.put(k, cellValue);
							}
							
						}
						result.put("numberSegmentId", orderName+sumNumber);
						result.put("otherName", otherName);
					}
					sb.append("\r\n");
					resultList.add(result);
					if(((j-1)%50)==0){
						sumNumber++;
					}
				}
				sb.append("\r\n");
			}
		}
		return resultList;
	}
	
	
	
	public Map getLicenseTypeMap() {
		return licenseTypeMap;
	}

	public void setLicenseTypeMap(Map licenseTypeMap) {
		this.licenseTypeMap = licenseTypeMap;
	}
	public boolean isSame() {
		return isSame;
	}
	public void setSame(boolean isSame) {
		this.isSame = isSame;
	}
	
	
	public static void main(String[] args) {
		Map map = new HashMap();
		map.put("����1", 1);
		map.put("Ħ��", 2);
		for(Object one:map.keySet()){
			if(one.toString().indexOf("Ħ�г�")!=-1){
				System.out.println(100);
			}
		}
		System.out.println(50);
		
	}
	
	
}
