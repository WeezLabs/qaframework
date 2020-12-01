package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage extends BasePage {
    private final String HOME_PAGE_LABEL = "xpath://h1[@class='jumbotron-home__header']";

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void openMainPage() {
        driver.get("https://distillery.com/");
    }

    public String getPageTitle() {
        WebElement element = waitForElementPresent(HOME_PAGE_LABEL, "Cannot get home page label!", 10);
        return element.getText();
    }
}
