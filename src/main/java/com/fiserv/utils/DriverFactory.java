package com.fiserv.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;

public class DriverFactory {
	public static final String BROWSER = "internetexplorer".toLowerCase();

	public static WebDriver getDriver(String baseUrl) {

		switch (BROWSER) {
		case "chrome":
			return getChromeDriver();
		case "firefox":
			return getFirefoxDriver();
		default:
			return getInternetExplorerDriver(baseUrl);
		}
	}

	private static WebDriver getInternetExplorerDriver(String baseUrl) {
		InternetExplorerDriverManager.getInstance().setup();
		InternetExplorerOptions options = new InternetExplorerOptions();
		options.introduceFlakinessByIgnoringSecurityDomains();
		options.ignoreZoomSettings();
		options.requireWindowFocus();
		options.addCommandSwitches("--start-maximized");
		options.withInitialBrowserUrl(baseUrl);
		return new InternetExplorerDriver(options);
	}

	private static WebDriver getFirefoxDriver() {
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "target/firefox_logs.txt");
		FirefoxDriverManager.getInstance().setup();
		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("--start-maximized");
		return new FirefoxDriver(options);
	}

	private static WebDriver getChromeDriver() {
		ChromeDriverManager.getInstance().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		return new ChromeDriver(options);
	}
}
