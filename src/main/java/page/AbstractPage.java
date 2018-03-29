package page;

import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.TimeOutDuration;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.TestFlowStepHandler;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * An abstract base for all the pages within the suite.
 */
public abstract class AbstractPage {

    private static final TimeOutDuration DEFAULT_TIMEOUT = new TimeOutDuration(25, TimeUnit.SECONDS);

    @Nonnull
    protected final AndroidDriver<MobileElement> driver;

    AbstractPage(@Nonnull AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, DEFAULT_TIMEOUT), this);
    }

    //region Appium driver helpers

    /**
     * Simulates pressing of device back button.
     */
    public void pressDeviceBack() {
        TestFlowStepHandler.logDesignStep("Click device BACK button");
        driver.navigate().back();
    }

    /**
     * Waits until expected element displayed.
     */
    public void waitUntil(final MobileElement mobileElement) {
        waitUntil(mobileElement, 60);
    }

    /**
     * Waits until expected element displayed and gets specify value.
     */
    public void waitUntil(final MobileElement mobileElement, final String value) {
        waitUntil(mobileElement, value, 60);
    }

    /**
     * Waits until expected array of elements has specified size.
     */
    public void waitUntil(final By elementLocator, final int number) {
        waitUntil(elementLocator, number, 60);
    }

    /**
     * Waits until expected element displayed.
     */
    public void waitUntil(final MobileElement mobileElement, final long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.visibilityOf(mobileElement));
    }

    /**
     * Waits until expected array of elements has specified size.
     */
    public void waitUntil(final By elementLocator, final int number, final long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.numberOfElementsToBe(elementLocator, number));
    }

    /**
     * Waits until expected condition.
     */
    public void waitUntil(final MobileElement mobileElement, final String value, final long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        if (!mobileElement.getAttribute("className").equals("android.widget.TextView")) {
            wait.until(ExpectedConditions.textToBePresentInElementValue(mobileElement, value));
        } else {
            wait.until(ExpectedConditions.textToBePresentInElement(mobileElement, value));
        }
    }

    /**
     * Hides keyboard.
     */
    public void hideKeyboard() {
        TestFlowStepHandler.logDesignStep("hide keyboard");
        try {
            driver.hideKeyboard();
        } catch (Exception exp) {
            // do nothing
        }
    }

    /**
     * Sends keys and hide keyboard.
     */
    public void sendKeysAndHideKeyboard(final MobileElement mobileElement, final String text) {
        ((AndroidElement) mobileElement).replaceValue(text);
        hideKeyboard();
    }

    /**
     * Swipes up.
     */
    public void swipeUp() {
        TestFlowStepHandler.logDesignStep("Swipe screen up");
        swipeVertical(0.5, 0.15);
    }

    /**
     * Swipes up until element not displayed.
     */
    public void swipeUp(final MobileElement mobileElement) {
        swipeVertical(mobileElement, 0.5, 0.15);
    }

    /**
     * Swipes up n times.
     */
    public void swipeUpNTimes(final int times) {
        for (int i = 0; i < times; i++) {
            swipeUp();
        }
    }

    /**
     * Swipes down until elemant not displayed.
     */
    public void swipeDown(final MobileElement mobileElement) {
        swipeVertical(mobileElement, 0.5, 0.85);
    }

    /**
     * Swipes down.
     */
    public void swipeDown() {
        TestFlowStepHandler.logDesignStep("Swipe screen down");
        swipeVertical(0.5, 0.85);
    }

    /**
     * Swipes down n times.
     */
    public void swipeDownNTimes(final int times) {
        for (int i = 0; i < times; i++) {
            swipeDown();
        }
    }

    /**
     * Swipes left.
     */
    public void swipeLeft() {
        TestFlowStepHandler.logDesignStep("Swipe screen left");
        swipeHorizontal(0.5, 0.15);
    }

    /**
     * Swipes left n times.
     */
    public void swipeLeftNTimes(final int times) {
        for (int i = 0; i < times; i++) {
            swipeLeft();
        }
    }

    /**
     * Swipes right.
     */
    public void swipeRight() {
        TestFlowStepHandler.logDesignStep("Swipe screen right");
        swipeHorizontal(0.5, 0.85);
    }

    /**
     * Swipes right n times.
     */
    public void swipeRightNTimes(final int times) {
        for (int i = 0; i < times; i++) {
            swipeRight();
        }
    }

    /**
     * Swipes vertical given a swipe element direction, start height percentage,
     * and end height percentage.
     */
    public void swipeVertical(final double startHeightPercentage, final double endHeightPercentage) {
        Dimension dimensions = driver.manage().window().getSize();
        Double screenHeightStart = dimensions.getHeight() * startHeightPercentage;
        Double screenHeightEnd = dimensions.getHeight() * endHeightPercentage;
        driver.swipe(0, screenHeightStart.intValue(), 0, screenHeightEnd.intValue(), 2000);
    }

    /**
     * Swipes vertical while element not displayed, start height percentage,
     * and end height percentage.
     */
    public void swipeVertical(MobileElement mobileElement, final double startHeightPercentage, final double
            endHeightPercentage) {
        Dimension dimensions = driver.manage().window().getSize();
        Double screenHeightStart = dimensions.getHeight() * startHeightPercentage;
        Double screenHeightEnd = dimensions.getHeight() * endHeightPercentage;
        int i = 0;
        while (!isDisplayed(mobileElement) && i < 5) {
            driver.swipe(0, screenHeightStart.intValue(), 0, screenHeightEnd.intValue(), 2000);
            i++;
        }
    }

    /**
     * Swipes horizontally.
     */
    public void swipeHorizontal(final double startWidthPercentage, final double endWidthPercentage) {
        Dimension dimensions = driver.manage().window().getSize();
        Double screenWidthStart = dimensions.getWidth() * startWidthPercentage;
        Double screenWidthEnd = dimensions.getWidth() * endWidthPercentage;
        driver.swipe(screenWidthStart.intValue(), 0, screenWidthEnd.intValue(), 0, 2000);
    }


    /**
     * Swipes given a swipe element direction, offset from start border,
     * offset from end border, and duration.
     */
    public void swipe(final SwipeElementDirection swipeElementDirection,
                      final int offsetFromStartBorder,
                      final int offsetFromEndBorder,
                      final int duration) {
        List<MobileElement> mobileElementList = driver.findElementsByClassName("android.widget.ScrollView");
        if (mobileElementList.isEmpty()) {
            mobileElementList = driver.findElementsByClassName("android.widget.ListView");
        }
        mobileElementList.get(0).swipe(swipeElementDirection, offsetFromStartBorder, offsetFromEndBorder, duration);
    }

    /**
     * Checks if element is displayed.
     */
    public boolean isDisplayed(final MobileElement mobileElement) {
        try {
            return mobileElement.isDisplayed();
        } catch (Throwable thr) {
            return false;
        }
    }
    //endregion
}
