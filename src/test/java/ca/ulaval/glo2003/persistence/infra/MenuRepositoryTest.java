package ca.ulaval.glo2003.persistence.infra;

import ca.ulaval.glo2003.entities.menu.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

abstract public class MenuRepositoryTest {

    protected abstract MenuRepository getRepository();

    private MenuRepository menuRepository;

    @BeforeEach
    public void setUp() {
        menuRepository = getRepository();
    }

    @Test
    public void givenGet_whenMenuExists_thenReturnMenu() {
        fail();
    }

    @Test
    public void givenGet_whenMenuDoesntExist_thenReturnNull() {
        fail();

    }

    @Test
    public void givenSave_whenParametersAreValid_thenReturnSavedId() {
        fail();

    }
}
