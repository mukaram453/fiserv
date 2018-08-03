package com.fiserv.pageobjects;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.fiserv.model.SETF;
import com.fiserv.tests.TestRunner;
import com.fiserv.utils.ExcelReaderUtil;

public class CustomerPage extends Page {

	public WebDriver driver;
	SETF setf;

	public CustomerPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "igtxtctl00_cphId_ctl00_wpDemographics_teBuyerFirstName")
	private WebElement fName;

	@FindBy(id = "igtxtctl00_cphId_ctl00_wpDemographics_teBuyerLastName")
	private WebElement lName;

	@FindBy(id = "igtxtctl00_cphId_ctl00_wpDemographics_dteDateOfBirth")
	private WebElement dob;

	@FindBy(id = "igtxtctl00_cphId_ctl00_wpDemographics_ctl00_wpCurrentAddress_teAddressLine1")
	private WebElement addressLine1;

	@FindBy(id = "igtxtctl00_cphId_ctl00_wpDemographics_ctl00_wpCurrentAddress_ucCity_txtCity")
	private WebElement city;

	@FindBy(id = "igtxtctl00_cphId_ctl00_wpDemographics_ctl00_wpCurrentAddress_meZipUsa")
	private WebElement zip;

	@FindBy(id = "ctl00_cphId_ctl00_wpDemographics_ctl00_wpCurrentAddress_cmbState")
	private WebElement stateDropDown;

	@FindBy(id = "igtxtctl00_cphId_ctl00_wpDemographics_ctl00_wpCurrentAddress_neYears")
	private WebElement cYears;

	@FindBy(id = "igtxtctl00_cphId_ctl00_wpDemographics_ctl00_wpCurrentAddress_neMonths")
	private WebElement months;

	@FindBy(id = "ctl00_cphId_ctl00_wpDemographics_ctl00_wpCurrentAddress_cmbLivingStatus")
	private WebElement addressStatusDropDown;

	@FindBy(id = "igtxtctl00_cphId_ctl01_wpEmployment_ctl00_wpCurrentEmploymentAddress_teYears")
	private WebElement eYears;

	@FindBy(id = "ctl00_cphId_ctl02_wpContract_ctl00_wpContractCollateral_ddlContractCollateralType")
	private WebElement newUsed;

	@FindBy(id = "igtxtctl00_cphId_ctl01_wpEmployment_ctl00_wpCurrentEmploymentAddress_teEmployer")
	private WebElement employer;

	@FindBy(id = "igtxtctl00_cphId_ctl02_wpContract_ctl00_wpContractCollateral_txtContractCollateralYear")
	private WebElement year;

	@FindBy(id = "ctl00_cphId_ctl02_wpContract_ctl00_wpContractCollateral_ddlContractCollateralYear")
	private WebElement ryear;

	@FindBy(id = "ctl00_cphId_ctl02_wpContract_ctl00_wpContractCollateral_ddlContractCollateralMake")
	private WebElement rmake;

	@FindBy(id = "igtxtctl00_cphId_ctl02_wpContract_ctl00_wpContractCollateral_txtContractCollateralMake")
	private WebElement make;

	@FindBy(id = "ctl00_cphId_ctl02_wpContract_ctl00_wpContractCollateral_ddlContractCollateralModel")
	private WebElement rmodel;

	@FindBy(id = "igtxtctl00_cphId_ctl02_wpContract_ctl00_wpContractCollateral_txtContractCollateralModel")
	private WebElement model;

	@FindBy(id = "igtxtctl00_cphId_ctl02_wpContract_ctl02_wpContractInfo_txtContractMSRP")
	private WebElement msrp;

	@FindBy(id = "igtxtctl00_cphId_ctl02_wpContract_ctl02_wpContractInfo_txtInvoice")
	private WebElement invoice;

	@FindBy(id = "igtxtctl00_cphId_ctl02_wpContract_ctl02_wpContractInfo_txtDlrBookValue")
	private WebElement bookValue;

	@FindBy(id = "ctl00_cphId_btnAppEntrySave__3")
	private WebElement saveButton;

	@FindBy(id = "ctl00_cphId_btnAppEntryExit__3")
	private WebElement cancelButton;

	@FindBy(xpath = "//*[text()='Close']")
	private WebElement closeButton;

	@FindBy(id = "ctl00ErrorDivWebPanel1_header_img")
	private WebElement errorCloseButton;

	@FindAll(value = { @FindBy(css = "#errorWarningMessages>table>tbody>tr>td>b") })
	private List<WebElement> errorMessages;

	@FindBy(id = "ucFooter_UsrCtrlGenericPopup_modalWinGenericPopup_tmpl_lblGenericMessage")
	private WebElement confirmMessage;

	@FindBy(id = "spanCenterSwitch")
	private WebElement centerSwitchButton;

	@FindBy(id = "modalWinCompCenter_tmpl_btnGO__3")
	private WebElement goButton;

	public CustomerPage enterCustomerDetails() {

		SETF setf = ExcelReaderUtil.setf;
		waitUntil(dob, 20).click();
		dob.sendKeys(setf.getDob());
		fName.sendKeys(setf.getfName());
		lName.sendKeys(setf.getlName());
		addressLine1.sendKeys(setf.getAddressLine1());
		zip.click();
		zip.sendKeys(setf.getZipCode());
		pressEnter(2);
		cYears.sendKeys(setf.getYears());
		months.sendKeys(setf.getMonths());
		new Select(addressStatusDropDown).selectByVisibleText(setf.getAddressStatus());
		employer.sendKeys(setf.getEmployer());
		eYears.sendKeys(setf.getYearsMonths());
		new Select(newUsed).selectByVisibleText(setf.getNewUsed());
		if (setf.getSheet().equals("SETF Used")) {
			new Select(ryear).selectByVisibleText(setf.getYear());
			rmake.click();
			action.sendKeys(setf.getMake()).build().perform();
			pressEnter(1);
			new Select(rmodel).selectByVisibleText(setf.getModel());
		} else {
			year.sendKeys(setf.getYear());
			make.sendKeys(setf.getMake());
			model.sendKeys(setf.getModel());
		}
		invoice.sendKeys(setf.getInvoice());
		msrp.sendKeys(setf.getMsrp());
		bookValue.sendKeys(setf.getBookValue());
		saveButton.click();
		Optional.ofNullable(alert(4)).ifPresent(Alert::dismiss);
		switchWindow(5);
		return this;
	}

	public boolean isApplicationSaved() {
		if (isElementPresent(confirmMessage, 5)) {
			closeButton.click();
			TestRunner.line = "Application saved Succesfully............... Details below..........";
			System.err.println(TestRunner.line);
			TestRunner.line = ExcelReaderUtil.setf.toString();
			System.out.println(TestRunner.line);
			ExcelReaderUtil.updateSheet("Y");
			defaultWindow();
			return true;
		}
		TestRunner.line = "Application not Saved............... Details below..........";
		System.err.println(TestRunner.line);
		TestRunner.line = ExcelReaderUtil.setf.toString();
		System.out.println(TestRunner.line);
		ExcelReaderUtil.setf.setErrors(errorMessages.stream().map(WebElement::getText)
				.filter(errors -> !errors.isEmpty()).collect(Collectors.toList()));
		pageRefresh();
		new HomePage(driver).openApplicationEntry();
		return false;
	}
}
