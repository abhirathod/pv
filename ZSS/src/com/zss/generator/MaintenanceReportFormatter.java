package com.zss.generator;

import java.text.MessageFormat;
import java.util.List;

import com.zss.cache.MaintenanceSheet;

public class MaintenanceReportFormatter {

	private List<MaintenanceSheet> statements;
	
	/*String REPORT_HEADER = 
			"------------------------------------------------------------------------------\n"
			+
			"Wing: {0} Flat: {1} Owner: {2} Contact:{3} \n"
			+
			"------------------------------------------------------------------------------\n"
			+
			"|Sr. No.|\tDate|\tOpening Balance|\tMaintenance|\tOutstanding Balance|\tPenalty"
			+ "|\tReceived Amount|\tRemaining Balance|\tPaid Date|\t Remarks|\n"
			+
			"------------------------------------------------------------------------------\n"
			;*/
	
	String REPORT_HEADER = 
			"------------------------------------------------------------------------------\n"
			+
			"Wing: {0} Flat: {1} Owner: {2} Contact:{3} \n"
			+
			"------------------------------------------------------------------------------\n"
			+
			"Sr. No.,\tDate,\tOpening Balance,\tMaintenance,\tOutstanding Balance,\tPenalty"
			+ ",\tReceived Amount,\tRemaining Balance,\tPaid Date,\t Remarks|\n"
			+
			"------------------------------------------------------------------------------\n"
			;
	
	String REPORT_RECORD =
			"{0},\t{1},\t{2},\t{3},\t{4},\t{5},\t{6},\t{7},\t{8},\t{9}"
			;
	public MaintenanceReportFormatter(List<MaintenanceSheet> statements) {
		this.statements = statements;
	}
	
	public StringBuilder prepareReport() {
		
		boolean prepareHeader = true;
		StringBuilder reportBuilder = new StringBuilder();
		int srNo = 1;
		for (MaintenanceSheet maintenanceSheet : statements) {
			if(prepareHeader) {
				String header = null;
				header = MessageFormat.format(REPORT_HEADER, maintenanceSheet.getWing(),
						maintenanceSheet.getFlatNo(),
						maintenanceSheet.getOwner(), "NA");
				reportBuilder.append(header);
				prepareHeader = false;
			}
			
			reportBuilder.append(MessageFormat.format(REPORT_RECORD, srNo, maintenanceSheet.getDate(),
					maintenanceSheet.getOpeningBalance(), maintenanceSheet.getMaintenance(), 
					maintenanceSheet.getTotalOutstandingBalance(), maintenanceSheet.getPenalty(), 
					maintenanceSheet.getReceivedMCharge(), maintenanceSheet.getRemainingBalance(),
					maintenanceSheet.getDate(), "NA", "NA"));
			
			reportBuilder.append("\n");
			srNo++;
		}
		
		return reportBuilder;
	}

}
