package com.github.ubaifadhli.util;

import com.github.ubaifadhli.constants.DriverLocationConstant;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URI;
import java.net.URL;

@Slf4j
public class DriverFactory {
    @SneakyThrows
    public static AppiumDriver<MobileElement> createMobileDriver(DriverConfiguration driverConfiguration) {
        DesiredCapabilities desiredCapabilities = driverConfiguration.getDesiredCapabilities();

        if (TetikusPropertiesHelper.isKeepDriverSession())
            desiredCapabilities.setCapability("noReset", true);

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

                WebDriver webDriver;

                switch (driverType) {
                    case CHROME:
                        ChromeOptions chromeOptions = new ChromeOptions();

                        String browserSessionAbsolutePath = TetikusPropertiesHelper.getBrowserSessionAbsolutePath();

                        if (!browserSessionAbsolutePath.equals(TetikusPropertiesHelper.DEFAULT_SESSION_FOLDER_PATH)) {
                            File sessionPathFile = new File(browserSessionAbsolutePath);

                            if (sessionPathFile.exists() && sessionPathFile.isDirectory())
                                log.info("Browser session folder already exists : " + browserSessionAbsolutePath);
                            else
                                sessionPathFile.mkdirs();

                            String optionArgument = String.format("--user-data-dir=%s", browserSessionAbsolutePath);
                            chromeOptions.addArguments(optionArgument);
                        }

                        webDriver = new ChromeDriver(chromeOptions);

                        break;

                    default:
                        Class<?> driverClass = Class.forName(driverType.browserClass());
                        webDriver = (WebDriver) driverClass.newInstance();
                }

                return webDriver;

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
