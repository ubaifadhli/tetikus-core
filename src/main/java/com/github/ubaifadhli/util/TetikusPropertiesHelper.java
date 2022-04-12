package com.github.ubaifadhli.util;

import java.util.Properties;

public class TetikusPropertiesHelper {
    private static final String TETIKUS_PROPERTIES_PATH = "tetikus.properties";

    private static final String DRIVER_CONFIGURATION_PATH = "driver.configuration.path";
    private static final String DRIVER_KEEP_SESSION = "driver.keep.session";
    private static final String REPORT_ENABLED = "report.enabled";

    // Default value for several properties.
    private static final String DEFAULT_DRIVER_CONFIGURATION_PATH = "driverconfig.json";

    private static Properties getTetikusProperties() {
        return ResourceHelper.readFileAsProperties(TETIKUS_PROPERTIES_PATH);
    }

    private static boolean getPropertyAsBoolean(String propertyName, boolean defaultValue) {
        String propertyValue = getTetikusProperties().getProperty(propertyName, null);

        if (propertyValue == null)
            return defaultValue;
        else
            return Boolean.parseBoolean(propertyValue);
    }

    private static String getPropertyAsString(String propertyName, String defaultValue) {
        String propertyValue = getTetikusProperties().getProperty(propertyName, null);

        return (propertyValue == null || propertyValue.length() < 1) ?
                defaultValue :
                propertyValue;
    }

    public static boolean isKeepDriverSession() {
        return getPropertyAsBoolean(DRIVER_KEEP_SESSION, false);
    }

    public static boolean isReportEnabled() {
        return getPropertyAsBoolean(REPORT_ENABLED, true);
    }

    public static String getDriverConfigurationPath() {
        return getPropertyAsString(DRIVER_CONFIGURATION_PATH, DEFAULT_DRIVER_CONFIGURATION_PATH);
    }
}