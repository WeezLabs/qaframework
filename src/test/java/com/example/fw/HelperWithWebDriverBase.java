package com.example.fw;

import org.openqa.selenium.*;

/**
 * Created by DISTILLERY on 20.07.17.
 */
public class HelperWithWebDriverBase {

    protected final ApplicationManager manager;
    protected WebDriver driver;

    public HelperWithWebDriverBase(ApplicationManager manager) {
        this.manager = manager;
        driver = manager.getwebDriverHelper().getDriver();
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

    /*
    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (webDriverHelper.acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            webDriverHelper.acceptNextAlert = true;
        }
    }
    */

}
