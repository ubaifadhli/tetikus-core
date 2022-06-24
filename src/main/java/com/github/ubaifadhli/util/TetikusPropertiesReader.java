package com.github.ubaifadhli.util;

public class TetikusPropertiesReader {
    private static String DEFAULT_PROPERTIES_NAME = "application-%s.properties";

    public static String getValueAsString(String propertyName) {
        String currentPlatform = CurrentThreadDriver.getCurrentPlatform().name();

        return ResourceHelper.readFileAsProperties(String.format(DEFAULT_PROPERTIES_NAME, currentPlatform))
                .getProperty(propertyName);
    }

    public static int getValueAsInteger(String propertyName) {
        return Integer.parseInt(getValueAsString(propertyName));
    }

    public static double getValueAsDouble(String propertyName) {
        return Double.parseDouble(getValueAsString(propertyName));
    }
}
