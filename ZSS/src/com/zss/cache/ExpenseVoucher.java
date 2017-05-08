package com.zss.cache;

import java.math.BigDecimal;
import java.util.Date;

public class ExpenseVoucher implements DateCapable {

	private int voucherNumber;
	private Date date;
	private String paidTo;
	private String description;
	private String debitType;
	private String paymentMode;
	private BigDecimal amount;
	private String type;
	
	
	public ExpenseVoucher() {
	}

	@Override
	public String toString() {
		return "ExpenseVoucher [voucherNumber=" + voucherNumber + ", date=" + date + ", paidTo=" + paidTo
				+ ", description=" + description + ", debitType=" + debitType + ", paymentMode=" + paymentMode
				+ ", amount=" + amount + ", type=" + getType() + "]";
	}

	public int getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(int voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPaidTo() {
		return paidTo;
	}

	public void setPaidTo(String paidTo) {
		this.paidTo = paidTo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDebitType() {
		return debitType;
	}

	public void setDebitType(String debitType) {
		this.debitType = debitType;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
