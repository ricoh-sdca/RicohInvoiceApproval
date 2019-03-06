package com.invoiceApproval.doa.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.invoiceApproval.doa.IInvoiceDao;
import com.invoiceApproval.entity.Invoice;
import com.invoiceApproval.repository.InvoiceRepository;

@Repository
@Transactional
public class InvoiceDao implements IInvoiceDao {
	
	private static final Logger logger = LogManager.getLogger(InvoiceDao.class);
	
	@Autowired
	InvoiceRepository repository;
	
	@PersistenceContext
	EntityManager manager;
	
	@Override
	public boolean isAllInvoicesProcessed(Integer orgId) {
		String query = "select i from Invoice i where i.organization.orgId=:orgId and i.invoiceStatus='pending'";
		int count = manager.createQuery(query).setParameter("orgId", orgId).getResultList().size();
		return count > 0 ? false : true;
	}

	@Override
	public Invoice saveInvoiceDetails(Invoice invoice) {
		logger.info("Calling saveInvoiceDetails .. ");
		return repository.save(invoice);
	}

}
