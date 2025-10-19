package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.CustomerDto;
import ca.ulaval.glo2003.entities.exceptions.ParametreInvalideException;
import ca.ulaval.glo2003.entities.exceptions.ParametreManquantException;
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
       CustomerDto customer = createDummyCustomer();
       CustomerDto result = customerFactory.create(customer);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("5141234567", result.getPhoneNumber());
        assertSame(customer, result); // Should return the same instance
    }

    @Test
    void create_shouldThrowParametreManquantException_whenCustomerIsNull() {
        ParametreManquantException exception = assertThrows(ParametreManquantException.class, () -> {
            customerFactory.create(null);
        });

        assertEquals("Customer  est requis", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreManquantException_whenNameIsNull() {
        CustomerDto customer = createDummyCustomer();
                    customer.setName(null);

        ParametreManquantException exception = assertThrows(ParametreManquantException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Customer name  est requis", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreManquantException_whenNameIsEmpty() {

        CustomerDto customer = createDummyCustomer();
        customer.setName("");

        ParametreManquantException exception = assertThrows(ParametreManquantException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Customer name  est requis", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenEmailIsNull() {
        CustomerDto customer = createDummyCustomer();
        customer.setEmail(null);

        ParametreInvalideException exception = assertThrows(ParametreInvalideException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Email", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenEmailIsEmpty() {
        CustomerDto customer = createDummyCustomer();
        customer.setEmail("");

        ParametreInvalideException exception = assertThrows(ParametreInvalideException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Email", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenEmailIsBlank() {

        CustomerDto customer = createDummyCustomer();
        customer.setEmail(" ");

        ParametreInvalideException exception = assertThrows(ParametreInvalideException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Email", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenEmailHasBadFormat() {
        CustomerDto customer = createDummyCustomer();
        customer.setEmail("test.com");

        ParametreInvalideException exception = assertThrows(ParametreInvalideException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Email", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenEmailHasMultipleAtSymbols() {
        CustomerDto customer = new CustomerDto();
        customer.setName("Jane Doe");
        customer.setEmail("test@@example.com");
        customer.setPhoneNumber("5141234567");
        ParametreInvalideException exception = assertThrows(ParametreInvalideException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Email", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenPhoneNumberIsNull() {
        CustomerDto customer = createDummyCustomer();
        customer.setPhoneNumber(null);
        ParametreInvalideException exception = assertThrows(ParametreInvalideException.class, () -> {
            customerFactory.create(customer);
        });
        assertEquals("Phone number", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenPhoneNumberIsEmpty() {
        CustomerDto customer = createDummyCustomer();
        customer.setPhoneNumber("");
        ParametreInvalideException exception = assertThrows(ParametreInvalideException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Phone number", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenPhoneNumberIsTooShort() {
        CustomerDto customer = createDummyCustomer();
        customer.setPhoneNumber("123456789"); // Moins de 10 caractères

        ParametreInvalideException exception = assertThrows(ParametreInvalideException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Phone number", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenPhoneNumberIsTooLong() {

        CustomerDto customer = createDummyCustomer();
        customer.setPhoneNumber("12345678901"); // 11 caractères

        ParametreInvalideException exception = assertThrows(ParametreInvalideException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Phone number", exception.getMessage());
    }

    @Test
    void create_shouldAcceptPhoneNumber_whenExactly10Digits() {

        CustomerDto customer = createDummyCustomer();

        CustomerDto result = customerFactory.create(customer);

        assertNotNull(result);
        assertEquals("5141234567", result.getPhoneNumber());
    }

    private CustomerDto createDummyCustomer() {
        CustomerDto customer = new CustomerDto();
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhoneNumber("5141234567");
        return customer;
    }
}