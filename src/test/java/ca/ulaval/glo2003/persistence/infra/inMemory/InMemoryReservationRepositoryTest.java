package ca.ulaval.glo2003.persistence.infra.inMemory;

import ca.ulaval.glo2003.entities.reservation.ReservationRepository;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryReservationRepository;
import ca.ulaval.glo2003.persistence.infra.ReservationRepositoryTest;

public class InMemoryReservationRepositoryTest extends ReservationRepositoryTest {


    @Override
    protected ReservationRepository getRepository() {
        return new InMemoryReservationRepository();
    }
}
