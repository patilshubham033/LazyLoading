package com.lazyloading.ui.test;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LazyLoading {

	WebDriver driver;
	int scrollLimit = 10;
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

		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Home']")).isDisplayed(), "Home Page is not displayed");

		scrollDown();
	}

	public void scrollDown() throws InterruptedException {
		for (int i = scrollLimit; i >= 0; i--) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(1000);
		}
	}

}
