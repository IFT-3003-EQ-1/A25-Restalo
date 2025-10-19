package ca.ulaval.glo2003.domain.dtos;

import ca.ulaval.glo2003.entities.Restaurant;

public class ReservationDto {
    private String number;
    private String  date;
    private ReservationTimeDto time;
    private int groupSize;
    private CustomerDto customer;
    private RestaurantDto restaurant;

    public ReservationDto() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ReservationTimeDto getTime() {
        return time;
    }

    public void setTime(ReservationTimeDto time) {
        this.time = time;
    }

    public RestaurantDto getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDto restaurant) {
        this.restaurant = restaurant;
    }
}
