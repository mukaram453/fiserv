package com.fiserv.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.fiserv.model.SETF;
import com.fiserv.utils.ExcelReaderUtil;

public class DealerSearchPage extends Page {

	final static String DUPLICATE_CANCEL = "span[ig_b='ctl00_cphId_duplicateAppResults_tmpl_fiservDuplicateAppResults_btnDupResultsCancel']";
	final static String DUPLICATE_CONTINUE = "span[ig_b='ctl00_cphId_duplicateAppResults_tmpl_fiservDuplicateAppResults_btnDupResultsContinue']";
	final static String PARTY1 = "#ctl00cphIdduplicateAppResultstmplfiservDuplicateAppResultswpDupAppResultsParty1_header_img";
	final static String COMMA = ",";
	public WebDriver driver;

	public DealerSearchPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "igtxtctl00_cphId_wpAppEntrySetup_wpDealerSearch_fiservDealerSearch_teDealerNumber")
	private WebElement dealerNumber;

	@FindBy(id = "ctl00_cphId_wpAppEntrySetup_wpAppEntryParty_ddlProductType")
	private WebElement productType;

	@FindBy(id = "igtxtctl00_cphId_wpAppEntrySetup_wpAppEntryParty_fiservDupplicateApp_meId1")
	private WebElement ssn;

	@FindBy(css = "span[ig_b='ctl00_cphId_wpAppEntrySetup_wibAppEntrySetupContinue']")
	private WebElement continueButton;

	@FindBy(css = DUPLICATE_CANCEL + COMMA + DUPLICATE_CONTINUE + COMMA + PARTY1)
	private WebElement duplicate;

	public boolean searchDealer() {
		SETF setf = ExcelReaderUtil.setf;
		switchFrame("clientArea", 20);
		waitUntil(dealerNumber, 4).clear();
		dealerNumber.sendKeys(setf.getDealerNumber());
		pressEnter(2);
		new Select(waitUntil(productType, 2))
				.selectByVisibleText("SETF Lease".equals(setf.getSheet()) ? "Lease" : "Retail");
		ssn.click();
		ssn.sendKeys(setf.getSsn());
		continueButton.click();
		boolean isDuplicate = isElementPresent(By.cssSelector(DUPLICATE_CANCEL), 5)
				| isElementPresent(By.cssSelector(DUPLICATE_CONTINUE), 1) | isElementPresent(By.cssSelector(PARTY1), 1);
		if (isDuplicate) {
			ExcelReaderUtil.updateSheet("Y");
		}
		return isDuplicate;

	}

	public void updateDuplicate(boolean duplicate) {
		if (duplicate) {
			waitUntil(By.cssSelector(DUPLICATE_CANCEL), 4).click();
			defaultWindow();
		}
	}
}
