package ca.ulaval.glo2003.domain.dtos.restaurant;

import java.util.Objects;

public class ConfigReservationDto {
    public int duration;

    public ConfigReservationDto(int duration) {
        this.duration = duration;
    }

    public ConfigReservationDto() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ConfigReservationDto that = (ConfigReservationDto) o;
        return duration == that.duration;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(duration);
    }
}
