package resources;

import java.io.IOException;
import java.time.Duration;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class PerformLogin extends Base {

	public void login(WebDriver driver, ExtentReports reports, ExtentTest test, String scenarioName)
			throws IOException, Exception {

		test.info("Starting the login process");

		// Navigate to the Northeastern University login URL from properties file
		test.info("Navigating to the NEU login page");
		driver.get(prop.getProperty("neu_url"));
		test.pass("Successfully navigated to the NEU login page");
		Thread.sleep(2000);

		// Initialize WebDriverWait to handle dynamic elements on the page
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		ScreenshotUtil.takeScreenshot(driver, scenarioName, "BeforeIdPassword");
		Thread.sleep(2000);
		// Wait until the login button is clickable and then click it
		test.info("Waiting for the login button to be clickable");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
		// Assert and report the outcome
		String expectedTitle = "Sign in to your account";
		String actualTitle = driver.getTitle();
		try {
			Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
			test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
		} catch (AssertionError e) {
			test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			throw e;
		}
		test.pass("Login button is clickable");

		// Enter the username (email) in the email field and proceed
		test.info("Entering username");
		WebElement email = driver.findElement(By.id("i0116"));
		email.sendKeys(prop.getProperty("NEU_USERNAME"));
		driver.findElement(By.id("idSIButton9")).click();
		test.pass("Username entered and submitted");
		Thread.sleep(2000);

		// Wait until the password field is ready, decode the password and enter it
		test.info("Waiting for the password field");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));

		// Decode and enter the password
		test.info("Decoding and entering password");
		WebElement pass = driver.findElement(By.id("i0118"));
		byte[] decodedpass = Base64.decodeBase64(prop.getProperty("NEU_PASSWORD"));
		String mypass = new String(decodedpass);
		Thread.sleep(2000);
		ScreenshotUtil.takeScreenshot(driver, scenarioName, "AfterIdPassword");
		Thread.sleep(2000);

		// Enter the decoded password and click the submit button
		pass.sendKeys(mypass);
		driver.findElement(By.id("idSIButton9")).click();
		test.pass("Password entered and submitted");
		Thread.sleep(10000);

		// Handle 'trust browser' prompt if it appears
		test.info("Handling 'trust browser' prompt");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("trust-browser-button")));
		ScreenshotUtil.takeScreenshot(driver, scenarioName, "BeforeDuoAuthentication");
		Thread.sleep(2000);
		driver.findElement(By.id("trust-browser-button")).click();
		test.pass("Handled 'trust browser' prompt");
		Thread.sleep(5000);
		ScreenshotUtil.takeScreenshot(driver, scenarioName, "AfterDuoAuthentication");
		Thread.sleep(2000);

		// Click the 'back' button if it appears
		test.info("Clicking the 'back' button");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idBtn_Back")));
		driver.findElement(By.id("idBtn_Back")).click();
		test.pass("Clicked the 'back' button");
		Thread.sleep(10000);

		// Close any pop-up modals
		test.info("Closing any pop-up modals");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Close popup modal']")));
		ScreenshotUtil.takeScreenshot(driver, scenarioName, "BeforeClosingPopup");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[@aria-label='Close popup modal']")).click();
		Thread.sleep(2000);
		ScreenshotUtil.takeScreenshot(driver, scenarioName, "AfterClosingPopup");
		Thread.sleep(2000);
		test.pass("Closed pop-up modals");
		expectedTitle = "Student Hub - Home";
		actualTitle = driver.getTitle();
		try {
			Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
			test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
		} catch (AssertionError e) {
			test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			throw e;
		}

		// Finalize the test log
		test.pass("Login process completed successfully");
		Thread.sleep(10000);
	}

}
