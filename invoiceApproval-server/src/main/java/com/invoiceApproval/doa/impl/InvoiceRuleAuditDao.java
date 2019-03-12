package com.invoiceApproval.doa.impl;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.invoiceApproval.doa.IInvoiceRuleAuditDao;
import com.invoiceApproval.entity.InvoiceRuleAudit;
import com.invoiceApproval.repository.InvoiceRuleAuditRepository;

@Repository
@Transactional
public class InvoiceRuleAuditDao implements IInvoiceRuleAuditDao {

	private static final Logger LOGGER = LogManager.getLogger(InvoiceRuleAuditDao.class);
	
	@Autowired
	InvoiceRuleAuditRepository auditRepository;

	@Override
	public InvoiceRuleAudit save(InvoiceRuleAudit ruleAudit) {
		LOGGER.info("Enter save() of InvoiceRuleAuditDao");
		return auditRepository.save(ruleAudit);
	}
}
