package com.zss.generator;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zss.cache.BankTransaction;
import com.zss.cache.DateCapable;
import com.zss.cache.ExpenseVoucher;
import com.zss.cache.MaintenanceSheet;
import com.zss.cache.MonthlyIncome;
import com.zss.utility.ZSSCache;

public class MaintenanceReportFormatter {

	private MaintenanceReportFormatterData data = new MaintenanceReportFormatterData();

	/*
	 * String REPORT_HEADER =
	 * "------------------------------------------------------------------------------\n"
	 * + "Wing: {0} Flat: {1} Owner: {2} Contact:{3} \n" +
	 * "------------------------------------------------------------------------------\n"
	 * +
	 * "|Sr. No.|\tDate|\tOpening Balance|\tMaintenance|\tOutstanding Balance|\tPenalty"
	 * + "|\tReceived Amount|\tRemaining Balance|\tPaid Date|\t Remarks|\n" +
	 * "------------------------------------------------------------------------------\n"
	 * ;
	 */

	static String M_USER_HEADER =
			/*
			 * "------------------------------------------------------------------------------\n"
			 * +
			 */
			",Wing: {0} Flat: {1} Owner: {2} Contact:{3} \n"
	/*
	 * +
	 * "------------------------------------------------------------------------------\n"
	 */
	;

	static String M_REPORT_HEADER = "Sr. No.,Date,Description,Drawn,Credited,Penalty,Balance\n"
	/*
	 * +
	 * "------------------------------------------------------------------------------\n"
	 */
	;
	static String M_REPORT_RECORD = "{0},{1},{2},{3},{4},{5},{6}";

	static String M_REPORT_HEADER_WITH_TAB = "Sr. No.,\tDate,\tDescription,\tDrawn,\tCredited,\tPenalty,\tBalance\n"
	/*
	 * +
	 * "------------------------------------------------------------------------------\n"
	 */
	;

	static String M_REPORT_RECORD_WITH_TAB = "{0},\t{1},\t{2},\t{3},\t{4},\t{5},\t{6}";

	/*
	 * static String REPORT_HEADER =
	 * "------------------------------------------------------------------------------\n"
	 * + ",Wing: {0} Flat: {1} Owner: {2} Contact:{3} \n" +
	 * "------------------------------------------------------------------------------\n"
	 * +
	 * "Sr. No.,\tDate,\tOpening Balance,\tMaintenance,\tOutstanding Balance,\tPenalty"
	 * +
	 * ",\tReceived Amount,\tRemaining Balance,\tPaid Date,\tReceipt No,\tRemarks|\n"
	 * +
	 * "------------------------------------------------------------------------------\n"
	 * ;
	 * 
	 * static String REPORT_RECORD =
	 * "{0},\t{1},\t{2},\t{3},\t{4},\t{5},\t{6},\t{7},\t{8},\t{9},\t{10}" ;
	 */
	static Map<String, String> FLAT_META_DATA = new HashMap<>();

	static {
		FLAT_META_DATA.put("1", "Kadam R. S.");
		FLAT_META_DATA.put("2", "Khadtare B. B.");
		FLAT_META_DATA.put("7", "Devgirikar S.");
		FLAT_META_DATA.put("8", "Kshirsagar B. L.");
		FLAT_META_DATA.put("9", "Awale D.T.");
		FLAT_META_DATA.put("10", "Dandekar V. N.");
		FLAT_META_DATA.put("15", "Rathod G. H.");
		FLAT_META_DATA.put("16", "Arde K. Y.");
		FLAT_META_DATA.put("17", "Nirgude M. V.");
		FLAT_META_DATA.put("18", "Naidu S. M.");
		FLAT_META_DATA.put("23", "Dumpatti N. Y.");
		FLAT_META_DATA.put("24", "Remobhotkar R.B./S.B.");
		FLAT_META_DATA.put("25", "Ghare S. D.");
		FLAT_META_DATA.put("26", "Rane A. K.");
		FLAT_META_DATA.put("31", "Kudal P. R.");
		FLAT_META_DATA.put("32", "Kirve M. M.");
		FLAT_META_DATA.put("33", "Rajmane M. M.");
		FLAT_META_DATA.put("34", "Rajguru H. V.");
		FLAT_META_DATA.put("3", "Khadtare B. B.");
		FLAT_META_DATA.put("4", "Samak S. G.");
		FLAT_META_DATA.put("5", "Naik B. B.");
		FLAT_META_DATA.put("6", "Poman L. K.");
		FLAT_META_DATA.put("11", "Khadtare B. B.");
		FLAT_META_DATA.put("12", "Kulkarni S. D.");
		FLAT_META_DATA.put("13", "Patil K. H.");
		FLAT_META_DATA.put("14", "Kour R. B.");
		FLAT_META_DATA.put("19", "Mane M. B.");
		FLAT_META_DATA.put("20", "Bhise V. R.");
		FLAT_META_DATA.put("21", "Naik C. N.");
		FLAT_META_DATA.put("22", "Patil M. R.");
		FLAT_META_DATA.put("27", "Patil B. B.");
		FLAT_META_DATA.put("28A", "Phadtare Sunanda");
		FLAT_META_DATA.put("28B", "Patil S. U.");
		FLAT_META_DATA.put("29A", "Katare");
		FLAT_META_DATA.put("29B", "Ghade S. S.");
		FLAT_META_DATA.put("30", "Todkar D. G.");
	}

