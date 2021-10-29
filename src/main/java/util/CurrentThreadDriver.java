package util;

import constants.PlatformConstant;
import io.appium.java_client.AppiumDriver;
import lombok.extern.java.Log;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.ConcurrentHashMap;

@Log
public class CurrentThreadDriver {
    private static ConcurrentHashMap<Long, CurrentThreadDriver> DRIVER_MAP;
    private final DriverConfiguration driverConfiguration;
    private WebDriver driver;

    private CurrentThreadDriver(DriverConfiguration driverConfiguration) {
        this.driverConfiguration = driverConfiguration;
    }

    private static CurrentThreadDriver getInstance() {
        return DRIVER_MAP.get(getCurrentThreadID());
    }

    private static long getCurrentThreadID() {
        return Thread.currentThread().getId();
    }

    public static WebDriver getWebDriver() {
        return getInstance().driver;
    }

    public static AppiumDriver getMobileDriver() {
        return (AppiumDriver) getWebDriver();
    }

    public static void initializeDriver(DriverConfiguration driverConfiguration) {
        if (DRIVER_MAP == null)
            DRIVER_MAP = new ConcurrentHashMap<>();


        if (DRIVER_MAP.containsKey(getCurrentThreadID())) {
            log.warning(String.format("Driver for thread %s has already been initialized and would not be reinitialized.", getCurrentThreadID()));

        } else {
            CurrentThreadDriver currentThreadDriver = new CurrentThreadDriver(driverConfiguration);

            PlatformConstant currentPlatform = PlatformConstant.valueOf(driverConfiguration.getPlatform());

            switch (currentPlatform) {
                case WEB:
                    currentThreadDriver.driver = DriverFactory.createWebDriver(driverConfiguration);
                    break;

                case MOBILE:
                    currentThreadDriver.driver = DriverFactory.createMobileDriver(driverConfiguration);
                    break;
            }

            DRIVER_MAP.put(getCurrentThreadID(), currentThreadDriver);
        }
    }

    public static boolean isCurrentPlatformWeb() {
        return getCurrentPlatform() == PlatformConstant.WEB;
    }

    public static boolean isCurrentPlatformMobile() {
        return getCurrentPlatform() == PlatformConstant.MOBILE;
    }

    public static PlatformConstant getCurrentPlatform() {
        return PlatformConstant.valueOf(getInstance().driverConfiguration.getPlatform());
    }
}
