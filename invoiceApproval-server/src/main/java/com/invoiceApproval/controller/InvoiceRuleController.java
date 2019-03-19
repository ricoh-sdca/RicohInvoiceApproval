package com.invoiceApproval.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

/**
 * This class is used for Invoice Approval Rule REST API
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.invoiceApproval.Utils.Constants;
import com.invoiceApproval.Utils.Messages;
import com.invoiceApproval.Utils.ResponseUtils;
import com.invoiceApproval.entity.InvoiceRule;
import com.invoiceApproval.entity.InvoiceRuleDTO;
import com.invoiceApproval.entity.ResponseVO;
import com.invoiceApproval.exception.InvoiceApprovalException;
import com.invoiceApproval.service.impl.InvoiceRuleService;

import javassist.tools.web.BadHttpRequest;

/**
 * This class represent invoice approval rule rest resource method. 
 */
@RestController
@CrossOrigin(origins="http://localhost:4200")
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
    //@GetMapping(path="/rules",produces="application/json")
    //NOT IN USE NOW KEPT FOR REFERENCE.
    public ModelMap findAll(@PathVariable Integer orgId) {
    	logger.info("Calling ");
    	ResponseVO responseVO = null;
    	try {
    		List<InvoiceRuleDTO> ruleDataList = invoiceRuleService.findAllRules();
    		if(ruleDataList != null && ruleDataList.size() > 0) {
    			responseVO = new ResponseVO(Constants.SUCCESS,null, null);
    			return ResponseUtils.getModelMap(responseVO, ruleDataList,Constants.RULEDETAILS);
    		}
    		else {
    			responseVO = new ResponseVO(Constants.FAILED,null, messages.get("rule.get.failed"));
    			return ResponseUtils.getModelMap(responseVO, null,Constants.RULEDETAILS);
    		}
		} catch (Exception e) {
			logger.error("An exception occured while executing REST call >> InvoiceApprovalRule >> findAll ",e.getCause());
			responseVO = new ResponseVO(Constants.FAILED,null, messages.get("rule.get.failed"));
			return ResponseUtils.getModelMap(responseVO, null,Constants.RULEDETAILS);
		}
    }

    /**
     * This method is used for fetching all RULES based on orgId
     * @return
     */
    @CrossOrigin(origins="http://localhost:4200")
	@GetMapping(path="/rules/orgId/{orgId}")
    public ModelMap findAllRulesByOrgId(@PathVariable("orgId") Integer orgId) {
    	logger.info("Calling ");
    	ResponseVO responseVO = null;
    	try {
    		List<InvoiceRuleDTO> list = new ArrayList<>();
    		InvoiceRule invoiceRule = invoiceRuleService.findAllRulesByOrgId(orgId);
    		if(invoiceRule != null)
    		{
    			list.add(new InvoiceRuleDTO().wrapToInvoiceRuleDTO(invoiceRule));
    			responseVO = new ResponseVO(Constants.SUCCESS,null, null);
    			return ResponseUtils.getModelMap(responseVO, list,Constants.RULEDETAILS);
    		}
    		else {
    			responseVO = new ResponseVO(Constants.FAILED,null, messages.get("rule.get.failed"));
    			return ResponseUtils.getModelMap(responseVO, null,Constants.RULEDETAILS);
    		}
		} catch (Exception e) {
			logger.error("An exception occured while executing REST call >> InvoiceApprovalRule >> findAll ",e);
			responseVO = new ResponseVO(Constants.FAILED,null, messages.get("rule.get.failed"));
			return ResponseUtils.getModelMap(responseVO, null,Constants.RULEDETAILS);
		}
    }
    
    /**
     * This method is used for fetching Rule based on Primary Key = id 
     * @param id
     * @return
     * @throws InvoiceApprovalException 
     */
    @GetMapping(path="/rules/{id}")
    public InvoiceRuleDTO find(@PathVariable("id") Integer id) throws InvoiceApprovalException{
        try {
			return invoiceRuleService.find(id);
		} catch (Exception e) {
			logger.error("An exception occured while executing REST call >> InvoiceApprovalRule >> find ",e.getCause());
			throw new InvoiceApprovalException(messages.get("rule.error")+" "+messages.get("rule.notFound"));
		}
    }

    /**
     * This method is used for creating new Rule
     * @param invoiceApprovalRule
     * @return
     */
    @PostMapping(path="/rules",consumes = "application/json")
    public ModelMap create(@Valid @RequestBody InvoiceRuleDTO invoiceRuleDTO) {
    	logger.info("Creating new rule for orgId > "+invoiceRuleDTO.getOrgId());
    	ResponseVO responseVO = null;
    	try {
    		invoiceRuleService.validateInvoiceRule(invoiceRuleDTO);
    		try {
    			InvoiceRule activeRule = invoiceRuleService.findAllRulesByOrgId(invoiceRuleDTO.getOrgId());
    			if(activeRule != null)
    				throw new InvoiceApprovalException(messages.get("rule.noUnique"));
			} catch (InvoiceApprovalException e) {
				if(e.getMessage().equals(messages.get("rule.noUnique"))) {
					throw new InvoiceApprovalException(messages.get("rule.noUnique"));
				}
			}
    		
    		InvoiceRule invoiceRule = invoiceRuleDTO.wrapper(invoiceRuleDTO);
    		invoiceRule = invoiceRuleService.create(invoiceRule);
    		if(null != invoiceRule) {
    			responseVO = new ResponseVO(Constants.SUCCESS, messages.get("rule.success"), null);
    			return ResponseUtils.getModelMap(responseVO, Arrays.asList(invoiceRule),Constants.RULEDETAILS);
    		}else {
    			responseVO = new ResponseVO(Constants.FAILED, null,messages.get("rule.invalid"));
    			return ResponseUtils.getModelMap(responseVO,null,Constants.RULEDETAILS);
    		}
		 } catch (InvoiceApprovalException ex) {
			responseVO = new ResponseVO(Constants.FAILED, null,ex.getErrorMessage());
			return ResponseUtils.getModelMap(responseVO,null,Constants.RULEDETAILS);
		} catch (Exception e) {
			logger.error("An exception occured while executing REST call >> InvoiceApprovalRule >> create ",e);
			responseVO = new ResponseVO(Constants.FAILED, null,messages.get("rule.error"));
			return ResponseUtils.getModelMap(responseVO,null,Constants.RULEDETAILS);
		} 
    }

    /**
     * This method is used for updating existing Rule based on Primary Key = id 
     * @param id
     * @param invoiceApprovalRule
     * @return
     * @throws BadHttpRequest
     */
    @CrossOrigin(origins="http://localhost:4200")
	@PutMapping(path="/rules/update/{id}",consumes = "application/json")
	public ModelMap update(@PathVariable("id") Integer id, @RequestBody InvoiceRuleDTO invoiceRuleDTO){
		ResponseVO responseVO = null;
    	ModelMap map = new ModelMap();
		try {
			invoiceRuleService.validateInvoiceRule(invoiceRuleDTO);
			InvoiceRule invoiceRule = invoiceRuleService.isRuleExists(id,invoiceRuleDTO);
			responseVO = invoiceRuleService.update(id, invoiceRule);
			
			InvoiceRule newRule = new InvoiceRule();
			newRule.setOrganization(invoiceRule.getOrganization());
			newRule = invoiceRuleService.create(invoiceRuleDTO.wrapForUpdate(invoiceRuleDTO,newRule));
			
			if(newRule != null) {
    			responseVO = new ResponseVO(Constants.SUCCESS, messages.get("rule.update.success"), null);
    			map = ResponseUtils.getModelMap(responseVO, Arrays.asList(newRule),Constants.RULEDETAILS);
    		}else {
    			responseVO = new ResponseVO(Constants.FAILED, null,messages.get("rule.invalid"));
    			map = ResponseUtils.getModelMap(responseVO, null,Constants.RULEDETAILS);
    		}
		} catch (Exception e) {
			logger.error("An exception occured while executing REST call >> InvoiceApprovalRule >> update ",e.getCause());
			responseVO = new ResponseVO(Constants.FAILED, null,messages.get("rule.error")+e.getMessage());
			map = ResponseUtils.getModelMap(responseVO, null,Constants.RULEDETAILS);
		}
		return map;
	}
	
	 /**
     * This method is used for deleting Rule based on Primary Key = ID
     * @param id
     */
    @CrossOrigin(origins="http://localhost:4200")
    @DeleteMapping(path="/rules/delete/{id}")
    public ModelMap delete(@PathVariable("id") Integer id) {
    	logger.info("Enter delete() of InvoiceRuleController");
    	ResponseVO responseVO = null;
    	try {
    		responseVO = invoiceRuleService.delete(id);
    		return ResponseUtils.getModelMap(responseVO, null,Constants.RULEDETAILS);
		} catch (Exception e) {
			logger.error("An exception occured while executing REST call >> InvoiceApprovalRule >> delete ",e.getCause());
			return ResponseUtils.getModelMap(responseVO, null,Constants.RULEDETAILS);
		}
    }
}
