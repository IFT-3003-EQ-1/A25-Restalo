package ca.ulaval.glo2003.entities.exceptions;

public class ParametreInvalideException extends RuntimeException {
    public String code;

    public ParametreInvalideException(String message) {
        super(message);

        code = "INVALID_PARAMETER";
    }
}