	public MaintenanceReportFormatter(List<MaintenanceSheet> statements) {
		this.data.statements = statements;
	}

	public StringBuilder prepareReport() {

		StringBuilder reportBuilder = new StringBuilder();
		int srNo = 1;
		boolean prepareHeader = true;
		boolean prepareUserHeader = true;
		BigDecimal totalCredited = BigDecimal.ZERO;
		BigDecimal totalDrawn = BigDecimal.ZERO;
		BigDecimal totalPenalty = BigDecimal.ZERO;

		for (MaintenanceSheet maintenanceSheet : data.statements) {

			String formattedDate = maintenanceSheet.getDate();
			final Date firstDateOfMonth = MaintenanceReportFormatterData.getFirstDateOfMonth(formattedDate);
			if (firstDateOfMonth.getMonth() == 3) { // APRIL
				prepareUserHeader = true;
				if (srNo > 1) {
					printTotalRecord(reportBuilder, totalCredited, totalDrawn, totalPenalty);
					totalCredited = BigDecimal.ZERO;
					totalDrawn = BigDecimal.ZERO;
					totalPenalty = BigDecimal.ZERO;
				}
				srNo = 1;
			}

			if (prepareHeader) {
				String header = MessageFormat.format(M_USER_HEADER, maintenanceSheet.getWing().replace("-Wing", ""),
						maintenanceSheet.getFlatNo(), FLAT_META_DATA.get(maintenanceSheet.getFlatNo()), "NA");
				reportBuilder.append(header);
				prepareHeader = false;
			}
			if (prepareUserHeader) {
				reportBuilder.append("\n");
				reportBuilder.append(M_REPORT_HEADER);
				prepareUserHeader = false;
			}

			if (srNo == 1) {
				reportBuilder.append(MessageFormat.format(M_REPORT_RECORD, srNo,
						MaintenanceReportFormatterData.getFormattedDate(firstDateOfMonth), "Opening Balance", "", "",
						"", maintenanceSheet.getOpeningBalance()));

				reportBuilder.append("\n");
				srNo++;
			}

			reportBuilder.append(MessageFormat.format(M_REPORT_RECORD, srNo,
					MaintenanceReportFormatterData.getFormattedDate(firstDateOfMonth), "To Maintenance",
					maintenanceSheet.getMaintenance(), "", "", maintenanceSheet.getTotalOutstandingBalance()));
			totalDrawn = totalDrawn.add(new BigDecimal(maintenanceSheet.getMaintenance()));

			reportBuilder.append("\n");
			srNo++;

			final String description = maintenanceSheet.getDescription();
			if (!"".equals(description)) {
				boolean addRecord = true;

				final String receivedMCharge = maintenanceSheet.getReceivedMCharge();
				if ("Penalty".equals(description)) {
					formattedDate = MaintenanceReportFormatterData
							.getFormattedDate(MaintenanceReportFormatterData.getLastDateOfMonth(formattedDate));
					totalPenalty = totalPenalty.add(new BigDecimal(maintenanceSheet.getPenalty()));
				} else {
					final BigDecimal receivedCharge = new BigDecimal(receivedMCharge);
					totalCredited = totalCredited.add(receivedCharge);
					addRecord = receivedCharge.compareTo(BigDecimal.ZERO) != 0;
				}
				if (addRecord) {
					reportBuilder.append(MessageFormat.format(M_REPORT_RECORD, srNo, formattedDate, description, "",
							receivedMCharge, maintenanceSheet.getPenalty(), maintenanceSheet.getRemainingBalance()));
					reportBuilder.append("\n");
					srNo++;
				}
			}
		}

		printTotalRecord(reportBuilder, totalCredited, totalDrawn, totalPenalty);

		return reportBuilder;
	}

