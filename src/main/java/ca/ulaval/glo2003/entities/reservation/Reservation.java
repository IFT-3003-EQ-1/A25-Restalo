package ca.ulaval.glo2003.entities.reservation;

import ca.ulaval.glo2003.entities.Customer;
import ca.ulaval.glo2003.entities.restaurant.Restaurant;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("reservations")
public class Reservation {
    @Id
    private  String id;
    private  String number;
    private  String  date; // AAAA-MM-DD
    private  ReservationTime time;
    private  int groupSize;
    private  Customer customer;
    private  Restaurant restaurant;


    public Reservation(String number, String date, ReservationTime time, int groupSize, Customer customer, Restaurant restaurant) {
        this.number = number;
        this.date = date;
        this.time = time;
        this.groupSize = groupSize;
        this.customer = customer;
        this.restaurant = restaurant;
    }

    public Reservation() {
    }

    public String getDate() {
        return date;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getNumber() {
        return number;
    }

    public ReservationTime getTime() {
        return time;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }


    public void setNumber(String number) {
        this.number = number;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(ReservationTime time) {
        this.time = time;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
