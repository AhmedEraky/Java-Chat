package com.iti.client.view.validation.implementation;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.client.view.validation.ValidationInterface;
import javafx.scene.control.Alert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationImplementation implements ValidationInterface {

    private Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private Pattern VALID_PHONE_NUMBER =
            Pattern.compile("(01)[0|1|2|5][0-9]{8}");

    @Override
    public boolean validate(User user, String confirmPassword) {
        boolean validData = false;
        if(validatePhone(user.getPhno()) && validateName(user.getFirstName(),user.getLastName()) &&
                validatePassword(user.getPassword() , confirmPassword)
                &&validateGender(user.getGender())&&validateCountry(user.getCountry())&&validateEmail(user.getEmail()))
            validData = true;
        return validData;
    }


    private boolean validateName(String firstName,String lastName){
        if(firstName.equals("")){
            showAlert("Invalid First Name");
            return false;
        }
        else if (lastName.equals("")){
            showAlert("Invalid Last Name");
            return false;
        }
        return true;
    }
    private boolean validateCountry(String country){
        if(country.equals("")) {
            showAlert("Invalid Country");

            return false;
        }
        return true;
    }
    private boolean validateGender(String gender){
        if(gender.equals("")){
            showAlert("Invalid Gender");
            return false;
        }

        return true;
    }
    private boolean validatePhone(String phoneNumber) {
        //regex for phone Number
        Matcher matcher = VALID_PHONE_NUMBER.matcher(phoneNumber);
        boolean validPhone = true;
        if (!matcher.matches()) {
            showAlert("Invalid Phone Number");
            validPhone = false;
        }else{
            validPhone = true;
        }
        return validPhone;
    }
    private boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if (!matcher.matches()) {
            showAlert("Invalid Email");
        }
        return matcher.matches();
    }
    private boolean validatePassword(String password, String confirmPassword) {
        //validation for password
        boolean confirmation = false;
        if (password.equals(confirmPassword)) {
            if (password.length() >= 6) {
                confirmation = true;
            } else {
                showAlert("Password must be more Than or equal 6 digits");
            }
        } else {
            showAlert("Failed Confirmation Password");
        }
        return confirmation;
    }
    public void showAlert(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorMessage);
        alert.show();
    }
}
