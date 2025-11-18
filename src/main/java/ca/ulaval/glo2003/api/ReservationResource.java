package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.assemblers.ReservationDtoAssembler;
import ca.ulaval.glo2003.domain.ReservationService;
import ca.ulaval.glo2003.domain.dtos.ReservationDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/reservations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReservationResource {

    private final ReservationService reservationService;

    private final ReservationDtoAssembler reservationAssembler;

    public ReservationResource(ReservationService reservationService, ReservationDtoAssembler reservationAssembler) {
        this.reservationService = reservationService;
        this.reservationAssembler = reservationAssembler;
    }

    @GET
    @Path("/{id}")
    public Response getReservation(@PathParam("id") String reservationId) {
        ReservationDto reservation =  reservationService.getReservation(reservationId);
        return Response.ok(reservationAssembler.toJson(reservation)).build();
    }

    @DELETE
    @Path("/{number}")
    public Response deleteReservation(@PathParam("number") String reservationNumber) {
        boolean isDeleted = reservationService.deleteReservation(reservationNumber);
        return Response.noContent().build();
    }
}
