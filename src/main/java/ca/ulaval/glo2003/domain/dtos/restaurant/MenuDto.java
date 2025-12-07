package ca.ulaval.glo2003.domain.dtos.restaurant;

import com.google.common.base.Strings;

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

    public MenuDto(@Nullable String id, @Nullable String title, String startDate, List<MenuItemDto> items, String restaurantId) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.items = items;
        this.restaurantId = restaurantId;
    }

    public Map<String, Object> toJson() {
        return Map.of(
                "id", Strings.nullToEmpty(id),
                "title", Strings.nullToEmpty(title),
                "startDate", startDate,
                "restaurantId", restaurantId,
                "items", items
        );
    }
}
