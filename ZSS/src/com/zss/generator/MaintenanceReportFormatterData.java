package com.zss.generator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.zss.cache.MaintenanceSheet;

public class MaintenanceReportFormatterData {
	
	public List<MaintenanceSheet> statements;
	public static SimpleDateFormat DD_MM_YY = new SimpleDateFormat("dd/MM/yy");

	public MaintenanceReportFormatterData() {
		
	}

	public static Date getLastDateOfMonth(String dateF) {
		Calendar calendar = MaintenanceReportFormatterData.getCalendarFromDate(dateF);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
		return calendar.getTime();	
	}

	public static Date getFirstDateOfMonth(String dateF) {
		Calendar calendar = MaintenanceReportFormatterData.getCalendarFromDate(dateF);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));  
		return calendar.getTime();
	}

	public static Date getFirstDateOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));  
		return calendar.getTime();
	}

	public static Date getLastDateOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
		return calendar.getTime();
	}

	static Calendar getCalendarFromDate(String dateF) {
		Calendar c = Calendar.getInstance();      
		try {
			final Date parse = MaintenanceReportFormatterData.DD_MM_YY.parse(dateF);
			c.setTime(parse);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public static Date getDate(String dateF) {
		return getCalendarFromDate(dateF).getTime();
	}
	
	public static String getFormattedDate(Date date) {
		return DD_MM_YY.format(date);
	}

	public static String getNextMonthFirstDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getLastDateOfMonth(date));
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return DD_MM_YY.format(calendar.getTime());
	}
	
}