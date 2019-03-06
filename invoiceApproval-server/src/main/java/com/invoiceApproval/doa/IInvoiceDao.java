package com.invoiceApproval.doa;

import com.invoiceApproval.entity.Invoice;

public interface IInvoiceDao {
	
	public boolean isAllInvoicesProcessed(Integer orgId);

	public Invoice saveInvoiceDetails(Invoice invoice);
}
