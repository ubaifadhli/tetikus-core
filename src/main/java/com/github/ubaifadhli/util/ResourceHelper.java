package com.github.ubaifadhli.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceHelper {
    public static InputStream readFileAsStream(String filePath) {
        if (!filePath.startsWith("/"))
            filePath = "/" + filePath;

        return ResourceHelper.class.getResourceAsStream(filePath);
    }

    public static Properties readFileAsProperties(String filePath) {
        InputStream fileStream = readFileAsStream(filePath);

        Properties properties = new Properties();

        try {
            properties.load(fileStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read resource + " + filePath);
        }
    }
}