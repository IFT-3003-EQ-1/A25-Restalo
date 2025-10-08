package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.domain.dtos.ProprietaireDto;
import ca.ulaval.glo2003.domain.dtos.RestaurantDto;

public class RestaurantEnd2EndUtils {

    public static RestaurantDto buildDefaultRestaurantDto() {
        ProprietaireDto proprietaireDto = new ProprietaireDto();
        proprietaireDto.nom = "Bob";
        proprietaireDto.id = "1";

        RestaurantDto  restaurantDto = new RestaurantDto();
        restaurantDto.capacite = 2;
        restaurantDto.horaireOuverture = "11:00:00";
        restaurantDto.horaireFermeture = "19:00:00";
        restaurantDto.nom = "Pizz";
        restaurantDto.proprietaire = proprietaireDto;
        return restaurantDto;
    }
}
