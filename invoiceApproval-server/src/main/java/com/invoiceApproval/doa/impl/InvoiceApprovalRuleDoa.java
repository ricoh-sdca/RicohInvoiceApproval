package com.invoiceApproval.doa.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	@Autowired
    private InvoiceApprovalRuleRepository repository;
	
	@Override
	public Iterable<InvoiceApprovalRule> findAllRules() throws Exception {
		logger.info("InvoiceApprovalRuleDoaImpl >> ");
		try {
			return repository.findAll();
		} catch (Exception e) {
			logger.error("An exception occured in findAllRules >>",e.getCause());
		}
		return null;
	}

	@Override
	public InvoiceApprovalRule find(Integer id) throws Exception {
		 try {
			return repository.getOne(id);
		} catch (Exception e) {
			logger.error("An exception occured in find >>",e.getCause());
		}
		return null;
	}

	@Override
	public InvoiceApprovalRule create(InvoiceApprovalRule invoiceApprovalRule) throws Exception {
		try {
			return repository.save(invoiceApprovalRule);
		} catch (Exception e) {
			logger.error("An exception occured in create >>",e.getCause());
		}
		return invoiceApprovalRule;
	}

	@Override
	public InvoiceApprovalRule update(Integer id, InvoiceApprovalRule invoiceApprovalRule) throws Exception {
		try {
			if (repository.existsById(id)) {
				return repository.save(invoiceApprovalRule);
			} else {
				throw new BadHttpRequest();
			}
		} catch (Exception e) {
			logger.error("An exception occured in update >>",e.getCause());
		}
		return invoiceApprovalRule;
	}

	@Override
	public void delete(Integer id) throws Exception {
		try {
			repository.deleteById(id);
		} catch (Exception e) {
			logger.error("An exception occured in delete >>",e.getCause());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<InvoiceApprovalRule> findAllRulesByOrgId(Integer orgId) throws Exception {
		try {
			String hql = "FROM InvoiceApprovalRule as ipr WHERE ipr.orgId = :orgId";
			List<InvoiceApprovalRule> invoiceApprovalRules = entityManager.createQuery(hql).setParameter("orgId", orgId)
			              .getResultList();
			return invoiceApprovalRules;
		} catch (Exception e) {
			logger.error("An exception occured in findAllRulesByOrgId >>",e.getCause());
		}
		return null;
	}

	

}
