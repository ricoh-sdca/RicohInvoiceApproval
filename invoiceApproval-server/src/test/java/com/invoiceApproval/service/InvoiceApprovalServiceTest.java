/*package com.invoiceApproval.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.invoiceApproval.doa.impl.InvoiceRuleDoa;
import com.invoiceApproval.entity.InvoiceRule;
import com.invoiceApproval.entity.InvoiceRuleDTO;
import com.invoiceApproval.entity.Rule;
import com.invoiceApproval.entity.RuleDetails;
import com.invoiceApproval.service.impl.InvoiceRuleService;


@RunWith(SpringRunner.class)
@RestClientTest(InvoiceRuleService.class)
public class InvoiceApprovalServiceTest {
	
	@MockBean
	InvoiceRuleDoa invoiceApprovalRuleDoa; 
	
	@Autowired
	private InvoiceRuleService invoiceApprovalRuleService;

	*//**
	 * Test Data - InvoiceApprovalRule
	 * @param id
	 * @param orgId
	 * @param ruleStatus
	 * @param rule
	 * @return
	 *//*
	public InvoiceRule getInvoiceApprovalRule(Integer id,Integer orgId,String ruleStatus, Rule rule) {
		InvoiceRule invoiceApprovalRule = new InvoiceRule();
		invoiceApprovalRule.setId(1);
		//invoiceApprovalRule.setOrgId(1);
		invoiceApprovalRule.setRuleStatus("active");
		invoiceApprovalRule.setRule(rule);
		return invoiceApprovalRule;
	}
	
	*//**
	 * Test Data - Rule
	 * @param ruleDetailsList
	 * @return
	 *//*
	public Rule getRule(List<RuleDetails> ruleDetailsList) {
		Rule rule = new Rule();
		rule.setType("AmountRange");
		rule.setRuleDetails(ruleDetailsList);
		return rule;
	}
	
	*//**
	 * Test Data - RuleDetails
	 * @return
	 *//*
	public List<RuleDetails> getRuleDetailsList(){
		List<RuleDetails> list = new ArrayList<RuleDetails>();
		//list.add(new RuleDetails("dollor", 0, 100, getLevel("1"),new Date()));
		//list.add(new RuleDetails("dollor", 100, 1000, getLevel("2"),new Date()));
		//list.add(new RuleDetails("dollor", 1000, 10000, getLevel("3"),new Date()));
		//list.add(new RuleDetails("dollor", 10000, 100000, getLevel("4"),new Date()));
		return list;
	}
	
	*//**
	 * Test Data - RuleDetails
	 * @return
	 *//*
	public List<RuleDetails> getRuleDetailsList_IncorrectRule(){
		List<RuleDetails> list = new ArrayList<RuleDetails>();
		list.add(new RuleDetails("dollor", 0, 100, getLevel("1"),new Date()));
		list.add(new RuleDetails("dollor", 200, 1000, getLevel("2"),new Date()));
		list.add(new RuleDetails("dollor", 1000, 10000, getLevel("3"),new Date()));
		list.add(new RuleDetails("dollor", 10000, 100000, getLevel("4"),new Date()));
		return list;
	}
	
	*//**
	 * Test Data - Level
	 * @param level
	 * @return
	 *//*
	public List<String> getLevel(String level){
		List<String> list = new ArrayList<String>(); 
		list.add(level);
		return list;
	}
	
	*//**
	 * Test data
	 * @return
	 *//*
	public Iterable<InvoiceRule> getMockInvoiceApprovalRuleList(){
		Iterable<InvoiceRule> list = new ArrayList<>();
		list.add(getInvoiceApprovalRule(1, 1, "active", getRule(getRuleDetailsList())));
		list.add(getInvoiceApprovalRule(2, 1, "active", getRule(getRuleDetailsList())));
		list.add(getInvoiceApprovalRule(3, 1, "active", getRule(getRuleDetailsList())));
		return list;
	}
	
	@Test
	public void findAll_test() throws Exception {
		Mockito.when(invoiceApprovalRuleService.findAllRules()).thenReturn(getMockInvoiceApprovalRuleList());
		List<InvoiceRuleDTO> iterable = invoiceApprovalRuleService.findAllRules();
		assertNotNull("list retrived successfully", iterable);
	}
	
	@Test
	public void find_test() throws Exception {
		Mockito.when(invoiceApprovalRuleService.find(1)).thenReturn(getInvoiceApprovalRule(1, 1, "active", getRule(getRuleDetailsList())));
		InvoiceRuleDTO iterable = invoiceApprovalRuleService.find(1);
		assertNotNull("list retrived successfully", iterable);
	}
	
	@Test
	public void findAllRulesByOrgId_test() throws Exception {
		Mockito.when(invoiceApprovalRuleService.findAllRulesByOrgId(1)).thenReturn(getMockInvoiceApprovalRuleList());
		Iterable<InvoiceRule> iterable = invoiceApprovalRuleService.findAllRulesByOrgId(1);
		assertNotNull("list retrived successfully", iterable);
	}
	
	@Test
	public void create_test() throws Exception{
		InvoiceRule invoiceApprovalRuleObj = getInvoiceApprovalRule(1, 1, "active", getRule(getRuleDetailsList()));
		Mockito.when(invoiceApprovalRuleService.create(invoiceApprovalRuleObj)).thenReturn(invoiceApprovalRuleObj);
		InvoiceRule invoiceApprovalRule = invoiceApprovalRuleService.create(invoiceApprovalRuleObj);
		assertNotNull("Invoice Rule is successfully created", invoiceApprovalRule);
	}
	
	@Test
	public void create_negative_test() throws Exception{
		InvoiceRule invoiceApprovalRuleObj = getInvoiceApprovalRule(1, 1, "active", getRule(getRuleDetailsList_IncorrectRule()));
		InvoiceRule invoiceApprovalRule = invoiceApprovalRuleService.create(invoiceApprovalRuleObj);
		assertNull("Incorrect Rule", invoiceApprovalRule);
	}
	
	@Test
	public void update_test() throws Exception{
		InvoiceRule invoiceApprovalRuleObj = getInvoiceApprovalRule(1, 1, "active", getRule(getRuleDetailsList()));
		Mockito.when(invoiceApprovalRuleService.update(1, invoiceApprovalRuleObj)).thenReturn(invoiceApprovalRuleObj);
		InvoiceRule invoiceApprovalRule = invoiceApprovalRuleService.update(1, invoiceApprovalRuleObj);
		assertNotNull("Invoice Rule is successfully updated", invoiceApprovalRule);
	}
	
	@Test
	public void update_negative_test() throws Exception{
		InvoiceRule invoiceApprovalRuleObj = getInvoiceApprovalRule(1, 1, "active", getRule(getRuleDetailsList_IncorrectRule()));
		InvoiceRule invoiceApprovalRule = invoiceApprovalRuleService.update(1, invoiceApprovalRuleObj);
		assertNull("Incorrect Rule", invoiceApprovalRule);
	}
	
	@Test
	public void delete_test() throws Exception{
		InvoiceRule invoiceApprovalRuleObj = getInvoiceApprovalRule(1, 1, "active", getRule(getRuleDetailsList()));
		Mockito.when(invoiceApprovalRuleDoa.find(1)).thenReturn(invoiceApprovalRuleObj);
		invoiceApprovalRuleService.delete(1);
		assertNotNull("Incorrect Rule");
	}
	
}
*/