package ca.ulaval.glo2003.entities;

import ca.ulaval.glo2003.domain.dtos.CustomerDto;
import ca.ulaval.glo2003.entities.exceptions.InvalideParameterException;
import ca.ulaval.glo2003.entities.exceptions.MissingParameterException;
import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerFactory {
    private static final String EMAIL_REGEX = "^[^@]+@[^@]+\\.[^@]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public Customer create(CustomerDto customer) {
        if(customer == null) {
            throw new MissingParameterException("Customer");
        }

        if(Strings.isNullOrEmpty(customer.name)) {
            throw new MissingParameterException("Customer name");
        }

        if(!isValidEmail(customer.email)) {
            throw new InvalideParameterException("Email");
        }

        if(Strings.isNullOrEmpty(customer.phoneNumber) || customer.phoneNumber.length() != 10) {
            throw new InvalideParameterException("Phone number");
        }

        return new Customer(customer.name, customer.email, customer.phoneNumber);
    }

    private static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

}
