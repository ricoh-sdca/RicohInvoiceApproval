package com.invoiceApproval.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.ws.Action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;
import org.springframework.beans.factory.annotation.Autowired;

import com.invoiceApproval.Utils.Constants;
import com.invoiceApproval.doa.impl.InvoiceDao;
import com.invoiceApproval.entity.Invoice;
import com.invoiceApproval.entity.RuleDetails;

@Rule(name="level 1",priority=1,description="Invoice Rule Engine")
public class InvoiceRuleEngine {

	private static final Logger logger = LogManager.getLogger(InvoiceRuleEngine.class);
	
	@Autowired 
	private InvoiceDao invoiceDao;
	
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
			invoice.setCurrApprovalLevel(Constants.LEVEL+"-1");
		}
		invoice.setFinalApprovalLevel(level);
		
		// Setting values
		invoice.setCreatedAt(new Date());
		invoice.setCreatedBy(Constants.SYSTEM);
		invoice.setUpdatedAt(new Date());
		invoice.setUpdatedBy(Constants.SYSTEM);
		
		// Save record in Invoice in db
		invoiceDao.saveInvoiceDetails(invoice);
	}
	
	/**
	 * This method is used to check if amount is within range of RULE
	 * @param i
	 * @param minValueInclusive
	 * @param maxValueExclusive
	 * @return
	 */
	public static boolean between(BigDecimal i, BigDecimal minValueInclusive, BigDecimal maxValueExclusive) {
	    if (i.doubleValue() >= minValueInclusive.doubleValue() 
	    		&& i.doubleValue() < maxValueExclusive.doubleValue())
	        return true;
	    else
	        return false;
	}
}
