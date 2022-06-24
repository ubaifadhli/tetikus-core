package com.github.ubaifadhli.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ubaifadhli.exceptions.DriverConfigurationParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class DriverConfigurationHelper {
    public static List<DriverConfiguration> getDriverConfigurations() {
        ObjectMapper objectMapper = new ObjectMapper();

        String driverConfigurationPath = PropertiesHelper.getDriverConfigurationPath();

        InputStream driverConfigurationsStream = ResourceHelper.readFileAsStream(driverConfigurationPath);

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
