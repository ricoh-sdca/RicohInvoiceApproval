package com.invoiceApproval.doa.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	 * @param orgId 
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
	public InvoiceRule findAllRulesByOrgId(Integer orgId) throws InvoiceApprovalException {
		logger.info("Calling findAllRulesByOrgId() of InvoiceRuleDoa ");
		try {
			String hql = "FROM InvoiceRule as ipr WHERE ipr.organization.orgId = :orgId and ipr.ruleStatus=:ruleStatus and ipr.organization.status=:orgStatus";
			InvoiceRule invoiceApprovalObj = (InvoiceRule)entityManager.createQuery(hql).setParameter("orgId", orgId).
					setParameter("orgStatus", Constants.ACTIVE).setParameter("ruleStatus",Constants.ACTIVE).getSingleResult();
			return invoiceApprovalObj;
		} catch (NoResultException e) {
			logger.error("An exception occured in findAllRulesByOrgId >>",messages.get("rule.notFound"));
			throw new InvoiceApprovalException(messages.get("rule.notFound"));
		}catch(NonUniqueResultException e){
			logger.error("An exception occured in findAllRulesByOrgId >>",messages.get("rule.noUnique"));
			throw new InvoiceApprovalException(messages.get("rule.noUnique"));
		}
	}
	
	@Override
	public InvoiceRule getRuleByIdAndOrgId(Integer ruleId,Integer orgId) throws InvoiceApprovalException
	{
		try {
			String hql = "FROM InvoiceRule as ipr WHERE ipr.organization.orgId = :orgId and ipr.id=:ruleId and ipr.ruleStatus=:status";
			InvoiceRule invoiceRule = (InvoiceRule) entityManager.createQuery(hql).setParameter("orgId", orgId).setParameter("ruleId", ruleId).setParameter("status", Constants.ACTIVE).
					getSingleResult();
			return invoiceRule;
		} catch (NoResultException e) {
			logger.error("An exception occured in findAllRulesByOrgId >>",messages.get("rule.notFound"));
			throw new InvoiceApprovalException(messages.get("rule.notFound"));
		}catch(NonUniqueResultException e){
			logger.error("An exception occured in findAllRulesByOrgId >>",messages.get("rule.noUnique"));
			throw new InvoiceApprovalException(messages.get("rule.noUnique"));
		}
	}
}
