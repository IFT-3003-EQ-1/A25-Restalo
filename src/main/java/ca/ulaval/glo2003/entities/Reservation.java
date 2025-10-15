package ca.ulaval.glo2003.entities;

import ca.ulaval.glo2003.domain.dtos.CustomerDto;
import ca.ulaval.glo2003.domain.dtos.ReservationTimeDto;

public class Reservation {
    private String number;
   private String  date;
   private ReservationTimeDto time;
   private int groupSize;
   private CustomerDto customer;
   private Restaurant restaurant;
   public Reservation(String date, ReservationTimeDto time, int groupSize, CustomerDto customer) {
      this.date = date;
      this.time = time;
      this.groupSize = groupSize;
      this.customer = customer;
   }

    public Reservation() {
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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "number='" + number + '\'' +
                ", date='" + date + '\'' +
                ", time=" + time +
                ", groupSize=" + groupSize +
                ", customer=" + customer +
                ", restaurant=" + restaurant +
                '}';
    }
}
