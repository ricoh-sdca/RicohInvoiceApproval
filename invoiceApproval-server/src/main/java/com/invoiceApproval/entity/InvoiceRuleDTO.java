package com.invoiceApproval.entity;

import java.util.Date;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.invoiceApproval.Utils.Constants;

public class InvoiceRuleDTO {
	public Integer id;

	@Type(type = "com.invoiceApproval.entity.types.JsonType", parameters = {
			@Parameter(name = "classType", value = "com.invoiceApproval.entity.Rule") })
	public Rule rule;

	private String ruleStatus;
	
	private String vendor;
	
	private String mode;

	private Integer orgId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public String getRuleStatus() {
		return ruleStatus;
	}

	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	
	public InvoiceRule wrapper(InvoiceRuleDTO invoiceRuleDTO) {
		InvoiceRule invoiceRule = new InvoiceRule();
		// Status
		invoiceRule.setRuleStatus(invoiceRuleDTO.getRuleStatus());
		// Org
		Organization orgObj = new Organization();
		orgObj.setOrgId(invoiceRuleDTO.getOrgId());
		invoiceRule.setOrganization(orgObj);
		
		// Rule
		invoiceRule.setRule(invoiceRuleDTO.getRule());
		
		// Created and Updated by / at
		invoiceRule.setCreatedAt(new Date());
		invoiceRule.setCreatedBy(Constants.SYSTEM);
		invoiceRule.setUpdatedAt(new Date());
		invoiceRule.setUpdatedBy(Constants.SYSTEM);
		
		return invoiceRule;
	}
	
}
