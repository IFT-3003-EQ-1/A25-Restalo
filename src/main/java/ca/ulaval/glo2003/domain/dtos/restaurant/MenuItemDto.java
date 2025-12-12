package ca.ulaval.glo2003.domain.dtos.restaurant;

import ca.ulaval.glo2003.entities.menu.MenuItem;

import java.util.Map;
import java.util.Objects;

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

    public static MenuItemDto fromEntity(MenuItem entity) {
        return new MenuItemDto(
                entity.getId(),
                entity.getName(),
                entity.getPrice()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MenuItemDto that = (MenuItemDto) o;
        return Float.compare(price, that.price) == 0 && Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
