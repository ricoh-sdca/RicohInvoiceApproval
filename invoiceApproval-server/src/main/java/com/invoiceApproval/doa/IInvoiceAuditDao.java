package com.invoiceApproval.doa;

import com.invoiceApproval.entity.InvoiceAudit;

public interface IInvoiceAuditDao {

	InvoiceAudit save(InvoiceAudit ruleAudit);
}
