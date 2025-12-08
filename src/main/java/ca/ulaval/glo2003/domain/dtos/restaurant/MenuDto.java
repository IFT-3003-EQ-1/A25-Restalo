package ca.ulaval.glo2003.domain.dtos.restaurant;

import ca.ulaval.glo2003.entities.menu.Menu;
import com.google.common.base.Strings;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
                "items", items.stream().map(MenuItemDto::toJson).toList()
        );
    }

    public static MenuDto fromEntity(Menu menu) {
        return new MenuDto(
                menu.getId(),
                menu.getTitle(),
                menu.getStartDate(),
                menu.getMenuItems().stream().map(MenuItemDto::fromEntity).toList(),
                menu.getRestaurant().getId()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MenuDto menuDto = (MenuDto) o;
        return Objects.equals(id, menuDto.id) && Objects.equals(title, menuDto.title) && Objects.equals(startDate, menuDto.startDate) && Objects.equals(items, menuDto.items) && Objects.equals(restaurantId, menuDto.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, startDate, items, restaurantId);
    }
}
