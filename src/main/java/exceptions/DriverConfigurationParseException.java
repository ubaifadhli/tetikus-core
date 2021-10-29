package exceptions;

public class DriverConfigurationParseException extends RuntimeException{
    private static final String MESSAGE = "Failed to parse Driver Configuration.";

    public DriverConfigurationParseException(Throwable error) {
        super(MESSAGE, error);
    }
}
