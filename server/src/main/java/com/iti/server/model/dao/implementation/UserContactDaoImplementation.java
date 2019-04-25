package com.iti.server.model.dao.implementation;


import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.entity.user.UserContact;

import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.UserContactDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserContactDaoImplementation implements UserContactDao {
     DBConnection dbConnection;

    public UserContactDaoImplementation(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void persistent(UserContact userContact, Session session) {
        Transaction transaction = session.beginTransaction();
        session.save(userContact);
        transaction.commit();
    }


    @Override
    public ArrayList<User> reterive(String userPhoneNumber, Session session) {
        Criteria criteria=session.createCriteria(User.class).add(Restrictions.eq("userPhoneNumber",userPhoneNumber));
        User user= (User) criteria.uniqueResult();
        ArrayList<User> users=new ArrayList();
        for(UserContact userContact:user.getUserContacts()){
            Criteria criteria1=session.createCriteria(User.class).add(Restrictions.eq("userPhoneNumber",userContact.getId().getContact()));
            users.add((User) criteria1.uniqueResult());
        }
        return users;
    }

    @Override
    public ArrayList<User> reteriveOnlineFriends(String phno, Session session) {
        Criteria criteria=session.createCriteria(User.class).add(Restrictions.eq("userPhoneNumber",phno));
        User user= (User) criteria.uniqueResult();
        ArrayList<User> users=new ArrayList();
        for(UserContact userContact:user.getUserContacts()){
            Criteria criteria1=session.createCriteria(User.class).add(Restrictions.eq("userPhoneNumber",userContact.getId().getContact())).add(Restrictions.eq("status","onlineAvailable"));
            User freind= (User) criteria1.uniqueResult();
            if (freind!=null)
                users.add(freind);
        }
        return users;
    }
   /* @Override
    public void persistent(UserContact userContact) {
         String query = " INSERT INTO user_contact (phone_number,contact) VALUES (?, ?)";
       
         try {
             PreparedStatement statement= dbConnection.getConnection().prepareStatement(query);
             statement.setString(1, userContact.getPhno());
             statement.setString(2, userContact.getContactPhno());
             statement.execute();
             statement.close();
         } catch (SQLException ex) {
             Logger.getLogger(UserContactDaoImplementation.class.getName()).log(Level.SEVERE, null, ex);
         }
           
    }

  */
}


