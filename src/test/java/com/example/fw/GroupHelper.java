package com.example.fw;

import com.example.tests.GoToClass;
import com.example.tests.UseTestCaseClass;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;



public class GroupHelper extends HelperWithWebDriverBase {

    public GroupHelper(ApplicationManager manager) {
        super(manager);
    }




    public Set<UseTestCaseClass> getGroups() {

        manager.getNavigetionHelper().goTo(new GoToClass("main-nav--work"));

//        driver.findElement(By.id(goToClass.getElementId()));
//        WebElement form =  driver.findElement(By.tagName("form")).get(0);

        return null;
    }

    public void goToStart() throws InterruptedException {

        useDelay();
        goToMenu();
        manager.getNavigetionHelper().goTo(new GoToClass("main-nav--work")); //our work
        useDelay();
        manager.getNavigetionHelper().goTo(new GoToClass("link-overlay-cizo")); //cizo
        useDelay();
    }


    public void useTestCase(UseTestCaseClass useTestCaseClass) {
        testCaseDoing(useTestCaseClass);
    }

    private void testCaseDoing(UseTestCaseClass useTestCaseClass) {
        try {
            assertEquals(useTestCaseClass.getLabelTextParagraph(), driver.findElement(By.cssSelector(useTestCaseClass.getLabelTestSelector())).getText());
        } catch (Error e) {
            WebDriverHelper.verificationErrors.append(e.toString());
        }
    }


    private void useDelay() throws InterruptedException {
        Thread.sleep(2000); // delay 2 sec
    }

    private void goToMenu() {
        driver.findElement(By.cssSelector("span.icon-menu__state")).click();
    }

    public void tapOnButton() throws InterruptedException {

        useDelay();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("hithere");
        driver.findElement(By.xpath("//input[@value='Subscribe']")).click();
        //       useDelay();
        verifyEmailError();

        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("");

    }

    private void verifyEmailError() {
        try {
            assertEquals("Please enter a valid email address", driver.findElement(By.cssSelector("div.hs-error-msgs.js-errorMessage")).getText());
        } catch (Error e) {
            WebDriverHelper.verificationErrors.append(e.toString());
        }
    }

    public void fillAllFieldsWrongEmail(String writeWrongEmail) throws InterruptedException {

        openUrl(1);

        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Testuser");
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys(writeWrongEmail);
        driver.findElement(By.name("phone")).clear();
        driver.findElement(By.name("phone")).sendKeys("wrong_phone");
        driver.findElement(By.name("message")).clear();
        driver.findElement(By.name("message")).sendKeys("wrong description");
        useDelay();
        driver.findElement(By.xpath("//form/a/div[7]")).click();
        useDelay();
        driver.findElement(By.xpath("//form/a/div[7]")).click();
        driver.findElement(By.xpath("//div[2]/span")).click();
        verifyEmailErrorMessageFooter();
        verifyPhoneErrorMessageFooter();
    }

    private void verifyPhoneErrorMessageFooter() {
        try {
            assertEquals("Please enter a valid phone number", driver.findElement(By.xpath("//div[3]/span")).getText());
        } catch (Error e) {
            WebDriverHelper.verificationErrors.append(e.toString());
        }
    }

    private void verifyEmailErrorMessageFooter() {
        try {
            assertEquals("Please enter a valid email address", driver.findElement(By.xpath("//div[2]/span")).getText());
        } catch (Error e) {
            WebDriverHelper.verificationErrors.append(e.toString());
        }
    }


    public void openPortnowLink(String wrongEmail) {
        openUrl(2);

    }
}
