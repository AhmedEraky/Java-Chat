/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.client.view.controller;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ResourceBundle;
import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.RegistrationDuplicateException;
import com.iti.client.view.MainClass;
import com.iti.client.view.factory.DataFactory;
import com.iti.client.view.factory.ParentFactory;
import com.iti.client.view.validation.ValidationInterface;
import com.iti.client.view.validation.implementation.ValidationImplementation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

import static com.iti.client.view.MainClass.alertMessageException;


/**
 * FXML Controller class
 *
 * @author Mahmoud Eraky
 */
public class RegistrationWindowController implements Initializable {

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
    private Button signUpBtn;
    @FXML
    private ComboBox genderCB;
    @FXML
    private ComboBox countryCB;
    @FXML
    private TextField bioTA;
    @FXML
    private Button loginPageBtn;
    @FXML
    private ImageView profileImage;
    @FXML
    private Button uploadImageBtn;
    @FXML
    private DatePicker datePicker;

    FileInputStream fileInputStream;
    MainClass mainClass;
    byte[] imageByte;
    ObservableList<String> countries;
    ObservableList<String> genders;
    private ValidationInterface validator=new ValidationImplementation();


    public RegistrationWindowController(MainClass mainClass) {
        this.mainClass = mainClass;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        countries= DataFactory.getCountriesList();
        genders=DataFactory.getgendersList();
        countryCB.setItems(countries);
        genderCB.setItems(genders);
        mainClass.getStage().setTitle("Registration");

        try {
            URL imageURL = getClass().getResource("default_image.png");
            BufferedImage bImage = ImageIO.read(imageURL);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos );
            imageByte = bos.toByteArray();
            profileImage.setImage(new Image(new ByteArrayInputStream(imageByte)));

        } catch (IOException e) {
            e.printStackTrace();
        }
        signUpBtn.setOnAction(this::signUp);
        uploadImageBtn.setOnAction(this::uploadImage);
        loginPageBtn.setOnAction(this::openLoginPage);
    }

    private void openLoginPage(ActionEvent actionEvent) {
        Parent root= ParentFactory.getLoginWindowRoot(mainClass);
        mainClass.changeScene(root);
    }

    private void signUp(ActionEvent actionEvent) {
        User user=new User();
        user.setPhno(phoneTF.getText());
        user.setPhoto(imageByte);
        user.setFirstName(firstNameTF.getText());
        user.setLastName(lastNameTF.getText());
        user.setEmail(emailTF.getText());
        user.setPassword(passwordTF.getText());
        user.setStatus("onlineAvailable");
        if(countryCB.getValue()==null)
            user.setCountry("");
        else
            user.setCountry(countryCB.getValue().toString());

        if(genderCB.getValue()==null)
            user.setGender("");
        else
            user.setGender(genderCB.getValue().toString());


        if (datePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Invalid Date");
            alert.showAndWait();

        }
        else if(validator.validate(user, confirmationTF.getText() )){
            try {
                java.sql.Date gettedDatePickerDate=java.sql.Date.valueOf(datePicker.getValue());
                user.setDatBirth(gettedDatePickerDate);
                user.setBio(bioTA.getText());

                mainClass.getServerServiceLocator().getEnteranceService().registration(user);
                user.setClientInterface(mainClass.getClientServiceInterface());
                mainClass.setUser(user);
                Parent root=ParentFactory.getChatWindowRoot(mainClass);
                mainClass.changeScene(root);
            } catch (RemoteException e) {
                alertMessageException("sorry error occurred in server");
            } catch (RegistrationDuplicateException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }

        }
    }

    private void uploadImage(ActionEvent actionEvent) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Select Image");
        File chosenFile=fileChooser.showOpenDialog(mainClass.getStage());
        if(chosenFile!=null){
            try {
                fileInputStream=new FileInputStream(chosenFile);
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


}
