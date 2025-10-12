package ca.ulaval.glo2003.restaurants.service;

import ca.ulaval.glo2003.restaurants.RestaurantRepository;
import ca.ulaval.glo2003.restaurants.domain.dtos.Hours;
import ca.ulaval.glo2003.restaurants.domain.dtos.Restaurant;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.time.Duration;
import java.time.*;
import java.util.List;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class RestaurantService {
 public Restaurant createRestaurant(String ownerId, Restaurant restaurant){
     LocalTime open = LocalTime.parse(restaurant.getHours().getOpen());
     LocalTime close =  LocalTime.parse(restaurant.getHours().getClose());
     Hours hours = new Hours(open.toString(), close.toString());

     // Création + persistance en mémoire
     String restaurantId = UUID.randomUUID().toString().replace("-", ""); // L'id doit être unique
     Restaurant resto = new Restaurant();
     resto.setId(restaurantId);
     resto.setOwnerId(ownerId);
     resto.setName(restaurant.getName());
     resto.setCapacity(restaurant.getCapacity());
     resto.setHours(hours);
     RestaurantRepository.save(resto);

     return resto;
 }

 public Restaurant getRestaurantByOwner(String ownerId, String restaurantId){
     Restaurant restaurant = null;
     var optionRestaurant = RestaurantRepository.findById(restaurantId);
     if (optionRestaurant.isPresent()) {
         //return Response.status(Response.Status.NOT_FOUND).build();
         var resto = optionRestaurant.get();
         // Accès réservé au propriétaire (sinon 404, on ne révèle pas l'existence)
         if (ownerId.equals(resto.getOwnerId())) {
             restaurant = resto;
            // return Response.status(Response.Status.NOT_FOUND).build();
         }
     }
   return restaurant;
 }

 public List<Map<String, Object>> listRestaurantsByOwner(String ownerId){
     return RestaurantRepository
             .listByOwner(ownerId)
             .stream()
             .map(this::toJson) // masque proprietaireId
             .toList();
 }

    private Map<String, Object> toJson(Restaurant r) {
        return Map.of(
                "id", r.getId(),
                "name", r.getName(),
                "capacity", r.getCapacity(),
                "hours", Map.of(
                        "open", r.getHours().getOpen(),
                        "close", r.getHours().getClose()
                )
        );
    }
}
