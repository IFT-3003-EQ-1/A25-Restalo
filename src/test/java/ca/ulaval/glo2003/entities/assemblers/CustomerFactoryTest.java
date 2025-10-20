package ca.ulaval.glo2003.entities.assemblers;

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
       Customer customer = createDummyCustomer();
       Customer result = customerFactory.create(customer);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("5141234567", result.getPhoneNumber());
        assertSame(customer, result); // Should return the same instance
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
        Customer customer = createDummyCustomer();
                    customer.setName(null);

        MissingParameterException exception = assertThrows(MissingParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Customer name  est requis", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreManquantException_whenNameIsEmpty() {

        Customer customer = createDummyCustomer();
        customer.setName("");

        MissingParameterException exception = assertThrows(MissingParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Customer name  est requis", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenEmailIsNull() {
        Customer customer = createDummyCustomer();
        customer.setEmail(null);

        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Email", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenEmailIsEmpty() {
        Customer customer = createDummyCustomer();
        customer.setEmail("");

        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Email", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenEmailIsBlank() {

        Customer customer = createDummyCustomer();
        customer.setEmail(" ");

        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Email", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenEmailHasBadFormat() {
        Customer customer = createDummyCustomer();
        customer.setEmail("test.com");

        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Email", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenEmailHasMultipleAtSymbols() {
        Customer customer = new Customer();
        customer.setName("Jane Doe");
        customer.setEmail("test@@example.com");
        customer.setPhoneNumber("5141234567");
        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Email", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenPhoneNumberIsNull() {
        Customer customer = createDummyCustomer();
        customer.setPhoneNumber(null);
        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            customerFactory.create(customer);
        });
        assertEquals("Phone number", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenPhoneNumberIsEmpty() {
        Customer customer = createDummyCustomer();
        customer.setPhoneNumber("");
        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Phone number", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenPhoneNumberIsTooShort() {
        Customer customer = createDummyCustomer();
        customer.setPhoneNumber("123456789"); // Moins de 10 caractères

        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Phone number", exception.getMessage());
    }

    @Test
    void create_shouldThrowParametreInvalideException_whenPhoneNumberIsTooLong() {

        Customer customer = createDummyCustomer();
        customer.setPhoneNumber("12345678901"); // 11 caractères

        InvalideParameterException exception = assertThrows(InvalideParameterException.class, () -> {
            customerFactory.create(customer);
        });

        assertEquals("Phone number", exception.getMessage());
    }

    @Test
    void create_shouldAcceptPhoneNumber_whenExactly10Digits() {

        Customer customer = createDummyCustomer();

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
}