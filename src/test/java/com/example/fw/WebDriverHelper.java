package com.example.fw;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

/**
 * Created by DISTILLERY on 18.07.17.
 */
public class WebDriverHelper {

    protected static WebDriver driver;
    private final ApplicationManager manager;
    public boolean acceptNextAlert = true;
    public static StringBuffer verificationErrors = new StringBuffer();

    public WebDriverHelper(ApplicationManager manager) {
        this.manager = manager;

        System.setProperty("webdriver.gecko.driver", "/Users/DISTILLERY/IdeaProjects/geckodriver");

        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://distillery.com");

    }

    public void stop() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }


    public WebDriver getDriver() {
        return driver;
    }
}
