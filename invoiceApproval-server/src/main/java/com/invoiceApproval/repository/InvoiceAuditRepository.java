package com.invoiceApproval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.invoiceApproval.entity.InvoiceAudit;

@RestResource(exported = false)
public interface InvoiceAuditRepository extends JpaRepository<InvoiceAudit, Integer> {

}
