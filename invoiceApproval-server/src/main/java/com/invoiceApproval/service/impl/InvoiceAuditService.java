package com.invoiceApproval.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoiceApproval.doa.IInvoiceAuditDao;
import com.invoiceApproval.service.IInvoiceAudit;

@Service
public class InvoiceAuditService implements IInvoiceAudit {

	private static final Logger LOGGER = LogManager.getLogger(InvoiceAuditService.class);
	
	@Autowired
	IInvoiceAuditDao ruleAuditDao;

}
