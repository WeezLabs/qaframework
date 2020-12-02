package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.ResourceBundle;

public class WebDriverFactory {

    public static WebDriver getWebDriver() throws Exception {
        ResourceBundle rb = ResourceBundle.getBundle("test");
        String browserName = rb.getString("BROWSER").toLowerCase();

        switch (browserName) {
            case "chrome":
                return new ChromeDriver();
            case "firefox":
                return new FirefoxDriver();
            case "safari":
                return new SafariDriver();
            default:
                throw new Exception("Given browser is not supported! " + browserName);
        }
    }
}
