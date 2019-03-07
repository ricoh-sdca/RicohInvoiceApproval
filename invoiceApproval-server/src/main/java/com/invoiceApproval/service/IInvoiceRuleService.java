package com.invoiceApproval.service;

import java.util.List;

import com.invoiceApproval.entity.InvoiceRule;
import com.invoiceApproval.entity.InvoiceRuleDTO;
import com.invoiceApproval.entity.ResponseVO;

public interface IInvoiceRuleService {

	public List<InvoiceRuleDTO> findAllRules() throws Exception;
	
	public Iterable<InvoiceRule> findAllRulesByOrgId(Integer orgId) throws Exception;
	
	public InvoiceRuleDTO find(Integer id) throws Exception;
	
	public InvoiceRule create(InvoiceRule invoiceApprovalRule) throws Exception;
	
	public ResponseVO update(Integer id,InvoiceRule invoiceApprovalRule) throws Exception;
	
	public ResponseVO delete(Integer id) throws Exception;
	
}
