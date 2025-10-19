package ca.ulaval.glo2003.entities.restaurant;

import ca.ulaval.glo2003.domain.dtos.restaurant.ConfigReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.exceptions.InvalideParameterException;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class RestaurantFactory {

    public static final int DEFAULT_RESERVATION_DURATION = 60; // minutes

    public RestaurantFactory() {
    }

    public Restaurant createRestaurant(Owner proprietaire, RestaurantDto restaurantDto) {
        if (restaurantDto.nom == null) {
            throw new MissingParameterException("nom");
        }
        if (restaurantDto.hoursOpen == null) {
            throw new MissingParameterException("horaireOuverture");
        }
        if (restaurantDto.hoursClose == null) {
            throw new MissingParameterException("horaireFermeture");
        }

        if (restaurantDto.nom.isBlank()) {
            throw new InvalideParameterException("Le nom ne peut pas être vide");
        }

        if (restaurantDto.capacity < 1) {
            throw new InvalideParameterException("La capacité doit être au moins 1");
        }

        LocalTime ouverture;
        LocalTime fermeture;
        try {
            ouverture = LocalTime.parse(restaurantDto.hoursOpen);
            fermeture = LocalTime.parse(restaurantDto.hoursClose);
        } catch (DateTimeParseException e) {
            throw new InvalideParameterException("`horaires.*` doivent respecter le format HH:mm:ss");
        }

        LocalTime min = LocalTime.MIDNIGHT;
        LocalTime max = LocalTime.of(23, 59, 59);

        if (!ouverture.isBefore(fermeture)) {
            throw  new InvalideParameterException("`ouverture` doit être strictement avant `fermeture`");
        }

        if (ouverture.isBefore(min) || fermeture.isAfter(max)) {
            throw new InvalideParameterException("Les heures doivent être entre 00:00:00 et 23:59:59");
        }

        if (Duration.between(ouverture, fermeture).toMinutes() < 60) {
            throw new InvalideParameterException("Le restaurant doit être ouvert au moins 1 heure");
        }

        String identifiant = UUID.randomUUID().toString().replace("-", "");

        ConfigReservation config = createConfigReservation(restaurantDto.configReservation);

        return new Restaurant(
                identifiant,
                proprietaire,
                restaurantDto.nom,
                restaurantDto.capacity,
                restaurantDto.hoursOpen,
                restaurantDto.hoursClose,
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
