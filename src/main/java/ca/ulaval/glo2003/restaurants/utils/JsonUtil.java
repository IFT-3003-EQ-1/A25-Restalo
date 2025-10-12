package ca.ulaval.glo2003.restaurants.utils;

import ca.ulaval.glo2003.restaurants.domain.Restaurant;

import java.util.Map;

public class JsonUtil {
    public static Map<String, Object> toJson(Restaurant r) {
        return Map.of(
                "id", r.getId(),
                "name", r.getName(),
                "capacity", r.getCapacity(),
                "hours", Map.of(
                        "open", r.getHours().getOpen(),
                        "close", r.getHours().getClose()
                )
        );
    }
}
