package ca.ulaval.glo2003.persistence.infra.mongoDB;

import ca.ulaval.glo2003.infra.persistence.DBConfig;
import ca.ulaval.glo2003.infra.persistence.mongoDB.MongoReservationRepository;
import ca.ulaval.glo2003.persistence.infra.ReservationRepositoryTest;
import com.mongodb.client.MongoClients;
import dev.morphia.Morphia;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class MongoReservationRepositoryTest extends ReservationRepositoryTest {

    public static final String DEFAULT_DATABASE_NAME = "testRestaloDB";
    public static final DBConfig.PersistenceType PERSISTENCE_TYPE = DBConfig.PersistenceType.MONGO_DB;

    @Container
    final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8");

    @Override
    protected MongoReservationRepository getRepository() {
        var dataStore = Morphia.createDatastore(MongoClients.create(mongoDBContainer.getConnectionString()),DEFAULT_DATABASE_NAME);
        System.out.println(mongoDBContainer.getConnectionString());
        return new MongoReservationRepository(dataStore);
    }
}
