package com.zss.test;

import java.util.ArrayList;
import java.util.List;

import com.zss.generator.AccountReportGenerator;
import com.zss.utility.DataParser;

public class TestApplication {

	public static void main(String[] args) {
		List<String> fileList = new ArrayList<String>();
		fileList.add("04-April-2014.xlsx");
		fileList.add("05-May-2014.xlsx");
		fileList.add("06-June-2014.xlsx");
		fileList.add("07-July-2014.xlsx");
		fileList.add("08-August-2014.xlsx");
		fileList.add("09-September-2014.xlsx");
		fileList.add("10-October-2014.xlsx");
		fileList.add("11-November-2014.xlsx");
		fileList.add("12-December-2014.xlsx");
		fileList.add("01-January-2015.xlsx");
		fileList.add("02-February-2015.xlsx");
		fileList.add("03-March-2015.xlsx");
		fileList.add("PurvaVihar-Misc.xlsx");
		String DIRECTORY_PATH = "D:\\workspaces\\PurvaVihar\\ACP_PV\\ZSS\\resources\\Purva Vihar\\FY2014-15\\";
		//DIRECTORY_PATH = "/media/abhijeet/OS/Devs/EWorkspace/Workspaces/EWorkspace/ACP_PV/ZSS/resources/Purva Vihar/FY2014-15/"
;		DataParser.setDIRECTORY_PATH(DIRECTORY_PATH);
		AccountReportGenerator acrGenerator = new AccountReportGenerator();
		acrGenerator.generateReport(fileList);
	}
}
