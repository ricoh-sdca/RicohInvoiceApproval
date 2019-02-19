package com.invoiceApproval.controller;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoiceApproval.entity.InvoiceApprovalRule;
import com.invoiceApproval.entity.ResponseVO;
import com.invoiceApproval.repository.InvoiceApprovalRuleRepository;
import com.invoiceApproval.service.impl.InvoiceApprovalService;

import javassist.tools.web.BadHttpRequest;

@RestController
@RequestMapping(path = "/rules")
public class InvoiceApprovalRuleController {
	
	private static final Logger logger = LogManager.getLogger(InvoiceApprovalRuleController.class);

    @Autowired
    private InvoiceApprovalRuleRepository repository;

    @Autowired
    private InvoiceApprovalService invoiceApprovalService;
    
    /**
     * This method is used for fetching all RULES 
     * @return
     */
    @GetMapping
    public Iterable<InvoiceApprovalRule> findAll() {
    	logger.info("Calling ");
    	try {
			return invoiceApprovalService.findAllRules();
		} catch (Exception e) {
			logger.error("An exception occured while executing REST call >> InvoiceApprovalRule >> findAll");
		}
		return null;
    }

    /**
     * This method is used for fetching Rule based on Primary Key = id 
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public InvoiceApprovalRule find(@PathVariable("id") Integer id) {
        return repository.getOne(id);
    }

    /**
     * This method is used for creating new Rule
     * @param invoiceApprovalRule
     * @return
     */
    @PostMapping(consumes = "application/json")
    public ResponseVO create(@RequestBody InvoiceApprovalRule invoiceApprovalRule) {
    	ResponseVO responseVO = new ResponseVO();
    	try {
			repository.save(invoiceApprovalRule);
			//throw new Exception();
			responseVO.setMessage("Rule is successfully added");
			
		} catch (Exception e) {
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
	@PutMapping(path = "/update/{id}")
	public InvoiceApprovalRule update(@PathVariable("id") Integer id,
			@RequestBody InvoiceApprovalRule invoiceApprovalRule) throws BadHttpRequest {
		if (repository.existsById(id)) {
			return repository.save(invoiceApprovalRule);
		} else {
			throw new BadHttpRequest();
		}
	}
	
	 /**
     * This method is used for deleting Rule based on Primary Key = ID
     * @param id
     */
    @DeleteMapping(path = "/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        repository.deleteById(id);
    }
}
