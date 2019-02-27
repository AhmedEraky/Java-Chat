package com.iti.ChatCommanServices.model.exception;

import java.io.IOException;
import java.sql.SQLException;

public class RegistrationDuplicateException extends SQLException {
    public RegistrationDuplicateException(String message) {
        super(message);
    }
}
