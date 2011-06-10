package com.hovto.chepai.tool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import com.hovto.chepai.model.NumberPlate;
import com.hovto.chepai.model.PostAddress;


public final class ExcelReport {

/*	*//**
	 * 生成excel报表
	 * @param title
	 * @param unit
	 * @param secondtitle
	 * @param contents
	 * @param path
	 * @throws FileNotFoundException 
	 *//*
	public static void exportExcel(String title,String unit,String[] secondtitle,List contents,String path) throws FileNotFoundException{
		FileOutputStream os = new FileOutputStream(path);
		//创建一个新的excel
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		//显示打印网格
		sheet.setPrintGridlines(true);
		
		int args_title_length = secondtitle.length;
		
	}*/
	
	@SuppressWarnings({ "unused", "deprecation" })
	public static void exportEMS(String filePath,String modelPath,PostAddress postAddress,String number,int amount) throws IOException{
		InputStream is = null;
		FileOutputStream os = null;
		try {
			is = new FileInputStream(modelPath);
			os = new FileOutputStream(filePath);
			HSSFWorkbook wb = new HSSFWorkbook(is);
			HSSFSheet sheet = wb.getSheet("Sheet1");
			//第一行
			HSSFRow row1 = sheet.getRow(0);
			HSSFCell today = row1.getCell((short) 3);
			//设置日期
			today.setCellValue(getToday());
			
			//第二行
			HSSFRow row2 = sheet.getRow(1);
			HSSFCell receiver = row2.getCell((short) 7);
			HSSFCell telephone = row2.getCell((short) 9);
			//收件人姓名、联系电话
			receiver.setCellValue(postAddress.getReceiver());
			telephone.setCellValue(postAddress.getTelephone());
			
			//第三行
			HSSFRow row3 = sheet.getRow(2);
			HSSFCell department = row3.getCell((short) 8);
			//单位名称
			String departmentName;
			if(postAddress.getDepartment()==null){
				departmentName = postAddress.getPlace()+"车管所";
			}else{
				departmentName = postAddress.getDepartment();
			}
			department.setCellValue(departmentName);
			
			
			//第四行
			HSSFRow row4 = sheet.getRow(3);
			HSSFCell address = row4.getCell((short) 7);
			//收件地址
			address.setCellValue(postAddress.getAddress());
			
			HSSFRow row7 = sheet.getRow(6);
			HSSFCell numberCell = row7.getCell((short) 1);
			HSSFCell amountCell = row7.getCell((short) 4);
			numberCell.setCellValue("批次号："+ number);
			amountCell.setCellValue(amount+"幅车牌");
			
			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}	finally{
			os.close();
			is.close();
		}
		
	}
	
	
	/**
	 * 总质检excel生成
	 * @param numberPlates
	 * @param smallBatchNumber
	 * @param filePath
	 */
	public static void export2(List<NumberPlate> numberPlates, String smallBatchNumber, String filePath) {
		try {
			filePath +="\\" + smallBatchNumber + ".xls";
			FileOutputStream os = new FileOutputStream(
			filePath);
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("sheet1");
			sheet.setMargin(HSSFSheet.TopMargin, (double)0.2);
			sheet.setMargin(HSSFSheet.BottomMargin, (double)0.1);
			sheet.setPrintGridlines(true);
			int args_title_length = 4;

			
			sheet.setColumnWidth((short) 0, (short) 2000);
			sheet.setColumnWidth((short) 1, (short) 6000);
			sheet.setColumnWidth((short) 2, (short) 6000);
			sheet.setColumnWidth((short) 3, (short) 9000);

			HSSFRow row = sheet.createRow((short) 0); // 第一行
			row.setHeight((short) 500);// 设置第一行的高度
			
			/* 设置内容的文字格式和显示的格式 */
			HSSFFont font = wb.createFont();
			font.setFontHeightInPoints((short) 16);// 字体大小
			font.setBoldweight(font.BOLDWEIGHT_BOLD);// 字体加粗
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setFont(font);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			HSSFCell cell = row.createCell((short) 0);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("省厅制证中心"+ smallBatchNumber);
			// 合并单元格
			sheet.addMergedRegion(new Region(0, (short) 0, 0,
					(short) (args_title_length - 1)));

			HSSFRow row1 = sheet.createRow((short) 1); // 第二行
			HSSFCellStyle cellStyle1 = wb.createCellStyle();
			cellStyle1.setAlignment(cellStyle.ALIGN_CENTER);
			HSSFFont font1 = wb.createFont();
			font1.setBoldweight(font.BOLDWEIGHT_BOLD);
			cellStyle1.setFont(font1);
			cell = row1.createCell((short) 0);
			cell.setCellStyle(cellStyle1);
			cell.setCellValue("序号");
			cell = row1.createCell((short) 1);
			cell.setCellStyle(cellStyle1);
			cell.setCellValue("业务部门");
			cell = row1.createCell((short) 2);
			cell.setCellStyle(cellStyle1);
			cell.setCellValue("车牌种类");
			cell = row1.createCell((short) 3);
			cell.setCellStyle(cellStyle1);
			cell.setCellValue("车牌号码");

			// 内容
			HSSFFont font12 = wb.createFont();
			font12.setFontHeightInPoints((short) 11);
			cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellStyle.setFont(font12);
			for (int i = 0; i < numberPlates.size(); i++) {
				row1 = sheet.createRow((short) i + 2);
				
				cell = row1.createCell((short) 0);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(i + 1 + "");
				cell = row1.createCell((short) 1);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(numberPlates.get(i).getBusinessDepartment()
						.getDepartment());
				cell.setCellStyle(cellStyle);
				cell = row1.createCell((short) 2);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(numberPlates.get(i).getNumberPlateType()
						.getTypeName());
				cell = row1.createCell((short) 3);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(numberPlates.get(i).getLicensePlateNumber());
			}
			wb.write(os);// 将文档对象写入文件输出流
			os.close();// 关闭文件输出流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String getToday(){
		Calendar c = Calendar.getInstance(); 
		return c.get(Calendar.YEAR)+"  "+(c.get(Calendar.MONTH)+1)+"  "+c.get(Calendar.DATE)+"  "+c.get(Calendar.HOUR);
	}
	
}