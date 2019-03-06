package com.invoiceApproval.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoiceApproval.Utils.Messages;
import com.invoiceApproval.doa.impl.InvoiceDao;
import com.invoiceApproval.entity.Invoice;
import com.invoiceApproval.entity.InvoiceRule;
import com.invoiceApproval.entity.RuleDetails;
import com.invoiceApproval.exception.InvoiceApprovalException;
import com.invoiceApproval.service.IInvoiceService;

@Service
public class InvoiceService implements IInvoiceService {

	private static final Logger logger = LogManager.getLogger(InvoiceService.class);
	
	@Autowired 
	private InvoiceDao invoiceDao;
	
	@Autowired
	private InvoiceRuleService invoiceRuleService; 
	
	@Autowired
	private Messages messages;
	
	@Override
	public boolean isAllInvoicesProcessed(Integer orgId) {
		logger.info("Enter isAllInvoicesProcessed() of an InvoiceService");
		return invoiceDao.isAllInvoicesProcessed(orgId);
	}
	
	@Override
	public Invoice saveInvoiceDetails(Invoice invoice) throws InvoiceApprovalException {

		// Validations - Mandatory fields 
		
		logger.info("calling saveInvoiceDetails() of InvoiceService");
		// Validations - Mandatory fields
		boolean isRuleValid = false;
		try {
			// Validation - Rule exists for OrgId?
			Iterable<InvoiceRule> invoiceRules = invoiceRuleService.findAllRulesByOrgId(invoice.getOrganization().getOrgId());
			if(invoiceRules != null) {
				outerloop:
				for (InvoiceRule invoiceRule : invoiceRules)  {
					List<RuleDetails> ruleDetails = invoiceRule.getRule().getRuleDetails();
					for (RuleDetails ruleDetail : ruleDetails)  {
						// Validation - Correct Rule exists ?
						if(1== ruleDetail.getToAmt().compareTo(invoice.getInvoiceAmt().subtract(new BigDecimal(1)))) {
							isRuleValid = true;
							break outerloop;
						}
					}
				}
				if(isRuleValid) {
					// Identify Current and Final Approver using Easy Rule Engine
					invoice.setCreatedBy("system");
					invoice.setCreatedAt(new Date());
					return invoiceDao.saveInvoiceDetails(invoice);
				}
				else {
					throw new InvoiceApprovalException("For this invoice : "+ invoice.getInvoiceNumber()+" there is no valid approval rule present");
				}
			}
			else {
				throw new InvoiceApprovalException("For this invoice : "+ invoice.getInvoiceNumber()+" there is no approval rule present");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(messages.get("invoice.error"),e);
			throw new InvoiceApprovalException(e.getMessage());
		}
	}

}
