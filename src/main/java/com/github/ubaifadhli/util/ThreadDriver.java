package com.github.ubaifadhli.util;

import com.github.ubaifadhli.constants.PlatformConstant;
import lombok.Data;
import org.openqa.selenium.WebDriver;

@Data
public class ThreadDriver {
    private final DriverConfiguration driverConfiguration;
    private WebDriver driver;

    public ThreadDriver(DriverConfiguration driverConfiguration) {
        this.driverConfiguration = driverConfiguration;
    }

    // Synchronized to prevent duplicate session ID. - STILL BROKEN
    public void initializeDriver() {
        System.out.println(System.currentTimeMillis() + "lagi init driver");

        PlatformConstant currentPlatform = PlatformConstant.valueOf(driverConfiguration.getPlatform());

        switch (currentPlatform) {
            case WEB:
                driver = DriverFactory.createWebDriver(driverConfiguration);
                break;

            case MOBILE:
                driver = DriverFactory.createMobileDriver(driverConfiguration);
                break;
        }
    }

    public void destroyDriver() {
        System.out.println("kepanggil dari " + Thread.currentThread().getId());
        driver.quit();
    }

}