	public static String prepareIncomeReport() {
		printReportHeader("INCOME REPORT");
		StringBuffer buffer = new StringBuffer();
		final Set<Date> keySet = ZSSCache.INCOME_DATA.keySet();
		List<Date> sortedDates = new ArrayList<>(keySet);
		Collections.sort(sortedDates);
		int lastYear = -1;
		boolean printHeader = true;
		for (Date date : sortedDates) {
			
			final Date firstDateOfMonth = date;
			buffer.append("\n,").append(MaintenanceReportFormatterData.getFormattedDate(firstDateOfMonth)).append(",\n");
			if (printHeader || (firstDateOfMonth.getMonth() == 3 && firstDateOfMonth.getYear() > lastYear)) { // APRIL
				lastYear = firstDateOfMonth.getYear();
				buffer.append("\nDate, Description, Amount\n");
				printHeader = false;
			}
			final Map<String, MonthlyIncome> map = ZSSCache.INCOME_DATA.get(date);
			final Set<String> paymentModes = map.keySet();
			BigDecimal totalMonthlyCashFlow = BigDecimal.ZERO;
			for (String paymentMode : paymentModes) {
				final MonthlyIncome monthlyIncome = map.get(paymentMode);
				if (monthlyIncome.getAmount().compareTo(BigDecimal.ZERO) != 0) {
					buffer.append(MaintenanceReportFormatterData.getFormattedDate(monthlyIncome.getDate()))
							.append(",")
							.append(getIncomeDescription(monthlyIncome))
							.append(",").append(monthlyIncome.getAmount().toPlainString()).append("\n");
					totalMonthlyCashFlow = totalMonthlyCashFlow.add(monthlyIncome.getAmount());
				}
			}

			// Monthwise total for all payment modes
			buffer.append("Total").append(",").append("").append(",").append(totalMonthlyCashFlow.toPlainString())
					.append("\n");
		}

		return buffer.toString();
	}

	private static String getIncomeDescription(final MonthlyIncome monthlyIncome) {
		if(monthlyIncome.getFlatNo().equals(BY_CASH_IN_HAND)) {
			return BY_CASH_IN_HAND;
		}
		return "By " + monthlyIncome.getPaymentMode() + " Received From "
				+ (monthlyIncome.getPaymentMode().equals("Cash") ? " Society Members" : FLAT_META_DATA.get(monthlyIncome.getFlatNo()));
	}

	private void printTotalRecord(StringBuilder reportBuilder, BigDecimal totalCredited, BigDecimal totalDrawn,
			BigDecimal totalPenalty) {
		reportBuilder.append(MessageFormat.format(M_REPORT_RECORD, "Total", "", "", totalDrawn.toPlainString(),
				totalCredited.toPlainString(), totalPenalty.toPlainString(), ""));

		reportBuilder.append("\n");
	}

	final static String BY_CASH_IN_HAND = "By Cash in Hand";
	final static String TO_CASH_IN_HAND = "To Cash in Hand";
	
