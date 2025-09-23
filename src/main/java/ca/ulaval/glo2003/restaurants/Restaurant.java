package ca.ulaval.glo2003.restaurants;

/**
 * Modèle de données pour la (dé)sérialisation JSON-B.
 * Champs publics + constructeur par défaut obligatoires pour éviter le 500.
 */
public class Restaurant {
    public String identifiant;     // généré côté serveur
    public String proprietaireId;  // provient de l'en-tête HTTP "Owner"
    public String nom;
    public int capacite;
    public Horaires horaires;

    public Restaurant() {}

    /** Sous-objet pour les horaires (format "HH:mm:ss"). */
    public static class Horaires {
        public String ouverture;
        public String fermeture;

        public Horaires() {} // requis
    }
}
