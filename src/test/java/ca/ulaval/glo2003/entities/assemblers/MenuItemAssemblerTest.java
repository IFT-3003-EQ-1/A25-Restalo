package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.restaurant.MenuItemDto;
import ca.ulaval.glo2003.entities.menu.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuItemAssemblerTest {

    private MenuItemDto menuItemDto;

    private MenuItem menuItem;

    private Map<String, Object> json;

    @BeforeEach
    public void setUp() {
        menuItemDto = new MenuItemDto(
                "1",
                "steak",
                30.0F
        );

        menuItem = new MenuItem(
                menuItemDto.id,
                menuItemDto.name,
                menuItemDto.price
        );

        json = new HashMap<>();
        json.put("id", menuItemDto.id);
        json.put("name", menuItemDto.name);
        json.put("price", menuItemDto.price);
    }

    @Test
    public void givenFromEntity_whenValidParameters_thenReturnMenuDto() {
        MenuItemDto dto = MenuItemDto.fromEntity(menuItem);

        assertEquals(menuItemDto, dto);
    }

    @Test
    public void givenToJson_whenValidFields_thenReturnJsonMap() {
        Map<String, Object> ret = menuItemDto.toJson();

        assertEquals(json, ret);
    }
}
