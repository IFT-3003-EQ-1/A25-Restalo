package ca.ulaval.glo2003.domain.dtos;

import ca.ulaval.glo2003.domain.dtos.restaurant.RestaurantDto;
import ca.ulaval.glo2003.entities.Customer;

public class ReservationDto {
    public String number;
    public String date;
    public String startTime;
    public int groupSize;
    public Customer customer;
    public RestaurantDto restaurant;

    public ReservationDto() {
    }

    public ReservationDto(String date, String startTime, int groupSize, Customer customer) {
        this.date = date;
        this.startTime = startTime;
        this.groupSize = groupSize;
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "CreateReservationDto{" +
                "date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", groupSize=" + groupSize +
                ", customer=" + customer +
                '}';
    }
}
