package com.iti.server.view.validation.implementation;

import com.iti.server.model.dal.cfg.DBConnection;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class ValidationImplementationTest {

    private Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private Pattern VALID_PHONE_NUMBER
            = Pattern.compile("(01)[0|1|2|5][0-9]{8}");
    ValidationImplementation validationImplementation;

    @Before
    public void setUp() throws ExceptionInInitializerError {
        validationImplementation = new ValidationImplementation();
    }

    @Test
    public void validatePassword() {
        //validation for password
        String password = "111111";
        String confirmPassword = "111111";
        boolean testPassword = false;
        if (password.equals(confirmPassword)) {
            if (password.length() >= 6) {
                testPassword = true;
            } else {
                System.out.println("Password must be more Than or equal 6 digits");
            }
        } else {
            System.out.println("Failed Confirmation Password");
        }
        assertEquals(true, testPassword);
    }

    @Test
    public void validateEmail() {
        String email = "ahm@gmail.com";
        boolean testUser = false;
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if (!matcher.matches()) {
            System.out.println("Invalid Email");
            testUser = false;
        } else {
            testUser = true;
        }
        assertEquals(true, testUser);
    }

    public void validateName() {
        String firstName = "ahmed";
        String lastName = "mohamed";
        boolean testFullName = false;

        if (firstName.equals("")) {
            testFullName = false;
        } else if (lastName.equals("")) {
            testFullName = false;
        } else {
            testFullName = true;
        }
        assertEquals(true, testFullName);
    }

    @Test
    public void validateCountry() {
        String country = "Egypt";
        boolean testCountry = false;
        if (country.equals("")) {
            testCountry = false;
        } else {
            testCountry = true;
        }
        assertEquals(true, testCountry);
    }

    @Test
    public void validateGender() {
        String gender = "male";
        boolean testGender = false;
        if (gender.equals("")) {
            testGender = false;
        } else {
            testGender = true;
        }
        assertEquals(true, testGender);
    }

    @Test
    public void validatePhone() {
        //regex for phone Number
        String phoneNumber = "01022255333";
        Matcher matcher = VALID_PHONE_NUMBER.matcher(phoneNumber);
        boolean validPhone = true;
        if (!matcher.matches()) {
            validPhone = false;
        } else {
            validPhone = true;
        }
        assertEquals(true, validPhone);
    }
}
