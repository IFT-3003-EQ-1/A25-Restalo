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
        if (restaurantDto.name == null) {
            throw new MissingParameterException("nom");
        }
        if (restaurantDto.hours.open == null) {
            throw new MissingParameterException("horaireOuverture");
        }
        if (restaurantDto.hours.close == null) {
            throw new MissingParameterException("horaireFermeture");
        }

        if (restaurantDto.name.isBlank()) {
            throw new InvalideParameterException("Le nom ne peut pas être vide");
        }

        if (restaurantDto.capacity < 1) {
            throw new InvalideParameterException("La capacité doit être au moins 1");
        }

        LocalTime open;
        LocalTime close;
        try {
            open = LocalTime.parse(restaurantDto.hours.open);
            close = LocalTime.parse(restaurantDto.hours.close);
        } catch (DateTimeParseException e) {
            throw new InvalideParameterException("`horaires.*` doivent respecter le format HH:mm:ss");
        }

        LocalTime min = LocalTime.MIDNIGHT;
        LocalTime max = LocalTime.of(23, 59, 59);

        if (!open.isBefore(close)) {
            throw  new InvalideParameterException("`ouverture` doit être strictement avant `fermeture`");
        }

        if (open.isBefore(min) || close.isAfter(max)) {
            throw new InvalideParameterException("Les heures doivent être entre 00:00:00 et 23:59:59");
        }

        if (Duration.between(open, close).toMinutes() < 60) {
            throw new InvalideParameterException("Le restaurant doit être ouvert au moins 1 heure");
        }

        String identifiant = UUID.randomUUID().toString().replace("-", "");

        ConfigReservation config = createConfigReservation(restaurantDto.reservation);

        return new Restaurant(
                identifiant,
                proprietaire,
                restaurantDto.name,
                restaurantDto.capacity,
                new Hours(restaurantDto.hours.open, restaurantDto.hours.close),
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
