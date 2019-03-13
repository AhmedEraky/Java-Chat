/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.client.view.controller;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.iti.ChatCommanServices.model.entity.message.EntityMessage;
import com.iti.ChatCommanServices.model.entity.message.MessageSettings;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.RejectFileException;
import com.iti.client.model.xml.*;
import com.iti.client.view.MainClass;
import com.iti.client.view.chatbot.ChatBotImplementation;
import com.iti.client.view.factory.DataFactory;
import com.iti.client.view.factory.ParentFactory;
import com.sun.org.apache.xerces.internal.jaxp.datatype.DatatypeFactoryImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.Notifications;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import static com.iti.client.view.MainClass.alertMessageException;
public class ChatWindowController extends ParentMenuBarController {

    @FXML
    private ListView<EntityMessage> chatList;

    @FXML
    private Pane userProfilePane;

    @FXML
    private ListView friendsList;

    @FXML
    private ImageView createGroupIcon;

    @FXML
    private ImageView addToGroup;

    @FXML
    private ImageView saveChat;

    @FXML
    private Label userNameLable;

    @FXML
    private ImageView attachFileImg;

    @FXML
    private Button chatBtn;

    @FXML
    private TextField newMessageTF;

    @FXML
    ToggleButton botBtn;

    @FXML
    private ColorPicker colorPicker;
    @FXML
    private ColorPicker backGround;
    @FXML
    private ComboBox fontsCB;
    @FXML
    private ComboBox sizeCB;
    @FXML
    private ToggleButton boldBtn;
    @FXML
    private ToggleButton underlineBtn;
    @FXML
    private ToggleButton italicBtn;

    String selectedColor ;
    boolean chatBotFlag = false;

