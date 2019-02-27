/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.server.controller;

import com.iti.server.model.ServerChatImplementation;
import com.iti.server.model.ServerClientEnteranceImplementation;
import com.iti.server.model.ServerClientInvitationImplementation;
import com.iti.server.model.ServerClientRegisterationImplementation;
import com.iti.server.model.ServerClientUpdationImplementation;
import com.iti.server.model.dal.cfg.DBConnection;
import com.iti.server.model.serverInterfaceImplementation.SereverImpelementation;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSetup {

    ServerClientRegisterationImplementation serverClientRegisterationImplementation;
    ServerChatImplementation serverChatImplementation;
    ServerClientEnteranceImplementation serverClientEnteranceImplementation;
    ServerClientInvitationImplementation serverClientInvitationImplementation;
    ServerClientUpdationImplementation serverClientUpdationImplementation;
    SereverImpelementation serverImplementation;
    Registry register;

    public ServerSetup() {

    }

    public void startServer(DBConnection dbConnction) {
        try {
            serverClientRegisterationImplementation = new ServerClientRegisterationImplementation(dbConnction);
            serverChatImplementation = new ServerChatImplementation(dbConnction);
            serverClientEnteranceImplementation = new ServerClientEnteranceImplementation(dbConnction);
            serverClientInvitationImplementation = new ServerClientInvitationImplementation(dbConnction);
            serverClientUpdationImplementation = new ServerClientUpdationImplementation(dbConnction);
            serverImplementation=new SereverImpelementation(dbConnction);
            register = LocateRegistry.getRegistry();
            register.rebind("ServerRegisterationService", serverClientRegisterationImplementation);
            register.rebind("ServerChatService", serverChatImplementation);
            register.rebind("ServerEnteranceService", serverClientEnteranceImplementation);
            register.rebind("ServerInvitationService", serverClientInvitationImplementation);
            register.rebind("ServerUpdatationService", serverClientUpdationImplementation);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public void stopServer() {
        try {
            register.unbind("ServerRegisterationService");
            register.unbind("ServerChatService");
            register.unbind("ServerEnteranceService");
            register.unbind("ServerInvitationService");
            register.unbind("ServerUpdatationService");
            System.out.println("Server Stopped");
        } catch (RemoteException ex) {
            Logger.getLogger(ServerSetup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(ServerSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
