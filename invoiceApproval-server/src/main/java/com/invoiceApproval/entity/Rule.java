package com.invoiceApproval.entity;

import java.util.List;

public class Rule {

	public String type;
	public List<RuleDetails> ruleDetails;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<RuleDetails> getRuleDetails() {
		return ruleDetails;
	}
	public void setRuleDetails(List<RuleDetails> ruleDetails) {
		this.ruleDetails = ruleDetails;
	}
	
	
	
}

