package com.hovto.chepai.tool;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public final class TimeUtil {

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String formatDate(Date date) {
		return formatDate.format(date);
	}
	
	public static String formatDateTime(Date date) {
		return format.format(date);
	}
	
	public static synchronized String getIdByTime() {
		return getCurrDate() + getCurrTime() + getRandomNumber();
	}

	public static String getCurrDate() {
		Date date = new Date();
		Format formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(date);
	}

	public static String getCurrTime() {
		Date date = new Date();
		Format formatter = new SimpleDateFormat("HHmmss");
		return formatter.format(date);
	}

	public static String getRandomNumber() {
		Random random = new Random();
		String retval = String.valueOf(random.nextInt(100));
		return retval;
	}

	public static void main(String[] args) {
		String str = TimeUtil.getIdByTime();
		System.out.println(str);

	}

}
