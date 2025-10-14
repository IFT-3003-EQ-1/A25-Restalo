package ca.ulaval.glo2003.restaurants.service;

import ca.ulaval.glo2003.restaurants.domain.*;
import ca.ulaval.glo2003.restaurants.utils.Constant;
import ca.ulaval.glo2003.restaurants.utils.JsonUtil;
import ca.ulaval.glo2003.restaurants.RestaurantRepository;
import ca.ulaval.glo2003.restaurants.utils.ReservationTimeAdjuster;

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

    public String addReservation(String restaurantId, CreateReservationDto createReservationDto){
     var restaurant = RestaurantRepository.findById(restaurantId);
     if (restaurant.isEmpty()) {
         return Constant.RESTAURANT_NOT_FOUND;
     }

     Restaurant resto = restaurant.get();
        System.out.println(resto);
        ReservationTime time = ReservationTimeAdjuster.adjustAndValidateReservationTime(createReservationDto.getStartTime(),resto);
        if(time == null){
            return Constant.BAD_RESERVATION_TIME;
        }

     String reservationId = UUID.randomUUID().toString().replace("-", "");
     Reservation reservation = new Reservation();
                 reservation.setNumber(reservationId);
                 reservation.setTime(time);
                 reservation.setRestaurant(resto);
                 reservation.setGroupSize(createReservationDto.getGroupSize());
                 reservation.setCustomer(createReservationDto.getCustomer());
                 reservation.setDate(createReservationDto.getDate());

      return RestaurantRepository.addReservation(reservation);
    }
}
