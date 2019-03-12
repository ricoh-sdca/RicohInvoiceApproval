package com.invoiceApproval.service.impl;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoiceApproval.doa.IInvoiceRuleAuditDao;
import com.invoiceApproval.entity.InvoiceRule;
import com.invoiceApproval.entity.InvoiceRuleAudit;
import com.invoiceApproval.service.IInvoiceRuleAuditService;

@Service
public class InvoiceAuditService implements IInvoiceRuleAuditService {

	private static final Logger LOGGER = LogManager.getLogger(InvoiceAuditService.class);
	
	@Autowired
	IInvoiceRuleAuditDao ruleAuditDao;

	@Override
	public InvoiceRuleAudit createRuleAudit(InvoiceRule invoiceRule) {
		LOGGER.info("Enter createRuleAudit() of InvoiceAuditService");
		InvoiceRuleAudit ruleAudit = new InvoiceRuleAudit();
		ruleAudit.setApprovalRule(invoiceRule);
		ruleAudit.setRule(invoiceRule.getRule());
		ruleAudit.setCreatedBy("System"); // Need to change set current login user
		return ruleAuditDao.save(ruleAudit);
	}
}
