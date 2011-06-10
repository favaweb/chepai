package com.hovto.chepai.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public final class CopyFile {

	/**
	 * 复制文件工具类
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new CopyFile().copy("E:\\Desktop\\spring_hibernate_2 - 副本\\readme.txt", "E:\\Desktop\\新建文件夹\\readme.txt", 2);
	}
	/**
	 * 复制文件方法
	 * @param source_name 源文件全路径
	 * @param dest_name 目标文件全路径
	 * @param type	如果目标文件存在的话,是否覆盖(1代表覆盖,2代表不覆盖)
	 * @return 返回0说明存在目标文件,不覆盖
	 */
	public static int copy(String source_name, String dest_name,int type) throws IOException{
		File source_file = new File(source_name);
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

}
