package com.invoiceApproval.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.invoiceApproval.Utils.Constants;
import com.invoiceApproval.Utils.Messages;
import com.invoiceApproval.entity.Invoice;
import com.invoiceApproval.entity.ResponseVO;
import com.invoiceApproval.service.impl.InvoiceService;

@RestController
public class InvoiceController {

	private static final Logger logger = LogManager.getLogger(InvoiceService.class);
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private Messages messages;

	/**
	 * This method is use to save the invoice details into database.
	 * @param invoice
	 * @return ResponseVO
	 */
	@PostMapping(path="/invoiceInput")
	public ResponseVO saveInvoiceDetails(@Valid @RequestBody Invoice invoice) {
		
		logger.info("calling saveInvoiceDetails() of InvoiceController");
		try {
			return  invoiceService.saveInvoiceDetails(invoice);
		} catch (Exception e) {
			logger.error(messages.get("invoice.error"),e);
			return new ResponseVO(Constants.FAILED, messages.get("invoice.error"), e.getMessage());
		}
	}
}
