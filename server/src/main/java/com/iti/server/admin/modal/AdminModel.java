/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.server.admin.modal;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.RegistrationDuplicateException;
import java.util.ArrayList;

/**
 *
 * @author pc
 */
public interface AdminModel {
    public void addClientByAdmin(User user) throws RegistrationDuplicateException;
    public ArrayList<User> displayUsers();
    public void updateUser(User user) throws RegistrationDuplicateException;
    public void deleteUser(String phoneNumber);
}
