package com.invoiceApproval.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoiceApproval.doa.impl.InvoiceDao;
import com.invoiceApproval.entity.Invoice;
import com.invoiceApproval.service.IInvoiceService;

@Service
public class InvoiceService implements IInvoiceService {

	private static final Logger logger = LogManager.getLogger(InvoiceService.class);
	
	@Autowired 
	private InvoiceDao invoiceDao;
	
	@Override
	public boolean isAllInvoicesProcessed(Integer orgId) {
		logger.info("Enter isAllInvoicesProcessed() of an InvoiceService");
		return invoiceDao.isAllInvoicesProcessed(orgId);
	}

	@Override
	public Invoice saveInvoiceDetails(Invoice invoice) {

		// Validations - Mandatory fields 
		
		// Validation - Rule exists for OrgId?
		
		// Validation - Correct Rule exists ?
		
		// Identify Current and Final Approver using Easy Rule Engine
		
		// IF ALL Validation - OK, then save
		
		invoiceDao.saveInvoiceDetails(invoice);
		
		return null;
	}

}
