package com.github.ubaifadhli.util;

import lombok.SneakyThrows;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ScreenshotHelper {
    public static String DEFAULT_SCREENSHOT_EXTENSION = "png";
    public static String DEFAULT_SCREENSHOT_FOLDER_NAME = "screenshots/";
    public static String DEFAULT_SCREENSHOT_FILENAME_FORMAT = "%s.%s";

    @SneakyThrows
    public static void getScreenshotForCurrentPlatform(String methodID) {
        TakesScreenshot screenshot = (TakesScreenshot) CurrentThreadDriver.getCurrentInstance().getDriver();
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);

        Path screenshotFolderPath = Paths.get(ReportingHelper.DEFAULT_REPORTING_DIRECTORY + DEFAULT_SCREENSHOT_FOLDER_NAME);

        if (Files.notExists(screenshotFolderPath))
            Files.createDirectories(screenshotFolderPath);

        String screenshotFullPath = ReportingHelper.DEFAULT_REPORTING_DIRECTORY + getScreenshotName(methodID);

        Files.copy(screenshotFile.toPath(),
                //TODO Get proper path for the system
                new File(screenshotFullPath).toPath(),
                StandardCopyOption.REPLACE_EXISTING
        );
    }

    public static String getScreenshotName(String methodID) {
        return String.format(DEFAULT_SCREENSHOT_FILENAME_FORMAT,
                DEFAULT_SCREENSHOT_FOLDER_NAME + methodID,
                DEFAULT_SCREENSHOT_EXTENSION
        );
    }
}
