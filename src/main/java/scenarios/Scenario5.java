package scenarios;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

import resources.PerformLogin;
import resources.ScreenshotUtil;

public class Scenario5 {

	public Properties prop;

	public void runScenario(WebDriver driver, ExtentReports reports, ExtentTest test) throws Exception {

		// Load properties from the configuration file
		prop = new Properties();
		String propertiesPath = System.getProperty("user.dir") + "/src/main/java/resources/config.properties";
		FileInputStream fis = new FileInputStream(propertiesPath);
		prop.load(fis);

		// Set up WebDriverWait for dynamic element handling
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		try {
			/*
			PerformLogin performLogin = new PerformLogin();
			performLogin.login(driver, reports, test, "Scenario5");
			*/
			driver.get(prop.getProperty("neu_url"));
			test.pass("Successfully navigated to the NEU login page");
			
			// Navigate to and click on 'Resources'
			test.info("Clicking on 'Resources' link");
			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Resources")));
			ScreenshotUtil.takeScreenshot(driver, "Scenario5", "BeforeClickingResources");
			Thread.sleep(2000);
			driver.findElement(By.linkText("Resources")).click();
			test.pass("Clicked on 'Resources'");

			// Click on a specific category icon
			test.info("Clicking on category icon");
			wait.until(ExpectedConditions.elementToBeClickable(By.className("categoryIcon_5ebd5061")));
			ScreenshotUtil.takeScreenshot(driver, "Scenario5", "BeforeClickingAcademicsClassesRegistration");
			driver.findElement(By.className("categoryIcon_5ebd5061")).click();
			Thread.sleep(2000);
			ScreenshotUtil.takeScreenshot(driver, "Scenario5", "AfterClickingAcademicsClassesRegistration");
			Thread.sleep(2000);
			test.pass("Clicked on category icon");

			// Navigate to the 'Academic Calendar' section
			test.info("Navigating to 'Academic Calendar'");
			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Academic Calendar")));
			ScreenshotUtil.takeScreenshot(driver, "Scenario5", "BeforeClickingAcademicCalendar");
			Thread.sleep(2000);

			driver.findElement(By.linkText("Academic Calendar")).click();
			test.pass("Navigated to 'Academic Calendar'");

			// Switch to the new window that opens after clicking the link
			test.info("Switching to the new window");
			String originalWindow = driver.getWindowHandle();
			for (String windowHandle : driver.getWindowHandles()) {
				if (!originalWindow.contentEquals(windowHandle)) {
					driver.switchTo().window(windowHandle);
					test.pass("Switched to the new window");
					break;
				}
			}

			// Click on a specific link within the academic calendar
			test.info("Clicking on a specific link within the Academic Calendar");
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='academic-calendar/']")));
			ScreenshotUtil.takeScreenshot(driver, "Scenario5", "BeforeClickingAcademicCalendarLink");
			Thread.sleep(2000);
			String expectedTitle = "Calendar - Office of the University Registrar at Northeastern University";
			String actualTitle = driver.getTitle();
			try {
				Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
				test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			} catch (AssertionError e) {
				test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
				throw e;
			}
			driver.findElement(By.cssSelector("a[href*='academic-calendar/']")).click();
			test.pass("Clicked on the specific link");
			Thread.sleep(2000);
			ScreenshotUtil.takeScreenshot(driver, "Scenario5", "AfterClickingAcademicCalendarLink");
			Thread.sleep(2000);

			// Scroll and interact with elements within an iframe
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0, 800)", "");
			driver.switchTo().frame("trumba.spud.6.iframe");
			WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("mixItem0")));
			expectedTitle = "Academic Calendar - Office of the University Registrar at Northeastern University";
			actualTitle = driver.getTitle();
			try {
				Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
				test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			} catch (AssertionError e) {
				test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
				throw e;
			}
			ScreenshotUtil.takeScreenshot(driver, "Scenario5", "BeforeCheckingUnderGrad");
			Thread.sleep(2000);
			checkbox.click();
			Thread.sleep(3000);
			ScreenshotUtil.takeScreenshot(driver, "Scenario5", "AfterCheckingUnderGrad");
			Thread.sleep(2000);
			driver.switchTo().defaultContent();
			driver.switchTo().frame("trumba.spud.2.iframe");
			test.info("Interacting with elements in iframes");

			// Scroll to a specific element and ensure its visibility
			js.executeScript("window.scrollBy(0, 3500)", "");
			WebElement calendarButton = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl04_ctl90_ctl00_buttonAtmc")));
			js.executeScript("arguments[0].scrollIntoView(true);", calendarButton);
			wait.until(ExpectedConditions.visibilityOf(calendarButton));
			ScreenshotUtil.takeScreenshot(driver, "Scenario5", "VisibleAddToCalendar");
			Thread.sleep(2000);
			test.pass("Scrolled to and ensured visibility of the calendar button");
			Thread.sleep(3000);

			// Mark the scenario as passed in the Extent Report
			test.pass("Scenario 5 : PASSED");
			System.out.println("Done with Scenario - 5");
		} catch (Exception e) {
			// Mark the scenario as failed in the Extent Report
			test.fail("Test failed with exception: " + e.getMessage());
			throw e;
		}
	}

}
