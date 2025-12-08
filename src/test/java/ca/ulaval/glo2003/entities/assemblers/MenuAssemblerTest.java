package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.restaurant.MenuDto;
import ca.ulaval.glo2003.entities.EntityTestUtils;
import ca.ulaval.glo2003.entities.menu.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuAssemblerTest {

    private MenuDto menuDto;

    private Menu menu;

    private Map<String, Object> menuJson;

    @BeforeEach
    public void setUp() {
        menuDto = EntityTestUtils.getMenuDto();
        menu = EntityTestUtils.getMenu();
        menuJson = EntityTestUtils.menuJson();
    }

    @Test
    public void givenFromEntity_whenValidParameters_thenReturnMenuDto() {
        MenuDto dto = MenuDto.fromEntity(menu);

        assertEquals(menuDto, dto);
    }

    @Test
    public void givenToJson_whenValidFields_thenReturnJsonMap() {
        Map<String, Object> json = menuDto.toJson();

        assertEquals(menuJson, json);
    }
}
