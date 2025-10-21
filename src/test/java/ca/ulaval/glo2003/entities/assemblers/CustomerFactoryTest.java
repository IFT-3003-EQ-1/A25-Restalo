package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.CustomerDto;
import ca.ulaval.glo2003.entities.Customer;
import ca.ulaval.glo2003.entities.CustomerFactory;
import ca.ulaval.glo2003.entities.exceptions.InvalideParameterException;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerFactoryTest {

    private CustomerFactory customerFactory;

    @BeforeEach
    void setUp() {
        customerFactory = new CustomerFactory();
    }

    @Test
    void create_shouldReturnCustomer_whenAllFieldsAreValid() {
       CustomerDto customer = createDummyCustomerDto();
       Customer result = customerFactory.create(customer);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("5141234567", result.getPhoneNumber());
    }

    @Test
    void create_shouldThrowParametreManquantException_whenCustomerIsNull() {
        MissingParameterException exception = assertThrows(MissingParameterException.class, () -> {
            customerFactory.create(null);
        });

        assertEquals("Customer  est requis", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreManquantException_whenNameIsNull() {
        CustomerDto customer = createDummyCustomerDto();
        customer.name = null;

        MissingParameterException exception = assertThrows(MissingParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Customer name  est requis", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreManquantException_whenNameIsEmpty() {

        CustomerDto customer = createDummyCustomerDto();
        customer.name = "";

        MissingParameterException exception = assertThrows(MissingParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Customer name  est requis", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenEmailIsNull() {
        CustomerDto customer = createDummyCustomerDto();
        customer.email = null;

        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Email", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenEmailIsEmpty() {
        CustomerDto customer = createDummyCustomerDto();
        customer.email = "";

        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Email", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenEmailIsBlank() {

        CustomerDto customer = createDummyCustomerDto();
        customer.email = " ";

        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Email", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenEmailHasBadFormat() {
        CustomerDto customer = createDummyCustomerDto();
        customer.email = "test.com";

        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Email", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenEmailHasMultipleAtSymbols() {
        CustomerDto customer = new CustomerDto("Jane Doe", "test@@example.com", "5141234567");
        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Email", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenPhoneNumberIsNull() {
        CustomerDto customer = createDummyCustomerDto();
        customer.phoneNumber = null;
        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            customerFactory.create(customer);
        });
        assertEquals("Phone number", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenPhoneNumberIsEmpty() {
        CustomerDto customer = createDummyCustomerDto();
        customer.phoneNumber = "";
        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Phone number", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenPhoneNumberIsTooShort() {
        CustomerDto customer = createDummyCustomerDto();
        customer.phoneNumber = "123456789"; // Moins de 10 caractères

        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Phone number", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenPhoneNumberIsTooLong() {

        CustomerDto customer = createDummyCustomerDto();
        customer.phoneNumber = "12345678901"; // 11 caractères

        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Phone number", exception.getMessage());
    }

    @Test
    void create_shouldAcceptPhoneNumber_whenExactly10Digits() {

        CustomerDto customer = createDummyCustomerDto();

        Customer result = customerFactory.create(customer);

        assertNotNull(result);
        assertEquals("5141234567", result.getPhoneNumber());
    }

    private Customer createDummyCustomer() {
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhoneNumber("5141234567");
        return customer;
    }

    private CustomerDto createDummyCustomerDto() {
        return new CustomerDto("John Doe", "john.doe@example.com", "5141234567");
    }
}