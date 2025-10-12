package ca.ulaval.glo2003.restaurants.service;

import ca.ulaval.glo2003.restaurants.utils.JsonUtil;
import ca.ulaval.glo2003.restaurants.RestaurantRepository;
import ca.ulaval.glo2003.restaurants.domain.Hours;
import ca.ulaval.glo2003.restaurants.domain.Restaurant;

import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RestaurantService {
 public Restaurant createRestaurant(String ownerId, Restaurant restaurant){
     LocalTime open = LocalTime.parse(restaurant.getHours().getOpen());
     LocalTime close =  LocalTime.parse(restaurant.getHours().getClose());
     Hours hours = new Hours(open.toString(), close.toString());

     String restaurantId = UUID.randomUUID().toString().replace("-", "");
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
         var resto = optionRestaurant.get();
         if (ownerId.equals(resto.getOwnerId())) {
             restaurant = resto;
         }
     }
   return restaurant;
 }

 public List<Map<String, Object>> listRestaurantsByOwner(String ownerId){
     return RestaurantRepository
             .listByOwner(ownerId)
             .stream()
             .map(JsonUtil::toJson)
             .toList();
    }
}
