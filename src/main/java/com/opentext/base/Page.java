package com.opentext.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.opentext.utilities.ExcelReader;
import com.opentext.utilities.ExtentManager;
import com.opentext.utilities.Utilities;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Page {
	public static WebDriver driver;
	public static Properties config = new Properties();;
	public static Properties locators = new Properties();;
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static WebDriverWait wait;
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\com\\opentext\\excel\\UserDetails.xlsx");
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	public static String browser;

	public Page() {
		if (driver == null) {
			try {
				fis = new FileInputStream(System.getProperty("user.dir")
						+ "\\src\\test\\resources\\com\\opentext\\properties\\config.properties");

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Log file loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fis = new FileInputStream(System.getProperty("user.dir")
						+ "\\src\\test\\resources\\com\\opentext\\properties\\locators.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				locators.load(fis);
				log.debug("Locators.properties file loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Setting Jenkins Environment Variable for Browser
			if (System.getenv("BrowserName") != null && !System.getenv("BrowserName").isEmpty()) {
				browser = System.getenv("BrowserName");
			} else {
				browser = config.getProperty("browser");
			}
			config.setProperty("browser", browser);

			if (config.getProperty("browser").equals("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				log.debug("Chrome driver intialized");
			} else if (config.getProperty("browser").equals("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				log.debug("firefox driver intialized");
			} else if (config.getProperty("browser").equals("ie")) {
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
				log.debug("IE driver intialized");
			}

			driver.get(config.getProperty("testsiteurl"));
			log.debug("Navigated to " + config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 5);
		}
	}

	public void click(String locator) {
		if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(locators.getProperty(locator))).click();
		} else if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(locators.getProperty(locator))).click();
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(locators.getProperty(locator))).click();
		}
		log.debug("Clicking on locator " + locator);
		test.log(LogStatus.INFO, "Clicking on " + locator);

	}

	public void type(String locator, String value) {
		if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(locators.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(locators.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(locators.getProperty(locator))).sendKeys(value);
		}
		test.log(LogStatus.INFO, "typing in " + locator + " and Entered value " + value);
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;

		} catch (NoSuchElementException e) {
			return false;

		}

	}

	public void verifyEquals(String actual, String expected) throws IOException {
		try {
			Assert.assertEquals(actual, expected);
		} catch (Throwable t) {
			Utilities.captureScreenShot();
			Reporter.log("<br>");
			Reporter.log(" < br > " + "Failed with Exception" + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + Utilities.screenshotName + "><img src="
					+ Utilities.screenshotName + " height=200 width=200></img></a>");
			test.log(LogStatus.FAIL, "Failed with Exception" + t.getMessage() + "<br>");
			test.log(LogStatus.FAIL, test.addScreenCapture(Utilities.screenshotName));

		}

	}

	public static void quit() {
		driver.quit();
	}

}
