package com.hovto.chepai.tool;

import java.io.IOException;
import java.util.List;

import com.hovto.chepai.exception.ChePaiException;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public final class JacobPrint {
	/**
	 * 总质检打印
	 * @param path excel路径名称
	 * @throws IOException
	 */
	public static void print(String path) throws IOException {
		changePrinter("Microsoft XPS Document Writer");
		ActiveXComponent xl = null ;
		try {
			ComThread.InitSTA();
			xl = new ActiveXComponent("Excel.Application");
			Dispatch.put(xl, "Visible", new Variant(true));
			Dispatch workbooks = xl.getProperty("Workbooks").toDispatch();
			Dispatch excel = Dispatch.call(workbooks, "open", path)
					.toDispatch();
			Dispatch.get(excel, "PrintOut");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ChePaiException("无法连接到当前打印机,请检查设备<a href='WorkFlow!list.action?flowTypeId=7'>返回</a>");
		} finally {
			xl.invoke("Quit", new Variant[] {});
			ComThread.Release();
			//打印后删除excel文件
			DelDir.delDir(path);
		}
	}
	

	
	public synchronized static void print(String path, List<String> s3,int index) throws IOException {
		if(index==1) {
			print(path);
		}else if(index==2){
			printEMS(path);
		}else if(index==3){
			printEMSList(s3);
		}
	}
	
	/**
	 * 单张EMS单打印
	 * @param path
	 * @throws IOException
	 */
	public static void printEMS(String path) throws IOException{
		ActiveXComponent xl = null ;
		ActiveXComponent wd = null;
		try {
			ComThread.InitSTA();
			wd = new ActiveXComponent("Word.Application");
			wd.setProperty("ActivePrinter",new Variant("EPSON LQ-635K ESC/P2"));
			xl = new ActiveXComponent("Excel.Application");
			Dispatch.put(xl, "Visible", new Variant(false));
			Dispatch workbooks = xl.getProperty("Workbooks").toDispatch();
			Dispatch excel = Dispatch.call(workbooks, "open", path)
					.toDispatch();
			Dispatch.get(excel, "PrintOut");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ChePaiException("无法连接到当前打印机,请检查设备<a href='javascript:history.go(-1);'>返回</a>");
		} finally {
			wd.invoke("Quit", new Variant[] {});
			xl.invoke("Quit", new Variant[] {});
			ComThread.Release();
			//打印后删除excel文件
			DelDir.delDir(path);
		}
	}
	
	/**
	 * EMS多张打印
	 * @param path
	 */
	public static void printEMSList(List<String> pathlist){
		ActiveXComponent xl = null;
		try {
			ComThread.InitSTA();
			xl = new ActiveXComponent("Excel.Application");
			Dispatch excel = null;
			Dispatch.put(xl, "Visible", new Variant(false));
			Dispatch workbooks = xl.getProperty("Workbooks").toDispatch();

			for(String path:pathlist){
				changePrinter("EPSON LQ-635K ESC/P2");
				excel = Dispatch.call(workbooks, "open", path)
						.toDispatch();
				Dispatch.get(excel, "PrintOut");
				xl.invoke("Quit", new Variant[] {});
				//打印后删除excel文件
				DelDir.delDir(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			xl.invoke("Quit", new Variant[] {});
			ComThread.Release();
		}
	}
	
	
	
	/**
	 * 修改默认打印机
	 * @param printName 打印机名称
	 */
	private static void changePrinter(String printName){
		ActiveXComponent wd = null;
		try {
			wd = new ActiveXComponent("Word.Application");
			wd.setProperty("ActivePrinter",new Variant(printName));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			wd.invoke("Quit", new Variant[] {});
		}
	}
	
	
}
