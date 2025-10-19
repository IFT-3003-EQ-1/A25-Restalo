package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.domain.ReservationService;
import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import ca.ulaval.glo2003.entities.Reservation;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/reservations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReservationResource {
    private final ReservationService reservationService;
    public ReservationResource(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GET
    @Path("/{id}")
    public Response getReservation(@PathParam("id") String reservationId) {
        ReservationDto reservation =  reservationService.getReservation(reservationId);
        return Response.ok(reservation).build();
    }
}
