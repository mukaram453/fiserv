package com.fiserv.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends Page {

	public WebDriver driver;

	public HomePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//*[text()='Credit Processing']")
	private WebElement creditProcessingButton;

	@FindBy(xpath = "//*[text()='Application Entry']")
	private WebElement applicationEntry;

	public DealerSearchPage openApplicationEntry() {
		waitUntil(creditProcessingButton, 10).click();
		waitUntil(applicationEntry, 2).click();
		return new DealerSearchPage(driver);
	}
}
