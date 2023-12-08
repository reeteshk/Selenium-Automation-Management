package scenarios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.io.File;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import resources.ScreenshotUtil;

public class Scenario3 {

	public Properties prop;

	public void runScenario(WebDriver driver, ExtentReports reports, ExtentTest test) throws Exception {

		// Load properties from the configuration file
		prop = new Properties();
		String propertiesPath = System.getProperty("user.dir") + "/src/main/java/resources/config.properties";
		String excelPath = System.getProperty("user.dir") + "/data.xlsx";
		FileInputStream fis = new FileInputStream(propertiesPath);
		prop.load(fis);

		// Navigate to the specified URL
		test.info("Navigating to the Canvas URL");
		driver.get(prop.getProperty("canvasurl"));
		test.pass("Successfully navigated to the Canvas URL");

		// Read data from the excel file
		test.info("Reading data from Excel file");
		FileInputStream inputstream = null;
		try {
			inputstream = new FileInputStream(new File(excelPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		XSSFWorkbook myworkbook = null;
		try {
			myworkbook = new XSSFWorkbook(inputstream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = myworkbook.getSheetAt(0);
		XSSFRow row = sheet.getRow(1);
		XSSFCell usernameCell = row.getCell(0);

		// Retrieve username, password, and other required data from the excel sheet
		String username = usernameCell.toString();
		XSSFCell shortUsername = row.getCell(2);
		String shortUname = shortUsername.toString();
		XSSFCell passwordCell = row.getCell(1);
		String password = passwordCell.toString();
		test.pass("Data read from Excel file successfully");

		byte[] decodedpass = Base64.decodeBase64(password);
		String mypass = new String(decodedpass);
		Thread.sleep(2000);

		// Initialize WebDriverWait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		try {
			/*
			// Log in using the credentials from the excel sheet
			ScreenshotUtil.takeScreenshot(driver, "Scenario2", "BeforeLoginDetails");
			Thread.sleep(2000);
			test.info("Logging in with provided credentials");
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));

			WebElement email = driver.findElement(By.id("i0116"));
			email.sendKeys(username);
			driver.findElement(By.id("idSIButton9")).click();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));

			WebElement pass = driver.findElement(By.id("i0118"));
			pass.sendKeys(mypass);
			ScreenshotUtil.takeScreenshot(driver, "Scenario2", "AfterLoginDetails");
			Thread.sleep(2000);
			driver.findElement(By.id("idSIButton9")).click();
			test.pass("Logged in successfully");
			Thread.sleep(10000);

			// Additional steps for logging in (like handling two-factor authentication,
			// etc.)
			wait.until(ExpectedConditions.elementToBeClickable(By.id("trust-browser-button")));
			ScreenshotUtil.takeScreenshot(driver, "Scenario2", "BeforeDuo");
			Thread.sleep(2000);
			driver.findElement(By.id("trust-browser-button")).click();
			Thread.sleep(5000);

			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@data-report-value='Submit']")))
					.click();
			*/
			Thread.sleep(2000);
			ScreenshotUtil.takeScreenshot(driver, "Scenario2", "BeforeClickingCalendar");
			Thread.sleep(2000);
			wait.until(ExpectedConditions.elementToBeClickable(By.id("global_nav_calendar_link")));
			String expectedTitle = "Dashboard";
			String actualTitle = driver.getTitle();
			try {
				Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
				test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			} catch (AssertionError e) {
				test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
				throw e;
			}

			WebElement calenderButton = driver.findElement(By.id("global_nav_calendar_link"));
			calenderButton.click();
			Thread.sleep(2000);

			wait.until(ExpectedConditions.elementToBeClickable(By.id("create_new_event_link")));
			expectedTitle = "Calendar";
			actualTitle = driver.getTitle();
			try {
				Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
				test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			} catch (AssertionError e) {
				test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
				throw e;
			}

			ScreenshotUtil.takeScreenshot(driver, "Scenario2", "AfterClickingCalendar");
			Thread.sleep(2000);
			WebElement addButton = driver.findElement(By.id("create_new_event_link"));
			addButton.click();
			Thread.sleep(2000);

			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[a[text()='My To Do']]")));
			ScreenshotUtil.takeScreenshot(driver, "Scenario2", "AfterClickingToDo");
			Thread.sleep(2000);
			WebElement liElement = driver.findElement(By.xpath("//li[a[text()='My To Do']]"));
			liElement.click();
			Thread.sleep(2000);
			ScreenshotUtil.takeScreenshot(driver, "Scenario2", "BeforeTask1Details");
			Thread.sleep(2000);

			XSSFCell title = row.getCell(3);
			String Title = title.toString();
			WebElement titleFeild = driver.findElement(By.id("planner_note_title"));
			titleFeild.sendKeys(Title);
			Thread.sleep(2000);

			XSSFCell date = row.getCell(4);
			String Date = date.toString();
			WebElement DateFeild = driver.findElement(By.id("planner_note_date"));
			DateFeild.clear();
			Thread.sleep(2000);
			DateFeild.sendKeys(Date);
			Thread.sleep(2000);

			XSSFCell time = row.getCell(5);
			String Time = time.toString();
			WebElement TimeFeild = driver.findElement(By.id("planner_note_time"));
			TimeFeild.sendKeys(Time);
			Thread.sleep(2000);

			XSSFCell details = row.getCell(7);
			String Details = details.toString();
			WebElement DeatilsFeild = driver.findElement(By.id("details_textarea"));
			DeatilsFeild.sendKeys(Details);
			Thread.sleep(2000);
			ScreenshotUtil.takeScreenshot(driver, "Scenario2", "AfterTask1Details");
			Thread.sleep(2000);

			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//*[@id=\"edit_planner_note_form_holder\"]/form/div[2]/button")))
					.click();
			Thread.sleep(2000);

			WebElement addButton2 = driver.findElement(By.id("create_new_event_link"));
			addButton2.click();
			Thread.sleep(2000);

			WebElement liElement2 = driver.findElement(By.xpath("//li[a[text()='My To Do']]"));
			liElement2.click();
			Thread.sleep(2000);
			ScreenshotUtil.takeScreenshot(driver, "Scenario2", "BeforeTask2Details");
			Thread.sleep(2000);

			XSSFRow row2 = sheet.getRow(2);
			XSSFCell title2 = row2.getCell(3);
			String Title2 = title2.toString();
			WebElement titleFeild2 = driver.findElement(By.id("planner_note_title"));
			titleFeild2.sendKeys(Title2);
			Thread.sleep(2000);

			XSSFCell date2 = row2.getCell(4);
			String Date2 = date2.toString();
			WebElement DateFeild2 = driver.findElement(By.id("planner_note_date"));
			DateFeild2.clear();
			Thread.sleep(2000);
			DateFeild2.sendKeys(Date2);
			Thread.sleep(2000);

			XSSFCell time2 = row2.getCell(5);
			String Time2 = time2.toString();
			WebElement TimeFeild2 = driver.findElement(By.id("planner_note_time"));
			TimeFeild2.sendKeys(Time2);
			Thread.sleep(2000);

			XSSFCell details2 = row2.getCell(7);
			String Details2 = details2.toString();
			Thread.sleep(2000);
			WebElement DeatilsFeild2 = driver.findElement(By.id("details_textarea"));
			DeatilsFeild2.sendKeys(Details2);
			Thread.sleep(2000);
			ScreenshotUtil.takeScreenshot(driver, "Scenario2", "AfterTask2Details");
			Thread.sleep(2000);

			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//*[@id=\"edit_planner_note_form_holder\"]/form/div[2]/button")))
					.click();
			Thread.sleep(5000);

			// Close the Excel workbook to release resources
			test.info("Closing the Excel workbook");
			myworkbook.close();
			test.pass("Excel workbook closed successfully");

			// Mark the test as passed in the report
			test.pass("Scenario 3 : PASSED");
			System.out.println("Done with Scenario - 2");
		} catch (Exception e) {
			// Mark the scenario as failed in the Extent Report
			test.fail("Test failed with exception: " + e.getMessage());
			throw e;
		}
	}

}
