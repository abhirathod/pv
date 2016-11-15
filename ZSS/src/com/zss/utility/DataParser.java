package com.zss.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataParser {

	private static final Logger log = Logger.getLogger(DataParser.class);
	private static String DIRECTORY_PATH="C:\\Users\\arathod\\Desktop\\Purva Vihar\\FY2014-15\\";
	
	public static void loadWorkbook(String fileName, boolean loadToCache) {
		
		int SHEET_COUNT = 0;
		Workbook workbook = null;
		FileInputStream fileInputStream = null;
		try {
			File file = new File(getDIRECTORY_PATH() + fileName); 
			fileInputStream = new FileInputStream(file);
			if(file.exists() && file.getName().toLowerCase().endsWith("xlsx")){
				workbook=new XSSFWorkbook(fileInputStream);
			} else if(file.exists() && file.getName().toLowerCase().endsWith("xls")){
				workbook = new HSSFWorkbook(fileInputStream);
			}
		} catch (FileNotFoundException e) {
			log.error("File Not Exist: ", e);
		} catch (IOException e) {
			log.error("Error while reading file:", e);
		} finally{
			if(workbook!=null){
				WorkbookCache.INSTANCE.addWorkbook(fileName, workbook);
				SHEET_COUNT=workbook.getNumberOfSheets();
//				System.out.println("DataParser.readFile() " + fileName + " SHEET_COUNT: " + SHEET_COUNT);
				//prepareSheets();
			}
			//FIXME: Compilation Error
			if(fileInputStream != null){
				try {
					fileInputStream.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
		
	}

	public static String getDIRECTORY_PATH() {
		return DIRECTORY_PATH;
	}

	public static void setDIRECTORY_PATH(String dIRECTORY_PATH) {
		DIRECTORY_PATH = dIRECTORY_PATH;
	}
	
	

}
