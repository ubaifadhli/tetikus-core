package com.github.ubaifadhli.util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lombok.Builder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Builder
public class Element {
    private final long DEFAULT_WAIT_DURATION = 30;

    private By webLocator;
    private By mobileLocator;

    private WebElement webElement;
    private WebElement mobileElement;

    private WebDriver getWebDriver() {
        return CurrentThreadDriver.getWebDriver();
    }

    private AppiumDriver getMobileDriver() {
        return CurrentThreadDriver.getMobileDriver();
    }

    public void click() {
        if (validToRunOnWeb()) {
            if (webElement == null)
                webElement = getWebDriver().findElement(webLocator);

            webElement.click();

        } else if (validToRunOnMobile()) {
            if (mobileElement == null)
                mobileElement = getMobileDriver().findElement(mobileLocator);

            mobileElement.click();

        }
    }

    public void typeIntoField(String text) {
        if (validToRunOnWeb()) {
            if (webElement == null)
                webElement = getWebDriver().findElement(webLocator);

            webElement.sendKeys(text);

        } else if (validToRunOnMobile()) {
            if (mobileElement == null)
                mobileElement = getMobileDriver().findElement(mobileLocator);

            mobileElement.sendKeys(text);

        }
    }

    public Element waitUntilClickable() {
        if (validToRunOnWeb()) {
            WebDriverWait webDriverWait = new WebDriverWait(getWebDriver(), DEFAULT_WAIT_DURATION);
            webElement = webDriverWait.until(ExpectedConditions.elementToBeClickable(webLocator));

        } else if (validToRunOnMobile()) {
            WebDriverWait webDriverWait = new WebDriverWait(getMobileDriver(), DEFAULT_WAIT_DURATION);
            mobileElement = webDriverWait.until(ExpectedConditions.elementToBeClickable(mobileLocator));

        }

        return this;
    }

    public Element waitUntilVisible() {
        if (validToRunOnWeb()) {
            WebDriverWait webDriverWait = new WebDriverWait(getWebDriver(), DEFAULT_WAIT_DURATION);
            webElement = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(webLocator));

        } else if (validToRunOnMobile()) {
            WebDriverWait webDriverWait = new WebDriverWait(getMobileDriver(), DEFAULT_WAIT_DURATION);
            mobileElement = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(mobileLocator));

        }

        return this;
    }

    public String getText() {
        if (validToRunOnWeb()) {
            if (webElement == null)
                webElement = getWebDriver().findElement(webLocator);

            return webElement.getText();

        } else if (validToRunOnMobile()) {
            if (mobileElement == null)
                mobileElement = getMobileDriver().findElement(mobileLocator);

            return mobileElement.getText();

        } else {
            throw new RuntimeException("Element did not run on any platform because locator has not been set up.");
        }
    }

    public WebElementFunction forWeb() {
        return new WebElementFunction(getWebDriver());
    }

    public MobileElementFunction forMobile() {
        return new MobileElementFunction(getMobileDriver());
    }

    private boolean isCurrentPlatformWeb() {
        return CurrentThreadDriver.isCurrentPlatformWeb();
    }

    private boolean validToRunOnWeb() {
        return CurrentThreadDriver.isCurrentPlatformWeb() && webLocator != null;
    }

    private boolean validToRunOnMobile() {
        return CurrentThreadDriver.isCurrentPlatformMobile() && mobileLocator != null;
    }
}

class WebElementFunction {
    private WebDriver driver;

    public WebElementFunction(WebDriver driver) {
        this.driver = driver;
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }
}

class MobileElementFunction {
    private AppiumDriver driver;

    public MobileElementFunction(AppiumDriver driver) {
        this.driver = driver;
    }

    private AndroidDriver asAndroidDriver() {
        return ((AndroidDriver) driver);
    }

    public void pressEnter() {
        asAndroidDriver().pressKey(new KeyEvent(AndroidKey.ENTER));
    }

    public void goBack() {
        asAndroidDriver().pressKey(new KeyEvent(AndroidKey.BACK));
    }

    public void swipeUp(int swipeUpPercentage) {
        int deviceMiddleY = driver.manage().window().getSize().getHeight() / 2;
        int deviceMiddleX = driver.manage().window().getSize().getWidth() / 2;

        if (swipeUpPercentage < 0 || swipeUpPercentage > 100)
            throw new RuntimeException("ScrollDownPercentage should be ranged from 0 to 100.");

        // The lowest value for this coordinate is 0, which means that it will swipe up to the top of the screen.
        int deviceEndScrollY = (int) (deviceMiddleY * (double) (100 - swipeUpPercentage) / 100);

        TouchAction touchAction = new TouchAction(driver);

        touchAction.press(PointOption.point(deviceMiddleX, deviceMiddleY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))) // IDK why but it wont work without this.
                .moveTo(PointOption.point(deviceMiddleX, deviceEndScrollY))
                .release()
                .perform();
    }
}
