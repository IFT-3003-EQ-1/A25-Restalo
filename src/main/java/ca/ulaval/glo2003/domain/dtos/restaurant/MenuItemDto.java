package ca.ulaval.glo2003.domain.dtos.restaurant;

import java.util.Map;

public class MenuItemDto {
    public String id;
    public String name;
    public float price;

    public MenuItemDto() {}

    public MenuItemDto(String id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }


    public Map<String, Object> toJson() {
        return Map.of("id", id, "name", name, "price", price);
    }
}
