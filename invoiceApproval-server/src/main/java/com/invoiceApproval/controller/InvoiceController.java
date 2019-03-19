package com.invoiceApproval.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.invoiceApproval.Utils.Constants;
import com.invoiceApproval.Utils.Messages;
import com.invoiceApproval.Utils.ResponseUtils;
import com.invoiceApproval.entity.Invoice;
import com.invoiceApproval.entity.InvoiceDTO;
import com.invoiceApproval.entity.ResponseVO;
import com.invoiceApproval.entity.User;
import com.invoiceApproval.entity.UserDTO;
import com.invoiceApproval.exception.InvoiceApprovalException;
import com.invoiceApproval.service.IUserService;
import com.invoiceApproval.service.impl.InvoiceService;

@SessionAttributes("user")
@RestController
@CrossOrigin(origins="http://localhost:4200")
public class InvoiceController {

	private static final Logger logger = LogManager.getLogger(InvoiceService.class);
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private Messages messages;
	
	@Autowired
	private IUserService userService;

	/**
	 * This method is use to save the invoice details into database.
	 * @param invoice
	 * @return ResponseVO
	 */
	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping(path="/invoiceInput")
	public ModelMap saveInvoiceDetails(@Valid @RequestBody Invoice invoice) {
		
		logger.info("calling saveInvoiceDetils() of InvoiceController");
		ResponseVO responseVO = null;
		try {
			responseVO = invoiceService.saveInvoiceDetails(invoice);
			return ResponseUtils.getModelMap(responseVO, null, Constants.INVOICEDETAILS);
		} catch (Exception e) {
			logger.error(messages.get("invoice.error"),e);
			responseVO =  new ResponseVO(Constants.FAILED, messages.get("invoice.error"), e.getMessage());
			return ResponseUtils.getModelMap(responseVO, null, Constants.INVOICEDETAILS);
		}
	}
	
	/**
	 * This method returns all pending invoices based on login user
	 * @return List
	 */
	@CrossOrigin(origins="http://localhost:4200")
	@GetMapping("/invoices/{userName}/{invoiceStatus}")
	public ModelMap getAllInvoicesByStatus(@PathVariable String userName,@PathVariable String invoiceStatus)
	{
		logger.info("calling getAllPendingInvoices() of InvoiceController");
 		try {
 			User user = userService.getUserByName(userName);
 			List<InvoiceDTO> invoiceDtos = invoiceService.getAllInvoices(user,invoiceStatus);
 			if(invoiceDtos != null)
 				return ResponseUtils.getModelMap(new ResponseVO(Constants.SUCCESS,null,null),invoiceDtos,Constants.INVOICEDETAILS);
 			else
 				return ResponseUtils.getModelMap(new ResponseVO(Constants.FAILED,messages.get("invoice.noPending"),null),invoiceDtos,Constants.INVOICEDETAILS);
		} catch (InvoiceApprovalException e) {
			logger.error(messages.get("invoice.error"),e);
			return ResponseUtils.getModelMap(new ResponseVO(Constants.FAILED,null,e.getErrorMessage()),null,Constants.INVOICEDETAILS);
		}
	}
	
	/**
	 * This method approve an invoice based on given invoice number.
	 * @param invoiceNumber
	 * @return ModelMap
	 * @throws InvoiceApprovalException
	 */
	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping(path="/invoices/approve")
	public ModelMap approveInvoice(@RequestBody InvoiceDTO invoiceDTO) throws InvoiceApprovalException
	{
		logger.info("calling approveInvoice() of InvoiceController");
		ResponseVO responseVO = null;
		try {
			responseVO = invoiceService.approveInvoice(invoiceDTO);
			return ResponseUtils.getModelMap(responseVO, null,Constants.INVOICEDETAILS);
		} catch (Exception e) {
			logger.error(messages.get("common.error"),e);
			return ResponseUtils.getModelMap(new ResponseVO(Constants.FAILED, null, e.getMessage()), null,Constants.INVOICEDETAILS);
		}
	}
	
	/**
	 * This method reject the given invoice based on invoice number.
	 * @param userName
	 * @param invoiceNumber
	 * @return ModelMap
	 * @throws InvoiceApprovalException
	 */
	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping(path="/invoices/reject")
	public ModelMap rejectInvoice(@RequestBody InvoiceDTO invoiceDTO) throws InvoiceApprovalException
	{
		logger.info("calling rejectInvoice() of InvoiceController");
		ResponseVO responseVO = null;
		try {
			responseVO = invoiceService.rejectInvoice(invoiceDTO);
			return ResponseUtils.getModelMap(responseVO, null,Constants.INVOICEDETAILS);
		} catch (Exception e) {
			logger.error(messages.get("common.error"),e);
			return ResponseUtils.getModelMap(new ResponseVO(Constants.FAILED, null, e.getMessage()), null,Constants.INVOICEDETAILS);
		}
	}
}
