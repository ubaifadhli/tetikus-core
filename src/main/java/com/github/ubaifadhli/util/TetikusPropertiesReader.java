package com.github.ubaifadhli.util;

public class TetikusPropertiesReader {
    private static String DEFAULT_PROPERTIES_NAME = "application-%s.properties";

    public static String getPropertyAsString(String propertyName) {
        String currentPlatform = CurrentThreadDriver.getCurrentPlatform().name();

        return ResourceHelper.readFileAsProperties(String.format(DEFAULT_PROPERTIES_NAME, currentPlatform))
                .getProperty(propertyName);
    }

    public static int getPropertyAsInteger(String propertyName) {
        return Integer.parseInt(getPropertyAsString(propertyName));
    }

    public static double getPropertyAsDouble(String propertyName) {
        return Double.parseDouble(getPropertyAsString(propertyName));
    }
}
