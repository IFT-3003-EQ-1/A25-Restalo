package ca.ulaval.glo2003.domain.dtos;

public class ReservationTimeDto {
    private String start; 
    private String end; 
    public ReservationTimeDto(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "ReservationTimeDto{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}