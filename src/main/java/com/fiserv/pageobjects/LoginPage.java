package com.fiserv.pageobjects;

import java.util.Optional;

import javax.swing.JPanel;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends Page {
	public WebDriver driver;
	final JPanel panel = new JPanel();

	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "ctl00_MainSiteContent_Login1_UserName")
	private WebElement userName;

	@FindBy(id = "ctl00_MainSiteContent_Login1_Password")
	private WebElement password;

	@FindBy(id = "ctl00_MainSiteContent_Login1_LoginButton")
	private WebElement signInButton;

	@FindBy(id = "modalWinCompCenter_tmpl_btnGO__3")
	private WebElement goButton;

	@FindBy(id = "spanLogoff")
	private WebElement logOff;

	public HomePage login(String username, String passwd, String env) {
		waitUntil(userName, 10).clear();
		userName.sendKeys(username);
		password.clear();
		password.sendKeys(passwd);
		signInButton.click();
		waitUntil(By.linkText(env), 10).click();
		switchWindow(3);
		waitUntil(goButton, 4).click();
		checkDuplicateSession();
		waitForPageLoaded(10);
		return new HomePage(driver);
	}

	public void logOff() {
		Optional.ofNullable(waitUntil(logOff, 4)).ifPresent(WebElement::click);
		Optional.ofNullable(alert(2)).ifPresent(Alert::accept);
		isElementPresent(By.linkText("Login"), 4);
	}
}
