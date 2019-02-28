package com.invoiceApproval.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoiceApproval.doa.IInvoiceDao;
import com.invoiceApproval.service.IInvoiceService;

@Service
public class InvoiceService implements IInvoiceService {

	private static final Logger logger = LogManager.getLogger(InvoiceService.class);
	
	@Autowired 
	private IInvoiceDao invoiceDao;
	
	@Override
	public boolean isAllInvoicesProcessed(Integer orgId) {
		logger.info("Enter isAllInvoicesProcessed() of an InvoiceService");
		return invoiceDao.isAllInvoicesProcessed(orgId);
	}

}
