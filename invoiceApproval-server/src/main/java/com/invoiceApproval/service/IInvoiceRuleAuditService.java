package com.invoiceApproval.service;

import com.invoiceApproval.entity.InvoiceRule;
import com.invoiceApproval.entity.InvoiceRuleAudit;

public interface IInvoiceRuleAuditService {

	public InvoiceRuleAudit createRuleAudit(InvoiceRule createdRule);

}
