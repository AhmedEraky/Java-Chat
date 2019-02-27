package com.iti.ChatCommanServices.model.entity.user;

import java.io.Serializable;

public class EntityLogin implements Serializable {
    String phno,password;

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
