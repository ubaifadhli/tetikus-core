package page;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

public class MobilePageObject {
    private final AppiumDriver<MobileElement> driver;

    public MobilePageObject(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
    }

    public MobileElement getElementByAccessibilityId(String accessibilityId) {
        return driver.findElementByAccessibilityId(accessibilityId);
    }

    public MobileElement getElementById(String id) {
        return driver.findElementById(id);
    }

    public void clickElementByAccessibilityId(String accessibilityId) {
        getElementByAccessibilityId(accessibilityId).click();
    }

    public void clickElementById(String id) {
        getElementById(id).click();
    }

    public void typeToElementById(String id, String text) {
        getElementById(id).sendKeys(text);
    }

    public void swipeByPoints(int xFrom, int yFrom, int xTo, int yTo) {
        (new TouchAction(driver))
                .press(PointOption.point(xFrom, yFrom))
                .moveTo(PointOption.point(xTo, yTo))
                .release()
                .perform();
    }
}
