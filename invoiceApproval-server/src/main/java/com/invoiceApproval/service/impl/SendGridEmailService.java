package com.invoiceApproval.service.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoiceApproval.service.EmailService;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Service
public class SendGridEmailService implements EmailService {

	private static final Logger LOGGER = LogManager.getLogger(SendGridEmailService.class);
	
	private SendGrid sendGridClient;
    
    @Autowired
    public SendGridEmailService(SendGrid sendGridClient) {
        this.sendGridClient = sendGridClient;
    }
    
    /**
     * This method is used for Sending Text mail
     * @param from
     * @param to
     * @param subject
     * @param body
     */
    @Override
    public Response sendText(String from, String to, String subject, String body) {
        Response response = sendEmail(from, to, subject, new Content("text/plain", body));
        System.out.println("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: "
                + response.getHeaders());
		return response;
    }
    
    /**
     * This method is used for Sending HTML mail
     * @param from
     * @param to
     * @param subject
     * @param body
     */
    @Override
    public Response sendHTML(String from, String to, String subject, String body) {
    	LOGGER.info("calling ... SendGridEmailService - sendHTML");
        Response response = sendEmail(from, to, subject, new Content("text/html", body));
        LOGGER.info("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: "
                + response.getHeaders());
		return response;
    }
    
    /**
     * This method is used for sending mail
     * @param from
     * @param to
     * @param subject
     * @param content
     * @return
     */
    private Response sendEmail(String from, String to, String subject, Content content) {
        Mail mail = new Mail(new Email(from), subject, new Email(to), content);
        mail.setReplyTo(new Email("no-reply@persistent.com"));
        Request request = new Request();
        Response response = null;
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = this.sendGridClient.api(request);
            LOGGER.info("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: "
                    + response.getHeaders());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }
}
