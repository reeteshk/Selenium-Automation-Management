package scenarios;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import resources.ScreenshotUtil;

public class Scenario4 {

	public Properties prop;

	public void runScenario(WebDriver driver, ExtentReports reports, ExtentTest test) throws Exception {

		// Load properties from the configuration file
		prop = new Properties();
		String propertiesPath = System.getProperty("user.dir") + "/src/main/java/resources/config.properties";
		FileInputStream fis = new FileInputStream(propertiesPath);
		prop.load(fis);

		try {
			// Navigate to the One Search Library URL specified in the properties file
			test.info("Navigating to the One Search Library URL");
			driver.get(prop.getProperty("onesearchlibraryurl"));
			test.pass("Successfully navigated to the One Search Library URL");
			Thread.sleep(5000);
			String expectedTitle = "SOS";
			String actualTitle = driver.getTitle();
			try {
				Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
				test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			} catch (AssertionError e) {
				test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
				throw e;
			}

			// Initialize WebDriverWait to handle dynamic element interactions
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

			// Click on the link to access the digital repository service
			test.info("Clicking on the Digital Repository Service link");
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//a[@aria-label='digital repository service, opens in a new window']")));
			ScreenshotUtil.takeScreenshot(driver, "Scenario4", "BeforeDigitalRepositoryService");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//a[@aria-label='digital repository service, opens in a new window']"))
					.click();
			test.pass("Clicked on the Digital Repository Service link");

			// Switch to the new window that opens after clicking the link
			test.info("Switching to the new window");
			String originalWindow = driver.getWindowHandle();
			for (String windowHandle : driver.getWindowHandles()) {
				if (!originalWindow.contentEquals(windowHandle)) {
					driver.switchTo().window(windowHandle);
					test.pass("Switched to the new window");
					driver.switchTo().window(originalWindow).close();
					driver.switchTo().window(windowHandle);
					break;
				}
			}
			Thread.sleep(2000);

			// Navigate to the Datasets section
			test.info("Navigating to the Datasets section");
			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Datasets")));
			expectedTitle = "DRS";
			actualTitle = driver.getTitle();
			try {
				Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
				test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			} catch (AssertionError e) {
				test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
				throw e;
			}

			ScreenshotUtil.takeScreenshot(driver, "Scenario4", "AfterDigitalRepositoryService");
			Thread.sleep(2000);
			driver.findElement(By.linkText("Datasets")).click();
			test.pass("Navigated to the Datasets section");
			Thread.sleep(2000);

			// Perform a search for specific datasets as per the property file
			test.info("Performing a search for datasets");
			WebElement search = driver.findElement(By.cssSelector("input[placeholder='Search this featured content']"));
			ScreenshotUtil.takeScreenshot(driver, "Scenario4", "AfterClickingDatasets");
			Thread.sleep(2000);
			search.sendKeys(prop.getProperty("searchdatasets"));
			test.pass("Search performed for datasets");
			Thread.sleep(5000);

			// Click on the search submit button
			test.info("Clicking on the search submit button");
			wait.until(ExpectedConditions
					.elementToBeClickable(By.cssSelector("button.btn.btn-info#search-submit-header")));
			List<WebElement> buttons = driver.findElements(By.cssSelector("button.btn.btn-info#search-submit-header"));
			if (!buttons.isEmpty()) {
				ScreenshotUtil.takeScreenshot(driver, "Scenario4", "BeforeClickingSearchforCSV");
				Thread.sleep(2000);
				buttons.get(1).click();
				test.pass("Clicked on the search submit button");
			} else {
				System.out.println("No buttons found");
				test.fail("No search submit button found");
			}
			Thread.sleep(2000);

			// Download the dataset by clicking on the Zip File link
			test.info("Downloading the dataset");
			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Zip File")));
			ScreenshotUtil.takeScreenshot(driver, "Scenario4", "AfterClickingSearchforCSV");
			Thread.sleep(2000);
			driver.findElement(By.linkText("Zip File")).click();
			test.pass("Dataset downloaded successfully");
			Thread.sleep(15000);

			// Mark the scenario as passed in the Extent Report
			test.pass("Scenario 4 : PASSED");
			System.out.println("Done with Scenario - 4");
		} catch (Exception e) {
			// Mark the scenario as failed in the Extent Report
			test.fail("Test failed with exception: " + e.getMessage());
			throw e;
		}
	}

}
