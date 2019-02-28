package com.invoiceApproval.doa.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.invoiceApproval.doa.IInvoiceDao;
import com.invoiceApproval.repository.InvoiceRepository;

@Repository
@Transactional
public class InvoiceDao implements IInvoiceDao {

	@Autowired
	InvoiceRepository repository;
	
	@PersistenceContext
	EntityManager manager;
	
	@Override
	public boolean isAllInvoicesProcessed(Integer orgId) {
		String query = "select i from Invoice i where i.organization.orgId=:orgId and i.invoiceStatus=:status";
		int count = manager.createQuery(query).setParameter("orgId", orgId).setParameter("invoiceStatus", "pending").getResultList().size();
		return count > 0 ? false : true;
	}

}
