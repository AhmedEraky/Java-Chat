package com.iti.client.controller;

import com.iti.ChatCommanServices.model.ServerInterfaces.*;
import com.iti.client.view.MainClass;
import javafx.scene.control.Alert;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.iti.client.view.MainClass.alertMessageException;


public class ServerServiceLocator {

	private ServerClientRegisterationInterface serverClientRegisterationInterface=null;
	private ServerClientEnteranceInterface serverClientEnteranceInterface=null;
	private ServerClientInvitationInterface serverClientInvitationInterface=null;
	private ServerClientUpdationInterface serverClientUpdationInterface=null;
	private ServerChatInterface serverChatInterface=null;


	public ServerClientRegisterationInterface getRegisterationService(){
		if(serverClientRegisterationInterface==null)
		{
			try{
				Registry regeisterClient=LocateRegistry.getRegistry("127.0.0.1");
				serverClientRegisterationInterface=(ServerClientRegisterationInterface)regeisterClient.lookup("ServerRegisterationService");

			}catch(RemoteException remoteClientException)
			{
				alertMessageException("sorry error occured during getRegisterationService in server");
			} catch (NotBoundException ex) {

				alertMessageException("sorry error occured during  getRegisterationService in server");

			}
		}
		return serverClientRegisterationInterface;
	}
	public ServerChatInterface getChatService(){

		if(serverChatInterface == null) {
			try {
				Registry regeisterClient = LocateRegistry.getRegistry("127.0.0.1");

				serverChatInterface = (ServerChatInterface) regeisterClient.lookup("ServerChatService");

			} catch (RemoteException remoteClientException) {
				alertMessageException("sorry error occured during  getChatService in server");

			} catch (NotBoundException ex) {
				alertMessageException("sorry error occured during  getChatService in server");
			}
		}
		return serverChatInterface;
	}
	public ServerClientEnteranceInterface getEnteranceService(){


		if(serverClientEnteranceInterface==null)
		{
			try {
				Registry regeisterClient = LocateRegistry.getRegistry("127.0.0.1");

				serverClientEnteranceInterface = (ServerClientEnteranceInterface) regeisterClient.lookup("ServerEnteranceService");

			} catch (RemoteException remoteClientException) {
				//throw exception to view
				alertMessageException("sorry error occured during  getEnteranceService in server");

			} catch (NotBoundException ex) {
				alertMessageException("sorry error occured during  getEnteranceService in server");
			}
		}
		return serverClientEnteranceInterface;
	}
	public ServerClientInvitationInterface getInvitationService() {
		if(serverClientInvitationInterface==null)
		{


			try {
				Registry regeisterClient = LocateRegistry.getRegistry("127.0.0.1");

				serverClientInvitationInterface = (ServerClientInvitationInterface) regeisterClient.lookup("ServerInvitationService");

			} catch (RemoteException remoteClientException) {
				alertMessageException("sorry error occured during  getInvitationService in server");
			} catch (NotBoundException ex) {
				alertMessageException("sorry error occured during  getInvitationService in server");
			}
		}
		return serverClientInvitationInterface;
	}
	public ServerClientUpdationInterface getUpdateService() {

		if(serverClientUpdationInterface==null) {

			try {
				Registry regeisterClient = LocateRegistry.getRegistry("127.0.0.1");
				serverClientUpdationInterface = (ServerClientUpdationInterface) regeisterClient.lookup("ServerUpdatationService");
			} catch (RemoteException remoteClientException) {
				alertMessageException("sorry error occured during  getUpdateService in server");
			} catch (NotBoundException ex) {
				alertMessageException("sorry error occured during  getUpdateService in server");
			}
		}
		return serverClientUpdationInterface;
	}

}