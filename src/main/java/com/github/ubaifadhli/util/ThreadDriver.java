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

    public void initializeDriver() {
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
        driver.quit();
    }

    public void closeDriver() {
        driver.close();
    }
}
