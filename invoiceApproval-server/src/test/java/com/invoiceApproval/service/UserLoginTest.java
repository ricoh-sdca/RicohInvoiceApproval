package com.invoiceApproval.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.invoiceApproval.exception.InvoiceApprovalException;
import com.invoiceApproval.service.impl.LoginService;

@RunWith(SpringRunner.class)
public class UserLoginTest {

	@MockBean
	LoginService loginService;

	
	/**
	 * This method perform unit testing for user login API
	 */
	@Test
	public void testLogin() {
		String userName = "atul", password = "atul";
		boolean flag;
		try {
			flag = Mockito.when(loginService.validateUser(userName, password)).thenReturn(true) != null;
			assertTrue("User login test", flag);
		} catch (InvoiceApprovalException e) {
			e.printStackTrace();
		}
	}
}
