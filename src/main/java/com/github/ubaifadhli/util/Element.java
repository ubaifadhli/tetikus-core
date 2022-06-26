package com.github.ubaifadhli.util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import lombok.Builder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@Builder
public class Element {
    private final long DEFAULT_WAIT_DURATION = 30;
    private By webLocator;
    private By mobileLocator;
    private int[] mobileCoordinate;

    private WebDriver getWebDriver() {
        return CurrentThreadDriver.getWebDriver();
    }

    private AppiumDriver getMobileDriver() {
        return CurrentThreadDriver.getMobileDriver();
    }

    private WebElement getWebElement() {
        return getWebDriver().findElement(webLocator);
    }

    private MobileElement getMobileElement() {
        return (MobileElement) getMobileDriver().findElement(mobileLocator);
    }

    private List<WebElement> asElements() {
        if (validToRunOnWeb())
            return getWebDriver().findElements(webLocator);

        else
            return getMobileDriver().findElements(mobileLocator);
    }

    public void click() {
        if (validToRunOnWeb())
            getWebElement().click();

        else if (validToRunOnMobile())
            if (mobileUseCoordinate())
                forMobile().tap(mobileCoordinate[0], mobileCoordinate[1]);
            else
                getMobileElement().click();
    }

    private boolean mobileUseCoordinate() {
        return mobileCoordinate != null && mobileCoordinate.length > 1;
    }

    public void pressEnter() {
        if (validToRunOnWeb())
            getWebElement().sendKeys(Keys.ENTER);

        else if (validToRunOnMobile())
            new MobileElementFunction(getMobileDriver()).pressEnter();
    }

    public void clear() {
        if (validToRunOnWeb())
            getWebElement().clear();

        else if (validToRunOnMobile())
            getMobileElement().clear();
    }

    public void clearAndType(String text) {
        clear();
        typeIntoField(text);
    }

    public void typeIntoField(String text) {
        if (validToRunOnWeb())
            getWebElement().sendKeys(text);

        else if (validToRunOnMobile())
            getMobileElement().sendKeys(text);
    }

    public Element waitUntilClickable() {
        By currentLocator = validToRunOnWeb() ? webLocator : mobileLocator;
        WebDriver currentDriver = validToRunOnWeb() ? getWebDriver() : getMobileDriver();

        WebDriverWait webDriverWait = new WebDriverWait(currentDriver, DEFAULT_WAIT_DURATION);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(currentLocator));

        return this;
    }

    public Element waitUntilVisible() {
        By currentLocator = validToRunOnWeb() ? webLocator : mobileLocator;
        WebDriver currentDriver = validToRunOnWeb() ? getWebDriver() : getMobileDriver();

        WebDriverWait webDriverWait = new WebDriverWait(currentDriver, DEFAULT_WAIT_DURATION);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(currentLocator));

        return this;
    }

    public void typeAndEnter(String text) {
        typeIntoField(text);

        if (validToRunOnWeb())
            getWebElement().sendKeys(Keys.ENTER);

        else if (validToRunOnMobile())
            ((AndroidDriver) getMobileDriver()).pressKey(new KeyEvent(AndroidKey.ENTER));
    }

    public String getText() {
        if (validToRunOnWeb())
            return getWebElement().getText();

        else if (validToRunOnMobile())
            return getMobileElement().getText();

        else
            throw new RuntimeException("Element did not run on any platform because locator has not been set up.");
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
