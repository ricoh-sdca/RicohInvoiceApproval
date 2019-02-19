package com.invoiceApproval.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "invoice_approval_rule_tbl")
public class InvoiceApprovalRule {

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;
	
	@Type(type = "com.invoiceApproval.entity.types.JsonType",
            parameters = {
                    @Parameter(
                            name = "classType",
                            value = "com.invoiceApproval.entity.Rule"
                    )
            })
    @Column(name = "rule")
	public Rule rule;
	
	@Column(name = "rule_status")
	public String ruleStatus;
	
	@Column(name = "org_id")
	public Integer orgId;

	/** SETTER / GETTER **/
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public String getRuleStatus() {
		return ruleStatus;
	}

	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
}
