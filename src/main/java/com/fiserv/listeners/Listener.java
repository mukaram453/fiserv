package com.fiserv.listeners;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.fiserv.model.SETF;
import com.fiserv.tests.BaseTest;
import com.fiserv.tests.TestRunner;
import com.fiserv.utils.ExcelReaderUtil;

public class Listener implements ITestListener {
	static String separator = File.separator;

	static String path = System.getProperty("user.dir") + separator + "SCREENSHOTS" + separator;

	@Override
	public void onTestFailure(ITestResult result) {
		takeScreenShot(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		takeScreenShot(result);
	}

	public static String makeDirectory() {
		String today = path + LocalDate.now().toString();
		File file = new File(today);
		if (!file.exists()) {
			file.mkdir();
		}
		return today;
	}

	public void takeScreenShot(ITestResult result) {
		if (BaseTest.driver != null) {
			String filePath = makeDirectory();
			SETF setf = ExcelReaderUtil.setf;
			Optional.ofNullable(setf.getSheet()).ifPresent(str -> str.replaceAll(" ", "_"));
			String screenshot = (setf.getSsn1() == null ? result.getName().toString().trim()
					: setf.getSheet() + "_" + setf.getSsn1() + "-" + setf.getSsn2() + "-" + setf.getSsn3()) + "-"
					+ System.currentTimeMillis();
			File scrFile = ((TakesScreenshot) BaseTest.driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(scrFile, new File(filePath + separator + screenshot + ".png"));
			} catch (IOException e) {
				System.out.println(TestRunner.line);
			}
		}
	}

	@Override
	public void onTestStart(ITestResult result) {

	}

	@Override
	public void onTestSuccess(ITestResult result) {
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext context) {
	}
}
