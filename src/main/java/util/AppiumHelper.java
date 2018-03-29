package util;

import com.thoughtworks.selenium.SeleniumLogLevels;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

/**
 * The helper handling Appium session creation.
 */
public final class AppiumHelper {

    /**
     * Starts a new Appium session providing a driver instance connected with the session.
     */
    @Nonnull
    public static AppiumDriver<MobileElement> startAppium(@Nonnull TestProperties properties) {
        AppiumDriver<MobileElement> driver;
        if (properties.platformName.equals(MobilePlatform.ANDROID)) {
            driver = new AndroidDriver<>(createService(properties), null);
        }
        else {
            driver = new IOSDriver<>(createService(properties), null);
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.launchApp();

        return driver;
    }

    @Nonnull
    private static AppiumDriverLocalService createService(@Nonnull TestProperties properties) {
        return new AppiumServiceBuilder()
                .withIPAddress(properties.appiumHost)
                .usingPort(properties.appiumPort)
                .withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, String.valueOf(properties.appiumBootstrapPort))
                .withArgument(AndroidServerFlag.SUPPRESS_ADB_KILL_SERVER)
                .withArgument(GeneralServerFlag.LOG_LEVEL, SeleniumLogLevels.ERROR)
                .withArgument(GeneralServerFlag.LOG_TIMESTAMP)
                .withArgument(GeneralServerFlag.LOCAL_TIMEZONE)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withCapabilities(createCapabilities(properties))
                .build();
    }

    @Nonnull
    private static DesiredCapabilities createCapabilities(@Nonnull TestProperties properties) {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, properties.platformName);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, properties.automationName);
        capabilities.setCapability(MobileCapabilityType.APP, properties.applicationFilePath.getAbsolutePath());

        if (properties.platformName.equals(MobilePlatform.ANDROID)) {
            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, properties.applicationId);
            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, properties.applicationActivity);
        } else {
            capabilities.setCapability(IOSMobileCapabilityType.PLATFORM, properties.platformVersion);
            capabilities.setCapability(IOSMobileCapabilityType.NATIVE_INSTRUMENTS_LIB, true);
            capabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);
            capabilities.setCapability(IOSMobileCapabilityType.NATIVE_WEB_TAP, true);
            capabilities.setCapability(IOSMobileCapabilityType.WEBVIEW_CONNECT_RETRIES, 10);
        }

        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, properties.deviceName);
        capabilities.setCapability(MobileCapabilityType.UDID, properties.deviceUdid);

        return capabilities;
    }
}
