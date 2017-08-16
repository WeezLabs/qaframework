package com.example.fw;

import com.example.tests.GoToClass;
import com.example.tests.GroupObject;
import com.example.tests.UseTestCaseClass;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.assertEquals;



public class GroupHelper extends HelperWithWebDriverBase {

    public GroupHelper(ApplicationManager manager) {
        super(manager);
    }




    public Set<GroupObject> getGroups() {


        manager.getNavigetionHelper().goToGroupListPage();

//id name value type
        WebElement form = driver.findElements(By.tagName("fieldset")).get(3);
        List<WebElement> radios = form.findElements(By.name("internetTypeOfService"));
        Set<GroupObject> groups = new HashSet<GroupObject>();

        for (WebElement radiobutton : radios) {
            String title = radiobutton.getAttribute("value");
            GroupObject group = new GroupObject()
                    .setName(title)
                    .setId(radiobutton.getAttribute("id"));
            groups.add(group);

            System.out.println("title = " + title);
        }

/*        for (WebElement radiobutton : radios) {
            String title = radiobutton.getAttribute("value");

//            int index = title.indexOf('l');
//            title = title.substring(index, title.length());

            Pattern p = Pattern.compile(".*");
            Matcher m = p.matcher(title);
            boolean b = m.matches();

            System.out.println(m.group());*/

            return groups;
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
