package com.dhboa.automation.framework.components;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import org.testng.Assert;

@Component
public class CommonActions {
	private WebDriverWait wait = null;
	private WebDriver driver = null;
	public long shortest = 2000;
	public long shorter = 2000;
	public long normal = 3000;
	public long longer = 4000;
	public long longest = 5000;
	public final String LOGIN_URL = "http://219.65.70.150/trex/#/login?redirect=%2F";

	public enum browsers {
		FIREFOX("firefox"), CHROME("chrome"), IE("ie");

		private String browserName;

		browsers(String name) {
			this.browserName = name;
		}

		public String getBrowserName() {
			return browserName;
		}
	}

	public WebDriver getWebDriver(String browserName) {
		if (browserName.equalsIgnoreCase(browsers.FIREFOX.getBrowserName())) {
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase(browsers.CHROME.getBrowserName())) {
			System.setProperty("webdriver.chrome.driver",
					"D:\\Office\\Softwares\\chromeSeleniumDriver\\chromedriver.exe");
			driver = new ChromeDriver();
		} else
			driver = null;
		wait = new WebDriverWait(driver, 20);
		return driver;
	}

	public void maximizeWindow(WebDriver driver) {
		driver.manage().window().maximize();
	}

	public void login(WebDriver driver, String applicationUrl, String userNameElId, String passwordElId,
			String loginElId, String userName, String password) throws InterruptedException {
		driver.get(applicationUrl);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		setValueToElementById(driver, userNameElId, userName);
		setValueToElementById(driver, passwordElId, password);
		clickElementById(driver, loginElId);
	}

	public void logout(WebDriver driver) throws InterruptedException {
		// This is for logging out
		clickElementByXpath(driver, "//a[contains(@class,'dropdown-toggle') and @data-toggle='dropdown']");
		// waitUntilElementVisibilityByXpath(driver,
		// "//a[@ng-click='logout()']");
		clickElementByXpath(driver, "//a[@ng-click='logout()']");
	}

	public void setValueToElementById(WebDriver driver, String id, String value) throws InterruptedException {
		Thread.sleep(shortest);
		driver.findElement(By.id(id)).sendKeys(value);
	}

	public void setValueToElementByXpath(WebDriver driver, String xpath, String value) throws InterruptedException {
		Thread.sleep(shortest);
		driver.findElement(By.xpath(xpath)).sendKeys(value);
	}

	public void clickElementById(WebDriver driver, String id) throws InterruptedException {
		Thread.sleep(shortest);
		driver.findElement(By.id(id)).click();
	}

	public void clickElementByXpath(WebDriver driver, String xpath) throws InterruptedException {
		Thread.sleep(shortest);
		// waitUntilElementVisibilityByXpath(driver, xpath);
		driver.findElement(By.xpath(xpath)).click();
	}

	public void checkElementPresentById(WebDriver driver, String id) throws InterruptedException {
		boolean elementPresent = true;
		try {
			elementPresent = (driver.findElement(By.id(id)) == null && driver.findElement(By.id(id)).isDisplayed())
					? false : true;
		} catch (Exception e) {
			elementPresent = false;
		}
		Thread.sleep(shortest);
		Assert.assertTrue(elementPresent, "Unable to find the element with id " + id);
	}

	public void checkMenuItems(WebDriver driver, boolean checkCondition, String... ids) throws InterruptedException {
		boolean elementPresent = true;
		String elementId = "";
		for (String id : ids) {
			try {
				elementId = id;
				elementPresent = (driver.findElement(By.id(id)) == null && driver.findElement(By.id(id)).isDisplayed())
						? false : true;
				elementPresent = !driver.findElement(By.xpath("//..[@id='" + id + "']/..")).getAttribute("class")
						.contains("ng-hide");
			} catch (Exception e) {
				elementPresent = false;
				break;
			}
		}
		Thread.sleep(shortest);
		if (checkCondition)
			Assert.assertTrue(elementPresent, "Unable to find the element with id " + elementId);
		else
			Assert.assertFalse(elementPresent, "Element found with id " + elementId);
	}

	public void checkElementPresentByXpath(WebDriver driver, String xpath) throws InterruptedException {
		boolean elementPresent = true;
		try {
			elementPresent = (driver.findElement(By.xpath(xpath)) == null
					&& driver.findElement(By.xpath(xpath)).isDisplayed()) ? false : true;
		} catch (Exception e) {
			elementPresent = false;
		}
		Thread.sleep(shortest);
		Assert.assertTrue(elementPresent, "Unable to find the element with xpath " + xpath);
	}

