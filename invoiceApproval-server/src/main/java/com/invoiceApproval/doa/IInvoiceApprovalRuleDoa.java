package com.invoiceApproval.doa;

import com.invoiceApproval.entity.InvoiceApprovalRule;

public interface IInvoiceApprovalRuleDoa {

	public Iterable<InvoiceApprovalRule> findAllRules() throws Exception;
	
	public Iterable<InvoiceApprovalRule> findAllRulesByOrgId(Integer orgId) throws Exception;
	
	public InvoiceApprovalRule find(Integer id) throws Exception;
	
	public InvoiceApprovalRule create(InvoiceApprovalRule invoiceApprovalRule) throws Exception;
	
	public InvoiceApprovalRule update(Integer id,InvoiceApprovalRule invoiceApprovalRule) throws Exception;
	
	public void delete(Integer id) throws Exception;
}
