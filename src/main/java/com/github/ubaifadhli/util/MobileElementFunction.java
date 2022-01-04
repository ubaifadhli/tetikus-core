package com.github.ubaifadhli.util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import java.time.Duration;

public class MobileElementFunction {
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

    public void tap(int xCoordinate, int yCoordinate) {
        TouchAction touchAction = new TouchAction(driver);

        touchAction.tap(PointOption.point(xCoordinate, yCoordinate))
                .release()
                .perform();
    }

    public void swipe(int swipeUpPercentage, SwipeDirection swipeDirection) {
        int deviceMiddleY = driver.manage().window().getSize().getHeight() / 2;
        int deviceMiddleX = driver.manage().window().getSize().getWidth() / 2;

        if (swipeUpPercentage < 0 || swipeUpPercentage > 100)
            throw new RuntimeException("ScrollDownPercentage should be ranged from 0 to 100.");

        int deviceEndScrollX;
        int deviceEndScrollY;

        switch (swipeDirection) {
            case UP:
                deviceEndScrollX = deviceMiddleX;
                deviceEndScrollY = (int) (deviceMiddleY * (double) (100 - swipeUpPercentage) / 100);
                break;

            case DOWN:
                deviceEndScrollX = deviceMiddleX;
                deviceEndScrollY = (int) (deviceMiddleY * (double) (100 + swipeUpPercentage) / 100);
                break;

            case LEFT:
                deviceEndScrollX = (int) (deviceMiddleX * (double) (100 - swipeUpPercentage) / 100);
                deviceEndScrollY = deviceMiddleY;
                break;

            case RIGHT:
                deviceEndScrollX = (int) (deviceMiddleX * (double) (100 + swipeUpPercentage) / 100);
                deviceEndScrollY = deviceMiddleY;
                break;

            default:
                deviceEndScrollX = deviceMiddleX;
                deviceEndScrollY = deviceMiddleY;
                break;
        }

        TouchAction touchAction = new TouchAction(driver);

        touchAction.press(PointOption.point(deviceMiddleX, deviceMiddleY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))) // IDK why but it wont work without this.
                .moveTo(PointOption.point(deviceEndScrollX, deviceEndScrollY))
                .release()
                .perform();
    }
}