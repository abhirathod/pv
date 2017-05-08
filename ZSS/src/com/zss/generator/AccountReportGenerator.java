package com.zss.generator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

	public void generateReport() {
		
//		AccountStatementLoader.prepare
		/*
		 * Unit
		 * User
		 * Account - Balance
		 * 
		 */
		
		Map<String, List<MaintenanceSheet>> userLedger = ZSSCache.getFlatLedger();
		Set<String> keySet = userLedger.keySet();
		//Set<String> sortedSet = new TreeSet<>();
		//sortedSet.addAll(keySet);
		final ArrayList<String> sortedKeyList = sortFlatByNumber(keySet);
		
		for (String flatNo : sortedKeyList) {
			List<MaintenanceSheet> statements = userLedger.get(flatNo);
			sortStatementsByDate(statements);
			
			//logFlatData(flatNo, statements);
			
			//TODO : Uncomment to print Maintenance SHEET
			prepareReport(statements);
			
		}
		System.out.println(MaintenanceReportFormatter.prepareIncomeReport());
		
		System.out.println(MaintenanceReportFormatter.prepareCashReport());

		System.out.println(MaintenanceReportFormatter.prepareExpenseReports());
		
	}

	private void sortStatementsByDate(List<MaintenanceSheet> statements) {
		Collections.sort(statements, new Comparator<MaintenanceSheet>() {
			@Override
			public int compare(MaintenanceSheet sheet1, MaintenanceSheet sheet2) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
				Date date1=null, date2=null;
				try {
					/*//FIXME : Date needs to be fixed.
					if(sheet1.getDate().startsWith("/"))
						date1 = dateFormat.parse("01"+sheet1.getDate());
					if(sheet2.getDate().startsWith("/"))
						date2 = dateFormat.parse("01"+sheet2.getDate());*/

					if(date1 == null)
						date1 = dateFormat.parse(sheet1.getDate());
					if(date2 == null)
						date2 = dateFormat.parse(sheet2.getDate());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return date1.compareTo(date2);
			}
		});
	}

	private ArrayList<String> sortFlatByNumber(Set<String> keySet) {
		final ArrayList<String> sortedKeyList = new ArrayList<>(keySet);
		Collections.sort(sortedKeyList, new Comparator<String>() {

			@Override
			public int compare(String flat1, String flat2) {
				final Integer int1;
				final Integer int2;
				try {
					int1 = getFlatNumber(flat1);
					int2 = getFlatNumber(flat2);
				} catch (Exception e) {
					System.err.println("---> Flat Comparison: " + flat1 + " : " + flat2);
					return flat1.compareTo(flat2);
				}
				return int1.compareTo(int2);
			}

			private int getFlatNumber(String flat1) {
				try {
					return Integer.parseInt(flat1);
				} catch (NumberFormatException e) {
					flat1 = flat1.replace("A", "");
					flat1 = flat1.replace("B", "");
					return Integer.parseInt(flat1);
				}
			}
		});
		return sortedKeyList;
	}

	public void loadData(List<String> fileList, boolean prepareData) {
		for (String fileName : fileList) {
			DataParser.loadWorkbook(fileName);
		}
		
		if(prepareData) {
			ZSSCache.prepareApplicationData();
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
