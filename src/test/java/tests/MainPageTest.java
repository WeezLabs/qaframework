package tests;

import helpers.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.MainPage;

import static org.assertj.core.api.Assertions.assertThat;

public class MainPageTest {
    private WebDriver driver;

    @BeforeTest
    public void beforeTest() throws Exception {
        driver = WebDriverFactory.getWebDriver();
    }

    @Test
    public void checkHomePageTitle() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        String pageTitle = mainPage.getPageTitle();
        assertThat(pageTitle).isEqualTo("DISTILLERY IS A\nFULL−SERVICE SOFTWARE\nDESIGN AND DEVELOPMENT COMPANY");
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
