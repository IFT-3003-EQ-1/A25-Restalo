package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.restaurant.MenuDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.exceptions.InvalideParameterException;
import ca.ulaval.glo2003.entities.menu.Menu;
import ca.ulaval.glo2003.entities.menu.MenuFactory;
import ca.ulaval.glo2003.entities.menu.MenuRepository;
import ca.ulaval.glo2003.entities.restaurant.OwnerFactory;
import ca.ulaval.glo2003.entities.restaurant.RestaurantFactory;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryMenuRepository;
import com.google.common.base.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MenuServiceIntegrationTest {

    private MenuService menuService;

    private Map<String, Menu> datastore;

    @BeforeEach
    public void setUp() {
        RestaurantFactory restaurantFactory = new RestaurantFactory();
        datastore = new HashMap<>();
        MenuRepository menuRepository = new InMemoryMenuRepository(datastore);
        MenuFactory menuFactory = new MenuFactory();
        OwnerFactory ownerFactory = new OwnerFactory();

        menuService = new MenuService(
                menuRepository,
                menuFactory,
                restaurantFactory,
                ownerFactory
        );
    }

    @Test
    public void givenCreateMenu_whenParametersValid_thenMenuIsCreated() {
        MenuDto menuDto = DomainTestUtils.getMenuDto();
        RestaurantDto restaurantDto = DomainTestUtils.getRestaurantDto();

        String id = menuService.createMenu(menuDto, restaurantDto);

        assertFalse(Strings.isNullOrEmpty(id));
    }

    @Test
    public void givenCreateMenu_whenParameterIsInvalid_thenMenuIsNotCreated() {
        MenuDto menuDto = DomainTestUtils.getMenuDto();
        menuDto.items.clear();
        RestaurantDto restaurantDto = DomainTestUtils.getRestaurantDto();

        assertThrows(InvalideParameterException.class, () -> menuService.createMenu(menuDto, restaurantDto));
        assertTrue(datastore.isEmpty());
    }
}
