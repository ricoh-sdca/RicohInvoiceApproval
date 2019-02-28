package com.invoiceApproval.controller;
import java.util.List;

/**
 * This class is used for Invoice Approval Rule REST API
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.invoiceApproval.Utils.Messages;
import com.invoiceApproval.entity.InvoiceRule;
import com.invoiceApproval.entity.ResponseVO;
import com.invoiceApproval.service.impl.InvoiceApprovalRuleService;

import javassist.tools.web.BadHttpRequest;

/**
 * This class represent invoice approval rule rest resource method. 
 */
@RestController
public class InvoiceApprovalRuleController {
	
	private static final Logger logger = LogManager.getLogger(InvoiceApprovalRuleController.class);

	@Autowired
    private Messages messages;
    
    @Autowired
    private InvoiceApprovalRuleService invoiceApprovalRuleService;
    
    /**
     * This method is used for fetching all RULES 
     * @return
     */
    @GetMapping(path="/rules",produces="application/json")
    public List<InvoiceRule> findAll() {
    	logger.info("Calling ");
    	try {
    		logger.info(messages.get("this.is.sample.text"));
			return invoiceApprovalRuleService.findAllRules();
		} catch (Exception e) {
			logger.error("An exception occured while executing REST call >> InvoiceApprovalRule >> findAll ",e.getCause());
		}
		return null;
    }

    /**
     * This method is used for fetching all RULES based on orgId
     * @return
     */
    @GetMapping(path="/rules/orgId/{orgId}")
    public Iterable<InvoiceRule> findAllRulesByOrgId(@PathVariable("orgId") Integer orgId) {
    	logger.info("Calling ");
    	try {
			return invoiceApprovalRuleService.findAllRulesByOrgId(orgId);
		} catch (Exception e) {
			logger.error("An exception occured while executing REST call >> InvoiceApprovalRule >> findAll ",e.getCause());
		}
		return null;
    }
    
    /**
     * This method is used for fetching Rule based on Primary Key = id 
     * @param id
     * @return
     */
    @GetMapping(path="/rules/{id}")
    public InvoiceRule find(@PathVariable("id") Integer id) {
        try {
			return invoiceApprovalRuleService.find(id);
		} catch (Exception e) {
			logger.error("An exception occured while executing REST call >> InvoiceApprovalRule >> find ",e.getCause());
		}
		return null;
    }

    /**
     * This method is used for creating new Rule
     * @param invoiceApprovalRule
     * @return
     */
    @PostMapping(path="/rules",consumes = "application/json")
    public ResponseVO create(@RequestBody InvoiceRule invoiceApprovalRule) {
    	ResponseVO responseVO = new ResponseVO();
    	try {
    		invoiceApprovalRuleService.create(invoiceApprovalRule);
			responseVO.setMessage("Rule is successfully added");
		} catch (Exception e) {
			logger.error("An exception occured while executing REST call >> InvoiceApprovalRule >> create ",e);
			responseVO.setErrorMessage("An error occurred during execution of REST call. Refer to application logs for more details.");
		}
    	return responseVO;
    }

    /**
     * This method is used for updating existing Rule based on Primary Key = id 
     * @param id
     * @param invoiceApprovalRule
     * @return
     * @throws BadHttpRequest
     */
	@PutMapping(path="/rules/update/{id}",consumes = "application/json")
	public InvoiceRule update(@PathVariable("id") Integer id,
			@RequestBody InvoiceRule invoiceApprovalRule){
		try {
			invoiceApprovalRuleService.update(id, invoiceApprovalRule);
		} catch (Exception e) {
			logger.error("An exception occured while executing REST call >> InvoiceApprovalRule >> update ",e.getCause());
		}
		return invoiceApprovalRule;
	}
	
	 /**
     * This method is used for deleting Rule based on Primary Key = ID
     * @param id
     */
    @DeleteMapping(path="/rules/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
    	try {
    		invoiceApprovalRuleService.delete(id);
		} catch (Exception e) {
			logger.error("An exception occured while executing REST call >> InvoiceApprovalRule >> delete ",e.getCause());
		}
    }
}
