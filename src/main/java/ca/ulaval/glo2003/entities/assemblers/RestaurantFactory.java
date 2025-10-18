package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.restaurant.ConfigReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.ProprietaireDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.restaurant.ConfigReservation;
import ca.ulaval.glo2003.entities.restaurant.Proprietaire;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.exceptions.ParametreInvalideException;
import ca.ulaval.glo2003.entities.exceptions.ParametreManquantException;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class RestaurantFactory {

    public static final int DEFAULT_RESERVATION_DURATION = 60; // minutes

    public RestaurantFactory() {
    }

    public Restaurant createRestaurant(Proprietaire proprietaire, RestaurantDto restaurantDto) {
        if (restaurantDto.nom == null) {
            throw new ParametreManquantException("nom");
        }
        if (restaurantDto.horaireOuverture == null) {
            throw new ParametreManquantException("horaireOuverture");
        }
        if (restaurantDto.horaireFermeture == null) {
            throw new  ParametreManquantException("horaireFermeture");
        }

        if (restaurantDto.nom.isBlank()) {
            throw new ParametreInvalideException("Le nom ne peut pas être vide");
        }

        if (restaurantDto.capacite < 1) {
            throw new ParametreInvalideException("La capacité doit être au moins 1");
        }

        LocalTime ouverture;
        LocalTime fermeture;
        try {
            ouverture = LocalTime.parse(restaurantDto.horaireOuverture);
            fermeture = LocalTime.parse(restaurantDto.horaireFermeture);
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

        ConfigReservation config = createConfigReservation(restaurantDto.configReservation);

        return new Restaurant(
                identifiant,
                proprietaire,
                restaurantDto.nom,
                restaurantDto.capacite,
                restaurantDto.horaireOuverture,
                restaurantDto.horaireFermeture,
                config);
    }

    private ConfigReservation createConfigReservation(ConfigReservationDto  configReservationDto) {
        if(configReservationDto == null) {
            return new ConfigReservation(DEFAULT_RESERVATION_DURATION);
        } else {
            return new ConfigReservation(configReservationDto.duration);
        }
    }
}
