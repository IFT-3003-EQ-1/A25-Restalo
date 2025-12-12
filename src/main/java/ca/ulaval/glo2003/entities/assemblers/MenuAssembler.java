package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.restaurant.MenuDto;
import ca.ulaval.glo2003.entities.menu.Menu;

public class MenuAssembler {

    public MenuDto toDto(Menu menu) {
        return new MenuDto();
    }
}
