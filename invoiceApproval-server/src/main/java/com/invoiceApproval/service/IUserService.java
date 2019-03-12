package com.invoiceApproval.service;

import java.util.List;

import com.invoiceApproval.entity.User;
import com.invoiceApproval.exception.InvoiceApprovalException;

public interface IUserService {

	public List<User> getUsersByApprovalLevel(String approverLevel);
	public User getUserByName(String userName) throws InvoiceApprovalException;
}
