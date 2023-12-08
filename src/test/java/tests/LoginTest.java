package tests;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import resources.Base;

public class LoginTest extends Base {

	WebDriver driver;

	@Test
	public void login() throws IOException, Exception {

		driver.get(prop.getProperty("neu_url"));
		Thread.sleep(2000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));

		WebElement email = driver.findElement(By.id("i0116"));
		email.sendKeys(prop.getProperty("NEU_USERNAME"));
		driver.findElement(By.id("idSIButton9")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));

		WebElement pass = driver.findElement(By.id("i0118"));
		pass.sendKeys(prop.getProperty("NEU_PASSWORD"));
		driver.findElement(By.id("idSIButton9")).click();
		Thread.sleep(10000);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("trust-browser-button")));
		driver.findElement(By.id("trust-browser-button")).click();
		Thread.sleep(5000);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("idBtn_Back")));
		driver.findElement(By.id("idBtn_Back")).click();
		Thread.sleep(10000);

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Close popup modal']")));
		driver.findElement(By.xpath("//button[@aria-label='Close popup modal']")).click();
	}

}
