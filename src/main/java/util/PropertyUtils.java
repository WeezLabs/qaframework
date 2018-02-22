package util;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.util.Properties;
import io.qameta.allure.Attachment;

public class PropertyUtils {

    private static final String TEST_PROPERTIES_FILE = "test.properties";

    private static TestProperties testProperties = loadProperties();

    /**
     * Adds provided properties to Allure report attachments.
     * @return string with properties, will be handled by Allure automatically
     */
    @Attachment(value = "testProperties")
    @SuppressWarnings("UnusedReturnValue") // Allure collects return value automatically
    public static String dumpProperties() {
        return testProperties.rawProperties.toString();
    }

    @Nonnull
    public static TestProperties getTestProperties() {
        return testProperties;
    }

    @Nonnull
    public static String getServerHost() {
        return testProperties.serverHost;
    }

    @Nonnull
    public static String getServerApiBaseUrl() {
        return testProperties.serverAPiBaseUrl;
    }

    @Nonnull
    public static int getServerHostPort() {
        return testProperties.serverHostPort;
    }

    @Nonnull
    public static boolean getSslEnabled() {
        return testProperties.sslEnabled;
    }

    @Nonnull
    private static TestProperties loadProperties() {
        Properties properties = new Properties();

        try(InputStream inputStream = PropertyUtils.class.getClassLoader().getResourceAsStream(TEST_PROPERTIES_FILE)) {
            properties.load(inputStream);
        } catch (Throwable thr) {
            throw new IllegalStateException("Failed to load properties", thr);
        }

        return new TestProperties(properties);
    }
}