	public static String prepareExpenseReports() {
		StringBuilder reportBuilder = new StringBuilder();
		
		final Set<String> reportTypes = ZSSCache.VOUCHER_DATA_BY_TYPE.keySet();
		for (String reportType : reportTypes) {
			printReportHeader(reportType.toUpperCase() + " REPORT");
			/*System.out.println("========================================================================");
			System.out.println("=====================  " + reportType.toUpperCase() +" REPORT   ================================");
			System.out.println("========================================================================");*/
			final List<ExpenseVoucher> cashVouchers = ZSSCache.VOUCHER_DATA_BY_TYPE.get(reportType);
			Collections.sort(cashVouchers, new Comparator<ExpenseVoucher>() {
				@Override
				public int compare(ExpenseVoucher o1, ExpenseVoucher o2) {
					return o1.getDate().compareTo(o2.getDate());
				}
			});
			boolean printHeader = true;
			int lastYear = -1;
			for (ExpenseVoucher expenseVoucher : cashVouchers) {
//				System.out.println(expenseVoucher);
				final Date firstDateOfMonth = expenseVoucher.getDate();
				if (printHeader || (firstDateOfMonth.getMonth() == 3 && firstDateOfMonth.getYear() > lastYear)) { // APRIL
					lastYear = firstDateOfMonth.getYear();
					System.out.print("\nDate, Description, Amount\n");
					printHeader = false;
				}
				String record = MaintenanceReportFormatterData.getFormattedDate(expenseVoucher.getDate()) + ", " + getExpenseDescription(expenseVoucher) + ", " + expenseVoucher.getAmount();
				System.out.println(record);
			}
			if(reportType.equals("Printing and Stationary")) {
				final List<BankTransaction> chequeIssueStatementCache = ZSSCache.chequeIssueStatementCache;
				for (BankTransaction bankTransaction : chequeIssueStatementCache) {
					String record = MaintenanceReportFormatterData.getFormattedDate(bankTransaction.getDate()) + ", " + getExpenseDescription(bankTransaction) + ", " + bankTransaction.getWithdrawal();
					System.out.println(record);
				}
			}
			
		}
		return reportBuilder.toString();
	}
	
	public static String prepareCashReport() {
		printReportHeader("CASH REPORT");
		StringBuilder reportBuilder = new StringBuilder();
		final List<ExpenseVoucher> cashVouchers = ZSSCache.VOUCHER_DATA_BY_PAYMODE.get("Cash");
		final List<BankTransaction> cashCreditStatements = ZSSCache.cashCreditStatementCache;
		final Map<Date, Map<String, MonthlyIncome>> monthlyIncomeData = ZSSCache.INCOME_DATA;
		final Map<Date, List<? extends DateCapable>> montlyCashVouchers = getMontlyDataMap(cashVouchers);
		final Map<Date, List<? extends DateCapable>> montlyCreditStatements = getMontlyDataMap(cashCreditStatements);
		final Set<Date> keySet = monthlyIncomeData.keySet();
		List<Date> sortedDates = new ArrayList<>(keySet);
		Collections.sort(sortedDates);
		BigDecimal cashInHand = new BigDecimal("10885");//10885 //11805 - Used to generate report
		boolean printHeader = true;
		int lastYear = -1;
		for (Date date : sortedDates) {
			final Date firstDateOfMonth = date;
			if (printHeader || (firstDateOfMonth.getMonth() == 3 && firstDateOfMonth.getYear() > lastYear)) { // APRIL
				lastYear = firstDateOfMonth.getYear();
				reportBuilder.append("\nDate, Description, Amount,,Date, Description, Amount\n");
				printHeader = false;
			}
			MonthlyIncome mi = new MonthlyIncome();
			mi.setAmount(cashInHand);
			mi.setDate(date);
			mi.setPaymentMode("Cash");
			mi.setFlatNo(BY_CASH_IN_HAND);
			final Map<String, MonthlyIncome> map = monthlyIncomeData.get(date);
			final List<MonthlyIncome> monthlyIncomes = new ArrayList(map.values());
			monthlyIncomes.add(0, mi);
			
			List list = montlyCashVouchers.get(date);
			final List list2 = montlyCreditStatements.get(date);
			if(list != null && list2 != null){
				list.addAll(list2);
			} else if(list == null) {
				if(list2 != null) {
					list = list2;
				} else {
					list = new ArrayList<>();
				}
			}
			
			boolean listsExhausted = false;
			BigDecimal creditAmount = BigDecimal.ZERO;
			BigDecimal debitAmount = BigDecimal.ZERO;
			int index = 0;
			do {
				listsExhausted = monthlyIncomes.size() < index && list.size() < index;
				final MonthlyIncome monthlyIncome;
				if (monthlyIncomes.size() > index)
					monthlyIncome = monthlyIncomes.get(index);
				else
					monthlyIncome = null;
				
				DateCapable dateCapable = null;
				if (list.size() > index)
					dateCapable = (DateCapable) list.get(index);
				index++;
				if(monthlyIncome == null && dateCapable == null) {
					continue;
				}
				if(monthlyIncome != null && monthlyIncome.getAmount().compareTo(BigDecimal.ZERO) != 0) {
					StringBuilder builder = new StringBuilder();
					builder.append(MaintenanceReportFormatterData.getFormattedDate(monthlyIncome.getDate()))
					.append(",")
					.append(getIncomeDescription(monthlyIncome))
					.append(",").append(monthlyIncome.getAmount().toPlainString())/*.append("\n")*/;
					reportBuilder.append(builder.toString());
					creditAmount = creditAmount.add(monthlyIncome.getAmount());
				} else {
					reportBuilder.append(",,");
				}
				reportBuilder.append(",,");
				if(dateCapable != null) {
					StringBuilder builder = new StringBuilder();
					builder.append(MaintenanceReportFormatterData.getFormattedDate(dateCapable.getDate()))
					.append(",")
					.append(getExpenseDescription(dateCapable))
					.append(",").append(dateCapable.getAmount().toPlainString())/*.append("\n")*/;
					;
					reportBuilder.append(builder.toString());
					debitAmount = debitAmount.add(dateCapable.getAmount());
				} else {
					reportBuilder.append(",,");
				}
				boolean noRecord = reportBuilder.toString().equals(",,,,,,");
				if(!noRecord)
					reportBuilder.append("\n");
				else reportBuilder = new StringBuilder();
			} while (!listsExhausted);
			
			cashInHand = creditAmount.subtract(debitAmount);
			final Date lastDateOfMonth = MaintenanceReportFormatterData.getLastDateOfMonth(firstDateOfMonth);
			reportBuilder.append(",,,,"+MaintenanceReportFormatterData.getFormattedDate(lastDateOfMonth)+","+TO_CASH_IN_HAND+"," + cashInHand.toPlainString());
			reportBuilder.append("\n");
			reportBuilder.append("Total,," + creditAmount.toPlainString() + ",,Total,," + debitAmount.add(cashInHand).toPlainString());
			reportBuilder.append("\n");
			reportBuilder.append("\n");
			String nextMonthFirstDate = MaintenanceReportFormatterData.getNextMonthFirstDate(firstDateOfMonth);
			reportBuilder.append("," + nextMonthFirstDate + ",,,," + nextMonthFirstDate +",\n");
			debitAmount = BigDecimal.ZERO;
		}
		return reportBuilder.toString();
	}

