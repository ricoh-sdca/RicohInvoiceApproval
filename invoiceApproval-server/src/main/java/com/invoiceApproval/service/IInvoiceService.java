package com.invoiceApproval.service;

import java.util.List;

import com.invoiceApproval.entity.Invoice;
import com.invoiceApproval.entity.InvoiceDTO;
import com.invoiceApproval.entity.ResponseVO;
import com.invoiceApproval.exception.InvoiceApprovalException;

public interface IInvoiceService {
	
	public boolean isAllInvoicesProcessed(Integer orgId,String invoiceStatus);
	public ResponseVO saveInvoiceDetails(Invoice invoice) throws InvoiceApprovalException;
	List<InvoiceDTO> getAllInvoices(String userName,String invoiceStatus) throws InvoiceApprovalException;
	public Invoice getPendingInvoiceById(String invoiceNumber) throws InvoiceApprovalException;
	ResponseVO approveInvoice(InvoiceDTO invoiceDTO) throws InvoiceApprovalException;
	ResponseVO rejectInvoice(InvoiceDTO invoiceDTO) throws InvoiceApprovalException;
	
}
