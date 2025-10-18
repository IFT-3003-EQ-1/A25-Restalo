package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.restaurant.ConfigReservationDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.ProprietaireDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.restaurant.ConfigReservation;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;

public class RestaurantAssembler {
    public RestaurantDto toDto(Restaurant restaurant) {
        return new RestaurantDto(
                restaurant.getId(),
                new ProprietaireDto(restaurant.getProprietaire().getId()),
                restaurant.getNom(),
                restaurant.getHoraireOuverture(),
                restaurant.getHoraireFermeture(),
                restaurant.getCapacite(),
                toDto(restaurant.getConfigReservation())
        );
    }

    private ConfigReservationDto toDto(ConfigReservation configReservation) {
        return new ConfigReservationDto(configReservation.getDuration());
    }
}
