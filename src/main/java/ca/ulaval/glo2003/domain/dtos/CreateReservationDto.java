package ca.ulaval.glo2003.domain.dtos;

public class CreateReservationDto {
    private String date; // date without time
    private String startTime; // time without timezone
    private int groupSize; // entier positif
    private CustomerDto customerDto;

    public CreateReservationDto() {
    }

    public CreateReservationDto(String date, String startTime, int groupSize, CustomerDto customerDto) {
        this.date = date;
        this.startTime = startTime;
        this.groupSize = groupSize;
        this.customerDto = customerDto;
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

    public CustomerDto getCustomer() {
        return customerDto;
    }

    public void setCustomer(CustomerDto customerDto) {
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
