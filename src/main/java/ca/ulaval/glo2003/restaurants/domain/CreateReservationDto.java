package ca.ulaval.glo2003.restaurants.domain;

public class CreateReservationDto {
    private String date; // date without time
    private String startTime; // time without timezone
    private int groupSize; // entier positif
    private Customer customer;

    public CreateReservationDto() {
    }

    public CreateReservationDto(String date, String startTime, int groupSize, Customer customer) {
        System.out.println("Creating reservation: " + date);
        System.out.println("Start time: " + startTime);
        System.out.println("Group Size: " + groupSize);
        System.out.println("Customer: " + customer);
        this.date = date;
        this.startTime = startTime;
        this.groupSize = groupSize;
        this.customer = customer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
