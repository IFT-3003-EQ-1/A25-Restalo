package ca.ulaval.glo2003.domain.dtos.restaurant;

import java.util.Map;

public class MenuItemDto {
    public String id;
    public String name;
    public float price;



    public Map<String, Object> toJson() {
        return Map.of("id", id, "name", name, "price", price);
    }
}
