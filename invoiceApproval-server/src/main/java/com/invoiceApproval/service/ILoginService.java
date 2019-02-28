package com.invoiceApproval.service;

import com.invoiceApproval.exception.InvoiceApprovalException;

public interface ILoginService {
	
	public boolean validateUser(String userName,String password) throws InvoiceApprovalException;
}
