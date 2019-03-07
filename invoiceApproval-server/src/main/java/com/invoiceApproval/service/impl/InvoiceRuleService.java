package com.invoiceApproval.service.impl;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoiceApproval.Utils.Constants;
import com.invoiceApproval.Utils.Messages;
import com.invoiceApproval.doa.impl.InvoiceRuleDoa;
import com.invoiceApproval.entity.InvoiceRule;
import com.invoiceApproval.entity.InvoiceRuleDTO;
import com.invoiceApproval.entity.ResponseVO;
import com.invoiceApproval.entity.RuleDetails;
import com.invoiceApproval.exception.InvoiceApprovalException;
import com.invoiceApproval.service.IInvoiceRuleService;
import com.invoiceApproval.service.IInvoiceService;

@Service
public class InvoiceRuleService implements IInvoiceRuleService  {

	private static final Logger logger = LogManager.getLogger(InvoiceRuleService.class);
	
	@Autowired
    private InvoiceRuleDoa invoiceRuleDoa;
	
	@Autowired 
	private IInvoiceService invoiceService;
	
	@Autowired
	Messages messages;
	
	/**
     * This method is used for fetching all RULES 
     * @return
     */
	@Override
	public List<InvoiceRuleDTO> findAllRules() throws Exception{
		logger.info("InvoiceApprovalServiceImpl >> ");
		List<InvoiceRule> invoiceRules = invoiceRuleDoa.findAllRules();
		List<InvoiceRuleDTO> invoiceRuleDTOs = new LinkedList<>();
		if(invoiceRules != null && invoiceRules.size() > 0)
		{
			for (InvoiceRule invoiceRule : invoiceRules) {
				
				InvoiceRuleDTO invoiceRuleDTO = new InvoiceRuleDTO();
				invoiceRuleDTOs.add(invoiceRuleDTO.wrapToInvoiceRuleDTO(invoiceRule));
			}
			return invoiceRuleDTOs;
		}
		return invoiceRuleDTOs;
	}

	 /**
     * This method is used for fetching Rule based on Primary Key = id 
     * @param id
     * @return
	 * @throws Exception 
     */
	@Override
	public InvoiceRuleDTO find(Integer id) throws Exception{
		try {
			InvoiceRule invoiceRule = invoiceRuleDoa.find(id);
			return new InvoiceRuleDTO().wrapToInvoiceRuleDTO(invoiceRule); 
		}catch (InvoiceApprovalException e) {
			throw new InvoiceApprovalException(e.getErrorMessage());
		}
	}

	/**
     * This method is used for creating new Rule
     * @param invoiceApprovalRule
     * @return
     */
	@Override
	public InvoiceRule create(InvoiceRule invoiceRule) throws Exception{
		logger.info("Validating rule");
		if(isValidRule(invoiceRule)) {
			return invoiceRuleDoa.create(invoiceRule);
		}else {
			logger.info("Incorrect amount range (Duplicate / Overlapping / Missing). Kindly verify amount range and try again");
			throw new InvoiceApprovalException(messages.get("rule.invalid"));
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
	public ResponseVO update(Integer id, InvoiceRule invoiceApprovalRule) throws Exception{
		if(isAllInvoicesProcessed(invoiceApprovalRule.getOrganization().getOrgId()) && isValidRule(invoiceApprovalRule)) {
			InvoiceRule updatedRule = invoiceRuleDoa.update(id, invoiceApprovalRule);
			if(updatedRule != null) 
				new ResponseVO(Constants.SUCCESS, messages.get("rule.update.success"), null);
			else
				new ResponseVO(Constants.FAILED, "Rule updation failed.", messages.get("rule.error"));
		}else {
			return new ResponseVO(Constants.FAILED, "Rule updation failed.", messages.get("rule.status.pending"));
		}
		return new ResponseVO(Constants.FAILED, "Rule updation failed.", messages.get("rule.error"));
	}
	
	 /**
     * This method is used for deleting Rule based on Primary Key = ID
     * @param id
	 * @return 
     */
	@Override
	public ResponseVO delete(Integer id) throws Exception{
		try {
			InvoiceRule obj = invoiceRuleDoa.find(id);
			if(isAllInvoicesProcessed(obj.getOrganization().getOrgId())) {
				invoiceRuleDoa.delete(id);
				return new ResponseVO(Constants.SUCCESS,messages.get("rule.delete.success"),null);
			}else {
				logger.info("Invoices still in Pending state. Cannot delete Rule");
				return new ResponseVO(Constants.FAILED, null,messages.get("rule.status.pending"));
			}
		}
		catch (Exception e) {
			logger.error(messages.get("rule.error")+messages.get("rule.notFound"));
			throw new InvoiceApprovalException(messages.get("rule.error")+messages.get("rule.notFound"));
		}
	}
	
	/**
	 * This method is used to find Approval Rules based on OrgId
	 * @param orgId
	 */
	@Override
	public Iterable<InvoiceRule> findAllRulesByOrgId(Integer orgId) throws Exception {
		return invoiceRuleDoa.findAllRulesByOrgId(orgId);
	}

	/**
	 * This method is used to determine is provide set of rules are valid or not.
	 * Validation will check - Duplication, Missing Range, Overlapping Range
	 * @param invoiceApprovalRule
	 * @return
	 */
	public boolean isValidRule(InvoiceRule invoiceApprovalRule) {
		logger.info("calling isValidRule .. ");
		if(invoiceApprovalRule.getRule() != null && invoiceApprovalRule.getRule().getRuleDetails() != null)
		{
			List<RuleDetails> list = invoiceApprovalRule.getRule().getRuleDetails();
			
			boolean inValidRuleFlag = false;
			
			//Comparator definition for RuleDetails
			Comparator<RuleDetails> compa = new Comparator<RuleDetails>() {
				@Override
				public int compare(RuleDetails o1, RuleDetails o2) {
					if(o1.getFromAmt().intValue() < o2.getFromAmt().intValue()) {
						return -1;
					}else if(o1.getFromAmt().intValue() > o2.getFromAmt().intValue()) {
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
				if(0 != (list.get(i).getToAmt().compareTo(list.get(i+1).getFromAmt()))) {
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

	public InvoiceRule isRuleExists(Integer id,InvoiceRuleDTO invoiceRuleDTO) throws InvoiceApprovalException {
		logger.info("Enter isRuleExists() of InvoiceRuleService");
		try {
			InvoiceRule invoiceRule = invoiceRuleDoa.find(id);
			if(invoiceRule != null)
			{
				return invoiceRuleDTO.wrapForUpdate(invoiceRuleDTO,invoiceRule);
			}
		} catch (Exception e) {
			logger.error(messages.get("rule.error"),e);
			throw new InvoiceApprovalException(messages.get("rule.error")+e.getMessage());
		}
		return null;
	}
	
}
