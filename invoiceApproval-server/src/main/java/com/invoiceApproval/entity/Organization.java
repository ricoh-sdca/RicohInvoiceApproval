package com.invoiceApproval.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

@Entity
@Table(name="org_tbl")
public class Organization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer orgId;
	
	@Column(name="org_name")
	private String orgName;
	
	@Type(type = "com.invoiceApproval.entity.types.JsonType",
            parameters = {
                    @Parameter(
                            name = "classType",
                            value = "com.invoiceApproval.entity.OrgProperties"
                    )
            })
    @Column(name = "properties")
	private OrgProperties orgProps;
	
	@Column(name = "status")
	private String status;
	
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
	
	@OneToMany(mappedBy="organization")
	private Set<InvoiceRule> approvalRules;
	
	@OneToMany(mappedBy="organization")
	private Set<User> users;
	
	@OneToMany(mappedBy="organization")
	private Set<Invoice> invoiceDetails;
	
	
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public OrgProperties getOrgProps() {
		return orgProps;
	}
	public void setOrgProps(OrgProperties orgProps) {
		this.orgProps = orgProps;
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
	public Set<InvoiceRule> getApprovalRules() {
		return approvalRules;
	}
	public void setApprovalRules(Set<InvoiceRule> approvalRules) {
		this.approvalRules = approvalRules;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Set<Invoice> getInvoiceDetails() {
		return invoiceDetails;
	}
	public void setInvoiceDetails(Set<Invoice> invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}
	
}
