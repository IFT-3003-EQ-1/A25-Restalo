package ca.ulaval.glo2003.restaurants.domain;

public class ReservationTime {
    private String start; // time without timezone
    private String end; // time without timezone

    public ReservationTime(String start, String end) {
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
        return "ReservationTime{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}
