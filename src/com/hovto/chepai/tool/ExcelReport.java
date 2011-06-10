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
	 * ����excel����
	 * @param title
	 * @param unit
	 * @param secondtitle
	 * @param contents
	 * @param path
	 * @throws FileNotFoundException 
	 *//*
	public static void exportExcel(String title,String unit,String[] secondtitle,List contents,String path) throws FileNotFoundException{
		FileOutputStream os = new FileOutputStream(path);
		//����һ���µ�excel
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		//��ʾ��ӡ����
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
			//��һ��
			HSSFRow row1 = sheet.getRow(0);
			HSSFCell today = row1.getCell((short) 3);
			//��������
			today.setCellValue(getToday());
			
			//�ڶ���
			HSSFRow row2 = sheet.getRow(1);
			HSSFCell receiver = row2.getCell((short) 7);
			HSSFCell telephone = row2.getCell((short) 9);
			//�ռ�����������ϵ�绰
			receiver.setCellValue(postAddress.getReceiver());
			telephone.setCellValue(postAddress.getTelephone());
			
			//������
			HSSFRow row3 = sheet.getRow(2);
			HSSFCell department = row3.getCell((short) 8);
			//��λ����
			String departmentName;
			if(postAddress.getDepartment()==null){
				departmentName = postAddress.getPlace()+"������";
			}else{
				departmentName = postAddress.getDepartment();
			}
			department.setCellValue(departmentName);
			
			
			//������
			HSSFRow row4 = sheet.getRow(3);
			HSSFCell address = row4.getCell((short) 7);
			//�ռ���ַ
			address.setCellValue(postAddress.getAddress());
			
			HSSFRow row7 = sheet.getRow(6);
			HSSFCell numberCell = row7.getCell((short) 1);
			HSSFCell amountCell = row7.getCell((short) 4);
			numberCell.setCellValue("���κţ�"+ number);
			amountCell.setCellValue(amount+"������");
			
			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}	finally{
			os.close();
			is.close();
		}
		
	}
	
	
	/**
	 * ���ʼ�excel����
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

			HSSFRow row = sheet.createRow((short) 0); // ��һ��
			row.setHeight((short) 500);// ���õ�һ�еĸ߶�
			
			/* �������ݵ����ָ�ʽ����ʾ�ĸ�ʽ */
			HSSFFont font = wb.createFont();
			font.setFontHeightInPoints((short) 16);// �����С
			font.setBoldweight(font.BOLDWEIGHT_BOLD);// ����Ӵ�
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setFont(font);
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			HSSFCell cell = row.createCell((short) 0);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("ʡ����֤����"+ smallBatchNumber);
			// �ϲ���Ԫ��
			sheet.addMergedRegion(new Region(0, (short) 0, 0,
					(short) (args_title_length - 1)));

			HSSFRow row1 = sheet.createRow((short) 1); // �ڶ���
			HSSFCellStyle cellStyle1 = wb.createCellStyle();
			cellStyle1.setAlignment(cellStyle.ALIGN_CENTER);
			HSSFFont font1 = wb.createFont();
			font1.setBoldweight(font.BOLDWEIGHT_BOLD);
			cellStyle1.setFont(font1);
			cell = row1.createCell((short) 0);
			cell.setCellStyle(cellStyle1);
			cell.setCellValue("���");
			cell = row1.createCell((short) 1);
			cell.setCellStyle(cellStyle1);
			cell.setCellValue("ҵ����");
			cell = row1.createCell((short) 2);
			cell.setCellStyle(cellStyle1);
			cell.setCellValue("��������");
			cell = row1.createCell((short) 3);
			cell.setCellStyle(cellStyle1);
			cell.setCellValue("���ƺ���");

			// ����
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
			wb.write(os);// ���ĵ�����д���ļ������
			os.close();// �ر��ļ������
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String getToday(){
		Calendar c = Calendar.getInstance(); 
		return c.get(Calendar.YEAR)+"  "+(c.get(Calendar.MONTH)+1)+"  "+c.get(Calendar.DATE)+"  "+c.get(Calendar.HOUR);
	}
	
}