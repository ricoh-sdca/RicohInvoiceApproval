package com.invoiceApproval.entity;

import java.math.BigDecimal;
import java.util.Date;

public class InvoiceDTO {

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private String invoiceNumber;
	private BigDecimal invoiceAmt;
	private String invoiceeEmail;
	private String invoiceImgLink;
	private Date invoiceDate;
	private Date invoiceDueDate;
	private String invoiceStatus;
	private String currApprovalLevel;
	private String finalApprovalLevel;
	private String vendor;
	private String createdBy;
	private Date createdAt;
	private String updatedBy;
	private Date updatedAt;
	private Integer orgId;
	
	//Added for invoice approve reject api
	private String username;
	private String comments;
	
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
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public InvoiceDTO wrapToDto(Invoice invoice) {
		InvoiceDTO dto = new InvoiceDTO();
		dto.setInvoiceNumber(invoice.getInvoiceNumber());
		dto.setInvoiceAmt(invoice.getInvoiceAmt());
		dto.setInvoiceDate(invoice.getInvoiceDate());
		dto.setInvoiceDueDate(invoice.getInvoiceDueDate());
		dto.setInvoiceStatus(invoice.getInvoiceStatus());
		dto.setOrgId(invoice.getOrganization().getOrgId());
		dto.setVendor(invoice.getVendor());
		dto.setInvoiceeEmail(invoice.getInvoiceeEmail());
		dto.setCurrApprovalLevel(invoice.getCurrApprovalLevel());
		dto.setFinalApprovalLevel(invoice.getFinalApprovalLevel());
		return dto;
	}
}