	private static void printReportHeader(String reportHeader) {
		System.out.println("========================================================================");
		System.out.println("=====================  " + reportHeader + "     ================================");
		System.out.println("========================================================================");
	}

	private static String getExpenseDescription(DateCapable dateCapable) {
		StringBuilder builder = new StringBuilder();
		if(dateCapable instanceof ExpenseVoucher) {
			final ExpenseVoucher expenseVoucher = (ExpenseVoucher)dateCapable;
			builder.append("To ")
			.append(expenseVoucher.getPaidTo())
			.append(" For ").append(expenseVoucher.getDescription())
			.append(" Debit As ").append(expenseVoucher.getPaymentMode());
		} else if(dateCapable instanceof BankTransaction) {
			BankTransaction bankTransaction = (BankTransaction)dateCapable;
			builder.append("To ").append(bankTransaction.getMode())
			.append(" Deposit ").append(bankTransaction.getDescription());
		}
		return builder.toString();
	}

	private static Map<Date, List<? extends DateCapable>> getMontlyDataMap(final List<? extends DateCapable> dataList) {
		Map<Date, List<? extends DateCapable>> monthlyData = new HashMap<>();
		for (DateCapable dateCapable : dataList) {
			try {
				final Date firstDateOfMonth = MaintenanceReportFormatterData.getFirstDateOfMonth(dateCapable.getDate());
				List data = monthlyData.get(firstDateOfMonth);
				if(data == null) {
					data = new ArrayList<>();
					monthlyData.put(firstDateOfMonth, data);
				}
				data.add(dateCapable);
			} catch (Exception e) {
				System.err.println(dateCapable);
				e.printStackTrace();
			}
		}
		return monthlyData;
	}
}
