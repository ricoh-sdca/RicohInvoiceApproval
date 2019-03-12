package com.invoiceApproval.service;

import org.springframework.ui.ModelMap;

import com.invoiceApproval.entity.Invoice;
import com.invoiceApproval.entity.ResponseVO;
import com.invoiceApproval.exception.InvoiceApprovalException;

public interface IInvoiceService {
	
	public boolean isAllInvoicesProcessed(Integer orgId,String invoiceStatus);
	public ResponseVO saveInvoiceDetails(Invoice invoice) throws InvoiceApprovalException;
	ModelMap getAllInvoices(String userName,String invoiceStatus) throws InvoiceApprovalException;
}
