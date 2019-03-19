package com.invoiceApproval.doa;

import java.util.List;

import com.invoiceApproval.entity.Invoice;
import com.invoiceApproval.entity.User;

public interface IInvoiceDao {
	
	public boolean isAllInvoicesProcessed(Integer orgId,String invoiceStatus);

	public Invoice saveInvoiceDetails(Invoice invoice);

	List<Invoice> getAllInvoices(User user,String invoiceStatus);
	
	public Invoice getPendingInvoiceById(String invoiceNumber);
}
