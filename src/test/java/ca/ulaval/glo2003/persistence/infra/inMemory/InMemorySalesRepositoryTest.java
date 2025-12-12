package ca.ulaval.glo2003.persistence.infra.inMemory;

import ca.ulaval.glo2003.entities.SalesRepository;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemorySalesRepository;
import ca.ulaval.glo2003.persistence.infra.SalesRepositoryTest;

public class InMemorySalesRepositoryTest extends SalesRepositoryTest {

    @Override
    protected SalesRepository getRepository() {
        return new InMemorySalesRepository();
    }
}
