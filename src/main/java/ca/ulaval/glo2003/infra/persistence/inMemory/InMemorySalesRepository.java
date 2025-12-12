package ca.ulaval.glo2003.infra.persistence.inMemory;

import ca.ulaval.glo2003.entities.Sales;
import ca.ulaval.glo2003.entities.SalesRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemorySalesRepository implements SalesRepository {

    private final Map<String, Sales> datastore;

    public InMemorySalesRepository() {
        this.datastore = new HashMap<>();
    }

    @Override
    public void saveSalesReport(Sales sales) {
        this.datastore.put(sales.getId(), sales);
    }
}
