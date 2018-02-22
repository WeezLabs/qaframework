package util;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Properties;

public class TestProperties {

    private static final String PROPERTY_SERVER_HOST = "serverHost";
    private static final String PROPERTY_SERVER_HOST_PORT = "serverHostPort";
    private static final String PROPERTY_SERVER_API_BASE_URL = "serverAPiBaseUrl";

    private static final String PROPERTY_SSL = "sslEnabled";

    @Nonnull
    public final Properties rawProperties;

    @Nonnull
    public final String serverHost;
    @Nonnull
    public final int serverHostPort;
    @Nonnull
    public final String serverAPiBaseUrl;
    @Nonnull
    public final boolean sslEnabled;


    public TestProperties(@Nonnull Properties properties) {
        rawProperties = properties;

        serverHost = properties.getProperty(PROPERTY_SERVER_HOST);
        serverHostPort = Integer.parseInt(properties.getProperty(PROPERTY_SERVER_HOST_PORT));
        serverAPiBaseUrl = properties.getProperty(PROPERTY_SERVER_API_BASE_URL);

        sslEnabled = Boolean.parseBoolean(properties.getProperty(PROPERTY_SSL));
    }
}
