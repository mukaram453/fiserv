package com.fiserv.tests;

import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.os.WindowsUtils;
import org.testng.annotations.Listeners;

import com.fiserv.listeners.Listener;
import com.fiserv.pageobjects.HomePage;
import com.fiserv.pageobjects.LoginPage;
import com.fiserv.utils.DriverFactory;
import com.fiserv.utils.ExcelReaderUtil;

@Listeners(Listener.class)
public abstract class BaseTest {

	public static WebDriver driver;
	protected static Properties config_Properties = new Properties();
	protected String url, user, password, env, file, isdemo;
	protected ExcelReaderUtil excelReaderUtil;
	LoginPage loginPage;
	HomePage homePage;
	String[] processes = { "IEDriverServer.exe", "iexplore.exe", "EXCEL.EXE" };

	public BaseTest() {
		readConfiguration(getClass().getResourceAsStream("/config.properties"));
		getConfig();
		getDriver(url);
		try {
			excelReaderUtil = new ExcelReaderUtil(System.getProperty("excel_file_path").trim());
		} catch (Exception e) {
			closeBrowser();
		}
	}

	public void closeBrowser() {
		Optional.ofNullable(driver).ifPresent(WebDriver::quit);
		driver = null;
	}

	private void getConfig() {
		this.url = config_Properties.getProperty("url");
		this.user = System.getProperty("user").trim();
		this.password = System.getProperty("password").trim();
		this.env = config_Properties.getProperty("env").trim();
		this.isdemo = config_Properties.getProperty("isdemo").trim();
	}

	public WebDriver getDriver(String baseUrl) {
		if (driver == null) {
			killProcesses(processes);
			driver = DriverFactory.getDriver(baseUrl);
			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		}
		return driver;
	}

	private void killProcesses(String... processes) {
		Stream.of(processes).forEach(WindowsUtils::killByName);
	}

	private Properties readConfiguration(InputStream file) {
		try {
			config_Properties.load(file);
		} catch (Exception e) {
			TestRunner.line = "Cannot read file " + e.getMessage();
			System.out.println(TestRunner.line);
		}
		return config_Properties;
	}

}
