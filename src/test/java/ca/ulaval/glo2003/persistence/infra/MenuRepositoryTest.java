package ca.ulaval.glo2003.persistence.infra;

import ca.ulaval.glo2003.entities.menu.Menu;
import ca.ulaval.glo2003.entities.menu.MenuRepository;
import com.google.common.base.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

abstract public class MenuRepositoryTest {

    protected abstract MenuRepository getRepository();

    private MenuRepository menuRepository;

    private Menu menu;

    @BeforeEach
    public void setUp() {
        menuRepository = getRepository();
        menu = InfraTestUtils.getMenu();
    }

    @Test
    public void givenGet_whenMenuExists_thenReturnMenu() {
        String id = menuRepository.save(menu);
        Optional<Menu> result = menuRepository.get(id);

        assertTrue(result.isPresent());
        assertEquals(menu, result.get());
    }

    @Test
    public void givenGet_whenMenuDoesntExist_thenReturnNull() {
        menuRepository.save(menu);
        Optional<Menu> result = menuRepository.get(InfraTestUtils.INVALID_ID);

        assertFalse(result.isPresent());
    }

    @Test
    public void givenSave_whenParametersAreValid_thenReturnSavedId() {
        String id = menuRepository.save(menu);

        assertFalse(Strings.isNullOrEmpty(id));
        assertEquals(menu.getId(), id);
    }
}
