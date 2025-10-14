package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dtos.ProprietaireDto;
import ca.ulaval.glo2003.domain.dtos.RestaurantDto;
import ca.ulaval.glo2003.entities.assemblers.ProprietaireFactory;
import ca.ulaval.glo2003.entities.assemblers.RestaurantAssembler;
import ca.ulaval.glo2003.entities.assemblers.RestaurantFactory;
import ca.ulaval.glo2003.entities.exceptions.AccessInterditException;
import ca.ulaval.glo2003.entities.exceptions.NotFoundException;
import ca.ulaval.glo2003.entities.exceptions.ParametreManquantException;
import ca.ulaval.glo2003.entities.filtres.FiltreRestaurantFactory;
import ca.ulaval.glo2003.infra.persistence.InMemoryRestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantServiceIntegratedTest {

    private RestaurantService restaurantService;

    private RestaurantDto restaurantDto;

    private ProprietaireDto proprietaireDto;

    @BeforeEach
    public void setUp() {
        restaurantService = new RestaurantService(
                new RestaurantFactory(),
                new InMemoryRestaurantRepository(),
                new ProprietaireFactory(),
                new RestaurantAssembler(),
                new FiltreRestaurantFactory()
        );

        proprietaireDto = new ProprietaireDto();
        proprietaireDto.id = "1";
        proprietaireDto.nom = "";

        restaurantDto = new RestaurantDto();
        restaurantDto.proprietaire = proprietaireDto;
        restaurantDto.nom = "Pizz";
        restaurantDto.horaireOuverture = "11:00:00";
        restaurantDto.horaireFermeture = "19:00:00";
        restaurantDto.capacite = 2;
    }

    @Test
    public void givenCreateRestaurant_whenParameterAreValide_thenRestaurantIsCreated() {
        String restaurantId = restaurantService.createRestaurant(proprietaireDto, restaurantDto);

        assertNotNull(restaurantId);
    }

    @Test
    public void givenCreateRestaurant_whenProprietaireIsNull_thenParameterManquantExceptionIsThrown() {
        restaurantDto.proprietaire.id = null;

        assertThrows(ParametreManquantException.class, () -> restaurantService.createRestaurant(proprietaireDto, restaurantDto));
    }

    @Test
    public void givenCreateRestaurant_whenNomIsNull_thenParameterManquantExceptionIsThrown() {
        restaurantDto.nom = null;

        assertThrows(ParametreManquantException.class, () -> restaurantService.createRestaurant(proprietaireDto, restaurantDto));
    }

    @Test
    public void givenCreateRestaurant_whenHoraireFermetureIsNull_thenParameterManquantExceptionIsThrown() {
        restaurantDto.horaireFermeture = null;

        assertThrows(ParametreManquantException.class, () -> restaurantService.createRestaurant(proprietaireDto, restaurantDto));
    }

    @Test
    public void givenCreateRestaurant_whenHoraireOuvertureIsNull_thenParameterManquantExceptionIsThrown() {
        restaurantDto.horaireOuverture = null;

        assertThrows(ParametreManquantException.class, () -> restaurantService.createRestaurant(proprietaireDto, restaurantDto));
    }

    @Test
    public void givenGetRestaurants_whenParameterAreValid_thenListRestaurantsIsReturned() {
        String proprietaireCible = proprietaireDto.id;
        restaurantService.createRestaurant(proprietaireDto, restaurantDto);
        restaurantService.createRestaurant(proprietaireDto, restaurantDto);
        proprietaireDto.id = "12345";
        restaurantService.createRestaurant(proprietaireDto, restaurantDto);

        assertEquals(2, restaurantService.getRestaurants(proprietaireCible).size());
    }

    @Test
    public void givenGetRestaurants_whenProprietaireIdIsNull_thenEmptyListIsReturned() {
        assertTrue(restaurantService.getRestaurants(null).isEmpty());

    }

    @Test
    public void givenGetRestaurant_whenParameterAreValid_thenRestaurantIsReturned() {
        restaurantDto.id = restaurantService.createRestaurant(proprietaireDto, restaurantDto);
        RestaurantDto dto = restaurantService.getRestaurant(restaurantDto.id, proprietaireDto.id);
        assertEquals(restaurantDto , dto);
    }

    @Test
    public void givenGetRestaurant_whenProprietaireIdIsInvalid_thenAccessInterditExceptionIsThrown() {
        restaurantDto.id = restaurantService.createRestaurant(proprietaireDto, restaurantDto);
        assertThrows(AccessInterditException.class, () -> restaurantService.getRestaurant(restaurantDto.id, "1234546"));
    }

    @Test
    public void givenGetRestaurant_whenRestaurantDoesntExist_thenNotFoundExceptionIsThrown() {
        restaurantService.createRestaurant(proprietaireDto, restaurantDto);
        restaurantDto.id = "12345";
        assertThrows(NotFoundException.class, () -> restaurantService.getRestaurant(restaurantDto.id, proprietaireDto.id));
    }

    @Test
    public void givenSearchRestaurants_whenNoParameterAreSpecified_thenReturnAllRestaurants() {
        restaurantService.createRestaurant(proprietaireDto, restaurantDto);
        restaurantService.createRestaurant(proprietaireDto, restaurantDto);
        restaurantDto.id = "12345";
        restaurantDto.nom = "Dejeuner";
        restaurantDto.horaireOuverture = "06:00:00";
        restaurantDto.horaireFermeture = "13:00:00";
        restaurantService.createRestaurant(proprietaireDto, restaurantDto);

        assertEquals(3, restaurantService.searchRestaurants(new RestaurantDto()).size());
    }

    @Test
    public void givenSearchRestaurants_withParamaterName_thenReturnOnlyMatchingRestaurants() {
        restaurantService.createRestaurant(proprietaireDto, restaurantDto);
        restaurantService.createRestaurant(proprietaireDto, restaurantDto);
        restaurantDto.id = "12345";
        restaurantDto.nom = "Dejeuner";
        restaurantDto.horaireOuverture = "06:00:00";
        restaurantDto.horaireFermeture = "13:00:00";
        restaurantService.createRestaurant(proprietaireDto, restaurantDto);

        RestaurantDto searchParams = new RestaurantDto();
        searchParams.nom = "Pizz";
        assertEquals(2, restaurantService.searchRestaurants(searchParams).size());

    }
}
