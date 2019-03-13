/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.server.model;

import com.iti.ChatCommanServices.model.ClientInterfaces.ClientServiceInterface;
import com.iti.ChatCommanServices.model.ServerInterfaces.ServerClientInvitationInterface;
import com.iti.ChatCommanServices.model.ServerInterfaces.ServerClientUpdationInterface;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.entity.user.UserContact;
import com.iti.ChatCommanServices.model.entity.user.UserInvitation;
import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.dao.LoginDao;
import com.iti.server.model.dao.UserContactDao;
import com.iti.server.model.dao.UserDao;
import com.iti.server.model.dao.UserInvitationDao;
import com.iti.server.model.dao.implementation.UserContactDaoImplementation;
import com.iti.server.model.dao.implementation.UserDaoImplementation;
import com.iti.server.model.dao.implementation.UserInvitationDaoImplementation;
import com.iti.server.model.utils.ServerUtils;
import com.iti.server.model.utils.implementation.ServerUtilsImplementation;
import org.hibernate.Session;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author AG
 */
public class ServerClientInvitationImplementation extends UnicastRemoteObject implements ServerClientInvitationInterface {

    LoginDao loginDao;
    DBConnection dbConnection;
    public static HashMap<String, ClientServiceInterface> clients;

    ServerUtils utils;

    public ServerClientInvitationImplementation(DBConnection dbConnection) throws RemoteException {
        this.dbConnection=dbConnection;
        clients=new HashMap<>();
        utils=new ServerUtilsImplementation();
        if(ServerClientEnteranceImplementation.clientSession==null)
            ServerClientEnteranceImplementation.clientSession=new HashMap<>();


    }

    /**
     * get Methods implementation of ClientInterface
     * @param client
     */
    @Override
    public void registerClient(String clientID, ClientServiceInterface client) throws RemoteException {
        clients.put(clientID,client);
        Session session=dbConnection.getConnection().openSession();
        if(!ServerClientEnteranceImplementation.clientSession.containsKey(clientID))
            ServerClientEnteranceImplementation.clientSession.put(clientID,session);

    }
    @Override
    public void acceptInvitation(UserInvitation userInvitation) throws RemoteException {
        /*System.out.println("pressed");
        UserInvitationDao userInvitationDao= new UserInvitationDaoImplementation(dbConnection);
        userInvitationDao.deleteFromSender(userInvitation,clientSession.get(userInvitation.getUser().getPhno()));
        UserContactDao userContactDao= new UserContactDaoImplementation(dbConnection);
        UserContact userContact = new UserContact() ;
        userContact.setPhno(userInvitation.getReceiverPhno());
        userContact.setContactPhno(userInvitation.getSenderPhno());
        userContactDao.persistent(userContact);
        userContact.setPhno(userInvitation.getSenderPhno());
        userContact.setContactPhno(userInvitation.getReceiverPhno());
        userContactDao.persistent(userContact);
*/
    }

    @Override
    public void rejectInvitation(UserInvitation userInvitation) throws RemoteException {
        UserInvitationDao userInvitationDao= new UserInvitationDaoImplementation(dbConnection);
        userInvitationDao.deleteFromReceiver(userInvitation,ServerClientEnteranceImplementation.clientSession.get(userInvitation.getUser().getPhno()));
    }



    @Override
    public void addContact(ArrayList<UserContact> userContacts) throws RemoteException {
        // check that userPhoneNumber  exists in userTable
        /*
        put them atributes
         */
       /* int invitationAccepted = 0;//accepteInvitation
        int emailReceived = 0;//sendEmail
        int invitationReceived = 0;//receivedInvitation
        UserDao userDao = new UserDaoImplementation(dbConnection);
        UserContactDao userContactDao = new UserContactDaoImplementation(dbConnection);
        UserInvitationDao userInvitationDao = new UserInvitationDaoImplementation(dbConnection);
        UserInvitation userInvitation = new UserInvitation();

        for(UserContact userContact:userContacts ) {
            boolean check = userDao.checkUserExist(userContact.getContactPhno());
            //if exist call dao method to add this phone number to user_contact
            if (check) {
                boolean checkInvitation = userInvitationDao.CheckInvitationExist(userContact);
                userInvitation.setSenderPhno(userContact.getPhno());
                userInvitation.setReceiverPhno(userContact.getContactPhno());
                if (checkInvitation) {
                    //delete from user_invitation
                    userInvitationDao.deleteFromReceiver(userInvitation);
                    //add user_contact
                    UserContact userContactFriend = new UserContact();
                    userContactFriend.setPhno(userContact.getContactPhno());
                    userContactFriend.setContactPhno(userContact.getPhno());
                    userContactDao.persistent(userContact); /// to other user
                    userContactDao.persistent(userContactFriend);
                    invitationAccepted++;
                } else {
                    //add in user_invitation
                    userInvitation.setIgnoreFlag(false);
                    userInvitationDao.persistent(userInvitation);
                    invitationReceived++;
                    User sender=userDao.reterive(userContact.getPhno());
                    //get user 
                    ServerClientUpdationInterface serverClientUpdationInterface=new ServerClientUpdationImplementation(dbConnection);
                    User user =userDao.reterive(userContact.getContactPhno());
                    if(!user.getStatus().equals("offline"))
                        clients.get(userContact.getContactPhno()).reciveNewFriendRequst(sender);
                }
            }
            else{
                emailReceived++;
            }
        }
        ServerUtils utils=new ServerUtilsImplementation();
        User sender=userDao.reterive(userContacts.get(0).getPhno());
        utils.addFriendsResult(invitationAccepted, invitationReceived, emailReceived,sender, clients);
   */ }

    @Override
    public ArrayList<User> getInvitations(String userphoneNumber) throws RemoteException {
        //ArrayList<User> friendUserInvtationsList;
        UserInvitationDao userInvitationDao = new UserInvitationDaoImplementation(dbConnection);
        return userInvitationDao.reterive(userphoneNumber,ServerClientEnteranceImplementation.clientSession.get(userphoneNumber));
    }

    @Override
    public int getInvitationsCount(String userphoneNumber) throws RemoteException {
        UserInvitationDao userInvtationCountDao = new UserInvitationDaoImplementation(dbConnection);
        int userInvtationCount = userInvtationCountDao.getInvtationCount(userphoneNumber,ServerClientEnteranceImplementation.clientSession.get(userphoneNumber));
        return userInvtationCount;
    }

    @Override
    public void ignoreInvitation(UserInvitation userInvitation) throws RemoteException {

        UserInvitationDao userInvtationDao = new UserInvitationDaoImplementation(dbConnection);
        userInvtationDao.updateIgnoreFlag(userInvitation,ServerClientEnteranceImplementation.clientSession.get(userInvitation.getUser()));

    }

    @Override
    public ArrayList<User> getInvitationsUsingSenderPhone(String userphoneNumber) throws RemoteException {
        UserInvitationDao userInvitationDao = new UserInvitationDaoImplementation(dbConnection);
        return userInvitationDao.reteriveInvitationUsingSenderPhone(userphoneNumber,ServerClientEnteranceImplementation.clientSession.get(userphoneNumber));
    }

}
