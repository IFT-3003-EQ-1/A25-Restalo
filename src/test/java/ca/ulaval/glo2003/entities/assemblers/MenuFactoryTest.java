package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.DomainTestUtils;
import ca.ulaval.glo2003.domain.dtos.restaurant.MenuDto;
import ca.ulaval.glo2003.entities.exceptions.InvalideParameterException;
import ca.ulaval.glo2003.entities.menu.Menu;
import ca.ulaval.glo2003.entities.menu.MenuFactory;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MenuFactoryTest {

    private MenuFactory menuFactory;

    private Restaurant restaurant;

    private MenuDto menuDto;

    @BeforeEach
    public void setUp() {

        menuFactory = new MenuFactory();
        menuDto = DomainTestUtils.getMenuDto();
        restaurant = DomainTestUtils.getRestaurant();
    }

    @Test
    public void givenCreateMenu_whenParametersAreValid_thenReturnMenu() {
        Menu menu = menuFactory.createMenu(menuDto, restaurant);

        assertEquals(restaurant.getId(), menu.getRestaurant().getId());
        assertEquals(menu.getTitle(), menuDto.title);
        assertEquals(menu.getStartDate(), menuDto.startDate);
    }

    @Test
    public void givenCreateMenu_whenStartDateIsInvalid_thenThrowInvalidParameterException() {
        menuDto.startDate = null;
        assertThrows(InvalideParameterException.class, () -> menuFactory.createMenu(menuDto, restaurant));
    }

    @Test
    public void givenCreateMenu_whenItemsIsEmpty_thenThrowInvalidParameterException() {
        menuDto.items.clear();

        assertThrows(InvalideParameterException.class, () -> menuFactory.createMenu(menuDto, restaurant));
    }

    @Test
    public void givenCreateMenu_whenNameIsNull_thenReturnMenuWithDefaultName() {
        menuDto.title = null;
        Menu menu  = menuFactory.createMenu(menuDto, restaurant);

        assertEquals(restaurant.getName(), menu.getTitle());
    }
}
