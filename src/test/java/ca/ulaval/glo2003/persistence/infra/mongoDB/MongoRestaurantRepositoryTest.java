package ca.ulaval.glo2003.persistence.infra.mongoDB;

import ca.ulaval.glo2003.entities.restaurant.RestaurantRepository;
import ca.ulaval.glo2003.infra.persistence.mongoDB.MongoRestaurantRepository;
import ca.ulaval.glo2003.persistence.infra.RestaurantRepositoryTest;
import com.mongodb.client.MongoClients;
import dev.morphia.Morphia;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class MongoRestaurantRepositoryTest extends RestaurantRepositoryTest {

    public static final String DEFAULT_DATABASE_NAME = "testRestaloDB";

    @Container
    final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8");

    @Override
    protected RestaurantRepository getRepository() {
        var dataStore = Morphia.createDatastore(MongoClients.create(mongoDBContainer.getConnectionString()),DEFAULT_DATABASE_NAME);
        System.out.println(mongoDBContainer.getConnectionString());
        return new MongoRestaurantRepository(dataStore);
    }
}
