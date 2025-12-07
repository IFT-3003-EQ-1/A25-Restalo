package ca.ulaval.glo2003.persistence.infra.mongoDB;

import ca.ulaval.glo2003.infra.persistence.mongoDB.MongoReservationRepository;
import ca.ulaval.glo2003.persistence.infra.InfraTestUtils;
import ca.ulaval.glo2003.persistence.infra.ReservationRepositoryTest;
import com.mongodb.client.MongoClients;
import dev.morphia.Morphia;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static ca.ulaval.glo2003.infra.persistence.DBConfig.DEFAULT_DATABASE_NAME;


@Testcontainers
public class MongoReservationRepositoryTest extends ReservationRepositoryTest {


    @Container
    final MongoDBContainer mongoDBContainer = new MongoDBContainer(InfraTestUtils.DOCKER_IMAGE_NAME);

    @Override
    protected MongoReservationRepository getRepository() {
        var dataStore = Morphia.createDatastore(MongoClients.create(mongoDBContainer.getConnectionString()),DEFAULT_DATABASE_NAME);
        return new MongoReservationRepository(dataStore);
    }
}
