package scenarios;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.PrintsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import resources.PerformLogin;
import resources.ScreenshotUtil;

public class Scenario1 {

	public Properties prop;

	public void runScenario(WebDriver driver, ExtentReports reports, ExtentTest test) throws Exception {

		// Load properties from the configuration file
		prop = new Properties();
		String propertiesPath = System.getProperty("user.dir") + "/src/main/java/resources/config.properties";
		FileInputStream fis = new FileInputStream(propertiesPath);
		prop.load(fis);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		// wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Resources")));
		try {
			PerformLogin performLogin = new PerformLogin();
			performLogin.login(driver, reports, test, "Scenario1");

			// Performing actions and logging them
			ScreenshotUtil.takeScreenshot(driver, "Scenario1", "BeforeClickingResources");
			Thread.sleep(2000);
			test.info("Clicking on 'Resources'");
			driver.findElement(By.linkText("Resources")).click();
			test.pass("Clicked on 'Resources'");

			test.info("Clicking on category icon");
			wait.until(ExpectedConditions.elementToBeClickable(By.className("categoryIcon_5ebd5061")));
			ScreenshotUtil.takeScreenshot(driver, "Scenario1", "BeforeClickingAcademicsClassesRegistration");
			Thread.sleep(2000);
			driver.findElement(By.className("categoryIcon_5ebd5061")).click();
			Thread.sleep(2000);
			ScreenshotUtil.takeScreenshot(driver, "Scenario1", "AfterClickingAcademicsClassesRegistration");
			Thread.sleep(2000);
			test.pass("Clicked on category icon");

			test.info("Clicking on 'My Transcript'");
			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("My Transcript")));
			ScreenshotUtil.takeScreenshot(driver, "Scenario1", "BeforeClickingMyTranscript");
			Thread.sleep(2000);
			driver.findElement(By.linkText("My Transcript")).click();
			test.pass("Clicked on 'My Transcript'");

			// Switching window and logging
			test.info("Switching to new window");
			String originalWindow = driver.getWindowHandle();
			for (String windowHandle : driver.getWindowHandles()) {
				if (!originalWindow.contentEquals(windowHandle)) {
					driver.switchTo().window(windowHandle);
					test.pass("Switched to new window");
					 driver.switchTo().window(originalWindow).close();
					 driver.switchTo().window(windowHandle);
					break;
				}
			}

			// Log in actions
			test.info("Logging in");
			byte[] decodedpass = Base64.decodeBase64(prop.getProperty("NEU_PASSWORD"));
			String mypass = new String(decodedpass);
			Thread.sleep(5000);

			Thread.sleep(2000);
			ScreenshotUtil.takeScreenshot(driver, "Scenario1", "BeforeLogin");
			Thread.sleep(2000);
			WebElement user = driver.findElement(By.name("j_username"));
			user.sendKeys(prop.getProperty("NEU_USER"));
			WebElement pass = driver.findElement(By.name("j_password"));
			pass.sendKeys(mypass);
			Thread.sleep(2000);
			ScreenshotUtil.takeScreenshot(driver, "Scenario1", "AfterLogin");
			Thread.sleep(2000);
			driver.findElement(By.name("_eventId_proceed")).click();

			driver.switchTo().frame("duo_iframe");
			wait.until(ExpectedConditions.elementToBeClickable(By.name("dampen_choice")));
			ScreenshotUtil.takeScreenshot(driver, "Scenario1", "BeforeDuo");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//button[normalize-space(text())='Send Me a Push']")).click();
			Thread.sleep(10000);
			test.pass("Logged in successfully");

			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='submit']")));
			String expectedTitle = "Academic Transcript Options";
			String actualTitle = driver.getTitle();
			try {
				Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
				test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			} catch (AssertionError e) {
				test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
				throw e;
			}
			ScreenshotUtil.takeScreenshot(driver, "Scenario1", "BeforeSelectingDropdown");
			Thread.sleep(2000);
			// Selecting options from dropdown
			test.info("Selecting 'Graduate' from dropdown");
			WebElement dropdownElement1 = driver.findElement(By.id("levl_id"));
			Select dropdown1 = new Select(dropdownElement1);
			dropdown1.selectByVisibleText("Graduate");
			Thread.sleep(3000);
			ScreenshotUtil.takeScreenshot(driver, "Scenario1", "AfterSelectingDropdown");
			Thread.sleep(2000);
			driver.findElement(By.cssSelector("input[type='submit'][value='Submit']")).click();
			test.pass("Selected 'Graduate' and submitted");
			expectedTitle = "Academic Transcript";
			actualTitle = driver.getTitle();
			try {
				Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
				test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			} catch (AssertionError e) {
				test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
				throw e;
			}
			// Printing to PDF
			test.info("Printing transcript to PDF");
			Pdf pdf = ((PrintsPage) driver).print(new PrintOptions());
			Files.write(Paths.get("./transcript.pdf"), OutputType.BYTES.convertFromBase64Png(pdf.getContent()));
			test.pass("Transcript printed to PDF");

			// Mark the test as passed in the report
			test.pass("Scenario 1 : PASSED");
			System.out.println("Done with Scenario - 1");
		} catch (Exception e) {
			// Mark the scenario as failed in the Extent Report
			test.fail("Test failed with exception: " + e.getMessage());
			throw e;
		}
	}
}
