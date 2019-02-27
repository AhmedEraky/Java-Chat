package com.iti.ChatCommanServices.model.exception;

import java.io.IOException;

public class DublicateEntryException extends IOException {
    public DublicateEntryException(String message) {
        super(message);

    }
}
