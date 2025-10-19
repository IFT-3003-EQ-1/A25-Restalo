package ca.ulaval.glo2003.domain.dtos;

public class ReservationTimeDto {
    private String start; 
    private String end; 
    public ReservationTimeDto(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public ReservationTimeDto() {
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public void setStart(String start) {
        this.start = start;
    }
    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "ReservationTimeDto{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}