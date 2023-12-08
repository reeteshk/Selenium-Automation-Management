package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {

	public static Properties prop;

	public static WebDriver initializeBrowser(WebDriver driver) throws IOException {

		// Load properties from the configuration file
		prop = new Properties();
		String propertiesPath = System.getProperty("user.dir") + "/src/main/java/resources/config.properties";
		FileInputStream fis = new FileInputStream(propertiesPath);
		prop.load(fis);

		// Get the browser name from the properties file
		String browserName = prop.getProperty("browser");
		System.out.println("Browser from properties: " + browserName);

		try {
			// Initialize ChromeDriver if 'chrome' is specified
			if (browserName.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				String projectPath = System.getProperty("user.dir");

				// Set preferences for Chrome browser
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("download.default_directory", projectPath);
				chromePrefs.put("plugins.always_open_pdf_externally", true);

				options.setExperimentalOption("prefs", chromePrefs);

				driver = new ChromeDriver(options);
				System.out.println("Chrome Driver initialized");
			}

			// Initialize FirefoxDriver if 'firefox' is specified
			else if (browserName.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				System.out.println("Firefox Driver initialized");
			}

			// Initialize InternetExplorerDriver if 'ie' is specified
			else if (browserName.equalsIgnoreCase("ie")) {
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
				System.out.println("IE Driver initialized");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Configure WebDriver options
		if (driver != null) {
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} else {
			System.out.println("Driver was not initialized");
		}

		return driver;
	}

}
