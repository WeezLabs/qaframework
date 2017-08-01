package com.example.fw;

import com.example.tests.GoToClass;

public class NavigationHelper extends HelperWithWebDriverBase{


    public NavigationHelper(ApplicationManager manager) {
        super(manager);
    }

    public void openMainPage() throws InterruptedException {
        openUrl("https://distillery.com/");
    }

    protected void goTo(GoToClass goToClass) {
//        if (findElement(By))
        findElement(goToClass).click();
    }

}
