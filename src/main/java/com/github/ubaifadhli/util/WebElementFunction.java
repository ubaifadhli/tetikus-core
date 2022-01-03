package com.github.ubaifadhli.util;

import org.openqa.selenium.WebDriver;

public class WebElementFunction {
    private WebDriver driver;

    public WebElementFunction(WebDriver driver) {
        this.driver = driver;
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }
}