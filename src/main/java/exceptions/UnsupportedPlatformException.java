package exceptions;

import constants.PlatformConstant;

public class UnsupportedPlatformException extends RuntimeException {
    private static final String MESSAGE = "Platform %s is currently not supported.";

    public UnsupportedPlatformException(String platformName) {
        super(String.format(MESSAGE, platformName));
    }
}
