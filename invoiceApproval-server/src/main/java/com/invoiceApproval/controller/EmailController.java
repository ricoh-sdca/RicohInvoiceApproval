package com.invoiceApproval.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoiceApproval.service.impl.SendGridEmailService;

@RestController
public class EmailController {
	
	private static final Logger LOGGER = LogManager.getLogger(EmailController.class);

	@Autowired 
	SendGridEmailService sendGridEmailService;
	
	@RequestMapping(value = "/sendemail")
    public void sendMail() throws IOException, URISyntaxException {
		LOGGER.info("Calling sendmail here...");
        sendGridEmailService.sendHTML("test@persistent.com", "aditya_thote@persistent.com", "Hello World", "Hello, <strong>how are you doing?</strong>");
        LOGGER.info("Existing sendmail here...");
    }
}
