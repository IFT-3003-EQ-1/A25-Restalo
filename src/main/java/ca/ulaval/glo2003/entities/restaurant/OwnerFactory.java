package ca.ulaval.glo2003.entities.restaurant;

import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;

public class OwnerFactory {

    public Owner createOwner(String ownerId) {
        if (ownerId == null || ownerId.isBlank()) {
            throw new MissingParameterException("Owner");
        }
        return new Owner(ownerId);
    }
}
