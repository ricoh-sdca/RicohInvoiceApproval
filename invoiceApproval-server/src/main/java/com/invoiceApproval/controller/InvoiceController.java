package com.invoiceApproval.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.invoiceApproval.Utils.Constants;
import com.invoiceApproval.Utils.Messages;
import com.invoiceApproval.entity.Invoice;
import com.invoiceApproval.entity.ResponseVO;
import com.invoiceApproval.exception.InvoiceApprovalException;
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
		
		logger.info("calling saveInvoiceDetils() of InvoiceController");
		try {
			return  invoiceService.saveInvoiceDetails(invoice);
		} catch (Exception e) {
			logger.error(messages.get("invoice.error"),e);
			return new ResponseVO(Constants.FAILED, messages.get("invoice.error"), e.getMessage());
		}
	}
	
	/**
	 * This method returns all pending invoices based on login user
	 * @return List of all pending invoice
	 */
	@GetMapping(path="/invoices/{username}")
	public ModelMap getAllPendingInvoices(@PathVariable(name="username") String userName)
	{
		logger.info("calling getAllPendingInvoices() of InvoiceController");
		ModelMap modelMap = null;
 		try {
 			modelMap = invoiceService.getAllInvoices(userName,Constants.PENDING);
 			return modelMap;
		} catch (InvoiceApprovalException e) {
			logger.error(messages.get("invoice.error"),e);
			return modelMap;
		}
	}
	
	@PostMapping(path="/invoices/approve/{invoicenumber}")
	public ModelMap approveInvoice(@PathVariable(name="invoicenumber") String invoiceNumber) throws InvoiceApprovalException
	{
		logger.info("calling approveInvoice() of InvoiceController");
		ModelMap modelMap = null;
 		modelMap = invoiceService.approveInvoice(invoiceNumber);
		return modelMap;
	}
}
