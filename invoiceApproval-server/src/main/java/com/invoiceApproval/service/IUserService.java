package com.invoiceApproval.service;

import java.util.List;

import com.invoiceApproval.entity.User;

public interface IUserService {

	public List<User> getUsersByApprovalLevel(String approverLevel);
}
