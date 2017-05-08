package com.zss.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.zss.generator.AccountReportGenerator;
import com.zss.utility.DataParser;

public class TestApplication {

	public static void main(String[] args) {
		List<String> fileList = new ArrayList<String>();
		String DIRECTORY_PATH;
		AccountReportGenerator reportGenerator = new AccountReportGenerator();
		boolean startPreparingData = false;

		DIRECTORY_PATH = "D:\\workspaces\\PurvaVihar\\ACP_PV\\ZSS\\resources\\Purva Vihar\\FY2013-14\\";
		DataParser.setDIRECTORY_PATH(DIRECTORY_PATH);
		fileList.clear();
		fileList.add("09-September-2013.xlsx");
		fileList.add("10-October-2013.xlsx");
		fileList.add("11-November-2013.xlsx");
		fileList.add("12-December-2013.xlsx");
		fileList.add("01-January-2014.xlsx");
		fileList.add("02-February-2014.xlsx");
		fileList.add("03-March-2014.xlsx");
		reportGenerator.loadData(fileList, startPreparingData);
//		reportGenerator_FY_13_14.generateReport();

		fileList.clear();
		fileList.add("04-April-2014.xlsx");
		fileList.add("05-May-2014.xlsx");
		fileList.add("06-June-2014.xlsx");
		fileList.add("07-July-2014.xlsx");
		fileList.add("08-August-2014.xlsx");
		fileList.add("09-September-2014.xlsx");
		fileList.add("10-October-2014.xlsx");
		fileList.add("11-November-2014.xlsx");
		fileList.add("09-September-2014.xlsx");
		fileList.add("10-October-2014.xlsx");
		fileList.add("11-November-2014.xlsx");
		fileList.add("12-December-2014.xlsx");
		fileList.add("01-January-2015.xlsx");
		fileList.add("02-February-2015.xlsx");
		fileList.add("03-March-2015.xlsx");
		fileList.add("PurvaVihar-Misc.xlsx");
		DIRECTORY_PATH = "D:\\workspaces\\PurvaVihar\\ACP_PV\\ZSS\\resources\\Purva Vihar\\FY2014-15\\";
		DataParser.setDIRECTORY_PATH(DIRECTORY_PATH);
		reportGenerator.loadData(fileList, startPreparingData);

		fileList.clear();
		fileList.add("04-April-2015.xlsx");
		fileList.add("05-May-2015.xlsx");
		fileList.add("06-June-2015.xlsx");
		//Ghare From July
		fileList.add("07-July-2015.xlsx");
		fileList.add("08-August-2015.xlsx");
		fileList.add("09-September-2015.xlsx");
		fileList.add("10-October-2015.xlsx");
		fileList.add("11-November-2015.xlsx");
		fileList.add("09-September-2015.xlsx");
		fileList.add("10-October-2015.xlsx");
		fileList.add("11-November-2015.xlsx");
		fileList.add("12-December-2015.xlsx");
		fileList.add("01-January-2016.xlsx");
		fileList.add("02-February-2016.xlsx");
		fileList.add("03-March-2016.xlsx");
		DIRECTORY_PATH = "D:\\workspaces\\PurvaVihar\\ACP_PV\\ZSS\\resources\\Purva Vihar\\FY2015-16\\";
		DataParser.setDIRECTORY_PATH(DIRECTORY_PATH);
		reportGenerator.loadData(fileList, startPreparingData);

		fileList.clear();
		fileList.add("04-April-2016.xlsx");
		fileList.add("05-May-2016.xlsx");
		fileList.add("06-June-2016.xlsx");
		fileList.add("07-July-2016.xlsx");
		fileList.add("08-August-2016.xlsx");
		fileList.add("09-September-2016.xlsx");
		fileList.add("10-October-2016.xlsx");
		fileList.add("11-November-2016.xlsx");
		fileList.add("09-September-2016.xlsx");
		fileList.add("10-October-2016.xlsx");
		fileList.add("11-November-2016.xlsx");
		fileList.add("12-December-2016.xlsx");
		fileList.add("01-January-2017.xlsx");
		fileList.add("02-February-2017.xlsx");
		fileList.add("03-March-2017.xlsx");
		DIRECTORY_PATH = "D:\\workspaces\\PurvaVihar\\ACP_PV\\ZSS\\resources\\Purva Vihar\\FY2016-17\\";
		DataParser.setDIRECTORY_PATH(DIRECTORY_PATH);
		reportGenerator.loadData(fileList, startPreparingData);
		
		fileList.clear();
		fileList.add("PurvaVihar-Misc.xlsx");
		DIRECTORY_PATH = "D:\\workspaces\\PurvaVihar\\ACP_PV\\ZSS\\resources\\Purva Vihar\\";
		DataParser.setDIRECTORY_PATH(DIRECTORY_PATH);
		startPreparingData = true;
		reportGenerator.loadData(fileList, startPreparingData);
		
		reportGenerator.generateReport();
		
		
	}
	
	
	public static Date getLastDateOfMonth(String dateF) {
		Calendar c = Calendar.getInstance();      
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		try {
			final Date parse = sdf.parse(dateF);
			c.setTime(parse);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));  
		return c.getTime();
	}

}
