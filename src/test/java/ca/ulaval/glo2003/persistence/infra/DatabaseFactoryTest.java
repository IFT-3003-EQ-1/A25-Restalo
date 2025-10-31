package ca.ulaval.glo2003.persistence.infra;

import ca.ulaval.glo2003.infra.persistence.*;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryReservationRepository;
import ca.ulaval.glo2003.infra.persistence.inMemory.InMemoryRestaurantRepository;
import ca.ulaval.glo2003.infra.persistence.mongoDB.MongoReservationRepository;
import ca.ulaval.glo2003.infra.persistence.mongoDB.MongoRestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseFactoryTest {

    private String originalPersistenceProperty;

    @BeforeEach
    void setUp() {
        originalPersistenceProperty = System.getProperty("persistence");
    }

    @AfterEach
    void tearDown() {
        if (originalPersistenceProperty == null) {
            System.clearProperty("persistence");
        } else {
            System.setProperty("persistence", originalPersistenceProperty);
        }
    }

    @Test
    void createRestaurantRepository_WithMongoType_ReturnsMongoRepository() {
        DatabaseFactory factory = new DatabaseFactory("mongo");
        RestaurantRepository repository = factory.createRestaurantRepository();
        assertNotNull(repository);
        assertInstanceOf(MongoRestaurantRepository.class, repository);
    }

    @Test
    void createRestaurantRepository_WithInMemoryType_ReturnsInMemoryRepository() {
        DatabaseFactory factory = new DatabaseFactory("inmemory");
        RestaurantRepository repository = factory.createRestaurantRepository();
        assertNotNull(repository);
        assertInstanceOf(InMemoryRestaurantRepository.class, repository);
    }

    @Test
    void createRestaurantRepository_WithUnknownType_ReturnsInMemoryRepositoryAsFallback() {
        DatabaseFactory factory = new DatabaseFactory("unknown");
        RestaurantRepository repository = factory.createRestaurantRepository();
        assertNotNull(repository);
        assertInstanceOf(InMemoryRestaurantRepository.class, repository);
    }

    @Test
    void createRestaurantRepository_WithNullType_ReturnsInMemoryRepositoryAsFallback() {
        DatabaseFactory factory = new DatabaseFactory(null);
        RestaurantRepository repository = factory.createRestaurantRepository();
        assertNotNull(repository);
        assertInstanceOf(InMemoryRestaurantRepository.class, repository);
    }

    @Test
    void createRestaurantRepository_WithMixedCaseMongoType_ReturnsMongoRepository() {
        DatabaseFactory factory = new DatabaseFactory("MoNgO");
        RestaurantRepository repository = factory.createRestaurantRepository();
        assertNotNull(repository);
        assertInstanceOf(MongoRestaurantRepository.class, repository);
    }

    @Test
    void createReservationRepository_WithMongoType_ReturnsMongoRepository() {
        DatabaseFactory factory = new DatabaseFactory("mongo");
        ReservationRepository repository = factory.createReservationRepository();
        assertNotNull(repository);
        assertInstanceOf(MongoReservationRepository.class, repository);
    }

    @Test
    void createReservationRepository_WithInMemoryType_ReturnsInMemoryRepository() {
        DatabaseFactory factory = new DatabaseFactory("inmemory");
        ReservationRepository repository = factory.createReservationRepository();
        assertNotNull(repository);
        assertInstanceOf(InMemoryReservationRepository.class, repository);
    }

    @Test
    void createReservationRepository_WithUnknownType_ReturnsInMemoryRepositoryAsFallback() {
        DatabaseFactory factory = new DatabaseFactory("postgresql");
        ReservationRepository repository = factory.createReservationRepository();
        assertNotNull(repository);
        assertInstanceOf(InMemoryReservationRepository.class, repository);
    }

    @Test
    void defaultConstructor_WithNoSystemProperty_UsesInMemoryAsFallback() {
        System.clearProperty("persistence");
        DatabaseFactory factory = new DatabaseFactory();
        RestaurantRepository repository = factory.createRestaurantRepository();
        assertNotNull(repository);
        assertInstanceOf(InMemoryRestaurantRepository.class, repository);
    }

    @Test
    void defaultConstructor_WithMongoSystemProperty_CreatesMongoRepository() {
        System.setProperty("persistence", "mongo");
        DatabaseFactory factory = new DatabaseFactory();
        RestaurantRepository repository = factory.createRestaurantRepository();
        assertNotNull(repository);
        assertInstanceOf(MongoRestaurantRepository.class, repository);
    }

    @Test
    void defaultConstructor_WithInMemorySystemProperty_CreatesInMemoryRepository() {
        System.setProperty("persistence", "inmemory");
        DatabaseFactory factory = new DatabaseFactory();
        RestaurantRepository repository = factory.createRestaurantRepository();
        assertNotNull(repository);
        assertInstanceOf(InMemoryRestaurantRepository.class, repository);
    }

    @Test
    void factoryCreatesNewInstancesEachTime() {
        DatabaseFactory factory = new DatabaseFactory("inmemory");
        RestaurantRepository repo1 = factory.createRestaurantRepository();
        RestaurantRepository repo2 = factory.createRestaurantRepository();
        assertNotSame(repo1, repo2, "Factory should create new instances each time");
    }
}