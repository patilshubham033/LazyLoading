package com.lazyloading.ui.test;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LazyLoading {

	WebDriver driver;
	String emailId = "emailId";
	String password = "password";

	@BeforeMethod
	public void setup() {

		System.setProperty("webdriver.chrome.driver", "/Users/ShubhamPatil/Desktop/chromedriver");
		driver = new ChromeDriver();
		driver.get("https://www.pinterest.com");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	@Test
	public void test() throws InterruptedException {

		driver.findElement(By.id("email")).sendKeys(emailId);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.xpath("//div[text()='Continue']")).click();

		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Home']")).isDisplayed(),
				"Home Page is not displayed");

		By elementLocator = By.xpath("//img[@loading='auto']");

		// Calling the locateElement method, to fetch all element
		locateElement(elementLocator);
	}

	public void locateElement(By elementLocator) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Initial element count
		int elementCount = driver.findElements(elementLocator).size();

		while (true) {
			// javascriptexecutor to scroll the page
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

			wait.ignoring(NoSuchElementException.class)
					.until(ExpectedConditions.invisibilityOfElementLocated(elementLocator));

			// Wait to load the new elements
			Thread.sleep(2000);

			// Check if the last fetch element count is same as new count,
			// It is's same then we already have fetch all the elements on the page.
			if (driver.findElements(elementLocator).size() == elementCount)
				break;

			// fetch the lattest elements count
			elementCount = driver.findElements(elementLocator).size();
		}
	}

}
