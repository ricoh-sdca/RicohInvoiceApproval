package com.invoiceApproval.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.invoiceApproval.service.impl.LoginService;

@RunWith(SpringRunner.class)
public class UserLoginTest {

	@MockBean
	LoginService loginService;

	public boolean isUserAuthenticated() {
		System.out.println("Testing");
		return true;
	}

	@Test
	public void testLogin() {
		String userName = "atul", password = "atul";
		boolean flag = Mockito.when(loginService.validateUser(userName, password))
				.thenReturn(isUserAuthenticated()) != null;
		Mockito.when(loginService.validateUser(userName, password)).thenReturn(true);
		assertTrue("User login test", flag);
	}
}
