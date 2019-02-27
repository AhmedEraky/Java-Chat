package com.iti.ChatCommanServices.model.entity.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserContact implements Serializable{
    String phno,contactPhno;
    //to save each user and his contacts Key is the phone number of the user Value list of the user contacts
    Map<String,List<User>> userContacts= new HashMap<String,List<User>>();
    //list of user contacts
    List<User> contacts= new ArrayList<User>();

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getContactPhno() {
        return contactPhno;
    }

    public void setContactPhno(String contactPhno) {
        this.contactPhno = contactPhno;
    }


    public Map<String, List<User>> getUserContacts() {
        return userContacts;
    }

    public void setUserContacts(Map<String, List<User>> userContacts) {
        this.userContacts = userContacts;
    }

    public List<User> getContacts() {
        return contacts;
    }

    public void setContacts(List<User> contacts) {
        this.contacts = contacts;
    }


}
