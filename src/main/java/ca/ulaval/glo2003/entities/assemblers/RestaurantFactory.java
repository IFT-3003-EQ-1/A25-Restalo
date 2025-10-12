package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.entities.Proprietaire;
import ca.ulaval.glo2003.entities.Restaurant;
import ca.ulaval.glo2003.entities.exceptions.ParametreInvalideException;
import ca.ulaval.glo2003.entities.exceptions.ParametreManquantException;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class RestaurantFactory {


    public RestaurantFactory() {
    }

    public Restaurant createRestaurant(Proprietaire proprietaire,
                                       String nom,
                                       int capacite,
                                       String horaireOuverture,
                                       String horaireFermeture) {
        if (nom == null) {
            throw new ParametreManquantException("nom");
        }
        if (horaireOuverture == null) {
            throw new ParametreManquantException("horaireOuverture");
        }
        if (horaireFermeture == null) {
            throw new  ParametreManquantException("horaireFermeture");
        }

        if (nom.isBlank()) {
            throw new ParametreInvalideException("Le nom ne peut pas être vide");
        }

        if (capacite < 1) {
            throw new ParametreInvalideException("La capacité doit être au moins 1");
        }

        LocalTime ouverture;
        LocalTime fermeture;
        try {
            ouverture = LocalTime.parse(horaireOuverture);
            fermeture = LocalTime.parse(horaireFermeture);
        } catch (DateTimeParseException e) {
            throw new ParametreInvalideException("`horaires.*` doivent respecter le format HH:mm:ss");
        }

        LocalTime min = LocalTime.MIDNIGHT;
        LocalTime max = LocalTime.of(23, 59, 59);

        if (!ouverture.isBefore(fermeture)) {
            throw  new ParametreInvalideException("`ouverture` doit être strictement avant `fermeture`");
        }

        if (ouverture.isBefore(min) || fermeture.isAfter(max)) {
            throw new ParametreInvalideException("Les heures doivent être entre 00:00:00 et 23:59:59");
        }

        if (Duration.between(ouverture, fermeture).toMinutes() < 60) {
            throw new ParametreInvalideException("Le restaurant doit être ouvert au moins 1 heure");
        }

        String identifiant = UUID.randomUUID().toString().replace("-", "");

        return new Restaurant(identifiant, proprietaire, nom, capacite, horaireOuverture, horaireFermeture);
    }
}
