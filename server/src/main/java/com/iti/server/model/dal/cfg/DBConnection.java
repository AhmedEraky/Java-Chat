package com.iti.server.model.dal.cfg;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    SessionFactory sessionFactory;


    public void ConnectToDB(){
        sessionFactory=new Configuration().configure().buildSessionFactory();
    }

    public SessionFactory getConnection() {
        return sessionFactory;
    }

    public void closeConnection(){
        sessionFactory.close();


    }

}
