package ca.ulaval.glo2003.restaurants.domain.dtos;

/**
 * Modèle de données pour la (dé)sérialisation JSON-B.
 * Champs publics + constructeur par défaut obligatoires pour éviter le 500.
 */
public class Restaurant {
    public String id;
    public String ownerId;
    public String name;
    public int capacity;
    public Hours hours;

    public Restaurant() {}
}
