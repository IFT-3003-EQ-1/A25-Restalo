package ca.ulaval.glo2003.infra.persistence.exceptions;

public class ConfigurationInvalidException extends RuntimeException {
    public ConfigurationInvalidException(String message) {
        super(message);
    }
}
