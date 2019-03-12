package com.invoiceApproval.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoiceApproval.Utils.Messages;
import com.invoiceApproval.doa.impl.UserDoa;
import com.invoiceApproval.entity.User;
import com.invoiceApproval.exception.InvoiceApprovalException;
import com.invoiceApproval.service.IUserService;

@Service
public class UserService implements IUserService{

	private static final Logger logger = LogManager.getLogger(UserService.class);
	
	@Autowired
	private UserDoa userDoa;
	
	@Autowired
	Messages messages;
	
	@Override
	public List<User> getUsersByApprovalLevel(String approverLevel) {
		logger.info("Enter getUsersByApprovalLevel() of UserService");
		userDoa.getUsersByApprovalLevel(approverLevel);
		return null;
	}

	/**
	 *This method return user entity base on username and userstatus = 'ACTIVE'
	 *@param String
	 *@return Object User
	 * @throws InvoiceApprovalException 
	 */
	@Override
	public User getUserByName(String userName) throws InvoiceApprovalException {
		logger.info("Enter getUserByName() of UserService");
		try {
			User user = userDoa.getUserByName(userName);
			if(user != null) {
				return user;
			}else {
				throw new InvoiceApprovalException(messages.get("user.invalid"));
			}
		} catch (Exception e) {
			logger.error(messages.get("common.error"),e);
			throw new InvoiceApprovalException(messages.get("user.invalid"));
		}
	}

}
