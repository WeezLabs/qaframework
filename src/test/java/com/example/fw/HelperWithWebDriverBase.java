package com.example.fw;

import com.example.tests.GoToClass;
import org.openqa.selenium.*;

import java.util.List;

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

    protected WebElement findElement(GoToClass goToClass) {
        return driver.findElement(By.id(goToClass.getElementId()));
    }

    protected List<WebElement> findElementS(GoToClass goToClass) {
//        return driver.findElement(By.id(goToClass.getElementId()));
        return null;
    }

    protected void openUrl(Integer nubmerUrl) {

        String openUrlVariable = "https://distillery.com";

        switch (nubmerUrl) {

            case 1:
                openUrlVariable = "https://distillery.com";
                System.out.println("Distillery site testing");
                break;
            case 2:
                openUrlVariable = "http://energy-telecom.portnov.com/qa/";
                System.out.println("Сайт Портнова для тестирования");
                break;
            case 3: System.out.println("one another web site");
                break;
            default:
                System.out.println("no one email is sending");

        }




        driver.get(openUrlVariable);
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
