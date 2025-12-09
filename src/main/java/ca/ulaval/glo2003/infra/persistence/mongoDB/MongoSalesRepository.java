package ca.ulaval.glo2003.infra.persistence.mongoDB;

import ca.ulaval.glo2003.entities.Sales;
import ca.ulaval.glo2003.entities.SalesRepository;
import dev.morphia.Datastore;

public class MongoSalesRepository implements SalesRepository {

    private final Datastore datastore;

    public MongoSalesRepository(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public void saveSalesReport(Sales sales) {
        this.datastore.save(sales);
    }
}
