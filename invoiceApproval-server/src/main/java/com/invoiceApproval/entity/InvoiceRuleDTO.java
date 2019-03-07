package com.invoiceApproval.entity;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.invoiceApproval.Utils.Constants;

public class InvoiceRuleDTO {
	public Integer id;

	@NotEmpty(message="{rule.ruledetails}")
	@Type(type = "com.invoiceApproval.entity.types.JsonType", parameters = {
			@Parameter(name = "classType", value = "com.invoiceApproval.entity.Rule") })
	public Rule rule;

	@NotEmpty(message="Rule status required")
	private String ruleStatus;
	
	private String vendor;
	
	private String mode;

	private Integer orgId;
	
	private String createdBy;

	private Date createdAt;

	private String updatedBy;

	private Date updatedAt;

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
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
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
		invoiceRule.setCreatedAt(invoiceRuleDTO.getCreatedAt() != null ? invoiceRuleDTO.getCreatedAt() : new Date());
		invoiceRule.setCreatedBy(invoiceRuleDTO.getCreatedBy() != null ? invoiceRuleDTO.getCreatedBy() : Constants.SYSTEM);
		invoiceRule.setUpdatedAt(invoiceRuleDTO.getUpdatedAt() != null ? invoiceRuleDTO.getUpdatedAt() : new Date());
		invoiceRule.setUpdatedBy(invoiceRuleDTO.getUpdatedBy() != null ?invoiceRuleDTO.getUpdatedBy() : Constants.SYSTEM);
		
		return invoiceRule;
	}
	
	public InvoiceRuleDTO wrapToInvoiceRuleDTO(InvoiceRule invoiceRule)
	{
		InvoiceRuleDTO invoiceRuleDTO = new InvoiceRuleDTO();
		invoiceRuleDTO.setId(invoiceRule.getId());
		invoiceRuleDTO.setMode(invoiceRule.getMode());
		invoiceRuleDTO.setOrgId(invoiceRule.getOrganization().getOrgId());
		invoiceRuleDTO.setRule(invoiceRule.getRule());
		invoiceRuleDTO.setRuleStatus(invoiceRule.getRuleStatus());
		invoiceRuleDTO.setVendor(invoiceRule.getVendor());
		return invoiceRuleDTO;
	}
	public InvoiceRule wrapForUpdate(InvoiceRuleDTO invoiceRuleDTO, InvoiceRule invoiceRule)
	{
		if(invoiceRuleDTO.getMode()!=null && !"".equals(invoiceRuleDTO.getMode()))
			 invoiceRule.setMode(invoiceRuleDTO.getMode());
		if(invoiceRuleDTO.getRule() != null)
			invoiceRule.setRule(invoiceRuleDTO.getRule());
		if(invoiceRuleDTO.getRuleStatus() != null && !"".equals(invoiceRuleDTO.getRuleStatus()))
			invoiceRule.setRuleStatus(invoiceRuleDTO.getRuleStatus());
		if(invoiceRuleDTO.getVendor() != null && !"".equals(invoiceRuleDTO.getVendor()))
			invoiceRule.setVendor(invoiceRuleDTO.getVendor());
			
		return invoiceRule;
	}
}
