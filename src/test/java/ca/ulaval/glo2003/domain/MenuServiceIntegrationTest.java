package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.entities.menu.MenuFactory;
import ca.ulaval.glo2003.entities.menu.MenuRepository;
import ca.ulaval.glo2003.entities.restaurant.OwnerFactory;
import ca.ulaval.glo2003.entities.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.entities.restaurant.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.fail;

public class MenuServiceIntegrationTest {
    // TODO

    private MenuService menuService;

    private MenuRepository menuRepository;

    private MenuFactory menuFactory;

    private RestaurantRepository restaurantRepository;

    private RestaurantFactory restaurantFactory;

    private OwnerFactory ownerFactory;

    @BeforeEach
    public void setUp() {
        menuService = new MenuService(
                menuRepository,
                menuFactory,
                restaurantFactory,
                ownerFactory
        );
    }

    @Test
    public void givenCreateMenu_whenParametersValid_thenMenuIsCreated() {
        fail();
    }

    @Test
    public void givenCreateMenu_whenParameterIsInvalid_thenMenuIsNotCreated() {
        fail();
    }
}
