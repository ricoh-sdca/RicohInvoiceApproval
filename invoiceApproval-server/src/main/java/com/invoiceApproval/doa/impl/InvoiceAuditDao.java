package com.invoiceApproval.doa.impl;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.invoiceApproval.doa.IInvoiceAuditDao;
import com.invoiceApproval.entity.InvoiceAudit;
import com.invoiceApproval.repository.InvoiceAuditRepository;

@Repository
@Transactional
public class InvoiceAuditDao implements IInvoiceAuditDao {

	private static final Logger LOGGER = LogManager.getLogger(InvoiceAuditDao.class);
	
	@Autowired
	InvoiceAuditRepository auditRepository;

	/** 
	 * This method save InvoiceAudit entity into database.
	 * @return InvoiceAudit
	 * @param InvoiceAudit
	 */
	@Override
	public InvoiceAudit save(InvoiceAudit ruleAudit) {
		LOGGER.info("Enter save() of InvoiceRuleAuditDao");
		return auditRepository.save(ruleAudit);
	}

}
