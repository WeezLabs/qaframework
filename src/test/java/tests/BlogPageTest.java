package tests;

import helpers.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.BlogPage;
import pages.MainPage;

public class BlogPageTest {
    private WebDriver driver;

    @BeforeTest
    public void beforeTest() throws IllegalArgumentException {
        driver = WebDriverFactory.getWebDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void checkMouseflowScriptPresence() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        BlogPage blogPage = mainPage.clickBlogMenuItem();
        blogPage.getMouseflowScript();
    }


    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
