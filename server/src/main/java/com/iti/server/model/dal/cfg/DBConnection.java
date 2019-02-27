package com.iti.server.model.dal.cfg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    Connection connection;

    public void connectToDriver(){
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ConnectToDB(){
        String url="jdbc:mysql://localhost:3306/javachatdatabase";
        try {

            connection=DriverManager.getConnection(url,"root","root");
        } catch (SQLException e) {
            //todo Handel This exception
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            //todo Handel This exception
            e.printStackTrace();
        }

    }

}
