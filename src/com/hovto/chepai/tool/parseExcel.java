package com.hovto.chepai.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.hovto.chepai.exception.ChePaiException;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class parseExcel {
	public static final int WORD_HTML = 8;   
    public static final int WORD_TXT = 7;   
    public static final int EXCEL_HTML = 44;   
  
    /**
      * WORD转HTML
      * @param docfile WORD文件全路径
      * @param htmlfile 转换后HTML存放路径
      */  
    public static void wordToHtml(String docfile, String htmlfile)    {   
         ActiveXComponent app = new ActiveXComponent("Word.Application"); // 启动word   
        try  {   
             app.setProperty("Visible", new Variant(false));   
             Dispatch docs = app.getProperty("Documents").toDispatch();   
             Dispatch doc = Dispatch.invoke(   
                     docs,   
                    "Open",   
                     Dispatch.Method,   
                    new Object[] { docfile, new Variant(false),   
                            new Variant(true) }, new int[1]).toDispatch();   
             Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {   
                     htmlfile, new Variant(WORD_HTML) }, new int[1]);   
             Variant f = new Variant(false);   
             Dispatch.call(doc, "Close", f);   
         }   catch (Exception e)   {   
             e.printStackTrace();   
         }    finally  {   
             app.invoke("Quit", new Variant[] {});   
         }   
     }   
  

    /**
     * 转换正确excel格式
     * @param xlsfile 源文件地址
     * @param toxlsfile 目标文件地址
     * @return
     * @throws IOException
     */
    public static File toExcel(String xlsfile, String toxlsfile) throws IOException   {   
         ActiveXComponent app = new ActiveXComponent("Excel.Application"); // 启动word   
        try  {   
             app.setProperty("Visible", new Variant(false));   
             Dispatch excels = app.getProperty("Workbooks").toDispatch();   
             Dispatch excel = Dispatch.invoke(   
                     excels,   
                    "Open",   
                     Dispatch.Method,   
                    new Object[] { xlsfile, new Variant(false),   
                            new Variant(true) }, new int[1]).toDispatch();   
             Dispatch.invoke(excel, "SaveAs", Dispatch.Method, new Object[] {   
            		 toxlsfile, new Variant(39) }, new int[1]);   
             Variant f = new Variant(false);   
             Dispatch.call(excel, "Close", f);   
             return new File(toxlsfile);
         }   catch (Exception e)   {   
             e.printStackTrace();
             return null;
         }   finally   {   
             app.invoke("Quit", new Variant[] {});
             ComThread.Release();
             DelDir.delDir(xlsfile);
         }   
     }
    
    public static File parse(File file,String filepath,String tofilepath) throws IOException{
    	copy(file,filepath,1);
    	return toExcel(filepath, tofilepath);
    }
    
	/**
	 * 复制文件方法
	 * @param source_name 源文件
	 * @param dest_name 目标文件全路径
	 * @param type	如果目标文件存在的话,是否覆盖(1代表覆盖,2代表不覆盖)
	 * @return 返回0说明存在目标文件,不覆盖
	 */
    public static int copy(File source_file, String dest_name,int type) throws IOException{
		File dest_file = new File(dest_name);
		
		FileInputStream source = null;
		FileOutputStream destination =null;
		byte[] buffer;
		int bytes_read;
		int result=0;
		
		try {
			if(!source_file.exists() || !source_file.isFile())
				throw new RuntimeException("源文件不存在!");
			if(!source_file.canRead())
				throw new RuntimeException("源文件不可读!");
			if(dest_file.exists()){
				if(dest_file.isFile()){
					if(type==1){//覆盖
						dest_file.delete();
						result = 1;
					}else{//不覆盖
						System.out.println(1);
						result = 2;
						return result;
					}
				}else{
					throw new RuntimeException("目标是目录而不是文件");
				}
			}else{
				File parentdir = new File(dest_file.getParent());
				if(!parentdir.exists())
					throw new RuntimeException("目标路径不存在");
				if(!parentdir.canWrite())
					throw new RuntimeException("目标路径不可写");
			}
			
			//复制文件
			source = new FileInputStream(source_file);
			destination = new FileOutputStream(dest_file);
			buffer = new byte[1024];
			while(true){
				bytes_read = source.read(buffer);
				if(bytes_read == -1){
					System.out.print(1);
					break;
				}
				destination.write(buffer, 0, bytes_read);
			}	
		}finally{
			if(source != null){
				try{
					source.close();
				}catch(IOException e){}
			}
			if(destination != null){
				try {
					destination.close();
				} catch (Exception e) {}
			}
		}
		return result;
	}
    
    
    
/*    public static void main(String[] args) throws IOException {
    	String src = "C:\\Documents and Settings\\Administrator\\桌面\\4403002011B0080.xls";   
    	String dis = "C:\\Documents and Settings\\Administrator\\桌面\\111.xls";  
    	String dis2 = "C:\\Documents and Settings\\Administrator\\桌面\\222.xls";  
    	  
    	File file = new File(src);
    	System.out.println(file.getName());
    	//parse(file,dis,dis2);
    	//parseExcel.toExcel(src,dis);  //word转成html  
	}*/
    
    public static void main(String[] args) {
    	ComThread.Release();
	}
    
}  