	public void waitUntilElementVisibilityById(WebDriver driver, String id) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
	}

	public void waitUntilElementVisibilityByXpath(WebDriver driver, String xpath) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}

	public void selectByVisibleTextForEleId(WebDriver driver, String id, String visibleText)
			throws InterruptedException {
		Thread.sleep(shortest);
		waitUntilElementVisibilityById(driver, id);
		Select dropDown = new Select(driver.findElement(By.id(id)));
		dropDown.selectByVisibleText(visibleText);
	}

	public void selectByIndexForEleId(WebDriver driver, String id, int index) throws InterruptedException {
		Thread.sleep(shortest);
		waitUntilElementVisibilityById(driver, id);
		Select dropDown = new Select(driver.findElement(By.id(id)));
		dropDown.selectByIndex(index);
	}

	public void selectByValueForEleId(WebDriver driver, String id, String value) throws InterruptedException {
		Thread.sleep(shortest);
		waitUntilElementVisibilityById(driver, id);
		Select dropDown = new Select(driver.findElement(By.id(id)));
		dropDown.selectByValue(value);
	}

	public void selectByVisibleTextForEleXpath(WebDriver driver, String xpath, String visibleText)
			throws InterruptedException {
		Thread.sleep(shortest);
		// waitUntilElementVisibilityByXpath(driver, xpath);
		Select dropDown = new Select(driver.findElement(By.xpath(xpath)));
		dropDown.selectByVisibleText(visibleText);
	}

	public void selectByVisibleTextForEleXpath(WebElement webElement, String xpath, String visibleText)
			throws InterruptedException {
		Thread.sleep(shortest);
		// waitUntilElementVisibilityByXpath(driver, xpath);
		Select dropDown = new Select(webElement.findElement(By.xpath(xpath)));
		dropDown.selectByVisibleText(visibleText);
	}

	public void selectByIndexForEleXpath(WebDriver driver, String xpath, int index) throws InterruptedException {
		Thread.sleep(shortest);
		// waitUntilElementVisibilityByXpath(driver, xpath);
		Select dropDown = new Select(driver.findElement(By.xpath(xpath)));
		dropDown.selectByIndex(index);
	}

	public void selectByValueForEleXpath(WebDriver driver, String xpath, String value) throws InterruptedException {
		Thread.sleep(shortest);
		// waitUntilElementVisibilityByXpath(driver, xpath);
		Select dropDown = new Select(driver.findElement(By.xpath(xpath)));
		dropDown.selectByValue(value);
	}

	public String getValueById(WebDriver driver, String id) throws InterruptedException {
		Thread.sleep(shortest);
		return driver.findElement(By.id(id)).getText();
	}

	public String getValueByXpath(WebDriver driver, String xpath) throws InterruptedException {
		Thread.sleep(shortest);
		return driver.findElement(By.xpath(xpath)).getText();
	}

	// Clicking on the menu item of the tne home page
	public void clickOnMenuItem(WebDriver driver, String menuItemName) throws InterruptedException {
		if (menuItemName.equalsIgnoreCase("travel"))
			clickElementByXpath(driver, "//div[@data-target='#travel']");
		else if (menuItemName.equalsIgnoreCase("expense"))
			clickElementByXpath(driver, "//div[@data-target='#expense']");
		else if (menuItemName.equalsIgnoreCase("myinbox"))
			clickElementByXpath(driver, "//div[@data-target='#inbox']");
		else if (menuItemName.equalsIgnoreCase("reports"))
			clickElementByXpath(driver, "//div[@data-target='#reports']");
		else if (menuItemName.equalsIgnoreCase("newtravelrequest")) {
			if (!isElementPresentByXPath(driver, ".//*[@id='travel' and contains(@class,'collapse in')]"))
				clickOnMenuItem(driver, "travel");
			clickElementByXpath(driver, "//a[@href='#/travel/new']");
		} else if (menuItemName.equalsIgnoreCase("myrequests")) {
			if (!isElementPresentByXPath(driver, ".//*[@id='travel' and contains(@class,'collapse in')]"))
				clickOnMenuItem(driver, "travel");
			clickElementByXpath(driver, "//a[@href='#/travel/myrequests']");
		} else if (menuItemName.equalsIgnoreCase("mytraveldrafts")) {
			if (!isElementPresentByXPath(driver, ".//*[@id='travel' and contains(@class,'collapse in')]"))
				clickOnMenuItem(driver, "travel");
			clickElementByXpath(driver, "//a[@href='#/travel/mydrafts']");
		} else if (menuItemName.equalsIgnoreCase("newexpenseclaim")) {
			if (!isElementPresentByXPath(driver, ".//*[@id='expense' and contains(@class,'collapse in')]"))
				clickOnMenuItem(driver, "expense");
			clickElementByXpath(driver, "//a[@href='#/expense/new']");
		} else if (menuItemName.equalsIgnoreCase("myclaims")) {
			if (!isElementPresentByXPath(driver, ".//*[@id='expense' and contains(@class,'collapse in')]"))
				clickOnMenuItem(driver, "expense");
			clickElementByXpath(driver, "//a[@href='#/expense/myclaims']");
		} else if (menuItemName.equalsIgnoreCase("myclaimdrafts")) {
			if (!isElementPresentByXPath(driver, ".//*[@id='expense' and contains(@class,'collapse in')]"))
				clickOnMenuItem(driver, "expense");
			clickElementByXpath(driver, "//a[@href='#/expense/mydrafts']");
		} else if (menuItemName.equalsIgnoreCase("personaltask")) {
			if (!isElementPresentByXPath(driver, ".//*[@id='inbox' and contains(@class,'collapse in')]"))
				clickOnMenuItem(driver, "myinbox");
			clickElementByXpath(driver, "//a[@href='#/inbox']");
		} else if (menuItemName.equalsIgnoreCase("traveltask")) {
			if (!isElementPresentByXPath(driver, ".//*[@id='inbox' and contains(@class,'collapse in')]"))
				clickOnMenuItem(driver, "myinbox");
			clickElementByXpath(driver, "//a[@href='#/inbox/TRAVEL_DESK']");
		} else if (menuItemName.equalsIgnoreCase("finance")) {
			if (!isElementPresentByXPath(driver, ".//*[@id='inbox' and contains(@class,'collapse in')]"))
				clickOnMenuItem(driver, "myinbox");
			clickElementByXpath(driver, "//a[@href='#/inbox/FINANCE']");
		} else if (menuItemName.equalsIgnoreCase("viewreports")) {
			if (!isElementPresentByXPath(driver, ".//*[@id='reports' and contains(@class,'collapse in')]"))
				clickOnMenuItem(driver, "reports");
			clickElementByXpath(driver, "#/reports/manage");
		} else if (menuItemName.equalsIgnoreCase("viewdashboards")) {
			if (!isElementPresentByXPath(driver, ".//*[@id='reports' and contains(@class,'collapse in')]"))
				clickOnMenuItem(driver, "reports");
			clickElementByXpath(driver, "#/dashboard/view");
		}
	}

	public void clickOnSettings(WebDriver driver, String option) throws InterruptedException {
		clickElementByXpath(driver, "//a[contains(@class,'dropdown-toggle') and @data-toggle='dropdown']");
		waitUntilElementVisibilityByXpath(driver, "//a[@ng-click='logout()']");
		if (option.equalsIgnoreCase("mypolicies"))
			clickElementByXpath(driver, "//a[@ng-click='showMyPolicies()']");
		else if (option.equalsIgnoreCase("myprofile"))
			clickElementByXpath(driver, "//a[@href='#/profile']");
		else if (option.equalsIgnoreCase("logout"))
			clickElementByXpath(driver, "//a[@ng-click='logout()']");
	}

	public void modifyProperties(String pathToProperties, String propName, String propValue) {
		FileInputStream in = null;
		Properties props = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(pathToProperties);
			props = new Properties();
			props.load(in);
			in.close();
			out = new FileOutputStream(pathToProperties);
			props.setProperty(propName, propValue);
			props.store(out, null);
			out.close();
		} catch (Exception e) {

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void login(WebDriver driver, String credentials) throws InterruptedException {
		String creds[] = credentials.split("#");
		login(driver, LOGIN_URL, "username", "password", "btnLogIn", creds[0], creds[1]);
		Thread.sleep(normal);
	}

	public boolean isElementPresentByXPath(WebDriver driver, String xpath) throws InterruptedException {
		boolean elementPresent = false;
		try {
			elementPresent = (driver.findElement(By.xpath(xpath)) == null
					&& driver.findElement(By.xpath(xpath)).isDisplayed()) ? false : true;
		} catch (Exception e) {
			elementPresent = false;
		}
		return elementPresent;
	}

	public boolean isElementPresentById(WebDriver driver, String id) throws InterruptedException {
		boolean elementPresent = false;
		try {
			elementPresent = (driver.findElement(By.id(id)) == null && driver.findElement(By.id(id)).isDisplayed())
					? false : true;
		} catch (Exception e) {
			elementPresent = false;
		}
		return elementPresent;
	}

	public void produceDelay(WebDriver driver, String delayInMilliSeconds) throws InterruptedException {
		Thread.sleep(Long.valueOf(delayInMilliSeconds));
	}

	public void openUrl(WebDriver driver, String url) throws InterruptedException {
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(normal);
	}

	public void browserQuit(WebDriver driver) {
		driver.quit();
	}
}
