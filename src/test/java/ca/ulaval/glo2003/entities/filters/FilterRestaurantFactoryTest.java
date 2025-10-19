package ca.ulaval.glo2003.entities.filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterRestaurantFactoryTest {

    private FilterRestaurantFactory factory;

    @BeforeEach
    public void setUp() {
        factory = new FilterRestaurantFactory();
    }

    @Test
    public void givenCreateFiltreRestaurant_whenNoParametreValide_thenReturnEmptyList() {
        assertEquals(0, factory.createFiltres(null,null,null).size());
    }

    @Test
    public void givenCreateFiltreRestaurant_whenNomValide_thenReturnOneFiltre() {
        assertEquals(1, factory.createFiltres("Pizz",null,null).size());
    }

    @Test
    public void givenCreateFiltreRestaurant_whenAllParametreValide_thenReturnListWithThreeFiltres() {
        assertEquals(3, factory.createFiltres("Pizz","19:00:00","19:00:00").size());

    }


}
