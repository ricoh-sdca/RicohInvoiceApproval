package com.invoiceApproval.service.impl;

import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoiceApproval.doa.impl.InvoiceApprovalRuleDoa;
import com.invoiceApproval.entity.InvoiceRule;
import com.invoiceApproval.entity.RuleDetails;
import com.invoiceApproval.service.IInvoiceApprovalRuleService;
import com.invoiceApproval.service.IInvoiceService;

@Service
public class InvoiceApprovalRuleService implements IInvoiceApprovalRuleService  {

	private static final Logger logger = LogManager.getLogger(InvoiceApprovalRuleService.class);
	
	@Autowired
    private InvoiceApprovalRuleDoa invoiceApprovalRuleDoa;
	
	@Autowired 
	private IInvoiceService invoiceService;
	
	/**
     * This method is used for fetching all RULES 
     * @return
     */
	@Override
	public List<InvoiceRule> findAllRules() throws Exception{
		logger.info("InvoiceApprovalServiceImpl >> ");
		return invoiceApprovalRuleDoa.findAllRules();
	}

	 /**
     * This method is used for fetching Rule based on Primary Key = id 
     * @param id
     * @return
     */
	@Override
	public InvoiceRule find(Integer id) throws Exception{
		return invoiceApprovalRuleDoa.find(id);
	}

	/**
     * This method is used for creating new Rule
     * @param invoiceApprovalRule
     * @return
     */
	@Override
	public InvoiceRule create(InvoiceRule invoiceApprovalRule) throws Exception{
		logger.info("This is testing here.....");
		if(isValidRule(invoiceApprovalRule)) {
			return invoiceApprovalRuleDoa.create(invoiceApprovalRule);
		}else {
			logger.info("This is testing here.....returing NULL");
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
	public InvoiceRule update(Integer id, InvoiceRule invoiceApprovalRule) throws Exception{
		if(isAllInvoicesProcessed(invoiceApprovalRule.getOrganization().getOrgId()) && isValidRule(invoiceApprovalRule)) {
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
		InvoiceRule obj = invoiceApprovalRuleDoa.find(id);
		if(isAllInvoicesProcessed(obj.getOrganization().getOrgId())) {
			invoiceApprovalRuleDoa.delete(id);
		}else {
			logger.error("Invoices still in Pending state. Cannot delete Rule");
		}
	}
	
	/**
	 * This method is used to find Approval Rules based on OrgId
	 * @param orgId
	 */
	@Override
	public Iterable<InvoiceRule> findAllRulesByOrgId(Integer orgId) throws Exception {
		return invoiceApprovalRuleDoa.findAllRulesByOrgId(orgId);
	}

	/**
	 * This method is used to determine is provide set of rules are valid or not.
	 * Validation will check - Duplication, Missing Range, Overlapping Range
	 * @param invoiceApprovalRule
	 * @return
	 */
	public boolean isValidRule(InvoiceRule invoiceApprovalRule) {
		
		if(invoiceApprovalRule.getRule() != null && invoiceApprovalRule.getRule().getRuleDetails() != null)
		{
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
		else
		{
			logger.error("Invoice rule details not found..");
			return false;
		}
	}
	
	/**
	 * This method is used to validate if all Invoices for the ORG_ID are processed (i.e. APPROVED / REJECTED)
	 * @param orgId
	 * @return
	 * @throws Exception 
	 */
	public boolean isAllInvoicesProcessed(Integer orgId) throws Exception {
		logger.info("Enter isAllInvoicesProcessed() of InvoiceApprovalRuleService");
		return invoiceService.isAllInvoicesProcessed(orgId);
	}
}
