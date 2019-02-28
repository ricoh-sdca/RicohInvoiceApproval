package com.invoiceApproval.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

@Entity
@Table(name="invoice_audit_tbl")
public class InvoiceAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer auditId; 
	
	@Column(name="user_comments")
	private String userComments;
	
	@Column(name="invoice_updated_status")
	private String updatedStatus;
	
	@Type(type = "com.invoiceApproval.entity.types.JsonType", parameters = {
			@Parameter(name = "classType", value = "com.invoiceApproval.entity.InvoiceFilterCriteria") })
	@Column(name="invoice_filter_criteria")
	private InvoiceFilterCriteria filterCriteria;
	
	@Column(name="user_approval_level")
	private String approvalLevel;
	
	@Column(name="invoice_updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	@ManyToOne
    @JoinColumn(name="invoice_number")
	private Invoice invoiceDetails;
	
	@ManyToOne
    @JoinColumn(name="invoice_updated_by")
	private User user;
	
	public Integer getAuditId() {
		return auditId;
	}
	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}
	public String getUserComments() {
		return userComments;
	}
	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}
	public String getUpdatedStatus() {
		return updatedStatus;
	}
	public void setUpdatedStatus(String updatedStatus) {
		this.updatedStatus = updatedStatus;
	}
	public InvoiceFilterCriteria getFilterCriteria() {
		return filterCriteria;
	}
	public void setFilterCriteria(InvoiceFilterCriteria filterCriteria) {
		this.filterCriteria = filterCriteria;
	}
	public String getApprovalLevel() {
		return approvalLevel;
	}
	public void setApprovalLevel(String approvalLevel) {
		this.approvalLevel = approvalLevel;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Invoice getInvoiceDetails() {
		return invoiceDetails;
	}
	public void setInvoiceDetails(Invoice invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
