package com.invoiceApproval.doa;

import java.util.List;

import com.invoiceApproval.entity.User;

public interface IUserDoa {

	public List<User> getUsersByApprovalLevel(String approverLevel);
	public User getUserByName(String userName);
}
