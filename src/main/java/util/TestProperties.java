package util;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Properties;

public class TestProperties {

    private static final String PROPERTY_SERVER_HOST = "serverHost";
    private static final String PROPERTY_SERVER_PORT = "serverPort";
    private static final String PROPERTY_SERVER_API_BASE_URL = "serverAPiBaseUrl";

    private static final String PROPERTY_APPLICATION_ID = "applicationId";
    private static final String PROPERTY_APPLICATION_ACTIVITY = "applicationActivity";
    private static final String PROPERTY_APPLICATION_FILE_PATH = "applicationFilePath";

    private static final String PROPERTY_APPIUM_HOST = "appiumHost";
    private static final String PROPERTY_APPIUM_PORT = "appiumPort";
    private static final String PROPERTY_APPIUM_BOOTSTRAP_PORT = "appiumBootstrapPort";

    private static final String PROPERTY_SCREEN_RECORDING = "screenRecording";

    private static final String PROPERTY_DEVICE_UDID = "device";
    private static final String PROPERTY_DEVICE_NAME = "deviceName";

    private static final String PROPERTY_SSL = "sslEnabled";

    private static final String PROPERTY_TESTING_TYPE = "testingType";
    private static final String PROPERTY_PLATFORM_NAME = "platformName";
    private static final String PROPERTY_AUTOMATION_NAME = "automationName";
    private static final String PROPERTY_PLATFORM_VERSION = "platformVersion";
    private static final String PROPERTY_BUILD_PATH = "buildPath";
    private static final String PROPERTY_SIMULATOR = "simulator";


    @Nonnull
    public final Properties rawProperties;

    @Nonnull
    public final String serverHost;
    public final int serverPort;
    @Nonnull
    public final String serverAPiBaseUrl;
    public final boolean sslEnabled;
    @Nonnull
    public final String appiumHost;
    public final int appiumPort;
    public final int appiumBootstrapPort;

    @Nonnull
    public final String applicationId;
    @Nonnull
    public final String applicationActivity;
    @Nonnull
    public final File applicationFilePath;

    @Nonnull
    public final String deviceUdid;
    @Nonnull
    public final String deviceName;

    public final boolean isScreenRecorderEnabled;
    @Nonnull
    public final String testingType;
    @Nonnull
    public final File buildPath;
    @Nonnull
    public final String platformName;
    @Nonnull
    public final String platformVersion;
    @Nonnull
    public final String automationName;

    public final boolean simulator;

    public TestProperties(@Nonnull Properties properties) {
        rawProperties = properties;

        serverHost = properties.getProperty(PROPERTY_SERVER_HOST);
        serverPort = Integer.parseInt(properties.getProperty(PROPERTY_SERVER_PORT));
        serverAPiBaseUrl = properties.getProperty(PROPERTY_SERVER_API_BASE_URL);

        sslEnabled = Boolean.parseBoolean(properties.getProperty(PROPERTY_SSL));

        applicationId = properties.getProperty(PROPERTY_APPLICATION_ID);
        applicationActivity = properties.getProperty(PROPERTY_APPLICATION_ACTIVITY);
        applicationFilePath = new File(properties.getProperty(PROPERTY_APPLICATION_FILE_PATH));

        deviceUdid = properties.getProperty(PROPERTY_DEVICE_UDID);
        deviceName = properties.getProperty(PROPERTY_DEVICE_NAME);

        isScreenRecorderEnabled = Boolean.parseBoolean(properties.getProperty(PROPERTY_SCREEN_RECORDING));
        testingType = properties.getProperty(PROPERTY_TESTING_TYPE);
        buildPath = new File(properties.getProperty(PROPERTY_BUILD_PATH));
        platformName = properties.getProperty(PROPERTY_PLATFORM_NAME);
        platformVersion = properties.getProperty(PROPERTY_PLATFORM_VERSION);
        automationName = properties.getProperty(PROPERTY_AUTOMATION_NAME);
        simulator = Boolean.parseBoolean(properties.getProperty(PROPERTY_SIMULATOR));

        appiumHost = properties.getProperty(PROPERTY_APPIUM_HOST);
        appiumPort = Integer.parseInt(properties.getProperty(PROPERTY_APPIUM_PORT));
        appiumBootstrapPort = Integer.parseInt(properties.getProperty(PROPERTY_APPIUM_BOOTSTRAP_PORT));
    }
}
