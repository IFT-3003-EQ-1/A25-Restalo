package ca.ulaval.glo2003.persistence.infra.mongoDB;

import ca.ulaval.glo2003.entities.menu.MenuRepository;
import ca.ulaval.glo2003.infra.persistence.mongoDB.MongoMenuRepository;
import ca.ulaval.glo2003.persistence.infra.InfraTestUtils;
import ca.ulaval.glo2003.persistence.infra.MenuRepositoryTest;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static ca.ulaval.glo2003.infra.persistence.DBConfig.DEFAULT_DATABASE_NAME;


@Testcontainers
public class MongoMenuRepositoryTest extends MenuRepositoryTest {


    @Container
    final MongoDBContainer mongoDBContainer = new MongoDBContainer(InfraTestUtils.DOCKER_IMAGE_NAME);


    @Override
    protected MenuRepository getRepository() {
        Datastore dataStore = Morphia.createDatastore(MongoClients.create(mongoDBContainer.getConnectionString()), DEFAULT_DATABASE_NAME);
        return new MongoMenuRepository(dataStore);
    }
}
