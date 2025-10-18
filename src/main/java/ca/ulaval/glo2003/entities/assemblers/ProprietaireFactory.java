package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.entities.restaurant.Proprietaire;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;

public class ProprietaireFactory {

    public Proprietaire createProprietaire(String proprietaireId) {
        if (proprietaireId == null || proprietaireId.isBlank()) {
            throw new MissingParameterException("Owner");
        }
        return new Proprietaire(proprietaireId);
    }
}
