package ca.ulaval.glo2003.restaurants;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/restaurants")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RessourceRestaurant {

    // ------------------------------
    // POST /restaurants : créer
    // ------------------------------
    @POST
    public Response creerRestaurant(@HeaderParam("Owner") String proprietaireId,
                                    Restaurant entree,
                                    @Context UriInfo infosUri) {
        // Champs obligatoires
        if (proprietaireId == null || proprietaireId.isBlank()) {
            return mauvaiseRequete("MISSING_PARAMETER", "`Owner` header est requis");
        }
        if (entree == null) {
            return mauvaiseRequete("MISSING_PARAMETER", "Le corps JSON est requis");
        }
        if (entree.nom == null || entree.horaires == null) {
            return mauvaiseRequete("MISSING_PARAMETER", "`nom` et `horaires` sont requis");
        }
        if (entree.horaires.ouverture == null || entree.horaires.fermeture == null) {
            return mauvaiseRequete("MISSING_PARAMETER", "`horaires.ouverture` et `horaires.fermeture` sont requis");
        }

        // Valeurs invalides
        // Le nom ne peut pas être vide
        if (entree.nom.isBlank()) {
            return mauvaiseRequete("INVALID_PARAMETER", "Le nom ne peut pas être vide");
        }
        // Capacité minimale de 1 personne
        if (entree.capacite < 1) {
            return mauvaiseRequete("INVALID_PARAMETER", "La capacité doit être au moins 1");
        }

        // Validation des heures (format + bornes + ordre + durée >= 1h)
        LocalTime ouverture, fermeture;
        try {
            ouverture = LocalTime.parse(entree.horaires.ouverture);   // "HH:mm:ss"
            fermeture = LocalTime.parse(entree.horaires.fermeture);
        } catch (DateTimeParseException e) {
            return mauvaiseRequete("INVALID_PARAMETER", "`horaires.*` doivent respecter le format HH:mm:ss");
        }

        LocalTime min = LocalTime.MIDNIGHT;           // 00:00:00
        LocalTime max = LocalTime.of(23, 59, 59);     // 23:59:59
        if (!ouverture.isBefore(fermeture)) {
            return mauvaiseRequete("INVALID_PARAMETER", "`ouverture` doit être strictement avant `fermeture`");
        }
        // Le restaurant ne peut pas ouvrir avant minuit (minimum 00:00:00)
        // Le restaurant doit fermer avant minuit (maximum 23:59:59)
        if (ouverture.isBefore(min) || fermeture.isAfter(max)) {
            return mauvaiseRequete("INVALID_PARAMETER", "Les heures doivent être entre 00:00:00 et 23:59:59");
        }
        // Doit être ouvert pendant au moins 1 heure
        if (Duration.between(ouverture, fermeture).toMinutes() < 60) {
            return mauvaiseRequete("INVALID_PARAMETER", "Le restaurant doit être ouvert au moins 1 heure");
        }

        // Création + persistance en mémoire
        String identifiant = UUID.randomUUID().toString().replace("-", ""); // L'id doit être unique
        Restaurant aCreer = new Restaurant();
        aCreer.identifiant    = identifiant;
        aCreer.proprietaireId = proprietaireId; // Le restaurant appartient à un propriétaire/restaurateur
        aCreer.nom            = entree.nom;
        aCreer.capacite       = entree.capacite;
        aCreer.horaires       = entree.horaires;

        DepotRestaurant.enregistrer(aCreer);

        // 201 + Location: <host>/restaurants/<id>
        URI location = infosUri.getBaseUriBuilder().path("restaurants").path(identifiant).build();
        return Response.created(location).build();
    }

    // ---------------------------------------
    // GET /restaurants/{id} : obtenir par id
    // ---------------------------------------
    @GET
    @Path("/{id}")
    public Response obtenirRestaurant(@HeaderParam("Owner") String proprietaireId,
                                      @PathParam("id") String identifiant) {
        if (proprietaireId == null || proprietaireId.isBlank()) {
            return mauvaiseRequete("MISSING_PARAMETER", "`Owner` header est requis");
        }

        var opt = DepotRestaurant.trouver(identifiant);
        if (opt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var resto = opt.get();
        // Accès réservé au propriétaire (sinon 404, on ne révèle pas l'existence)
        if (!proprietaireId.equals(resto.proprietaireId)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(versJson(resto)).build(); // ne pas exposer proprietaireId
    }

    // ---------------------------------------------------
    // GET /restaurants : lister pour un propriétaire
    // ---------------------------------------------------
    @GET
    public Response listerRestaurants(@HeaderParam("Owner") String proprietaireId) {
        if (proprietaireId == null || proprietaireId.isBlank()) {
            return mauvaiseRequete("MISSING_PARAMETER", "`Owner` header est requis");
        }

        List<Map<String, Object>> sortie = DepotRestaurant
                .listerParProprietaire(proprietaireId)
                .stream()
                .map(this::versJson) // masque proprietaireId
                .toList();

        return Response.ok(sortie).build();
    }

    // -----------------
    // Helpers internes
    // -----------------
    /** Formate la réponse sans exposer proprietaireId. */
    private Map<String, Object> versJson(Restaurant r) {
        return Map.of(
                "id", r.identifiant,
                "nom", r.nom,
                "capacite", r.capacite,
                "horaires", Map.of(
                        "ouverture", r.horaires.ouverture,
                        "fermeture", r.horaires.fermeture
                )
        );
    }

    /** 400 BAD REQUEST au format attendu. */
    private Response mauvaiseRequete(String code, String description) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", code, "description", description))
                .build();
    }
}
