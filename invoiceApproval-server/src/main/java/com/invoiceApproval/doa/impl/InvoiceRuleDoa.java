package com.invoiceApproval.doa.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.invoiceApproval.Utils.Constants;
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
				return repository.save(invoiceApprovalRule);
		} catch (Exception e) {
			logger.error("An exception occured in update >>",e.getCause());
		}
		return invoiceApprovalRule;
	}

	@Override
	public void delete(InvoiceRule invoiceRule) throws Exception {
		try {
			repository.save(invoiceRule);
		} catch (Exception e) {
			logger.error("An exception occured in delete >>",e.getCause());
		}
	}

	@Override
	public InvoiceRule findAllRulesByOrgId(Integer orgId) throws Exception {
		try {
			String hql = "FROM InvoiceRule as ipr WHERE ipr.organization.orgId = :orgId and ipr.ruleStatus='"+Constants.ACTIVE+"'";
			InvoiceRule invoiceApprovalObj = (InvoiceRule)entityManager.createQuery(hql).setParameter("orgId", orgId)
			              .getSingleResult();
			return invoiceApprovalObj;
		} catch (NoResultException e) {
			logger.error("An exception occured in findAllRulesByOrgId >>",messages.get("rule.notFound"));
		}catch(NonUniqueResultException e){
			logger.error("An exception occured in findAllRulesByOrgId >>",messages.get("rule.noUnique"));
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public InvoiceRule getRuleByIdAndOrgId(Integer ruleId,Integer orgId)
	{
		try {
			String hql = "FROM InvoiceRule as ipr WHERE ipr.organization.orgId = :orgId and ipr.id=:ruleId and ipr.ruleStatus=:status";
			List<InvoiceRule> invoiceRules = entityManager.createQuery(hql).setParameter("orgId", orgId).setParameter("ruleId", ruleId).setParameter("status", Constants.ACTIVE).
					getResultList();
			if(invoiceRules != null && invoiceRules.size() > 0) {
				return invoiceRules.get(0);
			}
		} catch (Exception e) {
			logger.error("An exception occured in findAllRulesByOrgId >>",e.getCause());
		}
		return null;
	}
}
