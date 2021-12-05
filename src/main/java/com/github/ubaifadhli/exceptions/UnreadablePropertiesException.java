package com.github.ubaifadhli.exceptions;

public class UnreadablePropertiesException extends RuntimeException{
    private static final String MESSAGE = "Failed to read properties from %s";

    public UnreadablePropertiesException(String propertiesFilename, Throwable error) {
        super(String.format(MESSAGE, propertiesFilename), error);
    }
}
