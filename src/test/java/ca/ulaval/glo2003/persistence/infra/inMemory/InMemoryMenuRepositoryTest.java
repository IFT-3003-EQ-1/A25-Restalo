package ca.ulaval.glo2003.persistence.infra.inMemory;

import ca.ulaval.glo2003.entities.menu.MenuRepository;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryMenuRepository;
import ca.ulaval.glo2003.persistence.infra.MenuRepositoryTest;

public class InMemoryMenuRepositoryTest extends MenuRepositoryTest {


    @Override
    protected MenuRepository getRepository() {
        return new InMemoryMenuRepository();
    }
}
