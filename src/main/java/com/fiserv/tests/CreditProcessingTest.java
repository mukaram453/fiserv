package com.fiserv.tests;

import java.util.Optional;

import javax.swing.JPanel;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.codoid.products.fillo.Recordset;
import com.fiserv.model.SETF;
import com.fiserv.pageobjects.CustomerPage;
import com.fiserv.pageobjects.DealerSearchPage;
import com.fiserv.pageobjects.HomePage;
import com.fiserv.pageobjects.LoginPage;
import com.fiserv.utils.ExcelReaderUtil;

public class CreditProcessingTest extends BaseTest {

	boolean isDuplicate = false;
	SETF setf;
	LoginPage loginPage;
	DealerSearchPage dealerSearchPage;
	CustomerPage customerPage;
	HomePage homePage;
	final JPanel panel = new JPanel();

	@BeforeClass
	public void beforeClass() {
		loginPage = new LoginPage(driver);
		dealerSearchPage = new DealerSearchPage(driver);
		customerPage = new CustomerPage(driver);
		homePage = loginPage.login(user, password, env);
		homePage.openApplicationEntry();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
		setf = ExcelReaderUtil.setFields();
		isDuplicate = dealerSearchPage.searchDealer();
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		dealerSearchPage.updateDuplicate(isDuplicate);
		isDuplicate = false;
		ExcelReaderUtil.moveToNextRow();
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		cleanUp();
	}

	@Test(dataProvider = "getLeaseDataFromExcel")
	public void creditProcessingLeaseTest() throws Exception {
		Optional.ofNullable(isDuplicate ? null : "")
				.orElseThrow(() -> new SkipException("Duplicate Application....Skipping this test case..... " + setf));
		Assert.assertTrue(isAppilcationSaved(), "Application not saved............... Details.........." + setf);
	}

	private boolean isAppilcationSaved() {
		return customerPage.enterCustomerDetails().isApplicationSaved();
	}

	@DataProvider
	public Object[][] getLeaseDataFromExcel() {
		return getData();
	}

	private Object[][] getData() {
		Recordset recordset = null;
		try {
			recordset = excelReaderUtil.readSheet(System.getProperty("sheet_name"));
		} catch (Exception e1) {
			closeBrowser();
		}
		Object[][] iterationData = null;
		try {
			if (recordset == null) {
				TestRunner.line = "No records in the excel in Processed = N ...Hence exiting.....";
				System.err.println(TestRunner.line);
				cleanUp();
			}
			int size = Boolean.parseBoolean(isdemo) ? 1 : recordset.getCount();
			iterationData = new Object[size][0];
		} catch (Exception e) {
			TestRunner.line = "Exception in data provider.....";
			System.out.println(TestRunner.line);
		}
		return iterationData;
	}

	private void cleanUp() {
		ExcelReaderUtil.closeExcel();
		closeBrowser();
	}

}
