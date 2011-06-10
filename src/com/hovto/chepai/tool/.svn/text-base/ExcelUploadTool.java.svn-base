package com.hovto.chepai.tool;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.hovto.chepai.exception.ChePaiException;

public final class ExcelUploadTool {

	private static ExcelUploadTool instance;
	private ExcelUploadTool() {}
	
	public static ExcelUploadTool getInstance() {
		if(instance == null) {
			synchronized(ExcelUploadTool.class) {
				if(instance == null)
					instance = new ExcelUploadTool();
			}
		}
		return instance;
	}
	
	public String uploadExcel(File excel, String excelFileName, String uploadPath) {
		if(excel != null) {
			//截取文件的后缀
			String excelExtension = excelFileName.substring(excelFileName.lastIndexOf(".") + 1, excelFileName.length());
			//重新创建一个独一无二的图片名字
			excelFileName = this.makeFileName(excelExtension);
			File saveFile = new File(new File(uploadPath), excelFileName);
			//判断路径是否存在
			if(!saveFile.getParentFile().exists()) saveFile.getParentFile().mkdirs();
			try {
				FileUtils.copyFile(excel, saveFile);
			} catch (IOException e) {
				e.printStackTrace();
				throw new ChePaiException("文件上传失败");
			}
		} else {
			throw new ChePaiException("文件不能为空");
		}
		return excelFileName;
	}
	
	private String makeFileName(String imageExtension) {
		return TimeUtil.getIdByTime() + "." + imageExtension;
	}
	
}
