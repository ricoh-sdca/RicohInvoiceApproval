package com.invoiceApproval.doa.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.invoiceApproval.Utils.Constants;
import com.invoiceApproval.doa.IUserDoa;
import com.invoiceApproval.entity.User;
import com.invoiceApproval.repository.UserRepository;

@Repository
@Transactional
public class UserDoa implements IUserDoa{

	private static final Logger LOGGER = LogManager.getLogger(UserDoa.class);
	
	@Autowired 
	UserRepository repository;
	
	@Override
	public List<User> getUsersByApprovalLevel(String approverLevel) {
		LOGGER.info("calling getUsersByApprovalLevel - "+approverLevel);
		return repository.getUsersByApprovalLevel(approverLevel, Constants.ACTIVE);
	}

	@Override
	public User getUserByName(String userName) {
		LOGGER.info("calling getUserByName() of UserDoa");
		return repository.getUserByName(userName, Constants.ACTIVE);
	}

}
