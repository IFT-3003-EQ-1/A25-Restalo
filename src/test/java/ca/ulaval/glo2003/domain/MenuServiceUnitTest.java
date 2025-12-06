package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.entities.menu.MenuFactory;
import ca.ulaval.glo2003.entities.menu.MenuRepository;
import ca.ulaval.glo2003.entities.restaurant.OwnerFactory;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import ca.ulaval.glo2003.entities.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.entities.restaurant.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class MenuServiceUnitTest {

    private MenuService menuService;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MenuFactory menuFactory;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantFactory restaurantFactory;

    @Mock
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
