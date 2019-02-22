package com.invoiceApproval.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.invoiceApproval.doa.impl.InvoiceApprovalRuleDoa;
import com.invoiceApproval.entity.InvoiceApprovalRule;
import com.invoiceApproval.entity.Rule;
import com.invoiceApproval.entity.RuleDetails;
import com.invoiceApproval.service.impl.InvoiceApprovalRuleService;


@RunWith(SpringRunner.class)
@RestClientTest(InvoiceApprovalRuleService.class)
public class InvoiceApprovalServiceTest {
	
	@MockBean
	InvoiceApprovalRuleDoa invoiceApprovalRuleDoa; 
	
	@Autowired
	private InvoiceApprovalRuleService invoiceApprovalRuleService;

	public InvoiceApprovalRule getInvoiceApprovalRule(Integer id,Integer orgId,String ruleStatus, Rule rule) {
		InvoiceApprovalRule invoiceApprovalRule = new InvoiceApprovalRule();
		invoiceApprovalRule.setId(1);
		invoiceApprovalRule.setOrgId(1);
		invoiceApprovalRule.setRuleStatus("active");
		invoiceApprovalRule.setRule(rule);
		return invoiceApprovalRule;
	}
	
	public Rule getRule(List<RuleDetails> ruleDetailsList) {
		Rule rule = new Rule();
		rule.setType("AmountRange");
		rule.setRuleDetails(ruleDetailsList);
		return rule;
	}
	
	public List<RuleDetails> getRuleDetailsList(){
		List<RuleDetails> list = new ArrayList<RuleDetails>();
		list.add(new RuleDetails("dollor", 0, 100, getLevel("1")));
		list.add(new RuleDetails("dollor", 100, 1000, getLevel("2")));
		list.add(new RuleDetails("dollor", 1000, 10000, getLevel("3")));
		list.add(new RuleDetails("dollor", 10000, 100000, getLevel("4")));
		return list;
	}
	
	public List<String> getLevel(String level){
		List<String> list = new ArrayList<String>(); 
		list.add(level);
		return list;
	}
	
	public Iterable<InvoiceApprovalRule> getMockInvoiceApprovalRuleList(){
		List<InvoiceApprovalRule> list = new ArrayList<InvoiceApprovalRule>();
		list.add(getInvoiceApprovalRule(1, 1, "active", getRule(getRuleDetailsList())));
		list.add(getInvoiceApprovalRule(2, 1, "active", getRule(getRuleDetailsList())));
		list.add(getInvoiceApprovalRule(3, 1, "active", getRule(getRuleDetailsList())));
		return list;
	}
	
	@Test
	public void findAll_test() throws Exception {
		Mockito.when(invoiceApprovalRuleService.findAllRules()).thenReturn(getMockInvoiceApprovalRuleList());
		Iterable<InvoiceApprovalRule> iterable = invoiceApprovalRuleService.findAllRules();
		assertNotNull("list retrived successfully", iterable);
	}
	
	@Test
	public void find_test() throws Exception {
		Mockito.when(invoiceApprovalRuleService.find(1)).thenReturn(getInvoiceApprovalRule(1, 1, "active", getRule(getRuleDetailsList())));
		InvoiceApprovalRule iterable = invoiceApprovalRuleService.find(1);
		assertNotNull("list retrived successfully", iterable);
	}
	
	@Test
	public void findAllRulesByOrgId_test() throws Exception {
		Mockito.when(invoiceApprovalRuleService.findAllRulesByOrgId(1)).thenReturn(getMockInvoiceApprovalRuleList());
		Iterable<InvoiceApprovalRule> iterable = invoiceApprovalRuleService.findAllRulesByOrgId(1);
		assertNotNull("list retrived successfully", iterable);
	}
	
}
