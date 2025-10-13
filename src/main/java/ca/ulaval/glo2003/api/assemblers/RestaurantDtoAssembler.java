package ca.ulaval.glo2003.api.assemblers;

import ca.ulaval.glo2003.domain.dtos.RestaurantDto;

import java.util.Map;
import java.util.Objects;

public class RestaurantDtoAssembler {
    public Map<String, Object> versJson(RestaurantDto restaurantDto) {
        return Map.of(
                "id", Objects.toString(restaurantDto.id, ""),
                "nom", restaurantDto.nom,
                "capacite", restaurantDto.capacite,
                "horaireOuverture", restaurantDto.horaireOuverture,
                "horaireFermeture", restaurantDto.horaireFermeture,
                "proprietaire", Map.of(
                        "id", restaurantDto.proprietaire.id,
                        "nom", restaurantDto.proprietaire.nom
                )
        );
    }
}
