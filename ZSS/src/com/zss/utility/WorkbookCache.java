package com.zss.utility;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class WorkbookCache {

	public static final WorkbookCache INSTANCE = getSingleInstance();

	private Map<String, Workbook> workbookCache;
	private Map<String, Sheet> sheetCache;
	private Map<String, FormulaEvaluator> formulaEvaluatorCache;

	private WorkbookCache() {
		workbookCache = new HashMap<String, Workbook>();
		sheetCache = new HashMap<String, Sheet>();
		formulaEvaluatorCache = new HashMap<String, FormulaEvaluator>();
	}

	private static WorkbookCache getSingleInstance() {
		/*WorkbookCache object = null;
		if (INSTANCE != null) {
			return object = new WorkbookCache();
		}*/
		return new WorkbookCache();
	}
	
	public void clear() {
		workbookCache.clear();
		sheetCache.clear();
		formulaEvaluatorCache.clear();
	}

	public void addWorkbook(String fileName, Workbook workbook) {
		if (workbook != null) {
			logMessage("Loading workbook... " + fileName);
			workbookCache.put(fileName, workbook);
			FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				if(sheet != null) {
					String sheetName = fileName + "_" + sheet.getSheetName();
					logMessage("Adding Sheet to Cache: " + sheetName);
					formulaEvaluatorCache.put(sheetName, formulaEvaluator);
					sheetCache.put(sheetName, sheet);
				} else {
					logMessage("Sheet not found for File: " + fileName);
				}
			}
		}
	}
	
	static void logMessage(String message) {
//		System.out.println(message);
	}

	protected Map<String, Sheet> getSheetCache() {
		return sheetCache;
	}
	
	public FormulaEvaluator getFormulaEvaluator(String sheetName) {
		return formulaEvaluatorCache.get(sheetName);
	}

}
