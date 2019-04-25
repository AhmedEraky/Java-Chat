/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.server.admin.modal.implementation;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.RegistrationDuplicateException;
import com.iti.server.admin.modal.AdminModel;
import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.implementation.UserDaoImplementation;
import org.hibernate.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;

/**
 *
 * @author pc
 */
public class AdminModelImplementation implements AdminModel {

    DBConnection dBConnection = new DBConnection();
    public static Session session;

    public AdminModelImplementation() {
        dBConnection.ConnectToDB();
        session = dBConnection.getConnection().openSession();
    }

    @Override
    public void addClientByAdmin(User user) throws RegistrationDuplicateException {
        try {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        } catch (Exception Ex) {
            throw new RegistrationDuplicateException("Oops Error Ocurred Duplicated Phone Number Or Email");
        }

    }

    @Override
    public ArrayList<User> displayUsers() {
        ArrayList<User> usersList = (ArrayList<User>) session.createCriteria(User.class).list();
        return usersList;
    }

    @Override
    public void updateUser(User user) throws RegistrationDuplicateException {
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
    }

    @Override
    public void deleteUser(String phoneNumber) {

        //Cascading
        User user = (User) session.load(User.class, phoneNumber);
        System.out.println(user.getFirstName());
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
    }


}
