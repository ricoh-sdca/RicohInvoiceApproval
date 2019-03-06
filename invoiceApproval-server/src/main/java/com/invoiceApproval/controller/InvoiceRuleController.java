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

import com.invoiceApproval.Utils.Constants;
import com.invoiceApproval.Utils.Messages;
import com.invoiceApproval.entity.InvoiceRule;
import com.invoiceApproval.entity.InvoiceRuleDTO;
import com.invoiceApproval.entity.ResponseVO;
import com.invoiceApproval.service.impl.InvoiceRuleService;

import javassist.tools.web.BadHttpRequest;

/**
 * This class represent invoice approval rule rest resource method. 
 */
@RestController
public class InvoiceRuleController {
	
	private static final Logger logger = LogManager.getLogger(InvoiceRuleController.class);

	@Autowired
    private Messages messages;
    
    @Autowired
    private InvoiceRuleService invoiceRuleService;
    
    /**
     * This method is used for fetching all RULES 
     * @return
     */
    @GetMapping(path="/rules",produces="application/json")
    public List<InvoiceRule> findAll() {
    	logger.info("Calling ");
    	try {
			return invoiceRuleService.findAllRules();
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
			return invoiceRuleService.findAllRulesByOrgId(orgId);
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
			return invoiceRuleService.find(id);
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
    public ResponseVO create(@RequestBody InvoiceRuleDTO invoiceRuleDTO) {
    	logger.info("Creating new rule for orgId > "+invoiceRuleDTO.getOrgId());
    	ResponseVO responseVO = null;
    	InvoiceRule invoiceRule = invoiceRuleDTO.wrapper(invoiceRuleDTO);
    	try {
    		invoiceRule = invoiceRuleService.create(invoiceRule);
    		if(null != invoiceRule) {
    			responseVO = new ResponseVO(Constants.SUCCESS, messages.get("rule.success"), null);
    		}else {
    			responseVO = new ResponseVO(Constants.FAILED, null,messages.get("rule.invalid"));
    		}
		} catch (Exception e) {
			logger.error("An exception occured while executing REST call >> InvoiceApprovalRule >> create ",e);
			responseVO = new ResponseVO(Constants.FAILED, null,messages.get("rule.error"));
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
	public ResponseVO update(@PathVariable("id") Integer id, @RequestBody InvoiceRuleDTO invoiceRuleDTO){
		ResponseVO responseVO = null;
		InvoiceRule invoiceRule = invoiceRuleDTO.wrapper(invoiceRuleDTO);
		try {
			invoiceRule = invoiceRuleService.update(id, invoiceRule);
			if(null != invoiceRule) {
    			responseVO = new ResponseVO(Constants.SUCCESS, messages.get("rule.update.success"), null);
    		}else {
    			responseVO = new ResponseVO(Constants.FAILED, null,messages.get("rule.invalid"));
    		}
		} catch (Exception e) {
			logger.error("An exception occured while executing REST call >> InvoiceApprovalRule >> update ",e.getCause());
			responseVO = new ResponseVO(Constants.FAILED, null,messages.get("rule.update.error"));
		}
		return responseVO;
	}
	
	 /**
     * This method is used for deleting Rule based on Primary Key = ID
     * @param id
     */
    @DeleteMapping(path="/rules/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
    	ResponseVO responseVO = null;
    	try {
    		invoiceRuleService.delete(id);
		} catch (Exception e) {
			logger.error("An exception occured while executing REST call >> InvoiceApprovalRule >> delete ",e.getCause());
		}
    }
}
