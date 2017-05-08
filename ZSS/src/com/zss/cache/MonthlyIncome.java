package com.zss.cache;

import java.math.BigDecimal;
import java.util.Date;

public class MonthlyIncome implements DateCapable {
	
	private String paymentMode;
	private BigDecimal amount = BigDecimal.ZERO;
	private Date date;
	private String flatNo;
	
	@Override
	public String toString() {
		return "MonthlyCashFlow [paymentMode=" + paymentMode + ", amount=" + amount + ", date="
				+ date + ", flatNo=" + flatNo + "]";
	}

	public String getPaymentMode() {
		return paymentMode;
	}
	
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date collectionDate) {
		this.date = collectionDate;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}
}