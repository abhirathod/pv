package com.zss.utility;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.zss.cache.BankTransaction;
import com.zss.cache.MaintenanceSheet;

public class ZSSCache {

	static Map<String, List<MaintenanceSheet>> flatStatementCache = new HashMap<String, List<MaintenanceSheet>>();  
	static List<BankTransaction> bankStatementCache = new ArrayList<BankTransaction>();  

	private static final int START_BANK_ROW = 4;
	private static final int END_BANK_ROW = -1;
	private static SimpleDateFormat BANK_DATE_FORMAT = new SimpleDateFormat("MM/dd/yy");
	private static final String DEFAULT_NA_VALUE = "N.A.";

	static int END_MAINTENANCE_ROW = 25;
	static int START_MAINTENANCE_ROW = 8;
	
	public static void prepareApplicationData() {
		Map<String, Sheet> sheetCache = WorkbookCache.INSTANCE.getSheetCache();
		Set<String> sheetNames = sheetCache.keySet();
		for (String sheetName : sheetNames) {
			//System.out.println("ZSSCache.prepareApplicationData() --> " + sheetName);
			Sheet sheet = sheetCache.get(sheetName); 
			//Process Maintenance Sheet
			if(sheet.getSheetName().contains("Wing")) {
				FormulaEvaluator formulaEvaluator = WorkbookCache.INSTANCE.getFormulaEvaluator(sheetName);
				//readSheetForAccountStatement(sheet, formulaEvaluator);
			} else if(sheet.getSheetName().contains("Bank")) {
				FormulaEvaluator formulaEvaluator = WorkbookCache.INSTANCE.getFormulaEvaluator(sheetName);
				readBankStatement(sheet, formulaEvaluator);
			}
		}
		
	}

	private static void readBankStatement(Sheet sheet, FormulaEvaluator formulaEvaluator) {
		// TODO Auto-generated method stub
		for (int i = ZSSCache.START_BANK_ROW; /*i <= ZSSCache.END_BANK_ROW*/; i++) {
			Row row = sheet.getRow(i);
			if(row.getCell(3) == null) {
				break;
			}
			BankTransaction transaction = new BankTransaction();
			String columnValue = getColumnValue(row, 2, formulaEvaluator);
			if (columnValue != null && !"".equals(columnValue)) {
				try {
					transaction.setDate(BANK_DATE_FORMAT.parse(columnValue));
				} catch (ParseException e) { 
					//e.printStackTrace();
				} 
			}
			transaction.setDescription(getColumnValue(row, 3, formulaEvaluator));
			transaction.setChequeNumber(getColumnValue(row, 4, formulaEvaluator));
			transaction.setMode(getColumnValue(row, 5, formulaEvaluator));
			columnValue = getColumnValue(row, 6, formulaEvaluator);
			if (columnValue != null && !"".equals(columnValue) && !DEFAULT_NA_VALUE.equals(columnValue)) {
				transaction.setWithdrawal(new BigDecimal(columnValue));
			}
			columnValue = getColumnValue(row, 7, formulaEvaluator);
			if (columnValue != null && !"".equals(columnValue) && !DEFAULT_NA_VALUE.equals(columnValue)) {
				transaction.setCredit(new BigDecimal(columnValue));
			}
			columnValue = "1000000000";//getColumnValue(row, 8, formulaEvaluator);
			
			if (columnValue != null && !"".equals(columnValue) && !DEFAULT_NA_VALUE.equals(columnValue)) {
				transaction.setBalance(new BigDecimal(columnValue));
			}
			System.out.println(transaction);
			bankStatementCache.add(transaction);
		}
		
	}

	private static void readSheetForAccountStatement(Sheet sheet, FormulaEvaluator formulaEvaluator) {
		for (int i = ZSSCache.START_MAINTENANCE_ROW; i <= ZSSCache.END_MAINTENANCE_ROW; i++) {
			Row row = sheet.getRow(i);
			
			MaintenanceSheet maintenanceSheet = new MaintenanceSheet(getColumnValue(row, 0, formulaEvaluator), sheet.getSheetName(),
					getColumnValue(row, 1, formulaEvaluator), getColumnValue(row, 2, formulaEvaluator),
					getColumnValue(row, 3, formulaEvaluator), /*getColumnValue(row, 4, formulaEvaluator)*/ null,
					getColumnValue(row, 5, formulaEvaluator), getColumnValue(row, 6, formulaEvaluator),
					/*getColumnValue(row, 7, formulaEvaluator)*/null, getColumnValue(row, 8, formulaEvaluator),
					getColumnValue(row, 9, formulaEvaluator));
			
			
			addUserStatements(maintenanceSheet);
			
		}
	
	}
	
	private static void addUserStatements(MaintenanceSheet maintenanceRecord) {
		if(flatStatementCache.get(maintenanceRecord.getFlatNo())==null) {
			flatStatementCache.put(maintenanceRecord.getFlatNo(), new ArrayList<MaintenanceSheet>());
		} 
		
		flatStatementCache.get(maintenanceRecord.getFlatNo()).add(maintenanceRecord);
	}

	private static String getColumnValue(Row row, int index, FormulaEvaluator formulaEvaluator) {
		Cell cell = row.getCell(index);
		String stringCellValue = DEFAULT_NA_VALUE;
//		System.out.println("Type: " + cell.getCellType() + ", Index=" + index + " Row " + row.getRowNum());
		
		switch(cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			stringCellValue = String.valueOf((int)cell.getNumericCellValue());
			if(HSSFDateUtil.isCellDateFormatted(cell)) {
				CellValue cValue = formulaEvaluator.evaluate(cell);
				double dv = cValue.getNumberValue();

				final Date date = HSSFDateUtil.getJavaDate(dv);
				stringCellValue = String.valueOf(date.getTime());
			    String dateFmt = cell.getCellStyle().getDataFormatString();
			    /* strValue = new SimpleDateFormat(dateFmt).format(date); - won't work as 
			    Java fmt differs from Excel fmt. If Excel date format is mm/dd/yyyy, Java 
			    will always be 00 for date since "m" is minutes of the hour.*/

			    stringCellValue = new CellDateFormatter(dateFmt).format(date); 
			    // takes care of idiosyncrasies of Excel
			}
			break;
		case Cell.CELL_TYPE_STRING:
			stringCellValue = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_BLANK:
			stringCellValue = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_FORMULA:
			System.out.println("Formula: " + cell.getCellFormula());
			stringCellValue = String.valueOf(formulaEvaluator.evaluateFormulaCell(cell));
			break;
		}
//		System.out.println(", stringCellValue= " + stringCellValue);
		stringCellValue = stringCellValue.trim();
		return stringCellValue;
	}

	public static Map<String, List<MaintenanceSheet>> getFlatLedger() {
		return flatStatementCache;
	}

	
}
