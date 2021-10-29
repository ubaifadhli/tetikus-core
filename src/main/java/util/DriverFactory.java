package util;

import constants.DriverLocationConstant;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import lombok.SneakyThrows;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;
import java.net.URL;

public class DriverFactory {
    @SneakyThrows
    public static AppiumDriver<MobileElement> createMobileDriver(DriverConfiguration driverConfiguration) {
        DesiredCapabilities desiredCapabilities = driverConfiguration.getDesiredCapabilities();

        URL appiumDriverURL = URI.create(driverConfiguration.getUrl()).toURL();
        return new AndroidDriver<>(appiumDriverURL, desiredCapabilities);
    }

    @SneakyThrows
    public static WebDriver createWebDriver(DriverConfiguration driverConfiguration) {
        DriverLocationConstant locationConstant = DriverLocationConstant.valueOf(driverConfiguration.getLocation());

        switch (locationConstant) {
            case LOCAL:
                // TODO do assertion on browser field, but probably not needed because of runtime exception from enum

                DriverManagerType driverType = DriverManagerType.valueOf(driverConfiguration.getBrowser());
                WebDriverManager.getInstance(driverType).setup();

                Class<?> driverClass = Class.forName(driverType.browserClass());
                return (WebDriver) driverClass.newInstance();

            case REMOTE:
                ImmutableCapabilities capabilities = new ImmutableCapabilities("browserName", driverConfiguration.getBrowser());
                URL webDriverURL = URI.create(driverConfiguration.getUrl()).toURL();

                return new RemoteWebDriver(webDriverURL, capabilities);

            default:
                // TODO create more grace handle for this
                throw new RuntimeException("");
        }
    }
}