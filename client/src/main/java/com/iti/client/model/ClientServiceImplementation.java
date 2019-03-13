package com.iti.client.model;

import com.iti.ChatCommanServices.model.ClientInterfaces.ClientServiceInterface;
import com.iti.ChatCommanServices.model.entity.message.EntityMessage;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.RejectFileException;
import com.iti.client.view.MainClass;
import com.iti.client.view.controller.*;
import java.io.File;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.controlsfx.control.Notifications;

public class ClientServiceImplementation extends UnicastRemoteObject implements ClientServiceInterface {

    MainClass mainClass;

    public ClientServiceImplementation(MainClass mainClass) throws RemoteException {
        this.mainClass = mainClass;
    }

    @Override
    public void reciveMessage(EntityMessage entityMessage) throws RemoteException {
        if (mainClass.getGui() instanceof ChatWindowController) {
            ((ChatWindowController) mainClass.getGui()).addNewMessage(entityMessage);
        } else {
            Platform.runLater(() -> {
                Media sound = new Media(new File(getrealURL()).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                Notifications.create().darkStyle().title("Message").text(entityMessage.getSenderUser()+" send "+entityMessage.getMessage()).showInformation();
            });
        }
    }

    private String getrealURL() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath+"\\notify.mp3";

    }

    @Override
    public String getClientID() throws RemoteException {
        return mainClass.getUser().getPhno();
    }

    @Override
    public void addNewOnlineContact(User user) throws RemoteException {
        if (mainClass.getGui() instanceof ChatWindowController) {
            ((ChatWindowController) mainClass.getGui()).addOnlineContact(user);
        }else {
            Platform.runLater(() -> {
                Media sound = new Media(new File(getrealURL()).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                Notifications.create().darkStyle().title("Message").text(user.getFirstName()+" is online now").showInformation();
            });
        }
    }

    @Override
    public void removeOnlineContact(User user) throws RemoteException {
        if (mainClass.getGui() instanceof ChatWindowController) {
            ((ChatWindowController) mainClass.getGui()).removeOnlineContact(user);
        }else {
            Platform.runLater(() -> {
                Media sound = new Media(new File(getrealURL()).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                Notifications.create().darkStyle().title("Message").text(user.getFirstName()+" is offline now").showInformation();
            });
        }

    }

    @Override
    public void showOnlineFriendChangeStatus(User user) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showNewInvitations(int count) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showAcceptedRequest(User user) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showAcceptedInvitation(ArrayList<String> invitationAccepted) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showReceivedInvitation(ArrayList<String> invitationReceived) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reciveNewFriendRequst(User user) throws RemoteException {
        if (mainClass.getGui() instanceof InvitationWindowController) {
            ((InvitationWindowController) mainClass.getGui()).reciveNewFriendRequst(user);
        }else {
            Platform.runLater(() -> {
                Media sound = new Media(new File(getrealURL()).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                Notifications.create().darkStyle().title("Message").text(user.getFirstName()+" send you a friend request").showInformation();
            });
        }
    }

    @Override
    public void changePasswordByNewClient() throws RemoteException {
        if (mainClass.getGui() instanceof LoginWindowController) {
            ((LoginWindowController) mainClass.getGui()).changePasswordByNewClient();
        }
    }

    @Override
    public void changedPasswordByNewUser() throws RemoteException {
        if (mainClass.getGui() instanceof ChatWindowController) {
            ((ChatWindowController) mainClass.getGui()).changedNewClientPasswordDone();
        }
    }

    @Override
    public void reciveFile(EntityMessage entityMessage, String path) throws RemoteException {
        try {
            File file = new File(path + entityMessage.getFileName());
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(entityMessage.getFileData(), 0, entityMessage.getFileLenght());
            fileOutputStream.flush();
            fileOutputStream.close();
            System.out.println("Done writing data...");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //((HomeWindowController)mainClass.getGui()).addNewMessage(entityMessage.getMessage());
    }

    @Override
    public String reciveDicisionFile(EntityMessage entityMessage) throws RemoteException, RejectFileException {
        try {
            if (mainClass.getGui() instanceof ChatWindowController) {
                return ((ChatWindowController) mainClass.getGui()).confirmFile(entityMessage);
            }
        } catch (RejectFileException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void addFriendsResult(int invitationAccepted, int invitationReceived, int emailReceived) throws RemoteException {
        if (mainClass.getGui() instanceof AddFriendWindowController) {
            ((AddFriendWindowController) mainClass.getGui()).addFriendsResult(invitationAccepted, invitationReceived, emailReceived);
        }
    }

    @Override
//    public void updateUserStatusOnFriendsWindow(User user, User userFriends) throws RemoteException {
//        //((MainWindowController)mainClass.getGui()).updateStatus(user);
    public void updateUserStatusOnFriendsWindow(User user, User userFriends) throws RemoteException {
        //((ChatWindowController) mainClass.getGui()).updateStatus(user);
    }

    @Override
    public void reciveGroupMessage(EntityMessage entityMessage, String groupID) throws RemoteException {
        if(mainClass.getGui() instanceof GroupChatWindowController)
            ((GroupChatWindowController)mainClass.getGui()).reciveGroupMessage(entityMessage,groupID);
        else {
            Platform.runLater(() -> {
                Media sound = new Media(new File(getrealURL()).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                Notifications.create().darkStyle().title("Message").text(entityMessage.getSenderUser().getPhno()+" send in group "+entityMessage.getMessage()).showInformation();
            });
        }
    }

    @Override
    public void reciveServerMessage(String message) throws RemoteException {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Server");
            alert.setHeaderText("Server Message");
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    @Override
    public synchronized void notifyCloseServer() throws RemoteException {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Server");
            alert.setHeaderText("Server Message");
            alert.setContentText("Server Is Going to CLose");
            alert.showAndWait();
        });

        Thread thread=new Thread(() -> {
            try {
                try {
                    mainClass.getServerServiceLocator().getRegisterationService().unRegistration(mainClass.getUser().getPhno());
                    Thread.sleep(3000);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.exit();
            System.exit(0);

        });
        thread.start();
    }
}
