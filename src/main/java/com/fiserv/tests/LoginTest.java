package com.fiserv.tests;

import org.testng.annotations.Test;

import com.fiserv.pageobjects.LoginPage;

public class LoginTest extends BaseTest {

	public LoginTest() {
		loginPage = new LoginPage(driver);
	}

	@Test
	public void login() {
		loginPage.login(user, password, env);
	}

}
