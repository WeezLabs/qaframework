package pages;

import org.openqa.selenium.WebDriver;

public class BlogPage extends BasePage {
    private final String MOUSEFLOW_SCRIPT = "xpath://script[@src='https://cdn.mouseflow.com/projects/a9bd6d6f-d15a-4b0f-ad0f-b674fe7f6e0c.js']";

    public BlogPage(WebDriver driver) {
        super(driver);
    }

    public void getMouseflowScript() {
        waitForElementPresent(MOUSEFLOW_SCRIPT, "Cannot find mouseflow script on the page!", 10);
    }
}
