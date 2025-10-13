package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.entities.Proprietaire;
import ca.ulaval.glo2003.entities.exceptions.ParametreManquantException;

public class ProprietaireFactory {

    public Proprietaire createProprietaire(String proprietaireId) {
        if (proprietaireId == null || proprietaireId.isBlank()) {
            throw new ParametreManquantException("Owner");
        }
        return new Proprietaire(proprietaireId);
    }
}
