package com.invoiceApproval.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.invoiceApproval.entity.ResponseVO;
import com.invoiceApproval.entity.UserDTO;
import com.invoiceApproval.exception.UserNotFoundException;
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

	
	/**
	 * This method called when user try to login. 
	 * @param userDTO
	 * @return success/failure message 
	 */
	@PostMapping(path="/login")
	public ResponseVO login(@Valid @RequestBody UserDTO userDTO) {
		
		LOGGER.info("Enter LoginController login()");
		
		ResponseVO responseVO = new ResponseVO();
		if (loginService.validateUser(userDTO.getUsername(), userDTO.getPassword())) {
			responseVO.setMessage("Login successfull");
			return responseVO;
		} else {
			LOGGER.error("Invalid user credential.");
			throw new UserNotFoundException("Invalid credential - user not found.");
		}
	}
}
