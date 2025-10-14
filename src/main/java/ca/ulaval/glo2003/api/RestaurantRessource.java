package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.assemblers.RestaurantDtoAssembler;
import ca.ulaval.glo2003.domain.RestaurantService;
import ca.ulaval.glo2003.domain.dtos.ProprietaireDto;
import ca.ulaval.glo2003.domain.dtos.RestaurantDto;
import ca.ulaval.glo2003.entities.exceptions.ParametreManquantException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Path("/restaurants")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestaurantRessource {
    private  final RestaurantService restaurantService;
    private final RestaurantDtoAssembler restaurantDtoAssembler;

    public RestaurantRessource(RestaurantService restaurantService,
                               RestaurantDtoAssembler restaurantDtoAssembler) {
        this.restaurantService = restaurantService;
        this.restaurantDtoAssembler = restaurantDtoAssembler;
    }

    @POST
    public Response creerRestaurant(@HeaderParam("Owner") String proprietaireId,
                                    RestaurantDto entree,
                                    @Context UriInfo infosUri) {

        if (entree == null) {
            throw new ParametreManquantException("entree");
        }


        ProprietaireDto proprietaireDto = new ProprietaireDto();
        proprietaireDto.id = proprietaireId;
        String restaurantId = restaurantService.createRestaurant(proprietaireDto, entree);

        URI location = infosUri.getBaseUriBuilder().path("restaurants").path(restaurantId).build();
        return Response.created(location).build();

    }

    @GET
    @Path("/{id}")
    public Response obtenirRestaurant(@HeaderParam("Owner") String proprietaireId,
                                      @PathParam("id") String identifiant) {
        if (proprietaireId == null || proprietaireId.isBlank()) {
            throw new  ParametreManquantException("Owner");
        }

        RestaurantDto restaurantDto = restaurantService.getRestaurant(identifiant, proprietaireId);


        return Response.ok(restaurantDtoAssembler.versJson(restaurantDto)).build();
    }

    @GET
    public Response listerRestaurants(@HeaderParam("Owner") String proprietaireId) {
        if (proprietaireId == null || proprietaireId.isBlank()) {
            throw new  ParametreManquantException("Owner");
        }
        List<RestaurantDto> restaurantDtos = restaurantService.getRestaurants(proprietaireId);

        List<Map<String, Object>> sortie = restaurantDtos
                .stream()
                .map(restaurantDtoAssembler::versJson) // masque proprietaireId
                .toList();

        return Response.ok(sortie).build();
    }

    @POST()
    @Path("/search/restaurants")
    public Response rechercherRestaurants(RestaurantDto champsRecherche) {
        if (champsRecherche == null) {
            champsRecherche = new  RestaurantDto();
        }
        List<RestaurantDto> restaurantDtos = restaurantService.searchRestaurants(champsRecherche);
        return Response.ok(restaurantDtos.stream().map(restaurantDtoAssembler::versJson).toList()).build();
    }
}
