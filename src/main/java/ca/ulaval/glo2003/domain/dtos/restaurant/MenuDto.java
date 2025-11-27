package ca.ulaval.glo2003.domain.dtos.restaurant;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuDto {

    @Nullable
    public String id;

    @Nullable
    public String title;

    public String startDate;

    public List<MenuItemDto> items;

    public String restaurantId;

    public MenuDto() {
        items = new ArrayList<>();
    }

    public Map<String, Object> toJson() {
        return Map.of(
                "id", id,
                "title", title,
                "startDate", startDate,
                "restaurantId", restaurantId,
                "items", items
        );
    }
}
