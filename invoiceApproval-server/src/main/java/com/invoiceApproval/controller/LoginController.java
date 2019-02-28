package com.invoiceApproval.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.invoiceApproval.Utils.Messages;
import com.invoiceApproval.entity.ResponseVO;
import com.invoiceApproval.entity.UserDTO;
import com.invoiceApproval.exception.InvoiceApprovalException;
import com.invoiceApproval.service.ILoginService;

/**
 * @author atul_jadhav
 *	
 */
@RestController
public class LoginController {

	private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

	@Autowired
	private ILoginService loginService;
	
	@Autowired
    private Messages messages;

	
	/**
	 * This method called when user login. 
	 * @param userDTO
	 * @return success/failure message 
	 * @throws InvoiceApprovalException 
	 */
	@PostMapping(path="/login")
	public ResponseVO login(@Valid @RequestBody UserDTO userDTO) throws InvoiceApprovalException {
		
		LOGGER.info("Enter LoginController login()");
		ResponseVO responseVO = new ResponseVO();
		try
		{
			if (loginService.validateUser(userDTO.getUsername(), userDTO.getPassword())) 
			{
				responseVO.setMessage(messages.get("user.login.success"));
				return responseVO;
			}
			else
			{
				LOGGER.error("Invalid User / Credential.");
				responseVO.setMessage(messages.get("user.login.failed"));
				responseVO.setErrorMessage(messages.get("user.login.failed.error"));
				return responseVO;
			}
		} 
		catch (Exception e)
		{
			LOGGER.error("Exception is: ",e);
			throw new InvoiceApprovalException("Error while user login : "+e.getMessage());
		}
	}
}
