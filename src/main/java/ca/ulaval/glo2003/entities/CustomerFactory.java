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

    public Customer create(CustomerDto customerDto) {
        if(customerDto == null) {
            throw new MissingParameterException("Customer");
        }

        if(Strings.isNullOrEmpty(customerDto.name)) {
            throw new MissingParameterException("Customer name");
        }

        if(!isValidEmail(customerDto.email)) {
            throw new InvalideParameterException("Email");
        }

        if(Strings.isNullOrEmpty(customerDto.phoneNumber) || customerDto.phoneNumber.length() != 10) {
            throw new InvalideParameterException("Phone number");
        }

        return new Customer(customerDto.name, customerDto.email, customerDto.phoneNumber);
    }

    private static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

}