    String chatID;
    MainClass mainClass;
    ObservableList<User> userFriends;
    HashMap<String, ObservableList<EntityMessage>> chatSession;
    User otherContact;
    File selectedFile;
    @FXML
    private ComboBox<String> Status;
    ObservableList<String> StatusChanged;
    public ChatWindowController(MainClass mainClass) {
        this.mainClass=mainClass;
        chatID="";
        chatSession=new HashMap<>();
        userFriends = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        StatusChanged=DataFactory.getStatusList();
        Status.setItems(StatusChanged);
        Status.setOnAction((action)->{
            User user=mainClass.getUser();
            String userStatus= Status.getValue().toString();
            user.setStatus(userStatus);
            try {
                if(userStatus.equals("offline"))
                    mainClass.getServerServiceLocator().getUpdateService().notifyOffline(user);
                else
                    mainClass.getServerServiceLocator().getUpdateService().notifyOnline(user);
            } catch (RemoteException ex) {
                Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        setButtonAction(mainClass);

        addToGroup.setImage(new Image ("/plus.png"));
        saveChat.setImage(new Image ("/floppy-disk.png"));
        createGroupIcon.setImage(new Image ("/createGroup.png"));
        attachFileImg.setImage(new Image ("/attachfile.png"));
        chatBtn.setOnAction(this::startChat);
        botBtn.setOnAction(this::enableChatBot);
        attachFileImg.setOnMousePressed(this::chooseFile);
        friendsList.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> param) {
                return new ListCell<User>() {
                    @Override
                    protected void updateItem(User user, boolean empty) {
                        super.updateItem(user, empty);
                        if (!empty) {
                            HBox hbox = new HBox();
                            ImageView friendImage=new ImageView();
                            byte [] imageByte = user.getImage();
                            friendImage.setFitHeight(30);
                            friendImage.setFitWidth(30);
                            friendImage.setImage(new Image(new ByteArrayInputStream(imageByte)));
                            hbox.getChildren().add(friendImage);
                            hbox.getChildren().addAll(new Label(user.getFirstName() +" "+user.getLastName()+" ") ,new Label(user.getStatus()));
                            Label label=(Label)hbox.getChildren().get(2);
                            System.out.println("sataus "+label.getText());
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });
        chatList.setCellFactory(new Callback<ListView<EntityMessage>, ListCell<EntityMessage>>() {
            @Override
            public ListCell<EntityMessage> call(ListView<EntityMessage> entityMessageListView) {
                return new ListCell<EntityMessage>(){
                    @Override
                    protected void updateItem(EntityMessage message, boolean empty) {
                        super.updateItem(message, empty);
                        if(!empty){

                            if(message.getSenderUser().getPhno().equals(mainClass.getUser().getPhno())) {
                                HBox hBox = new HBox();
                                hBox.setAlignment(Pos.BASELINE_RIGHT);
                                Label labelNewMessage = new Label();
                                labelNewMessage.setText(message.getMessage());
                                labelNewMessage.setAlignment(Pos.CENTER);

                                labelNewMessage.setStyle("-fx-font-family:"+message.getMessageSettings().getFontStyle());
                                labelNewMessage.setStyle(labelNewMessage.getStyle()+"; -fx-font-size: "+message.getMessageSettings().getFontSize());
                                labelNewMessage.setStyle(labelNewMessage.getStyle()+"; -fx-text-fill: "+message.getMessageSettings().getFontColor());
                                if (message.getMessageSettings().isItalic())
                                    labelNewMessage.setStyle(labelNewMessage.getStyle()+ "; -fx-font-style: italic ");
                                if (message.getMessageSettings().isBold())
                                    labelNewMessage.setStyle(labelNewMessage.getStyle()+" ; -fx-font-weight: bold ");

                                ImageView senderImage=new ImageView();
                                senderImage.setImage(new Image(new ByteArrayInputStream(mainClass.getUser().getImage())));
                                senderImage.setFitWidth(20);
                                senderImage.setFitHeight(20);
                                hBox.getChildren().add(labelNewMessage);
                                hBox.getChildren().add(senderImage);
                                setGraphic(hBox);
                            }else
                            {
                                HBox hBox = new HBox();
                                hBox.setAlignment(Pos.BASELINE_LEFT);
                                Label labelNewMessage = new Label();
                                labelNewMessage.setText(message.getMessage());
                                labelNewMessage.setAlignment(Pos.CENTER);

                                labelNewMessage.setStyle("; -fx-font-family:"+message.getMessageSettings().getFontStyle());
                                labelNewMessage.setStyle(labelNewMessage.getStyle()+"; -fx-font-size: "+message.getMessageSettings().getFontSize());
                                labelNewMessage.setStyle(labelNewMessage.getStyle()+"; -fx-text-fill: "+message.getMessageSettings().getFontColor());
                                if (message.getMessageSettings().isItalic())
                                    labelNewMessage.setStyle(labelNewMessage.getStyle()+ "; -fx-font-style: italic ");
                                if (message.getMessageSettings().isBold())
                                    labelNewMessage.setStyle(labelNewMessage.getStyle()+" ; -fx-font-weight: bold");


                                ImageView senderImage=new ImageView();
                                senderImage.setImage(new Image(new ByteArrayInputStream(message.getSenderUser().getImage())));
                                senderImage.setFitWidth(20);
                                senderImage.setFitHeight(20);
                                hBox.getChildren().add(senderImage);
                                hBox.getChildren().add(labelNewMessage);

                                Platform.runLater(() -> {
                                    setGraphic(hBox);
                                });
                            }
                        }
                    }
                };
            }
        });
        friendsList.setOnMouseClicked(this::startChatingWith);
        ObservableList<String> fontsObservableList = FXCollections.observableArrayList();
        fontsObservableList.addAll("Consolas","Ebrima","Bauhaus 93");
        fontsCB.setItems(fontsObservableList);
        fontsCB.getSelectionModel().select(0);
        ObservableList<Integer> fontSizeObservableList = FXCollections.observableArrayList();
        fontSizeObservableList.addAll(5,10,15,20,25,30,35,40,45,50);
        sizeCB.setItems(fontSizeObservableList);
        sizeCB.getSelectionModel().select(0);
        colorPicker.setOnAction((event) -> {

            selectedColor = "#" + Integer.toHexString(colorPicker.getValue().hashCode());
            newMessageTF.setStyle(newMessageTF.getStyle()+"-fx-text-fill: "+selectedColor+";");
        });
        fontsCB.setOnAction((event) -> {
            String selectedFont = fontsCB.getValue().toString();
            newMessageTF.setStyle(newMessageTF.getStyle()+"-fx-font-family:"+selectedFont);
        });
        sizeCB.setOnAction((event) -> {
            String size = sizeCB.getValue().toString();
            newMessageTF.setStyle(newMessageTF.getStyle()+"-fx-font-size: "+size+";");
            System.out.println(size);
        });
        boldBtn.setOnAction((event) -> {
            if(boldBtn.isSelected())
                newMessageTF.setStyle(newMessageTF.getStyle()+"-fx-font-weight:bold;");
            else
                newMessageTF.setStyle(newMessageTF.getStyle()+"-fx-font-weight:normal;");
        });
        italicBtn.setOnAction((event) -> {
            System.out.println("Hello");
            if(italicBtn.isSelected())
                newMessageTF.setStyle(newMessageTF.getStyle()+"-fx-font-style:italic;");
            else
                newMessageTF.setStyle(newMessageTF.getStyle()+"-fx-font-style:normal;");
        });
        underlineBtn.setOnAction((event) -> {
            if(underlineBtn.isSelected())
                newMessageTF.setStyle(newMessageTF.getStyle()+"-fx-underline: true ;");
            else
                newMessageTF.setStyle(newMessageTF.getStyle()+"-fx-underline: false ;");
        });
        userNameLable.setText(mainClass.getUser().getFirstName()+" "+mainClass.getUser().getLastName());

        createGroupIcon.setOnMousePressed(this::createGroupChat);
        getUserContact();


        saveChat.setOnMouseClicked(event -> {
            File file=null;
            File xslFile=new File("F:\\ChatSession.xsl");
            File htmlFile;
            htmlFile=new File("F:\\test.html");
            file=saveChatOnXML();
            if(file!=null)
                xsl(file.getAbsolutePath(),  htmlFile.getAbsolutePath(),xslFile.getAbsolutePath() );
        });

        mainClass.getStage().setTitle("Chat Window");

    }

    private static String getResourcesPath(String file) {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath+"\\"+file;
    }

    private void createGroupChat(MouseEvent mouseEvent) {
        Stage stage = new Stage();
        Parent root = ParentFactory.getAddGroupChatWindowRoot(mainClass, stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            stage.close();
        });
    }

    private MessageSettings getMessageSetting() {
        MessageSettings messageSettings=new MessageSettings();
        String size = sizeCB.getValue().toString();
        messageSettings.setFontSize(Float.parseFloat(size));

        String selectedFont = fontsCB.getValue().toString();
        messageSettings.setFontStyle(selectedFont);

        selectedColor = "#" + Integer.toHexString(colorPicker.getValue().hashCode());
        messageSettings.setFontColor(selectedColor);

        if(underlineBtn.isSelected())
            messageSettings.setUnderline(true);
        else
            messageSettings.setUnderline(false);


        if(botBtn.isSelected())
            messageSettings.setBold(true);
        else
            messageSettings.setBold(false);


        if(italicBtn.isSelected())
            messageSettings.setItalic(true);
        else
            messageSettings.setItalic(false);

        selectedColor = "#" + Integer.toHexString(backGround.getValue().hashCode());
        messageSettings.setTextBackground(selectedColor);
        return messageSettings;
    }

    private void startChatingWith(MouseEvent mouseEvent) {
        otherContact= (User) friendsList.getSelectionModel().getSelectedItem();
        try {
            chatID= mainClass.getServerServiceLocator().getChatService().startChating(mainClass.getUser(),otherContact);
            if(!chatSession.containsValue(chatID)){

                ObservableList<EntityMessage> messages=FXCollections.observableArrayList();
                messages.addAll(mainClass.getServerServiceLocator().getChatService().getMessages(chatID,mainClass.getUser()));
                chatSession.put(chatID,messages);
                if (otherContact.getStatus().equals("offline")){
                    newMessageTF.setDisable(true);
                }
                else
                    newMessageTF.setDisable(false);


            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }finally {
            chatList.refresh();
            chatList.setItems(chatSession.get(chatID));
        }
        Platform.runLater(() ->chatList.scrollTo(chatSession.get(chatID).size()-1));

    }

    public void sendNewMessgae(String newMessage){
        newMessage=newMessage.trim();
        EntityMessage entityMessage=new EntityMessage();
        entityMessage.setMessage(newMessage);
        entityMessage.setSenderUser(mainClass.getUser());
        entityMessage.setId(chatID);
        MessageSettings messageSettings=getMessageSetting();
        entityMessage.setMessageSettings(messageSettings);
        chatSession.get(chatID).add(entityMessage);
        if (!entityMessage.getMessage().equals("")) {
            Platform.runLater(() -> {
                try {
                    mainClass.getServerServiceLocator().getChatService().sendMessage(entityMessage, otherContact.getPhno());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }

        newMessageTF.clear();
    }



    public void addNewMessage(EntityMessage message){
        message.setMessage(message.getMessage().trim());

        if(!chatID.equals("")&&chatID.equals(message.getId())) {
            chatSession.get(chatID).add(message);
            if(chatBotFlag){
                ChatBotImplementation chatBotImplementation = new ChatBotImplementation();
                String response = chatBotImplementation.responseBot(message.getMessage());
                sendNewMessgae(response);
            }
        }
        else {
            Platform.runLater(() -> {
                Media sound = new Media(new File(getrealURL()).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                Notifications.create().darkStyle().title("Message").text(message.getSenderUser().getFirstName()+" send a message").showInformation();
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

    private void startChat(ActionEvent actionEvent){
        MessageSettings messageSettings = new MessageSettings();
        if(chatID.equals(""))
        {
            Platform.runLater(() ->mainClass.showMessageWarning("Please Select a friend to chat with"));
        }
        else if(newMessageTF.getText().equals(""))
        {
            Platform.runLater(() ->mainClass.showMessageWarning("Please Enter a Message to send"));
        }
        else {
            selectedColor = "#" + Integer.toHexString(colorPicker.getValue().hashCode());
            messageSettings.setFontColor(selectedColor);
            Font selectedFont = Font.font(fontsCB.getValue().toString());
            messageSettings.setFontStyle(selectedFont.toString());
            String size = sizeCB.getValue().toString();
            messageSettings.setFontSize(Integer.parseInt(size));
            if(boldBtn.isSelected())
                messageSettings.setBold(true);
            if(italicBtn.isSelected())
                messageSettings.setItalic(true);
            if(underlineBtn.isSelected())
                messageSettings.setUnderline(true);
            String messageText=newMessageTF.getText();
            sendNewMessgae(newMessageTF.getText());
            Platform.runLater(() ->chatList.scrollTo(chatSession.get(chatID).size()-1));
        }
    }

    private void getUserContact() {
        try {
            userFriends = FXCollections.observableArrayList();
            userFriends.addAll( mainClass.getServerServiceLocator().getUpdateService().getUserContacts(mainClass.getUser().getPhno()));
            friendsList.setItems(userFriends);
        } catch (RemoteException e) {
            alertMessageException("sorry error occured in server");
        }
    }

    public void addOnlineContact(User newUser){
        Platform.runLater(() -> {
            for (int i=0;i<userFriends.size();i++)
            {
                if (userFriends.get(i).getPhno().equals(newUser.getPhno())){
                    User user=userFriends.get(i);
                    userFriends.remove(i);
                    user.setStatus("onlineAvailable");
                    userFriends.add(user);
                }
            }
        });

    }

    public void removeOnlineContact(User newUser){
        Platform.runLater(() -> {

            for (int i=0;i<userFriends.size();i++)
            {
                if (userFriends.get(i).getPhno().equals(newUser.getPhno())){
                    User user=userFriends.get(i);
                    userFriends.remove(i);
                    user.setStatus("offline");
                    userFriends.add(user);
                }
            }

        });

    }

    public void changedNewClientPasswordDone() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("password Changed");
        alert.setContentText("Thanks for change your password");
        alert.showAndWait();
    }

    private void enableChatBot(ActionEvent actionEvent) {
        System.out.println("Bot Open");
        if (!chatBotFlag) {
            chatBotFlag = true;
            newMessageTF.setDisable(true);
        } else {
            chatBotFlag = false;
            newMessageTF.setDisable(false);

        }
    }


    private void chooseFile(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            checkForFriendAcceptanceFile(selectedFile);
        } else {
            mainClass.showMessageWarning("please choose file");
        }
    }

    private void fileTransfer(File selectedFile, EntityMessage entityMessage, String path) {
        try {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Runnable sendFileService = () -> {
                FileInputStream fileInputStream = null;
                try {
                    File file = new File(selectedFile.toString());
                    fileInputStream = new FileInputStream(file);
                    byte[] mydata = new byte[1024 * 1024];
                    int myLength = fileInputStream.read(mydata);
                    entityMessage.setFileLenght(myLength);
                    entityMessage.setFileData(mydata);
                    while (myLength > 0) {
                        mainClass.getServerServiceLocator().getChatService().sendFileTransfer(entityMessage, otherContact.getPhno(),path);
                        myLength = fileInputStream.read(mydata);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        fileInputStream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };
            executorService.submit(sendFileService);
            executorService.shutdown();

        } catch (Exception e) {
            alertMessageException("sorry error occured in server");

        }
    }


    private void checkForFriendAcceptanceFile(File file) {
        EntityMessage entityMessage = new EntityMessage();
        entityMessage.setMessage(file.getName());
        entityMessage.setSenderUser(mainClass.getUser());
        entityMessage.setId(chatID);
        entityMessage.setFileName(file.getName());
        try {
            String path=mainClass.getServerServiceLocator().getChatService().friendAcceptanceFile(entityMessage, otherContact.getPhno());
            fileTransfer(file,entityMessage,path);
        } catch (RemoteException ex) {
            alertMessageException("sorry error occured in server");
        } catch (RejectFileException e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Refuse File Invitation");
                alert.setHeaderText("OOps");
                alert.setContentText("sorry your friend "+otherContact.getFirstName()+"refuse your file");
                alert.showAndWait();

            });
        }
    }


    public String confirmFile(EntityMessage entityMessage) throws RejectFileException {

        AtomicBoolean rejectInvitationFlag = new AtomicBoolean(false);
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(entityMessage.getSenderUser().getFirstName() +" want send "+entityMessage.getFileName()+" for you");
            alert.setContentText("Are you ok with this?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() != ButtonType.OK)
                rejectInvitationFlag.set(true);
        });
        if (rejectInvitationFlag.get()){
            throw new RejectFileException("Sorry "+mainClass.getUser().getFirstName()+" reject Your File");
        }
        String home = System.getProperty("user.home");
        return home+"/Downloads/";
    }
    public void updateSttaus(User user){
        Platform.runLater(() -> {

            for (int i=0;i<userFriends.size();i++)
            {
                if (userFriends.get(i).getPhno().equals(user.getPhno())){
                    // userFriends.get(i).setStatus(user.getStatus());
                    User userUpdate=userFriends.get(i);
                    userFriends.remove(i);
                    userUpdate.setStatus(user.getStatus());
                    userFriends.add(userUpdate);

                }
            }
        });

    }


    public File saveChatOnXML() {
        File file = null;
        if((chatID.equals(""))){


            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please choose a chat session");
            alert.show();
        }
        else{
            file = new File("F:\\test.xml");
            if(file != null){
                try {
                    //GET users and messages
                    //chatSession (id and message)
                    //otherContact and mainclass.getuser
                    //save in xml
                    JAXBContext context = JAXBContext.newInstance("com.iti.client.model.xml");
                    ObjectFactory factory = new ObjectFactory();
                    ChatSession chatSessionXML = factory.createChatSession();
                    if(!(chatID.equals(""))){
                        chatSessionXML.setID(BigInteger.valueOf(Integer.parseInt(chatID)));
                    }

                    List<MessageType> messages = chatSessionXML.getMessage();
                    List<EntityMessage> messagesList = chatSession.get(chatID);
                    for (int i = 0; i < messagesList.size(); i++) {
                        MessageType message = factory.createMessageType();
                        message.setMessageContent(messagesList.get(i).getMessage());
                        GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
                        if(messagesList.get(i).getMessageTimeStamp()!= null){
                            cal.setTime(messagesList.get(i).getMessageTimeStamp());

                            XMLGregorianCalendar xMLGregorianCalendar = DatatypeFactoryImpl.newInstance().newXMLGregorianCalendar(cal);
                        }

                        //time
                        //cal.setMinute(setTime(messagesList.get(i).getMessageTimeStamp()));
                        // message.setMessagetimeStamp(xMLGregorianCalendar);
                        // sender name
                        User user=messagesList.get(i).getSenderUser();
                        UserType userType=factory.createUserType();
                        userType.setFirstName(user.getFirstName());
                        userType.setLastName(user.getLastName());
                        message.setSender(userType);
                        // message.setSender();
                        //create message settings
                        MessageSettingsType messageSettings = factory.createMessageSettingsType();
                        messageSettings.setBold(messagesList.get(i).getMessageSettings().isBold());
                        messageSettings.setColor(messagesList.get(i).getMessageSettings().getFontColor());
                        messageSettings.setFontSize(messagesList.get(i).getMessageSettings().getFontSize());
                        messageSettings.setFontStyle(messagesList.get(i).getMessageSettings().getFontStyle());
                        messageSettings.setItalic(messagesList.get(i).getMessageSettings().isItalic());
                        messageSettings.setTextBackground(messagesList.get(i).getMessageSettings().getTextBackground());
                        messageSettings.setUnderline(messagesList.get(i).getMessageSettings().isUnderline());

                        message.setMessageSettings(messageSettings);
                        messages.add(message);
                    }
                    //save in xml

                    Marshaller marsh = context.createMarshaller();
                    marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    marsh.marshal(chatSessionXML, file);

                } catch (JAXBException ex) {
                    Logger.getLogger(ChatWindowController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DatatypeConfigurationException ex) {
                    Logger.getLogger(ChatWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return file;
    }
    public static void xsl(String inFilename, String outFilename, String xslFilename) {
        try {
            // Create transformer factory
            TransformerFactory factory = TransformerFactory.newInstance();

            // Use the factory to create a template containing the xsl file
            Templates template = factory.newTemplates(new StreamSource(
                    new FileInputStream(xslFilename)));

            // Use the template to create a transformer
            Transformer xformer = template.newTransformer();

            // Prepare the input and output files
            Source source = new StreamSource(new FileInputStream(inFilename));
            Result result = new StreamResult(new FileOutputStream(outFilename));

            // Apply the xsl file to the source file and write the result
            // to the output file
            xformer.transform(source, result);
        } catch (FileNotFoundException e) {
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            // An error occurred while applying the XSL file
            // Get location of error in input file
            e.printStackTrace();
            SourceLocator locator = e.getLocator();
            int col = locator.getColumnNumber();
            int line = locator.getLineNumber();
            String publicId = locator.getPublicId();
            String systemId = locator.getSystemId();
        }
    }

}
