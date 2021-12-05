package com.github.ubaifadhli.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ubaifadhli.exceptions.DriverConfigurationParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class DriverConfigurationHelper {
    private static final String DEFAULT_DRIVER_CONFIGURATION_PATH = "/driverconfig.json";

    public static List<DriverConfiguration> getDriverConfigurations() {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream driverConfigurationsStream = DriverConfigurationHelper.class.getResourceAsStream(DEFAULT_DRIVER_CONFIGURATION_PATH);

        try {
            return Arrays.asList(objectMapper.readValue(driverConfigurationsStream, DriverConfiguration[].class));
        } catch (IOException e) {
            throw new DriverConfigurationParseException(e);
        }
    }

    public static int getDriverConfigurationsCount() {
        return getDriverConfigurations().size();
    }
}
