package com.zss.cache;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import com.zss.utility.ZSSCache;

public class BankTransaction implements DateCapable{

	@Override
	public String toString() {
		return "BankTransaction [date=" + ((date == null) ? null : ZSSCache.dateFormat.format(date)) + ", description=" + description 
				+ ", chequeNumber=" + chequeNumber
				+ ", mode=" + mode + ", withdrawal=" + getFormattedCurrency(withdrawal) + ", credit=" + getFormattedCurrency(credit)
				+ ", balance=" + getFormattedCurrency(balance) + "]";
	}
	
	String getFormattedCurrency(BigDecimal amount) {
		return NumberFormat.getInstance(new Locale("en", "in")).format(amount);
	}

	private Date date;
	private String description;
	private String chequeNumber;
	private String mode;
	private BigDecimal withdrawal = BigDecimal.ZERO;
	private BigDecimal credit = BigDecimal.ZERO;
	private BigDecimal balance = BigDecimal.ZERO;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public BigDecimal getWithdrawal() {
		return withdrawal;
	}

	public void setWithdrawal(BigDecimal withdrawal) {
		this.withdrawal = withdrawal;
	}

	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal openingBalance) {
		this.balance = openingBalance.add(credit).subtract(withdrawal);
	}

	@Override
	public BigDecimal getAmount() {
		return getCredit();
	}
}
