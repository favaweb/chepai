package com.hovto.chepai.tool;

import java.io.File;
import java.io.IOException;
/**
 * 删除文件/目录工具类
 * @param args
 * @throws IOException 
 */
public final class DelDir {
	
	public static void delDir(String filepath) throws IOException{
		System.out.println(filepath);
		try {
			File f = new File(filepath);//定义文件路径
			//判断是文件还是目录
			if(f.exists() && f.isDirectory()){
				if(f.listFiles().length==0){
					f.delete();//目录没有文件就直接删除
				}else{
					//若有则把文件放进数组,并判断是否有下级目录
					File[] delFile = f.listFiles();
					for(int j=0; j<delFile.length;j++){
						if(delFile[j].isDirectory()){
							//递归调用del方法并取得子目录路径
							delDir(delFile[j].getAbsolutePath());
						}
						delFile[j].delete();//删除文件
					}
				}
				delDir(filepath);
			}
			else{
				f.delete();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	public static void mdDir(String path) throws IOException{
		if(new File(path).exists()){
			delDir(path);
		}
		new File(path).mkdir();//创建目标文件夹
	}

}
