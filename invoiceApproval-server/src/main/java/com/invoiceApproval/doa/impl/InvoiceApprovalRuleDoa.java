package com.invoiceApproval.doa.impl;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.invoiceApproval.doa.IInvoiceApprovalRuleDoa;
import com.invoiceApproval.entity.InvoiceApprovalRule;
import com.invoiceApproval.repository.InvoiceApprovalRuleRepository;

import javassist.tools.web.BadHttpRequest;

@Transactional
@Repository
public class InvoiceApprovalRuleDoa implements IInvoiceApprovalRuleDoa {

	private static final Logger logger = LogManager.getLogger(InvoiceApprovalRuleDoa.class);
	
	@Autowired
    private InvoiceApprovalRuleRepository repository;
	
	@Override
	public Iterable<InvoiceApprovalRule> findAllRules() throws Exception {
		logger.info("InvoiceApprovalRuleDoaImpl >> ");
		return repository.findAll();
	}

	@Override
	public InvoiceApprovalRule find(Integer id) throws Exception {
		 return repository.getOne(id);
	}

	@Override
	public InvoiceApprovalRule create(InvoiceApprovalRule invoiceApprovalRule) throws Exception {
		return repository.save(invoiceApprovalRule);
	}

	@Override
	public InvoiceApprovalRule update(Integer id, InvoiceApprovalRule invoiceApprovalRule) throws Exception {
		if (repository.existsById(id)) {
			return repository.save(invoiceApprovalRule);
		} else {
			throw new BadHttpRequest();
		}
	}

	@Override
	public void delete(Integer id) throws Exception {
		repository.deleteById(id);
	}

	

}
