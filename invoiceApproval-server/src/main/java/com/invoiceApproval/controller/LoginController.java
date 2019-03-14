package com.invoiceApproval.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.invoiceApproval.Utils.Constants;
import com.invoiceApproval.Utils.Messages;
import com.invoiceApproval.entity.ResponseVO;
import com.invoiceApproval.entity.User;
import com.invoiceApproval.entity.UserDTO;
import com.invoiceApproval.exception.InvoiceApprovalException;
import com.invoiceApproval.service.ILoginService;

/**
 * @author atul_jadhav
 * 
 */
//@SessionAttributes("userDTO")
@RestController
@CrossOrigin(origins="http://localhost:4200")
public class LoginController {

	private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

	@Autowired
	private ILoginService loginService;

	@Autowired
	private Messages messages;
	
	//@ModelAttribute("userDTO")
	UserDTO getUser() {
		System.out.println("#########################");
	  return new UserDTO();
	}

	/**
	 * This method called when user login.
	 * 
	 * @param userDTO
	 * @return success/failure message
	 * @throws InvoiceApprovalException
	 */
	@PostMapping(path = "/login")
	public ResponseVO login(@Valid @RequestBody UserDTO userDTO) throws InvoiceApprovalException {

		LOGGER.info("Enter LoginController login()");
		ResponseVO responseVO = null;
		ModelMap model = new ModelMap();
		try {
			model.addAttribute("user", userDTO.getUsername());
			if (loginService.validateUser(userDTO.getUsername(), userDTO.getPassword())) {
				responseVO = new ResponseVO(Constants.SUCCESS, messages.get("user.login.success"), null);
				model.addAttribute("response", responseVO);
				return responseVO;
			} else {
				LOGGER.error("Invalid User / Credential.");
				responseVO = new ResponseVO(Constants.FAILED, null, messages.get("user.login.failed.error"));
				model.addAttribute("response", responseVO);
				return responseVO;
			}
		} catch (Exception e) {
			LOGGER.error("Exception is: ", e);
			throw new InvoiceApprovalException(messages.get("login.error") + e.getMessage());
		}
	}
}
