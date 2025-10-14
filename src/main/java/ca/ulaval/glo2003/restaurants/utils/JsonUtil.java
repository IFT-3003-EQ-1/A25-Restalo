package ca.ulaval.glo2003.restaurants.utils;

import ca.ulaval.glo2003.restaurants.domain.Restaurant;

import java.util.Map;

public class JsonUtil {
    public static Map<String, Object> toJson(Restaurant r) {
        return Map.of(
                "id", r.getId(),
                "nom", r.getName(),
                "capacite", r.getCapacity(),
                "horaires", Map.of(
                        "ouverture", r.getHours().getOpen(),
                        "fermeture", r.getHours().getClose()
                )
        );
    }
}
