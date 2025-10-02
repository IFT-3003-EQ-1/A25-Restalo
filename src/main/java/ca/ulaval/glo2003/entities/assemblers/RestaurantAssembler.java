package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.ProprietaireDto;
import ca.ulaval.glo2003.domain.dtos.RestaurantDto;
import ca.ulaval.glo2003.entities.Restaurant;

public class RestaurantAssembler {
    public RestaurantDto toDto(Restaurant restaurant) {
        return new RestaurantDto(
                restaurant.getId(),
                new ProprietaireDto(restaurant.getProprietaire().getId(), ""),
                restaurant.getNom(),
                restaurant.getHoraireOuverture(),
                restaurant.getHoraireFermeture(),
                restaurant.getCapacite()
        );
    }
}
