package ca.ulaval.glo2003.entities.filters;

public interface Filter<T> {
    boolean filter(T value);
}
