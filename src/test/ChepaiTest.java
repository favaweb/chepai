package test;

import java.awt.JobAttributes;
import java.awt.PageAttributes;
import java.awt.PrintJob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.hovto.chepai.exception.ChePaiException;
import com.hovto.chepai.tool.DelDir;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;



public class ChepaiTest {

	private static String str;
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		Calendar c=Calendar.getInstance();
		System.out.println(c.get(Calendar.YEAR)+"年"+c.get(Calendar.MONTH)+"月"+c.get(Calendar.DATE)+"日");
/*		
		JobAttributes   job_att   =   new   JobAttributes(); 
		  job_att.setDialog(JobAttributes.DialogType.COMMON); 
		  job_att.setPrinter("Microsoft Office Document Image Writer"); 
		 
		 System.out.println(job_att.getPrinter());*/
		 // PageAttributes   page_att   =   new   PageAttributes(); 
		 // PrintJob   printjob   =   this.getToolkit().getPrintJob(frame,   "ok ",   job_att,page_att);
		
		/*String path = "C:\\Documents and Settings\\maodi\\桌面\\我的文件\\报制数据包\\补制\\广州\\4401002010B0501.xls";
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
			throw new ChePaiException("无法连接到当前打印机,请检查设备<a href='javascript:history.go(-1);'>返回</a>");
		} finally {
			xl.invoke("Quit", new Variant[] {});
			ComThread.Release();
			//打印后删除excel文件
			DelDir.delDir(path);
		}
		*/
		//System.out.println("output:\t" + Math.round(8.0/3 * 100.0) / 100.0);
		/*DecimalFormat df = new DecimalFormat(".00");
		
		double d1 = 20;
		double d2 = 3;
		System.out.println(Math.round(d1 / d2 * 100.0) / 100.0);
		
		new ChepaiTest().load(str);
		System.out.println(str);*/
		
		/*String remoteIp = "\\\\192.168.1.118\\报制数据包\\";
		String[] orderType = {"补制"};
		
		for(int i=0; i<orderType.length; i++) {
			String parentPath = remoteIp + orderType[i];
			File parentFile = new File(parentPath);
			if(parentFile.isDirectory()) {
				
				for(String area : parentFile.list()) {
					String areaPath = parentPath + "\\" + area;
					File areaFile = new File(areaPath);
					for(String fileName : areaFile.list()) {
System.out.println("------------------------------------fileName:\t" + fileName.substring(0,fileName.indexOf(".")));
						ReadExcel.readExcel(areaPath + "\\" + fileName, System.out);
System.out.println("------------------------------------");
					}
				}
				
			}
			
			
		}*/
		
		
		/*Date d = new Date();
		
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		
		int hour = c.get(Calendar.HOUR_OF_DAY);
		System.out.println(hour);*/
		
		
		
		/*ApplicationContext app = new ClassPathXmlApplicationContext("beans.xml");
		SemifinishedProductTypeServer semifinishedProductTypeServer = (SemifinishedProductTypeServer) app.getBean("semifinishedProductTypeServer");
		

		List<SemifinishedProductType> sp= semifinishedProductTypeServer.findList();
		Map<Integer,int[]> map=new HashMap<Integer,int[]>();
		for(int i=0;i<sp.size();i++){
			for(int j=0;j<sp.get(i).getNumberPlateTypes().size();j++){
				if(map.get(sp.get(i).getNumberPlateTypes().get(j).getId()) == null)
					map.put(sp.get(i).getNumberPlateTypes().get(j).getId(), new int[]{sp.get(i).getId()});
				else
					map.put(sp.get(i).getNumberPlateTypes().get(j).getId(), new int[]{map.get(sp.get(i).getNumberPlateTypes().get(j).getId())[0],sp.get(i).getId()});
			}
		}
		
		System.out.println("---------------:\t" + map);*/
		
		
/*		String date = "2010-2-03";
		
		Date d = null;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			// 
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		
		System.out.println(c.get(Calendar.DATE));
		c.set(Calendar.DATE,c.get(Calendar.DATE) + 1);
		
		System.out.println(c.get(Calendar.DATE));*/
		
		/*String str = "12345_678";
		int index = str.indexOf("_");
		String subStr = str.substring(0,index);
		System.out.println(subStr);
		
		System.out.println(str.contentEquals("67"));*/
		
		/*String list = "";
		String str[] = list.split(",");
		for(String s : str)
			System.out.println(s);*/
		
		//File file = new File("\\\\192.168.1.118\\报制数据包");
		
		/*File file = new File("\\\\192.168.1.118\\报制数据包\\补制\\广州\\4401002010B0503.xls");
		FileInputStream fis = new FileInputStream(file);
		int i = 0;
		while((i=fis.read()) > 0) {
			System.out.print((char)i);
		}*/
		
		
		
		
	}
	
	@Test
	public void saveFlowType() {
		
		String date = "2010-2-3";
		try {
			System.out.println(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void load(String str) {
		str = "abc";
	}
	
}






















