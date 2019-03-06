package com.invoiceApproval.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.invoiceApproval.Utils.Constants;
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
@SessionAttributes("user")
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
	public ModelMap login(@Valid @RequestBody UserDTO userDTO) throws InvoiceApprovalException {
		
		LOGGER.info("Enter LoginController login()");
		ResponseVO responseVO = null;
		ModelMap model = new ModelMap();
		try
		{
			model.addAttribute("user", userDTO.getUsername());
			if (loginService.validateUser(userDTO.getUsername(), userDTO.getPassword())) 
			{
				responseVO = new ResponseVO(Constants.SUCCESS, messages.get("user.login.success"), null);
				model.addAttribute("response", responseVO);
				return model;
			}
			else
			{
				LOGGER.error("Invalid User / Credential.");
				responseVO = new ResponseVO(Constants.FAILED,messages.get("user.login.failed"),messages.get("user.login.failed.error"));
				model.addAttribute("response", responseVO);
				return model;
			}
		} 
		catch (Exception e)
		{
			LOGGER.error("Exception is: ",e);
			throw new InvoiceApprovalException("Error while user login : "+e.getMessage());
		}
	}
}
