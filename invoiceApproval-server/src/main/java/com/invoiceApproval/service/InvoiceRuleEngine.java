package com.invoiceApproval.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.invoiceApproval.Utils.Constants;
import com.invoiceApproval.Utils.Messages;
import com.invoiceApproval.doa.impl.InvoiceDao;
import com.invoiceApproval.entity.Invoice;
import com.invoiceApproval.entity.RuleDetails;
import com.invoiceApproval.entity.User;
import com.invoiceApproval.service.impl.SendGridEmailService;
import com.invoiceApproval.service.impl.UserService;
import com.sendgrid.Response;

@Component
@Rule(name="level 1",priority=1,description="Invoice Rule Engine")
public class InvoiceRuleEngine {

	private static final Logger logger = LogManager.getLogger(InvoiceRuleEngine.class);
	
	@Autowired 
	private InvoiceDao invoiceDao;
	
	@Autowired
	private SendGridEmailService sendGridEmailService; 
	
	@Autowired
	private Messages messages;
	
	@Autowired
	private UserService userService;
	
	public static String level = null;
	
	/**
	 * This method is used to check if Invoice Amount is within Rules defined by admin
	 * @param ruleDetailsList
	 * @param amount
	 * @return
	 */
	@Condition
	public boolean checkApprovalByAmout(@Fact("ruleDetails") List<RuleDetails> ruleDetailsList,@Fact("amount") BigDecimal amount)
	{
		for (int i = 0; i < ruleDetailsList.size(); i++) {
			RuleDetails obj = ruleDetailsList.get(i);
			if(between(amount,obj.getFromAmt(),obj.getToAmt())) {
				logger.info("Level >> "+obj.getLevel().get(0)+" for amount = "+amount);
				level = obj.getLevel().get(0);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method is used execute ACTION if checkApprovalByAmout returns TRUE
	 * @param facts
	 */
	@Action
	public void approveInvoice(Facts facts )
	{
		BigDecimal amount = facts.get("amount");
		Invoice invoice = facts.get("invoice");
		String ruleMode = facts.get("mode");
		
		// Setting LEVEL
		logger.info("Amount : "+amount+" Level "+level+" Approved...");
		if(ruleMode.equalsIgnoreCase(Constants.TOP)) {
			invoice.setCurrApprovalLevel(level);
		}else {
			invoice.setCurrApprovalLevel(Constants.LEVEL1);
		}
		invoice.setFinalApprovalLevel(level);
		
		// Setting values
		invoice.setCreatedAt(new Date());
		invoice.setCreatedBy(Constants.SYSTEM);
		invoice.setUpdatedAt(new Date());
		invoice.setUpdatedBy(Constants.SYSTEM);
		
		// Save record in Invoice in db
		logger.info("Saving Invoice Record to DB");
		Invoice newInvoice = invoiceDao.saveInvoiceDetails(invoice);

		// Get users with current_approver level
		logger.info("Getting list of all users with CurrApprovalLevel - "+newInvoice.getCurrApprovalLevel());
		List<User> userList = userService.getUsersByApprovalLevel(newInvoice.getCurrApprovalLevel());
		
		// Send E-mail to all users with current approver
		if(userList != null) {
			logger.info("Sending e-mail to #users ="+userList.size());
			for (int i = 0; i < userList.size(); i++) {
				User userObj = userList.get(i);
				Response response =  sendGridEmailService.sendHTML(messages.get("email.from"), 
						userObj.getEmailId(),
						messages.get("email.subject"), 
						messages.get("email.body"));
				if(response.getStatusCode() == Constants.ACCEPTED) {
					logger.info(
							"E-mail notification is being successfully accepted by SendGrid for processing for user ="
									+ userObj.getFirstName() + " " + userObj.getLastName());
				}else {
					logger.error("Error : E-mail status code other than 202, response Code ="+response.getStatusCode());
				}
			}
		}else {
			logger.warn("No users with approval level "+newInvoice.getCurrApprovalLevel()+" found, thus emails NOT sent");
		}
	}
	
	/**
	 * This method is used to check if amount is within range of RULE
	 * @param i
	 * @param minValueInclusive
	 * @param maxValueExclusive
	 * @return
	 */
	public boolean between(BigDecimal i, BigDecimal minValueInclusive, BigDecimal maxValueExclusive) {
	    if (i.doubleValue() >= minValueInclusive.doubleValue() 
	    		&& i.doubleValue() < maxValueExclusive.doubleValue())
	        return true;
	    else
	        return false;
	}
}
