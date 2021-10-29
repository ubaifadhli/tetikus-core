package page;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PageObject {
    WebDriver driver;

    public PageObject(WebDriver driver) {
        this.driver = driver;
    }

    public void openPageAt(String url) {
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public WebElement getElementAfterClickable(String element) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element)));
        return getElement(element);
    }

    public List<WebElement> getElementsAfterClickable(String elements) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(elements)));
        return getElements(elements);
    }

    public WebElement getElement(String element) {
        return driver.findElement(By.xpath(element));
    }

    public boolean doesElementExist(String element) {
        try {
            driver.findElement(By.xpath(element));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public List<WebElement> getElements(String elements) {
        return driver.findElements(By.xpath(elements));
    }

    public void clickElementAfterClickable(String element) {
        getElementAfterClickable(element).click();
    }

    public void clickElementAt(String elements, int index) {
        getElements(elements).get(index).click();
    }

    public void typeInput(String element, String input, Keys keysInput) {
        WebElement foundElement = getElement(element);

        foundElement.clear();

        foundElement.sendKeys(input, keysInput);
    }

    public String getTextFromClickableElement(String element) {
        return getElementAfterClickable(element).getText();
    }

    public List<String> getTextFromClickableElements(String elements) {
        List<String> texts = new ArrayList<>();

        for (WebElement element : getElementsAfterClickable(elements))
            texts.add(element.getText());

        return texts;
    }

    public String getTextFromClickableElement(WebElement element) {
        return element.getText();
    }

    public void waitForElementToStale(String element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.stalenessOf(getElement(element)));
        } catch (NoSuchElementException exception) {
            log.info("Staling element was not found. Trying to continue executing the test steps.");
        }
    }
}
