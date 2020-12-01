package tests;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.MainPage;

import static org.assertj.core.api.Assertions.assertThat;

public class MainPageTest {
    private ChromeDriver driver;

    @BeforeTest
    public void beforeTest() {
        driver = new ChromeDriver();
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
