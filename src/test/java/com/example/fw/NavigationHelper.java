package com.example.fw;

import com.example.tests.GoToClass;
import org.openqa.selenium.By;

public class NavigationHelper extends HelperWithWebDriverBase{


    public NavigationHelper(ApplicationManager manager) {
        super(manager);
    }

    public void openMainPage() throws InterruptedException {
        driver.get("https://distillery.com");
    }

    protected void goTo(GoToClass goToClass) {
        driver.findElement(By.id(goToClass.getElementId())).click();
    }
}
