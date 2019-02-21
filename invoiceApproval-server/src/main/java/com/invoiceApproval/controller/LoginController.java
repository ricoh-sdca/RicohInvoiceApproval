package com.invoiceApproval.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoiceApproval.entity.UserDTO;
import com.invoiceApproval.service.ILoginService;

@RestController
@RequestMapping(path = "/login")
public class LoginController {
	
	private static final Logger LOGGER = LogManager.getLogger(LoginController.class);
	
	@Autowired
	private ILoginService loginService;
	
	@PostMapping
	public ResponseEntity<String> login(@Valid @RequestBody UserDTO userDTO)
	{
		LOGGER.info("Enter LoginController login()");
		if(loginService.validateUser(userDTO.getUsername(),userDTO.getPassword()))
			return new ResponseEntity<String>("Login successfull", HttpStatus.OK);
		else
			return new ResponseEntity<String>("Invalid User..", HttpStatus.BAD_REQUEST);
	}
}
