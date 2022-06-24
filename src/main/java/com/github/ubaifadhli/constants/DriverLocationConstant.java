package com.github.ubaifadhli.constants;

/**
 * Location of platforms that you want to execute tests on.
 *
 * @author Fadhli Ubai
 */
public enum DriverLocationConstant {

    /**
     * Used when tests would be run on local machine. Only works for Web platform.
     * Using this enum would allow WebDriverManager to create WebDriver instance to run on local machine.
     */
    LOCAL,

    /**
     * Used when tests would be run on remote machine (or local Appium server).
     */
    REMOTE
}
