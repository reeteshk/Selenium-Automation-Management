package scenarios;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.PrintsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import resources.ScreenshotUtil;

public class Scenario2 {

	public Properties prop;

	public void runScenario(WebDriver driver, ExtentReports reports, ExtentTest test) throws Exception {

		// Load properties from the configuration file
		prop = new Properties();
		String propertiesPath = System.getProperty("user.dir") + "/src/main/java/resources/config.properties";
		FileInputStream fis = new FileInputStream(propertiesPath);
		prop.load(fis);

		// Log and navigate to the specified URL
		test.info("Navigating to the Classroom Guide URL");
		driver.get(prop.getProperty("classroomguideurl"));

		// Initialize WebDriverWait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

		try {
			String expectedTitle = "Classroom Status - Northeastern Tech Service Portal";
			String actualTitle = driver.getTitle();
			try {
				Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
				test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			} catch (AssertionError e) {
				test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
				throw e;
			}

			// Wait for a specific element to be clickable and then click it
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0, 4000)", "");
			Thread.sleep(5000);
			test.info("Waiting for Classroom Details link and clicking");
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//a[@href='/tech?id=classroom_details&classroom=9ac92fb397291d905a68bd8c1253afd0']")));
			ScreenshotUtil.takeScreenshot(driver, "Scenario3", "BeforeClickingBehrakisClass");
			Thread.sleep(2000);
			driver.findElement(
					By.xpath("//a[@href='/tech?id=classroom_details&classroom=9ac92fb397291d905a68bd8c1253afd0']"))
					.click();
			test.pass("Clicked on Classroom Details link");
			Thread.sleep(5000);

			// Wait for another element (PDF link) and click it
			test.info("Waiting for PDF link and clicking");
			wait.until(
					ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='Hybrid_Nuflex_Classroom.pdf']")));
			ScreenshotUtil.takeScreenshot(driver, "Scenario3", "AfterClickingBehrakisClass");
			Thread.sleep(2000);
			driver.findElement(By.cssSelector("a[href*='Hybrid_Nuflex_Classroom.pdf']")).click();
			test.pass("Clicked on PDF link");
			Thread.sleep(5000);
			
			/*
			// Switch to the new window opened after clicking the PDF link
			test.info("Switching to new window");
			String originalWindow = driver.getWindowHandle();
			for (String windowHandle : driver.getWindowHandles()) {
				if (!originalWindow.contentEquals(windowHandle)) {
					driver.switchTo().window(windowHandle);
					test.pass("Switched to new window");
					break;
				}
			}
			*/
			Thread.sleep(10000);

			// Mark the test as passed in the report
			test.pass("Scenario 2 : PASSED");
			System.out.println("Done with Scenario - 3");
		} catch (Exception e) {
			// Mark the scenario as failed in the Extent Report
			test.fail("Test failed with exception: " + e.getMessage());
			throw e;
		}
	}

}
