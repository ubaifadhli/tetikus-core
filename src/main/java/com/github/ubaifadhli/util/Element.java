package com.github.ubaifadhli.util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import lombok.Builder;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Builder
public class Element {
    private final long DEFAULT_WAIT_DURATION = 30;

    private By webLocator;
    private By mobileLocator;
    private int[] mobileCoordinate;

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
            webElement = null;

        } else if (validToRunOnMobile()) {
            if (mobileUseCoordinate()) {
                forMobile().tap(mobileCoordinate[0], mobileCoordinate[1]);

            } else {
                if (mobileElement == null)
                    mobileElement = getMobileDriver().findElement(mobileLocator);

                mobileElement.click();
                mobileElement = null;
            }

        }
    }

    private boolean mobileUseCoordinate() {
        return mobileCoordinate != null;
    }

    public void pressEnter() {
        if (validToRunOnWeb())
            webElement.sendKeys(Keys.ENTER);
        else
            new MobileElementFunction(getMobileDriver()).pressEnter();
    }

    public void clear() {
        if (validToRunOnWeb()) {
            if (webElement == null)
                webElement = getWebDriver().findElement(webLocator);

            webElement.clear();

        } else if (validToRunOnMobile()) {
            if (mobileElement == null)
                mobileElement = getMobileDriver().findElement(mobileLocator);

            mobileElement.clear();

        }
    }

    public void clearAndType(String text) {
        if (validToRunOnWeb()) {
            if (webElement == null)
                webElement = getWebDriver().findElement(webLocator);

            webElement.clear();
            webElement.sendKeys(text);

        } else if (validToRunOnMobile()) {
            if (mobileElement == null)
                mobileElement = getMobileDriver().findElement(mobileLocator);

            mobileElement.clear();
            mobileElement.sendKeys(text);

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

    public void typeAndEnter(String text) {
        if (validToRunOnWeb()) {
            if (webElement == null)
                webElement = getWebDriver().findElement(webLocator);

            webElement.sendKeys(text);
            webElement.sendKeys(Keys.ENTER);

        } else if (validToRunOnMobile()) {
            if (mobileElement == null)
                mobileElement = getMobileDriver().findElement(mobileLocator);

            mobileElement.sendKeys(text);
            ((AndroidDriver) getMobileDriver()).pressKey(new KeyEvent(AndroidKey.ENTER));
        }
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


