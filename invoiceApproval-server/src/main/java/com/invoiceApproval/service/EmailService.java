package com.invoiceApproval.service;

import com.sendgrid.Response;

public interface EmailService {

	Response sendText(String from, String to, String subject, String body);

	Response sendHTML(String from, String to, String subject, String body);
}