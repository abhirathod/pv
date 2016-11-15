package com.zss.cache;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MaintenanceSheet implements Comparable{
		private String flatNo;
		private String wing;
		private String owner;
		private String openingBalance;
		private String maintenance;
		private String totalOutstandingBalance;
		private String penalty;
		private String receivedMCharge;
		private String remainingBalance;
		private String date;
		private String receiptNo;
		
		public MaintenanceSheet(String flatNo, String wing, String owner,
				String openingBalance, String maintenance,
				String totalOutstandingBalance, String penalty,
				String receivedMCharge, String remainingBalance, String date,
				String receiptNo) {
			super();
			this.flatNo = flatNo;
			this.wing = wing;
			this.owner = owner;
			this.openingBalance = openingBalance;
			this.maintenance = maintenance;
			//this.totalOutstandingBalance = totalOutstandingBalance;
			this.penalty = penalty;
			this.receivedMCharge = receivedMCharge;
			//this.remainingBalance = remainingBalance;
			this.date = date;
			this.receiptNo = receiptNo;
			evaluateDyanmic();
		}

		private void evaluateDyanmic() {
			try {
				this.totalOutstandingBalance = String.valueOf(Integer.parseInt(openingBalance) + Integer.parseInt(maintenance));
				this.remainingBalance = String.valueOf(Integer.parseInt(totalOutstandingBalance) + Integer.parseInt(penalty) - Integer.parseInt(receivedMCharge));
			} catch (NumberFormatException e) {
				System.err.println(this);
			}
		}

		public String getFlatNo() {
			return flatNo;
		}

		public void setFlatNo(String flatNo) {
			this.flatNo = flatNo;
		}

		public String getWing() {
			return wing;
		}

		public void setWing(String wing) {
			this.wing = wing;
		}

		public String getOwner() {
			return owner;
		}

		public void setOwner(String owner) {
			this.owner = owner;
		}

		public String getOpeningBalance() {
			return openingBalance;
		}

		public void setOpeningBalance(String openingBalance) {
			this.openingBalance = openingBalance;
		}

		public String getMaintenance() {
			return maintenance;
		}

		public void setMaintenance(String maintenance) {
			this.maintenance = maintenance;
		}

		public String getTotalOutstandingBalance() {
			return totalOutstandingBalance;
		}

		public void setTotalOutstandingBalance(String totalOutstandingBalance) {
			this.totalOutstandingBalance = totalOutstandingBalance;
		}

		public String getPenalty() {
			return penalty;
		}

		public void setPenalty(String penalty) {
			this.penalty = penalty;
		}

		public String getReceivedMCharge() {
			return receivedMCharge;
		}

		public void setReceivedMCharge(String receivedMCharge) {
			this.receivedMCharge = receivedMCharge;
		}

		public String getRemainingBalance() {
			return remainingBalance;
		}

		public void setRemainingBalance(String remainingBalance) {
			this.remainingBalance = remainingBalance;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getReceiptNo() {
			return receiptNo;
		}

		public void setReceiptNo(String receiptNo) {
			this.receiptNo = receiptNo;
		}

		@Override
		public String toString() {
			return "MaintenanceSheet [wing=" + wing +", flatNo=" + flatNo + ", owner=" + owner
					+ ", openingBalance=" + openingBalance + ", maintenance="
					+ maintenance + ", totalOutstandingBalance="
					+ totalOutstandingBalance + ", penalty=" + penalty
					+ ", receivedMCharge=" + receivedMCharge
					+ ", remainingBalance=" + remainingBalance + ", date="
					+ date + ", receiptNo=" + receiptNo + "]";
		}

		@Override
		public int compareTo(Object arg0) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yy");
			Date date1=null, date2=null;
			try {
				if(this.getDate().startsWith("/")) 
					date1 = dateFormat.parse("01"+this.getDate());
				if(((MaintenanceSheet)arg0).getDate().startsWith("/")) 
					date2 = dateFormat.parse("01"+((MaintenanceSheet)arg0).getDate());
				//System.out.println("Date1: " + date1.getDay() + date1.getMonth() + date1.getYear());
				//System.out.println("Date2: " + date2.getDay() + date2.getMonth() + date2.getYear());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date1.compareTo(date2);
		}
		
	}