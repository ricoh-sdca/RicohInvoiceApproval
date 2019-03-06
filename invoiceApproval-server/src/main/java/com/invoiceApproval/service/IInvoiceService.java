package com.invoiceApproval.service;

import com.invoiceApproval.entity.Invoice;

public interface IInvoiceService {
	
	public boolean isAllInvoicesProcessed(Integer orgId);
	
	public Invoice saveInvoiceDetails(Invoice invoice);
}
