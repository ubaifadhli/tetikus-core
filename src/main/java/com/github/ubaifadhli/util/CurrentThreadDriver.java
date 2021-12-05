package com.github.ubaifadhli.util;

import com.github.ubaifadhli.constants.PlatformConstant;
import io.appium.java_client.AppiumDriver;
import lombok.extern.java.Log;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Log
public class CurrentThreadDriver {
    private static ConcurrentHashMap<Long, ThreadDriver> DRIVER_MAP;
    private static List<ThreadDriver> UNASSIGNED_DRIVERS;

    public static ThreadDriver getCurrentInstance() {
        return DRIVER_MAP.get(getCurrentThreadID());
    }

    private static long getCurrentThreadID() {
        return Thread.currentThread().getId();
    }

    public static WebDriver getWebDriver() {
        return getCurrentInstance().getDriver();
    }

    public static AppiumDriver getMobileDriver() {
        return (AppiumDriver) getWebDriver();
    }

    public static void initializeDriverForCurrentThread() {
        if (DRIVER_MAP.get(getCurrentThreadID()) != null)
            log.info(String.format("Thread %s already has an assigned driver.", Thread.currentThread().getId()));

        else {
            if (UNASSIGNED_DRIVERS.size() == 0)
                throw new RuntimeException("No unassigned driver found.");

            ThreadDriver threadDriver = UNASSIGNED_DRIVERS.get(0);
            threadDriver.initializeDriver();

            DRIVER_MAP.put(getCurrentThreadID(), threadDriver);
            UNASSIGNED_DRIVERS.remove(0);
        }
    }

    public static void destroyDriverForCurrentThread() {
        if (DRIVER_MAP.get(getCurrentThreadID()) != null) {
            ThreadDriver threadDriver = DRIVER_MAP.get(getCurrentThreadID());
            threadDriver.destroyDriver();

            UNASSIGNED_DRIVERS.add(threadDriver);

            DRIVER_MAP.remove(getCurrentThreadID());
        }
    }

    public static void readConfigurations(List<DriverConfiguration> driverConfigurations) {
        DRIVER_MAP = new ConcurrentHashMap<>();
        UNASSIGNED_DRIVERS = new ArrayList<>();

        driverConfigurations.forEach(currentConfig -> {
            ThreadDriver threadDriver = new ThreadDriver(currentConfig);
            UNASSIGNED_DRIVERS.add(threadDriver);
        });
    }

    public static boolean isCurrentPlatformWeb() {
        return getCurrentPlatform() == PlatformConstant.WEB;
    }

    public static boolean isCurrentPlatformMobile() {
        return getCurrentPlatform() == PlatformConstant.MOBILE;
    }

    public static PlatformConstant getCurrentPlatform() {
        return PlatformConstant.valueOf(getCurrentInstance().getDriverConfiguration().getPlatform());
    }
}