package ca.ulaval.glo2003.entities.reservation;

import dev.morphia.annotations.Entity;

@Entity
public class ReservationTime {
    private  String start;
    private  String end;

    public ReservationTime() {}

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
