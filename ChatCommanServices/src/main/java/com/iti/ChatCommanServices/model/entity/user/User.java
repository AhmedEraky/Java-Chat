package com.iti.ChatCommanServices.model.entity.user;


import com.iti.ChatCommanServices.model.ClientInterfaces.ClientServiceInterface;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {
    
    String  userPhoneNumber,firstName,lastName,email,password,country,gender,bio,status;
    Date datBirth;
    byte[] photo;
    ClientServiceInterface clientInterface;

    public ClientServiceInterface getClientInterface() {
        return clientInterface;
    }

    public void setClientInterface(ClientServiceInterface clientInterface) {
        this.clientInterface = clientInterface;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDatBirth() {
        return datBirth;
    }

    public void setDatBirth(Date datBirth) {
        this.datBirth = datBirth;
    }
    
    public String getPhno() {
        return  userPhoneNumber;
    }

    public void setPhno(String  userPhoneNumber) {
        this. userPhoneNumber =  userPhoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }


}
