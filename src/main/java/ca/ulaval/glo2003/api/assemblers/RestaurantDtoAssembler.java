package ca.ulaval.glo2003.api.assemblers;

import ca.ulaval.glo2003.domain.dtos.RestaurantDto;

import java.util.Map;

public class RestaurantDtoAssembler {
    public Map<String, Object> versJson(RestaurantDto restaurantDto) {
        return Map.of(
                "id", restaurantDto.id,
                "nom", restaurantDto.nom,
                "capacite", restaurantDto.capacite,
                "horaires", Map.of(
                        "ouverture", restaurantDto.horaireOuverture,
                        "fermeture", restaurantDto.horaireFermeture
                )
        );
    }
}
