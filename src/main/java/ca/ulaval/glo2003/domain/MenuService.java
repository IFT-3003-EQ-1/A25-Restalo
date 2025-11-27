package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.restaurant.MenuDto;
import ca.ulaval.glo2003.entities.menu.Menu;
import ca.ulaval.glo2003.entities.menu.MenuFactory;
import ca.ulaval.glo2003.entities.menu.MenuRepository;

public class MenuService {

    private final MenuRepository menuRepository;

    private final MenuFactory menuFactory;

    public MenuService(MenuRepository menuRepository, MenuFactory menuFactory) {
        this.menuRepository = menuRepository;
        this.menuFactory = menuFactory;
    }


    public MenuDto createMenu() {
        Menu menu = menuFactory.createMenu();
        menuRepository.create(menu);
        return new MenuDto();
    }
}
