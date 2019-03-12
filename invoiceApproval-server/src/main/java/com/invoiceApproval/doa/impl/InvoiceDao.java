package com.invoiceApproval.doa.impl;

import java.util.List;

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
	
	/**
	 * This method is used to verify of All invoices are processed (i.e. APPROVED / REJECTED)
	 * @param orgId
	 */
	@Override
	public boolean isAllInvoicesProcessed(Integer orgId,String invoiceStatus) {
		String query = "select i from Invoice i where i.organization.orgId=:orgId and i.invoiceStatus=:invoiceStatus";
		int count = manager.createQuery(query).setParameter("orgId", orgId).setParameter("invoiceStatus", invoiceStatus).getResultList().size();
		return count > 0 ? false : true;
	}

	/**
	 * This is method is used to store Invoice from Ricoh APS Account
	 * @param invoice
	 */
	@Override
	public Invoice saveInvoiceDetails(Invoice invoice) {
		logger.info("Calling saveInvoiceDetails .. ");
		return repository.save(invoice);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Invoice> getAllInvoices(String userName,String invoiceStatus) {
		String query = "select i from Invoice i , User u where ";
				if(userName != null ) {
					query += "i.organization.orgId = u.organization.orgId and u.userName ='"+userName+"' and u.userStatus='Active' "
							+ "and i.currApprovalLevel = u.approvalLevel and ";
				}else {
					query +="i.organization.orgId = u.organization.orgId and u.userStatus='Active' and ";
				}if(invoiceStatus != null) {
					query +="i.invoiceStatus='"+invoiceStatus+"' order by i.createdAt asc";
				}
		 return manager.createQuery(query).getResultList()	;
	}

}
