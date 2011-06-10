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
			//��ȡ�ļ��ĺ�׺
			String excelExtension = excelFileName.substring(excelFileName.lastIndexOf(".") + 1, excelFileName.length());
			//���´���һ����һ�޶���ͼƬ����
			excelFileName = this.makeFileName(excelExtension);
			File saveFile = new File(new File(uploadPath), excelFileName);
			//�ж�·���Ƿ����
			if(!saveFile.getParentFile().exists()) saveFile.getParentFile().mkdirs();
			try {
				FileUtils.copyFile(excel, saveFile);
			} catch (IOException e) {
				e.printStackTrace();
				throw new ChePaiException("�ļ��ϴ�ʧ��");
			}
		} else {
			throw new ChePaiException("�ļ�����Ϊ��");
		}
		return excelFileName;
	}
	
	private String makeFileName(String imageExtension) {
		return TimeUtil.getIdByTime() + "." + imageExtension;
	}
	
}
