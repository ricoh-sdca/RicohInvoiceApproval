package com.invoiceApproval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.invoiceApproval.entity.InvoiceRule;


@RestResource(exported = false)
public interface InvoiceApprovalRuleRepository extends JpaRepository<InvoiceRule, Integer> {

}