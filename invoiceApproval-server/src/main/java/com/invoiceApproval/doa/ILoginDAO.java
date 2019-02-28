package com.invoiceApproval.doa;

import com.invoiceApproval.exception.InvoiceApprovalException;

public interface ILoginDAO{
	
	public boolean validateUser(String userName,String password) throws InvoiceApprovalException;
}
