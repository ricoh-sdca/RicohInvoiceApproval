package com.invoiceApproval.doa;

import com.invoiceApproval.entity.InvoiceRule;
import com.invoiceApproval.exception.InvoiceApprovalException;

public interface IInvoiceRuleDoa {

	public Iterable<InvoiceRule> findAllRules() throws Exception;
	
	public InvoiceRule findAllRulesByOrgId(Integer orgId) throws Exception;
	
	public InvoiceRule find(Integer id) throws Exception;
	
	public InvoiceRule create(InvoiceRule invoiceApprovalRule) throws Exception;
	
	public InvoiceRule update(Integer id,InvoiceRule invoiceApprovalRule) throws Exception;
	
	public void delete(InvoiceRule nvoiceRule) throws Exception;
	
	public InvoiceRule getRuleByIdAndOrgId(Integer orgId,Integer ruleId) throws InvoiceApprovalException;
}
