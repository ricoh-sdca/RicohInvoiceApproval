package com.invoiceApproval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;

import com.invoiceApproval.entity.Invoice;

@RestResource(exported = false)
public interface InvoiceRepository extends JpaRepository<Invoice,String> {

	@Query("select i from Invoice i where i.invoiceNumber=?1 and i.invoiceStatus=?2")
	public Invoice getPendingInvoiceById(String invoiceNumber,String invoiceStatus);
}
