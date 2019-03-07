package com.invoiceApproval.doa.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.invoiceApproval.Utils.Messages;
import com.invoiceApproval.doa.IInvoiceRuleDoa;
import com.invoiceApproval.entity.InvoiceRule;
import com.invoiceApproval.exception.InvoiceApprovalException;
import com.invoiceApproval.repository.InvoiceRuleRepository;

@Transactional
@Repository
public class InvoiceRuleDoa implements IInvoiceRuleDoa {

	private static final Logger logger = LogManager.getLogger(InvoiceRuleDoa.class);
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	@Autowired
    private InvoiceRuleRepository repository;
	
	@Autowired
	Messages messages;
	
	/**
	 * This method is use to get all rules of an organization.
	 */
	@Override
	public List<InvoiceRule> findAllRules() throws Exception {
		logger.info("InvoiceApprovalRuleDoaImpl >> ");
		try {
			return repository.findAll();
		} catch (Exception e) {
			logger.error("An exception occured in findAllRules >>",e.getCause());
		}
		return null;
	}

	@Override
	public InvoiceRule find(Integer id) throws Exception {
		 try {
			return repository.getOne(id);
		} catch (Exception e) {
			logger.error("An exception occured in find >>",e.getCause());
			throw new InvoiceApprovalException(messages.get("rule.notFound"));
		}
	}

	@Override
	public InvoiceRule create(InvoiceRule invoiceRule) throws Exception {
		try {
			return repository.save(invoiceRule);
			// TODO save rule history
		} catch (Exception e) {
			logger.error("An exception occured in create >>",e);
		}
		return invoiceRule;
	}

	@Override
	public InvoiceRule update(Integer id, InvoiceRule invoiceApprovalRule) throws Exception {
		try {
			if (repository.existsById(id)) {
				return repository.save(invoiceApprovalRule);
			} else {
				throw new InvoiceApprovalException(messages.get("rule.notFound"));
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
	public Iterable<InvoiceRule> findAllRulesByOrgId(Integer orgId) throws Exception {
		try {
			String hql = "FROM InvoiceRule as ipr WHERE ipr.organization.orgId = :orgId and ipr.ruleStatus='active'";
			List<InvoiceRule> invoiceApprovalRules = entityManager.createQuery(hql).setParameter("orgId", orgId)
			              .getResultList();
			return invoiceApprovalRules;
		} catch (Exception e) {
			logger.error("An exception occured in findAllRulesByOrgId >>",e.getCause());
		}
		return null;
	}
	
	@Override
	public InvoiceRule getRuleByIdAndOrgId(Integer orgId,Integer ruleId)
	{
		try {
			String hql = "FROM InvoiceRule as ipr WHERE ipr.organization.orgId = :orgId and ipr.id=:ruleId";
			return (InvoiceRule) entityManager.createQuery(hql).setParameter("orgId", orgId).setParameter("ruleId", ruleId).getResultList();
		} catch (Exception e) {
			logger.error("An exception occured in findAllRulesByOrgId >>",e.getCause());
		}
		return null;
	}
}
