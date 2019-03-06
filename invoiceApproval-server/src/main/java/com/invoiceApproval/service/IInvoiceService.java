package com.invoiceApproval.service;

import com.invoiceApproval.entity.Invoice;
import com.invoiceApproval.exception.InvoiceApprovalException;

public interface IInvoiceService {
	
	public boolean isAllInvoicesProcessed(Integer orgId);
	public Invoice saveInvoiceDetails(Invoice invoice) throws InvoiceApprovalException;
}
