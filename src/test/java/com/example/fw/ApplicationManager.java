package com.example.fw;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.example.tests.GoToClass;
import com.example.tests.UseTestCaseClass;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;


import org.testng.ITestResult;

import org.junit.Assert.*;
import org.testng.asserts.Assertion;


import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;



public class ApplicationManager {

    private static WebDriver driver;
    private static String baseUrl;
    private static boolean acceptNextAlert = true;
    private static StringBuffer verificationErrors = new StringBuffer();

    ITestResult result;

    public ApplicationManager() {

        System.setProperty("webdriver.gecko.driver", "/Users/DISTILLERY/IdeaProjects/geckodriver");

        driver = new FirefoxDriver();
        baseUrl = "https://distillery.com";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void stop() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
//        String verificationErrorString = verificationErrors.toString();
//        if (!"".equals(verificationErrorString)) {
//            result.setStatus(ITestResult.FAILURE);
//            Reporter.setCurrentTestResult(result);
//        }
    }


    public void goToStart() throws InterruptedException {

        useDelay();
        openMainPage();
        useDelay();
        goToMenu();
        goTo(new GoToClass("main-nav--work")); //our work
        useDelay();
        goTo(new GoToClass("link-overlay-cizo")); //cizo
//        goTo(new GoToClass("bg-work-cizo"));
        useDelay();
    }

    public void useTestCase(UseTestCaseClass useTestCaseClass) {
        testCaseDoing(useTestCaseClass);
    }

    public void testCaseDoing(UseTestCaseClass useTestCaseClass) {
        try {
            assertEquals(useTestCaseClass.getLabelTextParagraph(), driver.findElement(By.cssSelector(useTestCaseClass.getLabelTestSelector())).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    protected void goTo(GoToClass goToClass) {
        driver.findElement(By.id(goToClass.getElementId())).click();
    }

    public void useDelay() throws InterruptedException {
        Thread.sleep(2000); // delay 5 sec
    }

    protected void goToMenu() {
        driver.findElement(By.cssSelector("span.icon-menu__state")).click();
    }

    public void openMainPage() throws InterruptedException {
        driver.get(baseUrl + "/");
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}

