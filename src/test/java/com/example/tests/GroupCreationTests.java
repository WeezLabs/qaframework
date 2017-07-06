package com.example.tests;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GroupCreationTests {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {

        System.setProperty("webdriver.gecko.driver", "/Users/DISTILLERY/IdeaProjects/geckodriver");

        driver = new FirefoxDriver();
        baseUrl = "https://distillery.com";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testValidGroupCanBeCreated() throws Exception {

        openMainPage();
        goToMenu();
        goTo(new GoToClass("main-nav--work")); //our work
        useDelay();
        goTo(new GoToClass("link-overlay-cizo")); //cizo
        useDelay();

        useTestCase(new UseTestCaseClass("RAW\n INGREDIENTS", "div.project-description-item-block > div.title-text.project-description-item-block__title"));
        useTestCase(new UseTestCaseClass("TRANSFORMATION", "div.project-description-item-block__transformation-block > div.title-text.project-description-item-block__title"));
        useTestCase(new UseTestCaseClass("HIGHLIGHTS", "div.project-description-item-block__highlights-block > div.title-text.project-description-item-block__title"));

    }

    private void useTestCase(UseTestCaseClass useTestCaseClass) {
        testCaseDoing(useTestCaseClass);
    }

    private void testCaseDoing(UseTestCaseClass useTestCaseClass) {
        try {
            assertEquals(useTestCaseClass.getLabelTestCase(), driver.findElement(By.cssSelector(useTestCaseClass.getLabelTestSelector())).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    private void goTo(GoToClass goToClass) {
        driver.findElement(By.id(goToClass.getElementId())).click();
    }

    private void useDelay() throws InterruptedException {
        // delay 5 sec
        Thread.sleep(5000);
    }

    private void goToMenu() {
        // go to Menu
        driver.findElement(By.cssSelector("span.icon-menu__state")).click();
    }

    private void openMainPage() {
        // open main page
        driver.get(baseUrl + "/");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
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
