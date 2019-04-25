package com.iti.client.view.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.client.view.MainClass;
import com.iti.client.view.factory.DataFactory;
import com.iti.client.view.factory.ParentFactory;
import com.iti.client.view.validation.ValidationInterface;
import com.iti.client.view.validation.implementation.ValidationImplementation;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ResourceBundle;

import static com.iti.client.view.MainClass.alertMessageException;

/**
 * FXML Controller class
 *
 * @author Bishoy
 */
public class UpdateWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField phoneTF;
    @FXML
    private TextField emailTF;
    @FXML
    private PasswordField passwordTF;
    @FXML
    private PasswordField confirmationTF;
    @FXML
    private TextField lastNameTF;
    @FXML
    private TextField firstNameTF;
    @FXML
    private Button confirmBtn;
    @FXML
    private ComboBox genderCB;
    @FXML
    private ComboBox countryCB;
    @FXML
    private TextField bioTA;
    @FXML
    private Button cancelBtn;
    @FXML
    private ImageView profileImage;
    @FXML
    private Button uploadImageBtn;

    byte[] imageByte;
    ObservableList<String> countries;
    ObservableList<String> gender;
    MainClass mainClass;
    ValidationInterface validator = new ValidationImplementation();

    Stage stage;

    public Stage getStage() {
        return stage;
    }

    public UpdateWindowController(MainClass mainClass, Stage stage) {
        this.stage=stage;
        this.mainClass = mainClass;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainClass.getStage().setTitle("Update");
        User user = mainClass.getUser();
        ReadOnlyStringWrapper phoneNumber = new ReadOnlyStringWrapper(user.getPhno());
        imageByte = user.getImage();
        phoneTF.textProperty().bind(phoneNumber);
        countries= DataFactory.getCountriesList();
        countryCB.setItems(countries);
        gender= DataFactory.getgendersList();
        genderCB.setItems(gender);
        emailTF.setText(user.getEmail());
        firstNameTF.setText(user.getFirstName());
        lastNameTF.setText(user.getLastName());
        countryCB.setValue(user.getCountry());
        genderCB.setValue(user.getGender());
        profileImage.setImage(new Image(new ByteArrayInputStream(user.getImage())));
        uploadImageBtn.setOnAction(this::changePhoto);
        confirmBtn.setOnAction(this::updateUserData);
        cancelBtn.setOnAction(this::returnToHome);
        
    }
    private void changePhoto(ActionEvent actionEvent){
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Select Image");
        File chosenFile=fileChooser.showOpenDialog(mainClass.getStage());
        if(chosenFile!=null){
            try {
                FileInputStream fileInputStream=new FileInputStream(chosenFile);
                imageByte=new byte[(int) chosenFile.length()];
                fileInputStream.read(imageByte);
                profileImage.setImage(new Image(new ByteArrayInputStream(imageByte)));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void updateUserData(ActionEvent actionEvent) {
        
        User user = mainClass.getUser();
        user.setPhno(phoneTF.getText());
        user.setFirstName(firstNameTF.getText());
        user.setLastName(lastNameTF.getText());
        user.setEmail(emailTF.getText());
        user.setPassword(passwordTF.getText());
        user.setImage(imageByte);
        user.setStatus("onlineAvailable");
        if(countryCB.getValue()==null)
            user.setCountry("");
        else
            user.setCountry(countryCB.getValue().toString());

        if(genderCB.getValue()==null)
            user.setGender("");
        else
        user.setGender(genderCB.getValue().toString());
        java.sql.Date gettedDatePickerDate=java.sql.Date.valueOf(datePicker.getValue());
        user.setDateBirth(gettedDatePickerDate);
        user.setBio(bioTA.getText());
        if(validator.validate(user, confirmationTF.getText() )){
            try {
                mainClass.getServerServiceLocator().getUpdateService().updateUserInformation(user);
                stage.close();
                mainClass.getStage().show();
                //todo Update Parent Window Image;
            } catch (RemoteException e) {
                alertMessageException("sorry error occurred in server");
            }
        }
    }
    
    private void returnToHome(ActionEvent actionEvent) {


        stage.close();
        mainClass.getStage().show();
    }
}