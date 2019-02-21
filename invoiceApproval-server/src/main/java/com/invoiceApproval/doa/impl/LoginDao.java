package com.invoiceApproval.doa.impl;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.invoiceApproval.doa.ILoginDAO;
import com.invoiceApproval.entity.User;
import com.invoiceApproval.repository.UserRepository;

@Repository
@Transactional
public class LoginDao implements ILoginDAO {

	private static final Logger LOGGER = LogManager.getLogger(LoginDao.class);
			
	@Autowired 
	UserRepository repository;

	public boolean validateUser(String userName,String password) {
		LOGGER.info("Enter LoginDao validateUser()");
		User user = repository.findUserByCredential(userName, password);
		return user != null ? true : false;
	}
}
