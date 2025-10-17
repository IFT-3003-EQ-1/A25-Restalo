package ca.ulaval.glo2003.entities;

public class ReservationTime {
    private final String start;
    private final String end;

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
        return "ReservationTimeDto{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}
