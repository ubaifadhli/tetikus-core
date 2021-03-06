package com.github.ubaifadhli.util;

import com.github.ubaifadhli.constants.PlatformConstant;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import lombok.extern.java.Log;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Log
public class CurrentThreadDriver {
    private static ConcurrentHashMap<Long, ThreadDriver> DRIVER_MAP = new ConcurrentHashMap<>();
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

    public static AndroidDriver getAndroidDriver() {
        return (AndroidDriver) getMobileDriver();
    }

    public static void initializeDriverForCurrentThread() {
        if (DRIVER_MAP.get(getCurrentThreadID()) != null)
            log.info(String.format("Thread %s already has an assigned driver.", Thread.currentThread().getId()));

        else {
            synchronized (CurrentThreadDriver.class) {
                try {
                    if (UNASSIGNED_DRIVERS.size() == 0)
                        throw new RuntimeException("No unassigned driver found.");

                    ThreadDriver threadDriver = UNASSIGNED_DRIVERS.get(0);
                    threadDriver.initializeDriver();

                    DRIVER_MAP.put(getCurrentThreadID(), threadDriver);

                } finally {
                    UNASSIGNED_DRIVERS.remove(0);
                }
            }
        }
    }

    public static void destroyDriverForCurrentThread() {
        if (DRIVER_MAP.get(getCurrentThreadID()) != null) {
            synchronized (CurrentThreadDriver.class) {
                ThreadDriver threadDriver = DRIVER_MAP.get(getCurrentThreadID());

                threadDriver.destroyDriver();

                UNASSIGNED_DRIVERS.add(threadDriver);
                DRIVER_MAP.remove(getCurrentThreadID());
            }
        }
    }

    // No need for synchronization because in this phase the runner still runs as single thread.
    public static void readConfigurations(List<DriverConfiguration> driverConfigurations) {
        UNASSIGNED_DRIVERS = new ArrayList<>();

        driverConfigurations.stream()
                .map(ThreadDriver::new)
                .forEach(UNASSIGNED_DRIVERS::add);
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
