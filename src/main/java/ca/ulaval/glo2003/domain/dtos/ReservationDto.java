package ca.ulaval.glo2003.domain.dtos;

public class ReservationDto {
    public String date; // date without time
    public String startTime; // time without timezone
    public int groupSize; // entier positif
    public CustomerDto customer;

    public ReservationDto() {
    }

    public ReservationDto(String date, String startTime, int groupSize, CustomerDto customer) {
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
