package com.iti.server.controller;

import com.iti.ChatCommanServices.model.ServerInterfaces.*;
import com.iti.server.model.ServerChatImplementation;
import com.iti.server.model.ServerClientEnteranceImplementation;
import com.iti.server.model.ServerClientInvitationImplementation;
import com.iti.server.model.ServerClientRegisterationImplementation;
import com.iti.server.model.ServerClientUpdationImplementation;
import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.serverInterface.ServerInterface;
import com.iti.server.model.serverInterfaceImplementation.SereverImpelementation;
import java.rmi.RemoteException;

public class ServerController {
   public DBConnection dbConnction;
   ServerClientRegisterationInterface serverInterface;
   ServerChatInterface serverChatInterface;
   ServerClientEnteranceInterface serverClientEnteranceInterface;
   ServerClientInvitationInterface serverClientInvitationInterface;
   ServerClientUpdationInterface serverClientUpdationInterface;
   ServerInterface serverStatsticsInterface;

    public ServerController() {

        dbConnction=new DBConnection();
        dbConnction.connectToDriver();
        dbConnction.ConnectToDB();
        try {
            serverInterface=new ServerClientRegisterationImplementation(dbConnction);
            serverChatInterface=new ServerChatImplementation(dbConnction);
            serverClientEnteranceInterface=new ServerClientEnteranceImplementation(dbConnction);
            serverClientInvitationInterface=new ServerClientInvitationImplementation(dbConnction);
            serverClientUpdationInterface=new ServerClientUpdationImplementation(dbConnction);
            serverStatsticsInterface=new SereverImpelementation(dbConnction);
            
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
