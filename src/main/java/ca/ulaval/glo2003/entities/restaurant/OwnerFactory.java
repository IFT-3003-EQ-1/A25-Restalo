package ca.ulaval.glo2003.entities.restaurant;

import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;

public class OwnerFactory {

    public Owner createProprietaire(String proprietaireId) {
        if (proprietaireId == null || proprietaireId.isBlank()) {
            throw new MissingParameterException("Owner");
        }
        return new Owner(proprietaireId);
    }
}
