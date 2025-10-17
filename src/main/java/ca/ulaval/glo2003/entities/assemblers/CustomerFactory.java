package ca.ulaval.glo2003.entities.assemblers;

import ca.ulaval.glo2003.domain.dtos.CustomerDto;
import ca.ulaval.glo2003.entities.exceptions.ParametreInvalideException;
import ca.ulaval.glo2003.entities.exceptions.ParametreManquantException;
import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerFactory {
    public static final String EMAIL_REGEX = "^[^@]+@[^@]+\\.[^@]+$";
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    public CustomerDto create(CustomerDto customer) {
        if(customer == null) {
            throw new ParametreManquantException("Customer");
        }

        if(Strings.isNullOrEmpty(customer.getName())) {
            throw new ParametreManquantException("Customer name");
        }

        if(!isValidEmail(customer.getEmail())) {
            throw new ParametreInvalideException("Email");
        }

        if(Strings.isNullOrEmpty(customer.getPhoneNumber()) || customer.getPhoneNumber().length() != 10) {
            throw new ParametreInvalideException("Phone number");
        }

        return customer;
    }

    private static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

}
