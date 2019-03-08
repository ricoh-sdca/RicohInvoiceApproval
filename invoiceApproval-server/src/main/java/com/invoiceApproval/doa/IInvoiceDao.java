package com.invoiceApproval.doa;

import com.invoiceApproval.entity.Invoice;

public interface IInvoiceDao {
	
	public boolean isAllInvoicesProcessed(Integer orgId,String invoiceStatus);

	public Invoice saveInvoiceDetails(Invoice invoice);
}
