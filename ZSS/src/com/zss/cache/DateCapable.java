package com.zss.cache;

import java.math.BigDecimal;
import java.util.Date;

public interface DateCapable {

	Date getDate();
	
	void setDate(Date date);

	BigDecimal getAmount();
}
