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
		private String description = ""; 
		private String paymentMode = "Cash"; 
		
		public MaintenanceSheet(String flatNo, String wing, String owner,
				String openingBalance, String maintenance,
				String totalOutstandingBalance, String penalty,
				String receivedMCharge, String remainingBalance, String date,
				String receiptNo, String paymentMode) {
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
			if(date.contains(";@")) {
				date = date.replace(";@", "");
			}
			this.date = date;
			this.receiptNo = receiptNo;
			if(paymentMode != null){
				this.setPaymentMode(paymentMode);
			}
			evaluateDyanmic();
			computeDescription();
		}

		private void computeDescription() {
			/*if(this.receivedMCharge != null && Integer.parseInt(this.receivedMCharge) > 0 ) {
				setDescription("By Cash");
				if(Integer.parseInt(this.receivedMCharge) > 2000)
					System.out.println("#### Integer.parseInt(receivedMCharge)" + Integer.parseInt(this.receivedMCharge) + "(-1 * Integer.parseInt(remainingBalance) )" + (-1 * Integer.parseInt(this.remainingBalance) ));
				if(this.remainingBalance != null && Integer.parseInt(this.remainingBalance) < 0 && Integer.parseInt(this.receivedMCharge) == (-1 * Integer.parseInt(this.remainingBalance) )) {
					setDescription("By Cheque");
				}
			}*/
			
			switch (getPaymentMode()) {
			case "Cheque":
				setDescription("By " + getPaymentMode());
				break;

			default:
				setDescription("By " + getPaymentMode());
				break;
			}

			
			if(this.penalty != null && Integer.parseInt(this.penalty) > 0 ) {
				setDescription("Penalty");
			}
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

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getPaymentMode() {
			return paymentMode;
		}

		public void setPaymentMode(String paymentMode) {
			this.paymentMode = paymentMode;
		}

		@Override
		public String toString() {
			return "MaintenanceSheet [wing=" + wing +", flatNo=" + flatNo + ", owner=" + owner
					+ ", openingBalance=" + openingBalance + ", maintenance="
					+ maintenance + ", totalOutstandingBalance="
					+ totalOutstandingBalance + ", penalty=" + penalty
					+ ", receivedMCharge=" + receivedMCharge
					+ ", remainingBalance=" + remainingBalance + ", date="
					+ date + ", receiptNo=" + receiptNo + ", description=" + getDescription() 
					+ ", paymentMode=" + getPaymentMode() + "]";
		}

		@Override
		public int compareTo(Object arg0) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
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