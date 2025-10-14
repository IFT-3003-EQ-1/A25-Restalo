package ca.ulaval.glo2003.restaurants.resources;

import ca.ulaval.glo2003.restaurants.domain.CreateReservationDto;
import ca.ulaval.glo2003.restaurants.domain.Reservation;
import ca.ulaval.glo2003.restaurants.domain.Restaurant;
import ca.ulaval.glo2003.restaurants.service.ReservationService;
import ca.ulaval.glo2003.restaurants.utils.Constant;
import ca.ulaval.glo2003.restaurants.utils.ValidationObject;
import ca.ulaval.glo2003.restaurants.utils.Validator;
import ca.ulaval.glo2003.restaurants.service.RestaurantService;
import ca.ulaval.glo2003.restaurants.utils.JsonUtil;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.Map;

@Path("/reservations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReservationResource {

    // ------------------------------
    // POST /restaurants : créer
    // ------------------------------
    private final ReservationService reservationService;

    public ReservationResource() {
        this.reservationService = new ReservationService();
    }

    @GET
    @Path("/{id}")
    public Response getReservation(@PathParam("id") String reservationId) {
        ValidationObject validationObject = Validator.validateGetReservation(reservationId);
        if(validationObject.getCode() != null){
            return badRequest(validationObject);
        }

        Reservation reservation = this.reservationService.getReservation(reservationId);
        if(reservation == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(reservation).build();
    }

    private Response badRequest(ValidationObject validationObject) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", validationObject.getCode(), "description", validationObject.getDescription()))
                .build();
    }
}
