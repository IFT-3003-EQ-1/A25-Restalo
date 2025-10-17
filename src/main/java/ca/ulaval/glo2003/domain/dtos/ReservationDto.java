package ca.ulaval.glo2003.domain.dtos;

public class ReservationDto {
    public String date; // date without time
    public String startTime; // time without timezone
    public int groupSize; // entier positif
    public CustomerDto customerDto;

    public ReservationDto() {
    }

    public ReservationDto(String date, String startTime, int groupSize, CustomerDto customerDto) {
        this.date = date;
        this.startTime = startTime;
        this.groupSize = groupSize;
        this.customerDto = customerDto;
    }

    @Override
    public String toString() {
        return "CreateReservationDto{" +
                "date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", groupSize=" + groupSize +
                ", customer=" + customerDto +
                '}';
    }
}
