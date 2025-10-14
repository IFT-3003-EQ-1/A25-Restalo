package ca.ulaval.glo2003.restaurants.domain;

public class Reservation {
    private String number;
   private String  date;
   private ReservationTime time;
   private int groupSize;
   private Customer customer;
   private Restaurant restaurant;
   public Reservation(String date, ReservationTime time, int groupSize, Customer customer) {
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ReservationTime getTime() {
        return time;
    }

    public void setTime(ReservationTime time) {
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
