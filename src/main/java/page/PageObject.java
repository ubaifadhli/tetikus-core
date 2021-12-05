package page;

import io.appium.java_client.AppiumDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import util.CurrentThreadDriver;

@Slf4j
public class PageObject {
    protected String baseURL;

    public PageObject() {}

    public void openPage() {
        if (isCurrentPlatformWeb())
            getWebDriver().get(baseURL);
        else
            log.debug("openPage method is not executed because it currently runs at Mobile.");

    }

    public void openPage(String url) {
        if (isCurrentPlatformWeb())
            getWebDriver().get(url);
        else
            log.debug("openPage method is not executed because it currently runs at Mobile.");
    }


    public void waitFor(long seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected WebDriver getWebDriver() {
        return CurrentThreadDriver.getWebDriver();
    }

    protected AppiumDriver getMobileDriver() {
        return CurrentThreadDriver.getMobileDriver();
    }

    protected boolean isCurrentPlatformWeb() {
        return CurrentThreadDriver.isCurrentPlatformWeb();
    }

    protected boolean isCurrentPlatformMobile() {
        return CurrentThreadDriver.isCurrentPlatformMobile();
    }
}