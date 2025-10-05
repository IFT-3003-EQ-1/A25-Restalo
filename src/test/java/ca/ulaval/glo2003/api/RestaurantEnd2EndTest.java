package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.Main;
import ca.ulaval.glo2003.domain.dtos.ProprietaireDto;
import ca.ulaval.glo2003.domain.dtos.RestaurantDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RestaurantEnd2EndTest {

    @BeforeAll
    static void startServer() {
        Main.main(new String[0]);
    }

    @BeforeEach
    void setUp() {

    }

    @AfterAll
    static void stopServer() {
        Main.stopServer();
    }

    @Test
    public void canCreateRestaurant() {
        // given
        ProprietaireDto proprietaireDto = new ProprietaireDto();
        proprietaireDto.nom = "";
        proprietaireDto.id = "";

        RestaurantDto  restaurantDto = new RestaurantDto();
        restaurantDto.capacite = 2;
        restaurantDto.horaireOuverture = "";
        restaurantDto.horaireFermeture = "";
        restaurantDto.nom = "";
        restaurantDto.proprietaire = proprietaireDto;

        // when
        

        // expected
    }
}
