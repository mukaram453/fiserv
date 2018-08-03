package com.fiserv.pageobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Page {
	private static final String BASE_URL = "";
	Actions action;
	public WebDriver driver;

	@FindBy(id = "spanLogoff")
	private WebElement logOffButton;

	public Page(WebDriver driver) {
		this.driver = driver;
		action = new Actions(driver);
	}

	public void openBrowser(String url) {
		driver.get(url);
	}

	public String getPageUrl() {

		return driver.getCurrentUrl();
	}

	public String getBaseURL() {

		return BASE_URL;
	}

	public boolean isAt(String pageUrl) {
		String actualUrl = getPageUrl();
		return actualUrl.contains(pageUrl);
	}

	public void logOff() {
		logOffButton.click();
	}

	public List<WebElement> getElementsByLocator(String locator) {
		By by;
		if (locator.startsWith("//")) {
			by = By.xpath(locator);
		} else {
			by = By.cssSelector(locator);
		}
		fluentWait(by);
		return driver.findElements(by);
	}

	public WebElement getElementFromListContainingText(List<WebElement> elements, String elementText) {

		if (elements != null && elements.size() > 0) {
			for (WebElement element : elements) {
				if (element.getText().contains(elementText)) {
					return element;
				}
			}
		}

		return null;
	}

	public String getListElementText(List<String> elements, String elementStr) {
		if (elements.indexOf(elementStr) == -1) {
			return "not found!";
		} else {
			String element = elements.get(elements.indexOf(elementStr)).trim();
			return element;
		}
	}

	public List<String> getElementsTextsByLocator(String locator) {
		List<WebElement> listElements = getElementsByLocator(locator.trim());
		List<String> listElementsTexts = new ArrayList<String>();
		for (WebElement listElement : listElements) {
			listElementsTexts.add(listElement.getText().trim());
		}
		return listElementsTexts;
	}

	protected void waitUntil(ExpectedCondition<?> expectedCondition, int timeoutSeconds) {
		new WebDriverWait(driver, timeoutSeconds).until(expectedCondition);
	}

	public WebElement fluentWaitForTextInElement(final WebElement element) {
		Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(driver -> {
			return (element.getAttribute("value").length() > 0) ? element : null;
		});

		return foo;
	}

	public WebElement waitUntil(final By by, int timeout) {
		return isElement(by, timeout);
	}

	private WebElement isElement(final By by, int timeout) {
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			return new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(by));
		} finally {
			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		}
	}

	public boolean isElementPresent(final By by, int timeout) {
		try {
			return isElement(by, timeout).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void switchWindow(int timeout) {
		waitInSec(timeout);
		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			driver.switchTo().window(window);
			driver.manage().window().maximize();
		}
	}

	public void pageRefresh() {
		driver.navigate().refresh();
		checkDuplicateSession();
		defaultWindow();
	}

	public void checkDuplicateSession() {
		By by = By.cssSelector("[ig_b='modalActiveSessionWarning_tmpl_BtnSessionOk']");
		boolean duplicateSession = isElementPresent(by, 4);
		if (duplicateSession) {
			driver.findElement(by).click();
		}
	}

	public void waitForPageLoaded(int timeout) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(expectation);
		} catch (Throwable error) {
			System.out.println("Timeout waiting for Page Load Request to complete.");
		}
	}

	public void defaultWindow() {
		driver.switchTo().defaultContent();
		waitInSec(5);
	}

	public void switchFrame(String frame, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
	}

	public Alert alert(int timeout) {
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			Alert alertPresent = new WebDriverWait(driver, timeout).until(ExpectedConditions.alertIsPresent());
			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			return alertPresent;
		} catch (Exception e) {
			return null;
		}
	}

	public WebElement waitUntil(final WebElement element, int timeout) {
		return isElement(element, timeout);
	}

	private WebElement isElement(final WebElement element, int timeout) {
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			return new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
		} finally {
			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		}
	}

	public boolean isElementPresent(final WebElement element, int timeout) {
		try {
			return isElement(element, timeout).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public WebElement fluentWait(final By locator) {
		Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(driver -> {
			return driver.findElement(locator);
		});

		return foo;
	}

	public WebElement fluentWait(final WebElement element) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(driver -> {
			return element;
		});

		return foo;
	}

	public void switchToTab(int windowNumber) {
		waitInSec(2);
		List<String> windowHandles;
		int i = 0;
		do {
			i++;
			windowHandles = new ArrayList<>(driver.getWindowHandles());
		} while (windowHandles.size() < windowNumber && i < 10);

		switchToWindow(windowHandles.get(windowNumber - 1));
	}

	public void repeatUntil(Runnable action, ExpectedCondition<?> expectedCondition, int numberOfRepeats,
			int timeToWaitInSeconds) {
		int numberOfTries = 0;
		do {
			numberOfTries++;
			action.run();
			try {
				waitUntil(expectedCondition, timeToWaitInSeconds);
				if (numberOfTries > 1) {
				}
				return;
			} catch (Exception e) {
				// ignore
			}
		} while (numberOfTries < numberOfRepeats);
	}

	public void waitInSec(int sec) {

		try {
			TimeUnit.SECONDS.sleep(sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public <T extends Page> T goToPreviousPage(Class<T> clazz) throws RuntimeException {
		driver.navigate().back();
		T page = null;
		try {
			page = clazz.getDeclaredConstructor(WebDriver.class).newInstance(driver);
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return page;
	}

	public Page pressEnter(int sec) {
		action.sendKeys(Keys.ENTER).build().perform();
		waitInSec(sec);
		return this;
	}

	public Page goToPreviousPage() {
		driver.navigate().back();
		return this;
	}

	public List<String> getDropdownValues(WebElement element) {
		List<WebElement> webElements = new Select(element).getOptions();
		List<String> elementTexts = new ArrayList<>();
		for (WebElement el : webElements) {
			elementTexts.add(el.getText().trim());
		}
		return elementTexts;
	}

	public List<String> getDropdownValues(String selectId) {
		WebElement e = driver.findElement(By.id(selectId));
		List<WebElement> webElements = new Select(e).getOptions();
		List<String> elementTexts = new ArrayList<>();
		for (WebElement el : webElements) {
			if (!"".equals(el.getText())) {
				elementTexts.add(el.getText().trim());
			}
		}
		return elementTexts;
	}

	public <T extends Page> T selectDropdownValue(String selectId, String value, Class<T> clazz)
			throws RuntimeException {
		WebElement el = driver.findElement(By.id(selectId));
		Select select = new Select(el);
		select.selectByVisibleText(value);
		T page = null;
		try {
			page = clazz.getDeclaredConstructor(WebDriver.class).newInstance(driver);
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return page;
	}

	public void switchToWindow(String handle) {
		driver.switchTo().window(handle);
	}

}
