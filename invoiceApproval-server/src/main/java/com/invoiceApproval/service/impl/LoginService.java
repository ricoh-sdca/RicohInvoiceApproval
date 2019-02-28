package com.invoiceApproval.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoiceApproval.doa.ILoginDAO;
import com.invoiceApproval.exception.InvoiceApprovalException;
import com.invoiceApproval.service.ILoginService;

@Service
public class LoginService implements ILoginService  {

	private static final Logger logger = LogManager.getLogger(LoginService.class);

	@Autowired
	private ILoginDAO loginDao;
	
	/**
	 * This method checks if user is valid or not by comparing credentials with database.
	 * @return boolean
	 * @param userName,password
	 * @throws InvoiceApprovalException 
	 */
	@Override
	public boolean validateUser(String userName, String password) throws InvoiceApprovalException{
		logger.info("Enter LoginService validateUser()");
		return loginDao.validateUser(userName, password);
	}
	
}
