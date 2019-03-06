package com.invoiceApproval.service.impl;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoiceApproval.Utils.Constants;
import com.invoiceApproval.Utils.Messages;
import com.invoiceApproval.doa.impl.InvoiceDao;
import com.invoiceApproval.entity.Invoice;
import com.invoiceApproval.entity.InvoiceRule;
import com.invoiceApproval.entity.ResponseVO;
import com.invoiceApproval.exception.InvoiceApprovalException;
import com.invoiceApproval.service.IInvoiceService;
import com.invoiceApproval.service.InvoiceRuleEngine;

@Service
public class InvoiceService implements IInvoiceService {

	private static final Logger logger = LogManager.getLogger(InvoiceService.class);
	
	@Autowired 
	private InvoiceDao invoiceDao;
	
	@Autowired
	private InvoiceRuleService invoiceRuleService; 
	
	@Autowired
	private Messages messages;
	
	@Autowired
	private InvoiceRuleEngine invoiceRuleEngine; 
	
	/**
	 * This method is used to verify of All invoices are processed (i.e. APPROVED / REJECTED)
	 * @param orgId
	 */
	@Override
	public boolean isAllInvoicesProcessed(Integer orgId) {
		logger.info("Enter isAllInvoicesProcessed() of an InvoiceService");
		return invoiceDao.isAllInvoicesProcessed(orgId);
	}
	
	/**
	 * This is method is used to store Invoice from Ricoh APS Account
	 * @param invoice
	 */
	@Override
	public ResponseVO saveInvoiceDetails(Invoice invoice) throws InvoiceApprovalException {

		// Validations - Mandatory fields 
		logger.info("calling saveInvoiceDetails() of InvoiceService");
		try {
			//Saving status of Invoice to Constants.PENDING
			invoice.setInvoiceStatus(Constants.PENDING);
			
			// Validation - Rule exists for OrgId?
			Iterable<InvoiceRule> invoiceRules = invoiceRuleService.findAllRulesByOrgId(invoice.getOrganization().getOrgId());
			if(invoiceRules != null) {
				InvoiceRule invoiceRule = invoiceRules.iterator().next();
				
				// Identify Current and Final Approver using Easy Rule Engine
				Facts facts = new Facts();
				facts.put("invoice",invoice);
				facts.put("mode",invoiceRule.getMode());
				facts.put("ruleDetails", invoiceRule.getRule().getRuleDetails());
				facts.put("amount", invoice.getInvoiceAmt());
				
				Rules rules = new Rules();
				rules.register(invoiceRuleEngine);
				
				RulesEngine engine = new DefaultRulesEngine();
				
				//Validate if Engine Rule returns TRUE or FALSE
				Map<org.jeasy.rules.api.Rule, Boolean> map = engine.check(rules, facts);
				if(map.get(RuleProxy.asRule(invoiceRuleEngine))) {
					engine.fire(rules, facts);
				}else {
					logger.error(messages.get("invoice.rule.mismatch"));
					return new ResponseVO(Constants.FAILED, null,messages.get("invoice.rule.mismatch"));
				}
			}else {
				return new ResponseVO(Constants.FAILED, null,messages.get("invoice.rule.mismatch"));
			}
		}
		catch (Exception e) {
			logger.error(messages.get("invoice.error"),e);
			throw new InvoiceApprovalException(e.getMessage());
		}
		return new ResponseVO(Constants.SUCCESS, messages.get("invoice.success"),null);
	}
}
