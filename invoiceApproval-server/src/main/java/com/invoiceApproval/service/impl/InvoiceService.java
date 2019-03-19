package com.invoiceApproval.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoiceApproval.Utils.Constants;
import com.invoiceApproval.Utils.Messages;
import com.invoiceApproval.doa.impl.InvoiceAuditDao;
import com.invoiceApproval.doa.impl.InvoiceDao;
import com.invoiceApproval.entity.Invoice;
import com.invoiceApproval.entity.InvoiceAudit;
import com.invoiceApproval.entity.InvoiceDTO;
import com.invoiceApproval.entity.InvoiceRule;
import com.invoiceApproval.entity.ResponseVO;
import com.invoiceApproval.entity.User;
import com.invoiceApproval.exception.InvoiceApprovalException;
import com.invoiceApproval.service.IInvoiceService;
import com.invoiceApproval.service.InvoiceRuleEngine;

@Service
public class InvoiceService implements IInvoiceService {

	private static final Logger logger = LogManager.getLogger(InvoiceService.class);
	
	@Autowired 
	private InvoiceDao invoiceDao;
	
	@Autowired
	private InvoiceRuleService invoiceRuleService; 
	
	@Autowired
	private Messages messages;
	
	@Autowired
	private InvoiceRuleEngine invoiceRuleEngine; 
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InvoiceAuditDao invoiceAuditDao;
	
	/**
	 * This method is used to verify of All invoices are processed (i.e. APPROVED / REJECTED)
	 * @param orgId
	 */
	@Override
	public boolean isAllInvoicesProcessed(Integer orgId,String invoiceStatus) {
		logger.info("Enter isAllInvoicesProcessed() of an InvoiceService");
		return invoiceDao.isAllInvoicesProcessed(orgId,invoiceStatus);
	}
	
	/**
	 * This is method is used to store Invoice from Ricoh APS Account
	 * @param invoice
	 */
	@Override
	public ResponseVO saveInvoiceDetails(Invoice invoice) throws InvoiceApprovalException {

		// Validations - Mandatory fields 
		logger.info("calling saveInvoiceDetails() of InvoiceService");
		try {
			//Saving status of Invoice to Constants.PENDING
			invoice.setInvoiceStatus(Constants.PENDING);
			
			// Validation - Rule exists for OrgId?
			InvoiceRule invoiceRule = invoiceRuleService.findAllRulesByOrgId(invoice.getOrganization().getOrgId());
			if(invoiceRule != null) {

				// Identify Current and Final Approver using Easy Rule Engine
				Facts facts = new Facts();
				facts.put("invoice",invoice);
				facts.put("mode",invoiceRule.getMode());
				facts.put("ruleDetails", invoiceRule.getRule().getRuleDetails());
				facts.put("amount", invoice.getInvoiceAmt());
				
				Rules rules = new Rules();
				rules.register(invoiceRuleEngine);
				
				RulesEngine engine = new DefaultRulesEngine();
				
				//Validate if Engine Rule returns TRUE or FALSE
				Map<org.jeasy.rules.api.Rule, Boolean> map = engine.check(rules, facts);
				if(map.get(RuleProxy.asRule(invoiceRuleEngine))) {
					engine.fire(rules, facts);
				}else {
					logger.error(messages.get("invoice.rule.mismatch"));
					return new ResponseVO(Constants.FAILED, null,messages.get("invoice.rule.mismatch"));
				}
			}else {
				return new ResponseVO(Constants.FAILED, null,messages.get("invoice.rule.mismatch"));
			}
		}
		catch (Exception e) {
			logger.error(messages.get("invoice.error"),e);
			throw new InvoiceApprovalException(e.getMessage());
		}
		return new ResponseVO(Constants.SUCCESS, messages.get("invoice.success"),null);
	}
	
	/**
	 * This method returns all invoices based on user and invoice status.
	 * @return List
	 * @param String 
	 */
	@Override
	public List<InvoiceDTO> getAllInvoices(User user,String invoiceStatus) throws InvoiceApprovalException {
		logger.info("Enter getUserPendingInvoices() of InvoiceService");
		List<InvoiceDTO> dtosList = null;
		try {
			List<Invoice> invoiceList = invoiceDao.getAllInvoices(user,invoiceStatus);
			if(invoiceList != null && invoiceList.size() > 0) {
				dtosList = new LinkedList<>();
				for (Invoice invoice : invoiceList) {
					InvoiceDTO dto = new InvoiceDTO();
					dtosList.add(dto.wrapToDto(invoice));
				}
				return dtosList;
			}
		} catch (Exception e) {
			logger.error(messages.get("invoice.error"),e);
			throw new InvoiceApprovalException(messages.get("invoice.error")+e.getMessage());
		}
		return null;
	}

