package com.invoiceApproval.entity;

import java.util.List;

public class RuleDetails {

	public String currency;
	public int fromAmt;
	public int toAmt;
	public List<String> level;
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public int getFromAmt() {
		return fromAmt;
	}
	public void setFromAmt(int fromAmt) {
		this.fromAmt = fromAmt;
	}
	public int getToAmt() {
		return toAmt;
	}
	public void setToAmt(int toAmt) {
		this.toAmt = toAmt;
	}
	public List<String> getLevel() {
		return level;
	}
	public void setLevel(List<String> level) {
		this.level = level;
	}
	public RuleDetails(String currency, int fromAmt, int toAmt, List<String> level) {
		super();
		this.currency = currency;
		this.fromAmt = fromAmt;
		this.toAmt = toAmt;
		this.level = level;
	}
	public RuleDetails() {
		super();
	}
}
