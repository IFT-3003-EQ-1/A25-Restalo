package ca.ulaval.glo2003.entities.filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterReservationFactoryTest {

    private FilterReservationFactory filterReservationFactory;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void givenCreateFilters_whenAllParametersAreValid_thenFourFiltersAreCreated() {
        filterReservationFactory = new FilterReservationFactory();
        assertEquals(4, filterReservationFactory.createFilters(
                "Bob",
                "2015-04-04",
                "12345",
                "123"
        ).size());
    }

    @Test
    public void givenCreateFilters_whenThreeParametersAreValid_thenThreeFiltersAreCreated() {
        filterReservationFactory = new FilterReservationFactory();
        assertEquals(3, filterReservationFactory.createFilters(
                null,
                "2015-04-04",
                "12345",
                "123"
        ).size());
    }

    @Test
    public void givenCreateFilters_whenTwoParametersAreValid_thenTwoFiltersAreCreated() {
        filterReservationFactory = new FilterReservationFactory();
        assertEquals(2, filterReservationFactory.createFilters(
                null,
                null,
                "12345",
                "123"
        ).size());
    }

}
