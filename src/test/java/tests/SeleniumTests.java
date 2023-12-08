package tests;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import resources.Base;
import resources.PerformLogin;
import scenarios.Scenario1;
import scenarios.Scenario2;
import scenarios.Scenario3;
import scenarios.Scenario4;
import scenarios.Scenario5;

public class SeleniumTests {

	WebDriver driver = null;
	ExtentHtmlReporter reporter;
	ExtentReports reports;
	ExtentTest testScenario1;
	ExtentTest testScenario2;
	ExtentTest testScenario3;
	ExtentTest testScenario4;
	ExtentTest testScenario5;

	@BeforeSuite(enabled = true)
	public void beforeSuite() throws Exception {
		// Setting up the Extent Report for logging test results
		reporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/testReport.html");
		reports = new ExtentReports();
		reports.attachReporter(reporter);

		// Configuring the appearance and properties of the report
		reporter.config().setChartVisibilityOnOpen(true);
		reporter.config().setDocumentTitle("Selenium Test Report - Group 4");
		reporter.config().setReportName("Test Report");
		reporter.config().setTestViewChartLocation(ChartLocation.TOP);
		reporter.config().setTheme(Theme.STANDARD);
		reporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		if (driver == null) {
			// Initializing the WebDriver
			driver = Base.initializeBrowser(driver);
		}
	}

	// This method is executed before each test method and test class
	@BeforeMethod(enabled = true)
	@BeforeTest(enabled = true)
	public void beforeTest() throws Exception {
//		if (driver == null) {
//			// Initializing the WebDriver
//			driver = Base.initializeBrowser(driver);
//		}
	}

	// Test methods for different scenarios, each with a specified priority

	@Test(priority = 0)
	public void Scenario1Test() throws IOException {
		// Implementing test scenario 1
		try {
			// Create a new test in the Extent Report for Scenario1
			testScenario1 = reports.createTest("Scenario 1 : Download the latest transcript");

			Scenario1 scenario1 = new Scenario1();
			scenario1.runScenario(driver, reports, testScenario1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 1)
	public void Scenario3Test() throws IOException {
		// Implementing test scenario 2
		try {
			// Create a new test in the Extent Report for Scenario3
			testScenario3 = reports.createTest("Scenario 2 : Add two To-Do tasks for yourself");

			Scenario3 scenario3 = new Scenario3();
			scenario3.runScenario(driver, reports, testScenario3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 2)
	public void Scenario2Test() throws IOException {
		// Implementing test scenario 3
		try {
			// Create a new test in the Extent Report for Scenario2
			testScenario2 = reports.createTest("Scenario 3 : Download a classroom guide");

			Scenario2 scenario2 = new Scenario2();
			scenario2.runScenario(driver, reports, testScenario2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 3)
	public void Scenario4Test() throws IOException {
		// Implementing test scenario 4
		try {
			// Create a new test in the Extent Report for Scenario4
			testScenario4 = reports.createTest("Scenario 4 : Download a DATASET");

			Scenario4 scenario4 = new Scenario4();
			scenario4.runScenario(driver, reports, testScenario4);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 4)
	public void Scenario5Test() throws IOException {
		// Implementing test scenario 5
		try {
			// Create a new test in the Extent Report for Scenario5
			testScenario5 = reports.createTest("Scenario 5 : Update the Academic calendar");

			Scenario5 scenario5 = new Scenario5();
			scenario5.runScenario(driver, reports, testScenario5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	// This method is executed after each test method and test class
	@AfterMethod(enabled = true)
	@AfterTest(enabled = true)
	public void afterTest() {
		// Closing the WebDriver and releasing resources
		if (driver != null) {
			driver.quit();
		}
		driver = null;
		// Finalizing the test report
		reports.flush();
	}
	*/
	
	@AfterSuite(enabled = true)
	public void afterSuite() {
	    // Closing the WebDriver and releasing resources
	    if (driver != null) {
	        driver.quit();
	    }
	    driver = null;

	    // Finalizing the test report
	    reports.flush();
	}


}
