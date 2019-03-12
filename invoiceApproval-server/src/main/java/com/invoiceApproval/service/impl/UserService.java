package com.invoiceApproval.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoiceApproval.doa.impl.UserDoa;
import com.invoiceApproval.entity.User;
import com.invoiceApproval.service.IUserService;

@Service
public class UserService implements IUserService{

	@Autowired
	private UserDoa userDoa;
	
	@Override
	public List<User> getUsersByApprovalLevel(String approverLevel) {
		userDoa.getUsersByApprovalLevel(approverLevel);
		return null;
	}

}
