package ca.ulaval.glo2003.domain.dtos;

import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;

public class ReservationDto {
    public String number;
    public String date;
    public ReservationTimeDto time;
    public int groupSize;
    public CustomerDto customer;
    public RestaurantDto restaurant;

    public ReservationDto() {
        this.time = new ReservationTimeDto();
        this.customer = new CustomerDto();
    }

    public ReservationDto(String number, String date, ReservationTimeDto time, int groupSize, CustomerDto customer, RestaurantDto restaurant) {
        this.number = number;
        this.date = date;
        this.time = time;
        this.groupSize = groupSize;
        this.customer = customer;
        this.restaurant = restaurant;
    }


}
