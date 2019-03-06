package com.invoiceApproval.service;

import com.invoiceApproval.entity.InvoiceRule;
import com.invoiceApproval.entity.ResponseVO;

public interface IInvoiceRuleService {

	public Iterable<InvoiceRule> findAllRules() throws Exception;
	
	public Iterable<InvoiceRule> findAllRulesByOrgId(Integer orgId) throws Exception;
	
	public InvoiceRule find(Integer id) throws Exception;
	
	public InvoiceRule create(InvoiceRule invoiceApprovalRule) throws Exception;
	
	public ResponseVO update(Integer id,InvoiceRule invoiceApprovalRule) throws Exception;
	
	public ResponseVO delete(Integer id) throws Exception;
	
}
