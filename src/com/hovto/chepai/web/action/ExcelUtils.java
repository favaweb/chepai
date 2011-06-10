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
	/**读取Excel文件的内容
	 * @param file  待读取的文件
	 * @return
	 */
	public List readExcel(File realFile, String file,Map deptMap,Map licenseTypeMap){
		//业务部门
		this.deptMap = deptMap;
		//车牌类型
		this.licenseTypeMap=licenseTypeMap;
		
		
		StringBuffer sb = new StringBuffer();
		List resultList = new ArrayList();	//正确数据
		List rlList=new ArrayList();//号段返回正确数据
		
		//拿到文件名称 (大批号)
		String orderName=file.substring(file.lastIndexOf("\\")+1, file.lastIndexOf("."));
		if(this.isExist(orderName))
			throw new ChePaiException("大批号：" + orderName + "　已经存在,请重新确定导入数据是否正确!<a href='NumberPlate!importInput.action'>返回操作</a>");
		
		
		String[] s=file.split("\\\\");
		//地区
		String district=s[s.length-2];
		// 类型：号段，自选，补制
		String type=s[s.length-3];
		
		
		Workbook wb = null;
		try {
			wb=Workbook.getWorkbook(realFile);
		} catch (BiffException e) {
			e.printStackTrace();
			throw new ChePaiException("Excel文档保存格式错误,必须是Microsoft soft office Excel工作簿的文件格式!<a href='NumberPlate!importInput.action'>返回操作</a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(wb==null)
			return null;
		
		//获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了
		Sheet[] sheet = wb.getSheets();
		String otherName=(sheet[0].getRow(0))[0].getContents();//获取其它名称
		
		
			if(sheet!=null&&sheet.length>0){
				//读取单元格
				resultList = readCell(sb,sheet,orderName,otherName,resultList);
				/*//对每个工作表进行循环
				for(int i=0;i<sheet.length;i++){
					//得到当前工作表的行数
					int rowNum = sheet[i].getRows();
					int sumNumber=1001;//子批号
					String plateType  = null;
					
					//从第三行开始拿..数据..
					for(int j=2;j<rowNum;j++){
						if(java.lang.Character.isDigit((sheet[0].getRow(j))[0].getContents().charAt(0))){
							Map result = new HashMap();
							//得到当前行的所有单元格
							Cell[] cells = sheet[i].getRow(j);
							if(cells!=null&&cells.length>0){
								//对每个单元格进行循环
								for(int k=0;k<cells.length;k++){
									//读取当前单元格的值
									String cellValue = cells[k].getContents();
									sb.append(cellValue+"\t");
									
									//序号
									if(k==0){
										result.put("orderNumber", cellValue.trim());
									}else if(k==1){ //业务部门
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
			//如果是港澳牌
			if(district.equals("省受理科")){
				Map temp = (Map)resultList.get(0);
				if(temp.get(7).toString().indexOf("-")!=-1){
					special = 1;
				}
			}
			
			if(type.equals("号段") || special == 1){
				for(int i=0;i<resultList.size();i++){
					Map map=(Map)resultList.get(i);
					if(java.lang.Character.isDigit(map.get("orderNumber").toString().charAt(0))){
						//获取业务部门
						String businessDepartment=map.get("businessDepartment").toString();
						//获取车牌种类
						String licensePlateType=map.get("licensePlateType").toString();
						//获取过滤条件
						String filterCondition=map.get(7).toString();
						//获取号码段
						String numberSegment=map.get("number").toString();
						
						int space = getSpace(licenseTypeMap,licensePlateType);
						
						//获取号码-前面部分
						String beginNumber=numberSegment.substring(0,numberSegment.lastIndexOf("-")).trim();
						//获取号码-后面部分
						String endNumber=numberSegment.substring(numberSegment.lastIndexOf("-")+1,numberSegment.length()).trim();
						char[] beginChar=beginNumber.trim().toCharArray();
						char[] endChar=endNumber.trim().toCharArray();
						String beginInt="";//定义接收号码开始数字
						String endInt="";//定义接收号码介绍数字
						int[] status=new int[beginChar.length];//状态1为数字
						for(int j=0;j<beginChar.length;j++){
							if(java.lang.Character.isDigit(beginChar[j])){//判断是否为数字
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
						String finallyNumber="";//最终号码
						int sumNumber=1001;
						while (flag) {
							finallyNumber="";
							Map mapNumber=new HashMap();
							 for (int j = 0; j < beginChar.length; j++)
	                            {
								 finallyNumber =finallyNumber+beginChar[j];
	                            }
							 //获取段号
							 String numString="";
							 for(int j=0;j<beginChar.length;j++){
								 if(java.lang.Character.isDigit(beginChar[j])){
									 numString=numString+beginChar[j];
								 }
							 }
							 //获取车牌后2个字段
							 int num=Integer.parseInt(numString.substring(numString.length()-2, numString.length()).trim());
							 if(num==(space+begin)||num==(begin)){
								 if(!numString.equals(beginInt))
									 sumNumber++;
							 }
							 boolean fl=this.filterCondition(filterCondition, finallyNumber);
							 if(fl){//判断是否通过过滤条件
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
				//最后关闭资源，释放内存
				wb.close();
				return resultList;
			}
				//最后关闭资源，释放内存
				wb.close();
				return rlList;
	}
	
	/**读取Excel文件的内容
	 * @param file  待读取的文件
	 * @return
	 */
	public List readExcel(File file, String fileName, String area, String orderType,Map deptMap,Map licenseTypeMap){
		//业务部门
		this.deptMap = deptMap;
		//车牌类型
		this.licenseTypeMap=licenseTypeMap;
		
		
		StringBuffer sb = new StringBuffer();
		List resultList = new ArrayList();	//正确数据
		List rlList=new ArrayList();//号段返回正确数据
		
		//拿到文件名称 (大批号)
		String orderName=fileName.substring(0,fileName.indexOf("."));
		if(this.isExist(orderName))
			throw new ChePaiException("大批号：" + orderName + "　已经存在,请重新确定导入数据是否正确!<a href='NumberPlate!importInput.action'>返回操作</a>");
		
		//地区
		String district=area;
		// 类型：号段，自选，补制
		String type=orderType;
		
		
		Workbook wb = null;
		try {
			wb=Workbook.getWorkbook(file);
		} catch (BiffException e) {
			e.printStackTrace();
			throw new ChePaiException("Excel文档保存格式错误,必须是Microsoft soft office Excel工作簿的文件格式!<a href='NumberPlate!importInput.action'>返回操作</a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(wb==null)
			return null;
		
		//获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了
		Sheet[] sheet = wb.getSheets();
		String otherName=(sheet[0].getRow(0))[0].getContents();//获取其它名称
		
		
			if(sheet!=null&&sheet.length>0){
				//对每个工作表进行循环
				for(int i=0;i<sheet.length;i++){
					//得到当前工作表的行数
					int rowNum = sheet[i].getRows();
					int sumNumber=1001;//子批号
					String plateType  = null;
					resultList = readCell(sb,sheet,orderName,otherName,resultList);
					//从第三行开始拿..数据..
					/*for(int j=2;j<rowNum;j++){
						if(java.lang.Character.isDigit((sheet[0].getRow(j))[0].getContents().charAt(0))){
							Map result = new HashMap();
							//得到当前行的所有单元格
							Cell[] cells = sheet[i].getRow(j);
							if(cells!=null&&cells.length>0){
								//对每个单元格进行循环
								for(int k=0;k<cells.length;k++){
									//读取当前单元格的值
									String cellValue = cells[k].getContents();
									sb.append(cellValue+"\t");
									
									//序号
									if(k==0){
										result.put("orderNumber", cellValue.trim());
									}else if(k==1){ //业务部门
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
			//如果是港澳牌
			if(district.equals("省受理科")){
				Map temp = (Map)resultList.get(0);
				if(temp.get(7).toString().indexOf("-")!=-1){
					special = 1;
				}
			}
			
			if(type.equals("号段") || special == 1){
				for(int i=0;i<resultList.size();i++){
					Map map=(Map)resultList.get(i);
					if(java.lang.Character.isDigit(map.get("orderNumber").toString().charAt(0))){
						//获取业务部门
						String businessDepartment=map.get("businessDepartment").toString();
						//获取车牌种类
						String licensePlateType=map.get("licensePlateType").toString();
						//获取过滤条件
						String filterCondition=map.get(7).toString();
						//获取号码段
						String numberSegment=map.get("number").toString();
						
						int space = getSpace(licenseTypeMap,licensePlateType);
						
						
						//获取号码-前面部分
						String beginNumber=numberSegment.substring(0,numberSegment.lastIndexOf("-")).trim();
						//获取号码-后面部分
						String endNumber=numberSegment.substring(numberSegment.lastIndexOf("-")+1,numberSegment.length()).trim();
						char[] beginChar=beginNumber.trim().toCharArray();
						char[] endChar=endNumber.trim().toCharArray();
						String beginInt="";//定义接收号码开始数字
						String endInt="";//定义接收号码介绍数字
						int[] status=new int[beginChar.length];//状态1为数字
						for(int j=0;j<beginChar.length;j++){
							if(java.lang.Character.isDigit(beginChar[j])){//判断是否为数字
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
						String finallyNumber="";//最终号码
						int sumNumber=1001;
						while (flag) {
							finallyNumber="";
							Map mapNumber=new HashMap();
							 for (int j = 0; j < beginChar.length; j++)
	                            {
								 finallyNumber =finallyNumber+beginChar[j];
	                            }
							 //获取段号
							 String numString="";
							 for(int j=0;j<beginChar.length;j++){
								 if(java.lang.Character.isDigit(beginChar[j])){
									 numString=numString+beginChar[j];
								 }
							 }
							 //获取车牌后2个字段
							 int num=Integer.parseInt(numString.substring(numString.length()-2, numString.length()).trim());
							 if(num==(space+begin)||num==(begin)){
								 if(!numString.equals(beginInt))
									 sumNumber++;
							 }
							 boolean fl=this.filterCondition(filterCondition, finallyNumber);
							 if(fl){//判断是否通过过滤条件
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
				//最后关闭资源，释放内存
				wb.close();
				return resultList;
			}
				//最后关闭资源，释放内存
				wb.close();
				return rlList;
	}
	
	/**
	 * 获取间隔数，摩托车为100一小批，其他为50一小批
	 * @param map
	 * @return
	 */
	private int getSpace(Map map,String type) {
		for(Object one:map.keySet()){
			if(one != null && one.toString().indexOf("摩托车")!=-1 ){
				if(map.get(one)!= null && map.get(one).toString().equals(type))
					return 100;
			}
		}
		return 50;
	}
	
	/**
	 * 
	 * @param stuts 过滤条件
	 * @param number 车牌号码
	 * @return 通过则返回真，不通过则返回假
	 * 第一种规则，去尾数4
	 * 第二种规则，数字不全为0
	 * 第三种规则，规则1+规则2
	 * 
	 */
	public boolean filterCondition(String stuts,String number){
		boolean flag = true;
        if (stuts.indexOf("1-")!=-1 || stuts.indexOf("3-")!=-1 )  //第一种规则、第三种规则
        {
            if (number.substring(number.length()-1, number.length()).equals("4"))
            {
            	flag = false;
            }
        }
        if (stuts.indexOf("2-")!=-1 || stuts.indexOf("3-")!=-1 )  //第二种规则、第三种规则
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
	
	
	//根据名称获取id
	public void loadDept(String cellValue){
		BusinessDepartment b = new BusinessDepartment();
		b.setDepartment(cellValue.trim().toString());
		businessDepartmentServer.add(b);
		this.deptMap = businessDepartmentServer.queryAll();
	}

	
	private List readCell(StringBuffer sb,Sheet[] sheet,String orderName,String otherName,List resultList){
		//对每个工作表进行循环
		for(int i=0;i<sheet.length;i++){
			//得到当前工作表的行数
			int rowNum = sheet[i].getRows();
			int sumNumber=1001;//子批号
			String plateType  = null;
			
			
			int cellNum_orderNumber =-1; //序号在excel文件中的列数
			int cellNum_businessDepartment=-1;//业务部门在excel文件中的列数
			int cellNum_licensePlateType=-1;//车牌类型在excel文件中的列数
			int cellNum_number=-1;//车牌号码在excel文件中的列数
			
			//从第二行拿行标题
			
				Cell[] aCells = sheet[0].getRow(1);
				for(int f=0;f<aCells.length;f++){
					String cellValue = aCells[f].getContents();
					if(cellValue.indexOf("序号")!=-1){
						cellNum_orderNumber=f;
					}
					else if(cellValue.indexOf("业务部门")!=-1){
						cellNum_businessDepartment=f;
					}
					else if(cellValue.indexOf("车牌种类")!=-1){
						cellNum_licensePlateType=f;
					}
					else if(cellValue.indexOf("车牌号码")!=-1){
						cellNum_number=f;
					}
				}
			
			
			
			//从第三行开始拿..数据..
			for(int j=2;j<rowNum;j++){
				if(java.lang.Character.isDigit((sheet[0].getRow(j))[0].getContents().charAt(0))){
					Map result = new HashMap();
					//得到当前行的所有单元格
					Cell[] cells = sheet[i].getRow(j);
					if(cells!=null&&cells.length>0){
						//对每个单元格进行循环
						for(int k=0;k<cells.length;k++){
							//读取当前单元格的值
							String cellValue = cells[k].getContents();
							sb.append(cellValue+"\t");
							
							//序号
							if(k==cellNum_orderNumber){
								result.put("orderNumber", cellValue.trim());
							}else if(k==cellNum_businessDepartment){ //业务部门
								boolean str =this.deptMap.containsKey(cellValue.trim().toString());
								if(!str){
									this.loadDept(cellValue);
								}
								result.put("businessDepartment",this.deptMap.get(cellValue.trim()).toString());
							}else if(k==cellNum_licensePlateType){
								if(cellValue.trim().indexOf("（")!=-1){
									cellValue = cellValue.substring(0, cellValue.trim().indexOf("（"));
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
		map.put("测试1", 1);
		map.put("摩托", 2);
		for(Object one:map.keySet()){
			if(one.toString().indexOf("摩托车")!=-1){
				System.out.println(100);
			}
		}
		System.out.println(50);
		
	}
	
	
}
