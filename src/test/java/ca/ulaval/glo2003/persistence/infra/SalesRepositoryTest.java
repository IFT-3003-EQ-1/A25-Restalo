package ca.ulaval.glo2003.persistence.infra;

import ca.ulaval.glo2003.entities.Sales;
import ca.ulaval.glo2003.entities.SalesRepository;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class SalesRepositoryTest {

    private SalesRepository salesRepository;

    private Sales sales;

    abstract protected SalesRepository getRepository();

    @BeforeEach
    public void setUp() {
        salesRepository = getRepository();
        sales = new Sales(
                "1",
                "2014-04-04",
                200.0F,
                new Restaurant()
        );
    }

    @Test
    public void givenSaveSalesReport_whenParameterIsValid_thenSaleIsSaved() {
        salesRepository.saveSalesReport(sales);
    }
}
