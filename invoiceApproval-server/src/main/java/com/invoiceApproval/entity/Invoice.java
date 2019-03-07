package com.invoiceApproval.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="invoice_tbl")
public class Invoice implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="invoice_number",nullable=false)
	@NotEmpty(message="{invoice.invoiceNumber}")
	private String invoiceNumber;
	
	@Column(name="amount_due",nullable=false)
	@NotNull(message="{invoice.invoiceAmt}")
	private BigDecimal invoiceAmt;
	
	@Column(name="invoicee_email",nullable=false)
	@NotEmpty(message="{invoice.invoiceeEmail}")
	private String invoiceeEmail;
	
	@Column(name="invoice_img_link",nullable=false)
	@NotEmpty(message="{invoice.invoiceImgLink}")
	private String invoiceImgLink;
	
	
	@Temporal(TemporalType.DATE)
	@NotNull(message="{invoice.invoiceDate}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="invoice_date",nullable=false)
	private Date invoiceDate;
	
	
	@Temporal(TemporalType.DATE)
	@NotNull(message="{invoice.invoiceDueDate}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="due_date",nullable=false)
	private Date invoiceDueDate;
	
	
	@NotEmpty(message="{invoice.invoiceStatus}")
	@Column(name="invoice_status",nullable=false)
	private String invoiceStatus;
	
	@Column(name="curr_approval_level",nullable=false)
	private String currApprovalLevel;
	
	@Column(name="final_approval_level",nullable=false)
	private String finalApprovalLevel;
	
	@Column
	private String vendor;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	@ManyToOne
	@JoinColumn(name="org_id")
	private Organization organization;
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public BigDecimal getInvoiceAmt() {
		return invoiceAmt;
	}
	public void setInvoiceAmt(BigDecimal invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}
	public String getInvoiceeEmail() {
		return invoiceeEmail;
	}
	public void setInvoiceeEmail(String invoiceeEmail) {
		this.invoiceeEmail = invoiceeEmail;
	}
	public String getInvoiceImgLink() {
		return invoiceImgLink;
	}
	public void setInvoiceImgLink(String invoiceImgLink) {
		this.invoiceImgLink = invoiceImgLink;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public Date getInvoiceDueDate() {
		return invoiceDueDate;
	}
	public void setInvoiceDueDate(Date invoiceDueDate) {
		this.invoiceDueDate = invoiceDueDate;
	}
	public String getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	public String getCurrApprovalLevel() {
		return currApprovalLevel;
	}
	public void setCurrApprovalLevel(String currApprovalLevel) {
		this.currApprovalLevel = currApprovalLevel;
	}
	public String getFinalApprovalLevel() {
		return finalApprovalLevel;
	}
	public void setFinalApprovalLevel(String finalApprovalLevel) {
		this.finalApprovalLevel = finalApprovalLevel;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
}
