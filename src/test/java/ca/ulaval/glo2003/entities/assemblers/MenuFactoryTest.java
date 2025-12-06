package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.restaurant.MenuDto;
import ca.ulaval.glo2003.entities.menu.Menu;
import ca.ulaval.glo2003.entities.menu.MenuFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class MenuFactoryTest {

    private MenuFactory menuFactory;

    private Menu menu;

    private MenuDto menuDto;

    @BeforeEach
    public void setUp() {
        menuFactory = new MenuFactory();
    }

    @Test
    public void givenCreateMenu_whenParametersAreValid_thenReturnMenu() {
        fail();
    }

    @Test
    public void givenCreateMenu_whenStartDateIsInvalid_thenThrowInvalidParameterException() {
        fail();

    }

    @Test
    public void givenCreateMenu_whenItemsIsEmpty_thenThrowInvalidParameterException() {
        fail();

    }

    @Test
    public void givenCreateMenu_whenNameIsNull_thenReturnMenuWithDefaultName() {
        fail();

    }
}
