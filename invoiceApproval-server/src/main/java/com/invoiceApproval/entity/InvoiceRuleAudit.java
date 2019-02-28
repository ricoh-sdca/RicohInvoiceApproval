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

/**
 * This class represent invoice rule audit / History details
 * @author atul_jadhav
 */
@Entity
@Table(name = "invoice_approval_rule_audit_tbl")
public class InvoiceRuleAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer auditRuleId;

	@Type(type = "com.invoiceApproval.entity.types.JsonType", parameters = {
			@Parameter(name = "classType", value = "com.invoiceApproval.entity.Rule") })
	@Column(name = "rule")
	private Rule rule;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@ManyToOne
	@JoinColumn(name = "rule_id")
	private InvoiceRule approvalRule;

	public Integer getAuditRuleId() {
		return auditRuleId;
	}

	public void setAuditRuleId(Integer auditRuleId) {
		this.auditRuleId = auditRuleId;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
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

	public InvoiceRule getApprovalRule() {
		return approvalRule;
	}

	public void setApprovalRule(InvoiceRule approvalRule) {
		this.approvalRule = approvalRule;
	}

}
