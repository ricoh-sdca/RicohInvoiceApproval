package com.invoiceApproval.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class RuleDetails {

	public String currency;
	public BigDecimal fromAmt;
	public BigDecimal toAmt;
	public List<String> level;
	public Date updatedAt;
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public List<String> getLevel() {
		return level;
	}
	public void setLevel(List<String> level) {
		this.level = level;
	}
	
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public BigDecimal getFromAmt() {
		return fromAmt;
	}
	public void setFromAmt(BigDecimal fromAmt) {
		this.fromAmt = fromAmt;
	}
	public BigDecimal getToAmt() {
		return toAmt;
	}
	public void setToAmt(BigDecimal toAmt) {
		this.toAmt = toAmt;
	}
	public RuleDetails(String currency, BigDecimal fromAmt, BigDecimal toAmt, List<String> level, Date updatedAt) {
		super();
		this.currency = currency;
		this.fromAmt = fromAmt;
		this.toAmt = toAmt;
		this.level = level;
		this.updatedAt = updatedAt;
	}
	public RuleDetails() {
		super();
	}
}
