package ca.ulaval.glo2003.entities.exceptions;

public class ParametreManquantException extends RuntimeException {
    public String code;

    public ParametreManquantException(String nomParam) {
        super(nomParam + "  est requis");
        code = "MISSING_PARAMETER";
    }
}
