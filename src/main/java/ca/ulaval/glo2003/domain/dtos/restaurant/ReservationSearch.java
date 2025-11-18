package ca.ulaval.glo2003.domain.dtos.restaurant;

public class ReservationSearch {
   public String ownerId;
   public String restaurantId;
   public String reservationDate;
   public String customerName;

    public ReservationSearch(String ownerId, String customerName, String reservationData, String restaurantId) {
        this.ownerId = ownerId;
        this.customerName = customerName;
        this.reservationDate = reservationData;
        this.restaurantId = restaurantId;
    }
}
