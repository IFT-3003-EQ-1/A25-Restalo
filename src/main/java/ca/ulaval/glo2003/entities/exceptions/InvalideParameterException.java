package ca.ulaval.glo2003.entities.exceptions;

public class InvalideParameterException extends RuntimeException {
    public String code;

    public InvalideParameterException(String message) {
        super(message);

        code = "INVALID_PARAMETER";
    }
}
