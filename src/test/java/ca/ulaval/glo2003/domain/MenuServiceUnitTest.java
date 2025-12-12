package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.restaurant.MenuDto;
import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.exceptions.InvalideParameterException;
import ca.ulaval.glo2003.entities.menu.MenuFactory;
import ca.ulaval.glo2003.entities.menu.MenuRepository;
import ca.ulaval.glo2003.entities.restaurant.RestaurantFactory;
import com.google.common.base.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MenuServiceUnitTest {

    private MenuService menuService;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MenuFactory menuFactory;

    @Mock
    private RestaurantFactory restaurantFactory;

    private MenuDto menuDto;

    private RestaurantDto restaurantDto;

    @BeforeEach
    public void setUp() {
        menuService = new MenuService(
                menuRepository,
                menuFactory,
                restaurantFactory
        );
        restaurantDto = DomainTestUtils.getRestaurantDto();
        menuDto = DomainTestUtils.getMenuDto();
    }


    @Test
    public void givenCreateMenu_whenParametersValid_thenMenuIsCreated() {

        when(restaurantFactory.fromDto(any())).thenReturn(DomainTestUtils.getRestaurant());
        when(menuFactory.createMenu(any(), any())).thenReturn(DomainTestUtils.getMenu());

        String id = menuService.createMenu(menuDto, restaurantDto);

        assertFalse(Strings.isNullOrEmpty(id));
        assertEquals(menuDto.id, id);
    }

    @Test
    public void givenCreateMenu_whenParameterIsInvalid_thenMenuIsNotCreated() {
        menuDto.items.clear();

        when(restaurantFactory.fromDto(any())).thenReturn(DomainTestUtils.getRestaurant());
        when(menuFactory.createMenu(any(), any())).thenThrow(new InvalideParameterException(""));

        assertThrows(InvalideParameterException.class, () -> menuService.createMenu(menuDto, restaurantDto));
    }
}
