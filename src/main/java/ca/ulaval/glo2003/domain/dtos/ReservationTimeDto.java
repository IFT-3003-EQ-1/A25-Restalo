package ca.ulaval.glo2003.domain.dtos;

public class ReservationTimeDto {
    public String start;
    public String end;

    public ReservationTimeDto(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public ReservationTimeDto() {
    }

    @Override
    public String toString() {
        return "ReservationTimeDto{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}