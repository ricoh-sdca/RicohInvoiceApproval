package com.invoiceApproval.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.invoiceApproval.entity.Invoice;
import com.invoiceApproval.entity.ResponseVO;
import com.invoiceApproval.service.impl.InvoiceService;

@RestController
public class InvoiceController {

	public InvoiceService invoiceService;
	
	public ResponseVO saveInvoiceDetails(@RequestBody Invoice invoice) {
		ResponseVO responseVO = null;
		invoiceService.saveInvoiceDetails(invoice);
		return responseVO;
	}
}
