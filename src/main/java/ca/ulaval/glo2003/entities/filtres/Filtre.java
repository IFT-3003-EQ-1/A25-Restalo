package ca.ulaval.glo2003.entities.filtres;

public interface Filtre<T> {
    boolean filtrer(T value);
}