	/**
	 * This method approves given invoice number and send success/failed response back to user.
	 * @param invoiceNumber
	 * @param user
	 * @return ResponseVO
	 * @throws InvoiceApprovalException
	 */
	@Override
	public ResponseVO approveInvoice(InvoiceDTO invoiceDTO) throws InvoiceApprovalException {
		logger.info("Enter approveInvoice() of InvoiceService");
		try {
			User user = userService.getUserByName(invoiceDTO.getUsername());
			Invoice invoice	= getPendingInvoiceById(invoiceDTO.getInvoiceNumber());
			Iterator<InvoiceRule> itr = invoice.getOrganization().getApprovalRules().stream().iterator();
			String invoiceMode = null;
			Invoice updatedInvoice = null;
			InvoiceAudit invoiceAudit = null;
			while (itr.hasNext()) {
				InvoiceRule invoiceRule = (InvoiceRule) itr.next();
				if(invoiceRule.getRuleStatus().equals(Constants.ACTIVE))
				{
					invoiceMode = invoiceRule.getMode();
					break;
				}
			}
			if(invoiceMode != null && invoiceMode.equalsIgnoreCase(Constants.TOP) && invoice.getFinalApprovalLevel().equals(user.getApprovalLevel()))
			{
				invoice.setInvoiceStatus(Constants.APPROVED);
				invoice.setUpdatedAt(new Date());
				invoice.setUpdatedBy(user.getUserName());
				invoice.setCurrApprovalLevel(user.getApprovalLevel());
				updatedInvoice = invoiceDao.saveInvoiceDetails(invoice);
				
				if(updatedInvoice != null) {
					invoiceAudit = wrapToInvoiceAudit(updatedInvoice);
					invoiceAudit.setUser(user);
					invoiceAudit.setUserComments(invoiceDTO.getComments());
					invoiceAudit.setApprovalLevel(user.getApprovalLevel());
					invoiceAuditDao.save(invoiceAudit);
					return new ResponseVO(Constants.SUCCESS, messages.get("invoice.approve"), null);
				}
			}
			else if(invoiceMode != null && invoiceMode.equalsIgnoreCase(Constants.SEQ) && invoice.getCurrApprovalLevel().equals(user.getApprovalLevel()))
			{
				invoice.setUpdatedAt(new Date());
				invoice.setUpdatedBy(user.getUserName());
				if(invoice.getFinalApprovalLevel().equals(user.getApprovalLevel())) {
					invoice.setInvoiceStatus(Constants.APPROVED);
					invoice.setCurrApprovalLevel(user.getApprovalLevel());
				}else {
					invoice.setCurrApprovalLevel((Integer.parseInt(user.getApprovalLevel())+1)+"");	
				}
				updatedInvoice = invoiceDao.saveInvoiceDetails(invoice);
				
				if(updatedInvoice != null) {
					invoiceAudit = wrapToInvoiceAudit(updatedInvoice);
					invoiceAudit.setUser(user);
					invoiceAudit.setUserComments(invoiceDTO.getComments());
					invoiceAudit.setApprovalLevel(user.getApprovalLevel());
					invoiceAuditDao.save(invoiceAudit);
					return new ResponseVO(Constants.SUCCESS, messages.get("invoice.approve"), null);
				}
			}
			else {
				return new ResponseVO(Constants.FAILED, null, messages.get("invoice.notAllowToApprove"));
			}
		} catch (Exception e) {
			logger.error(messages.get("invoice.error"),e);
			throw new InvoiceApprovalException(e.getMessage());
		}
		return null;
	}

	/**
	 * This method return Invoice entity based on invoice number.
	 * @param invoiceNumber
	 * @return Invoice entity
	 */
	@Override
	public Invoice getPendingInvoiceById(String invoiceNumber) throws InvoiceApprovalException {
		logger.info("Enter getInvoiceById() of InvoiceService");
		try {
			Invoice invoice = invoiceDao.getPendingInvoiceById(invoiceNumber);
			if(invoice == null) {
				throw new InvoiceApprovalException(messages.get("invoice.notfound"));
			}else {
				return invoice;
			}
		} catch (Exception e) {
			logger.info(messages.get("invoice.notfound"));
			throw new InvoiceApprovalException(messages.get("invoice.notfound"));
		}
	}

	/**
	 * This method reject the given invoice based on invoice number.
	 * @param invoiceNumber
	 * @param user
	 * @return ResponseVO
	 * @throws InvoiceApprovalException 
	 */
	@Override
	public ResponseVO rejectInvoice(InvoiceDTO invoiceDTO) throws InvoiceApprovalException {
		logger.info("Enter rejectInvoice() of InvoiceService");
		try {
			User user = userService.getUserByName(invoiceDTO.getUsername());
			Invoice invoice	= getPendingInvoiceById(invoiceDTO.getInvoiceNumber());
			invoice.setInvoiceStatus(Constants.REJECTED);
			invoice.setUpdatedAt(new Date());
			invoice.setUpdatedBy(user.getUserName());
			invoice.setCurrApprovalLevel(user.getApprovalLevel());
			Invoice rejectedInvoice = invoiceDao.saveInvoiceDetails(invoice);
			if(rejectedInvoice != null)
			{
				InvoiceAudit invoiceAudit = wrapToInvoiceAudit(rejectedInvoice);
				invoiceAudit.setUser(user);
				invoiceAudit.setUserComments(invoiceDTO.getComments());
				invoiceAudit.setApprovalLevel(user.getApprovalLevel());
				invoiceAuditDao.save(invoiceAudit);
				return new ResponseVO(Constants.SUCCESS, messages.get("invoice.reject"), null);
			}
			else {
				throw new InvoiceApprovalException(messages.get("common.error"));
			}
			
		} catch (Exception e) {
			logger.error(messages.get("invoice.error"),e);
			throw new InvoiceApprovalException(e.getMessage());
		}
	}
	protected InvoiceAudit wrapToInvoiceAudit(Invoice invoice) {
		InvoiceAudit invoiceAudit = new InvoiceAudit();
		invoiceAudit.setInvoiceDetails(invoice);
		invoiceAudit.setUpdatedAt(new Date());
		invoiceAudit.setUpdatedStatus(invoice.getInvoiceStatus());
		return invoiceAudit;
	}
}
