package com.invoiceApproval.doa.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.invoiceApproval.Utils.Constants;
import com.invoiceApproval.doa.IInvoiceDao;
import com.invoiceApproval.entity.Invoice;
import com.invoiceApproval.entity.User;
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

	/** 
	 * This method returns all pending invoices based on login user
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Invoice> getAllInvoices(User user,String invoiceStatus) {
		StringBuffer query = new StringBuffer("select distinct i from Invoice i , User u where ");
				if(!"".equals(user.getApprovalLevel()) && user.getApprovalLevel() != null) {
					query.append("i.organization.orgId = '"+user.getOrganization().getOrgId()+"' and u.approvalLevel ='"+user.getApprovalLevel()+"' and u.userStatus='"+Constants.ACTIVE+"' ");
					query.append("and i.currApprovalLevel = '"+user.getApprovalLevel()+"' and ");
				}else {
					query.append("i.organization.orgId = '"+user.getOrganization().getOrgId()+"' and u.userStatus='"+Constants.ACTIVE+"' and ");
				}if(!"".equals(invoiceStatus) && invoiceStatus != null && !invoiceStatus.equals("all")) {
					query.append("i.invoiceStatus='"+invoiceStatus+"' order by i.createdAt asc");
				}else {
					query.append("1=1 order by i.createdAt asc");
				}
		 return manager.createQuery(query.toString()).getResultList();
	}

	@Override
	public Invoice getPendingInvoiceById(String invoiceNumber) {
		return repository.getPendingInvoiceById(invoiceNumber,Constants.PENDING);
	}

}
