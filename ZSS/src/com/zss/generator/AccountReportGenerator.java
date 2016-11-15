package com.zss.generator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zss.cache.MaintenanceSheet;
import com.zss.utility.DataParser;
import com.zss.utility.ZSSCache;

public class AccountReportGenerator {

	public void generateReport(List<String> fileList) {
		for (String fileName : fileList) {
			DataParser.loadWorkbook(fileName, true);
		}
		
		ZSSCache.prepareApplicationData();
		
//		AccountStatementLoader.prepare
		/*
		 * Unit
		 * User
		 * Account - Balance
		 * 
		 */
		
		Map<String, List<MaintenanceSheet>> userLedger = ZSSCache.getFlatLedger();
		Set<String> keySet = userLedger.keySet();
		for (String flatNo : keySet) {
			List<MaintenanceSheet> statements = userLedger.get(flatNo);
			Collections.sort(statements, new Comparator<MaintenanceSheet>() {
				@Override
				public int compare(MaintenanceSheet sheet1, MaintenanceSheet sheet2) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yy");
					Date date1=null, date2=null;
					try {
						
						//FIXME : Date needs to be fixed.
						if(sheet1.getDate().startsWith("/"))
							date1 = dateFormat.parse("01"+sheet1.getDate());
						if(sheet2.getDate().startsWith("/"))
							date2 = dateFormat.parse("01"+sheet2.getDate());
						//System.out.println("Date1: " + date1.getDay() + date1.getMonth() + date1.getYear());
						//System.out.println("Date2: " + date2.getDay() + date2.getMonth() + date2.getYear());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return date1.compareTo(date2);
				}
			});
			
			//logFlatData(flatNo, statements);
			
			//TODO : Uncomment to print Maintenance SHEET
			//prepareReport(statements);
			
		}
		
	}

	private void prepareReport(List<MaintenanceSheet> statements) {
		MaintenanceReportFormatter rep = new MaintenanceReportFormatter(statements);
		System.out.println(rep.prepareReport().toString());
	}

	private void logFlatData(String flatNo, List<MaintenanceSheet> statements) {
		System.out.println("--------------------------------------------------------------");
		System.out.println("----------------------- FLAT NO: "+ flatNo +"---------------------------");
		System.out.println("--------------------------------------------------------------");
		for (MaintenanceSheet maintenanceSheet : statements) {
			System.out.println(maintenanceSheet);
		}
	}

	
}
