package com.invoiceApproval.service.impl;

import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoiceApproval.doa.impl.InvoiceApprovalRuleDoa;
import com.invoiceApproval.entity.InvoiceApprovalRule;
import com.invoiceApproval.entity.RuleDetails;
import com.invoiceApproval.service.IInvoiceApprovalRuleService;

@Service
public class InvoiceApprovalService implements IInvoiceApprovalRuleService  {

	private static final Logger logger = LogManager.getLogger(InvoiceApprovalService.class);
	
	@Autowired
    private InvoiceApprovalRuleDoa invoiceApprovalRuleDoa;
	
	/**
     * This method is used for fetching all RULES 
     * @return
     */
	@Override
	public Iterable<InvoiceApprovalRule> findAllRules() throws Exception{
		logger.info("InvoiceApprovalServiceImpl >> ");
		return invoiceApprovalRuleDoa.findAllRules();
	}

	 /**
     * This method is used for fetching Rule based on Primary Key = id 
     * @param id
     * @return
     */
	@Override
	public InvoiceApprovalRule find(Integer id) throws Exception{
		return invoiceApprovalRuleDoa.find(id);
	}

	/**
     * This method is used for creating new Rule
     * @param invoiceApprovalRule
     * @return
     */
	@Override
	public InvoiceApprovalRule create(InvoiceApprovalRule invoiceApprovalRule) throws Exception{
		if(isValidRule(invoiceApprovalRule)) {
			return invoiceApprovalRuleDoa.create(invoiceApprovalRule);
		}else {
			return null;
		}
	}

	/**
     * This method is used for updating existing Rule based on Primary Key = id 
     * @param id
     * @param invoiceApprovalRule
     * @return
     * @throws Exception
     */
	@Override
	public InvoiceApprovalRule update(Integer id, InvoiceApprovalRule invoiceApprovalRule) throws Exception{
		if(isAllInvoicesProcessed(invoiceApprovalRule.getOrgId()) && isValidRule(invoiceApprovalRule)) {
			return invoiceApprovalRuleDoa.update(id, invoiceApprovalRule);
		}else {
			return null;
		}
	}
	
	 /**
     * This method is used for deleting Rule based on Primary Key = ID
     * @param id
     */
	@Override
	public void delete(Integer id) throws Exception{
		invoiceApprovalRuleDoa.delete(id);
	}

	/**
	 * This method is used to determine is provide set of rules are valid or not.
	 * Validation will check - Duplication, Missing Range, Overlapping Range
	 * @param invoiceApprovalRule
	 * @return
	 */
	public boolean isValidRule(InvoiceApprovalRule invoiceApprovalRule) {
		List<RuleDetails> list = invoiceApprovalRule.getRule().getRuleDetails();
		boolean inValidRuleFlag = false;
		
		//Comparator definition for RuleDetails
		Comparator<RuleDetails> compa = new Comparator<RuleDetails>() {
			@Override
			public int compare(RuleDetails o1, RuleDetails o2) {
				if(o1.getFromAmt() < o2.getFromAmt()) {
					return -1;
				}else if(o1.getFromAmt() > o2.getFromAmt()) {
					return 1;
				}else {
					return 0;	
				}
			}
		};
		
		// Sorting list based on FromAmt Filed
		list.sort(compa);
		
		//Validating Rule 
		for (int i = 0; i < list.size()-1; i++) {
			if(list.get(i).getToAmt() != list.get(i+1).getFromAmt()) {
				logger.info("Missing/Duplicate/Overlapping range .."+list.get(i).getToAmt() +"-"+list.get(i+1).getFromAmt());
				inValidRuleFlag = true;
				break;
			}
		}
		
		// Returning flag
		if(!inValidRuleFlag) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * This method is used to validate if all Invoices for the ORG_ID are processed (i.e. APPROVED / REJECTED)
	 * @param orgId
	 * @return
	 */
	public boolean isAllInvoicesProcessed(Integer orgId) {
		//TODO : Check if all invoices for Org_id are processed (i.e. APPROVED / REJECTED)
		return true;
	}
}
