package com.example.fw;


public class ApplicationManager {

    private WebDriverHelper webDriverHelper;
    private GroupHelper groupHelper;
    private NavigationHelper navigationHelper;



    public void stop() {
        if (webDriverHelper != null) {
            webDriverHelper.stop();
        }

    }


    public WebDriverHelper getwebDriverHelper() {
        if (webDriverHelper == null) {
            webDriverHelper = new WebDriverHelper();
        }
        return webDriverHelper;
    }

    public GroupHelper getGroupHelper() {
        if (groupHelper == null) {
            groupHelper = new GroupHelper(this);
        }
        return groupHelper;
    }


    public NavigationHelper getNavigetionHelper() {
        if (navigationHelper == null) {
            navigationHelper = new NavigationHelper(this);
        }
        return navigationHelper;
    }
}

