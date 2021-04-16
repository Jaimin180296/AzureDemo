package AzureDemo;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class MavenTest {

    public WebDriver driver;
    public WebDriverWait wait;
    public final String AUTOMATE_USERNAME = "jaiminmehta3";
    public final String AUTOMATE_ACCESS_KEY = "f2q1FMatZb3X88b19jQ6";
    public final String URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";

    @BeforeClass
    public void setup() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("os", "OS X");
        caps.setCapability("os_version", "Catalina");
        caps.setCapability("browser", "Chrome");
        caps.setCapability("browser_version", "latest");
        caps.setCapability("browserstack.local", "false");
        caps.setCapability("browserstack.selenium_version", "3.141.59");
        driver = new RemoteWebDriver(new URL(URL), caps);
        SessionId session = ((RemoteWebDriver) driver).getSessionId();
        System.out.println("Session id: " + session.toString());


    }

    @Test()
    public void test() throws InterruptedException {
        driver.navigate().to("https://www.google.com/");
          WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("BrowserStack");
        element.submit();
        // Setting the status of test as 'passed' or 'failed' based on the condition; if title of the web page contains 'BrowserStack'
        WebDriverWait wait = new WebDriverWait(driver, 5);
        try {
            wait.until(ExpectedConditions.titleContains("BrowserStack"));
            markTestStatus("passed","Yaay title contains 'BrowserStack'!",driver);
        }
        catch(Exception e) {
            markTestStatus("failed","Naay title does not contain 'BrowserStack'!",driver);
        }
        System.out.println(driver.getTitle());
        driver.quit();
    }
    // This method accepts the status, reason and WebDriver instance and marks the test on BrowserStack
    public static void markTestStatus(String status, String reason, WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""+status+"\", \"reason\": \""+reason+"\"}}");
    }


    @AfterTest
    public void stop() {
        driver.quit();
    }
}


