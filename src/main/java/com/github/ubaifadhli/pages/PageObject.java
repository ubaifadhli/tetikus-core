package com.github.ubaifadhli.pages;

import io.appium.java_client.AppiumDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import com.github.ubaifadhli.util.CurrentThreadDriver;

/**
 * Base class for implementing Page Object design pattern.
 * Includes several basic functionality for accessing platform drivers.
 *
 * @author Fadhli Ubai
 *
 */
@Slf4j
public class PageObject {
    /**
     * Stores URL of website to interact with. Only used for Web platform.
     */
    protected String baseURL;

    public PageObject() {}

    /**
     * Opens website of related PageObject using value of {@link PageObject#baseURL}. Only used for Web platform.
     */
    public void openPage() {
        if (isCurrentPlatformWeb())
            getWebDriver().get(baseURL);
        else
            log.debug("openPage method is not executed because it currently runs at Mobile.");

    }

    /**
     * Opens website to interact with. Only used for Web platform.
     * @param url URL of website to interact with.
     */
    public void openPage(String url) {
        if (isCurrentPlatformWeb())
            getWebDriver().get(url);
        else
            log.debug("openPage method is not executed because it currently runs at Mobile.");
    }


    /**
     * Puts test execution to sleep for certain amount of time.
     * @param seconds Duration of waiting, in seconds.
     */
    public void waitFor(long seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Superclass WebDriver instance that current thread handles.
     * Might also return AppiumDriver instance, but using {@link PageObject#getMobileDriver()} is preferred.
     */
    protected WebDriver getWebDriver() {
        return CurrentThreadDriver.getWebDriver();
    }

    /**
     * @return AppiumDriver instance that current thread handles.
     * Might fail when used in Web thread, so using {@link PageObject#getWebDriver()} is preferred.
     */
    protected AppiumDriver getMobileDriver() {
        return CurrentThreadDriver.getMobileDriver();
    }

    /**
     * Checks whether current thread runs for Web platform or not.
     */
    protected boolean isCurrentPlatformWeb() {
        return CurrentThreadDriver.isCurrentPlatformWeb();
    }

    /**
     * Checks whether current thread runs for Mobile platform or not.
     */
    protected boolean isCurrentPlatformMobile() {
        return CurrentThreadDriver.isCurrentPlatformMobile();
    }
}
