package com.example.fw;

import com.example.tests.GoToClass;
import com.example.tests.UseTestCaseClass;
import org.openqa.selenium.*;

import static org.testng.Assert.assertEquals;



public class GroupHelper extends HelperWithWebDriverBase {

    public GroupHelper(ApplicationManager manager) {
        super(manager);
    }


    public void goToStart() throws InterruptedException {

        useDelay();
        manager.getNavigetionHelper().openMainPage();
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

    public void testCaseDoing(UseTestCaseClass useTestCaseClass) {
        try {
            assertEquals(useTestCaseClass.getLabelTextParagraph(), driver.findElement(By.cssSelector(useTestCaseClass.getLabelTestSelector())).getText());
        } catch (Error e) {
            WebDriverHelper.verificationErrors.append(e.toString());
        }
    }


    public void useDelay() throws InterruptedException {
        Thread.sleep(2000); // delay 5 sec
    }

    protected void goToMenu() {
        driver.findElement(By.cssSelector("span.icon-menu__state")).click();
    }

}
