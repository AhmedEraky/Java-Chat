package com.iti.ChatCommanServices.model.exception;

public class LoginFaieldException extends Exception {

    public LoginFaieldException(String s){
        super(s);
    };

    public LoginFaieldException() {
        throw new UnsupportedOperationException("Login Failed");
    }
}
