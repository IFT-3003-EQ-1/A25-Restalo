package ca.ulaval.glo2003.entities.exceptions;

public class MissingParameterException extends RuntimeException {
    public String code;

    public MissingParameterException(String nomParam) {
        super(nomParam + "  est requis");
        code = "MISSING_PARAMETER";
    }
}
