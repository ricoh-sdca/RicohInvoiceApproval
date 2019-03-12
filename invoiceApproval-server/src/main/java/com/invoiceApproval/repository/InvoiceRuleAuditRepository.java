package com.invoiceApproval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.invoiceApproval.entity.InvoiceRuleAudit;

@RestResource(exported = false)
public interface InvoiceRuleAuditRepository extends JpaRepository<InvoiceRuleAudit, Integer> {

}
