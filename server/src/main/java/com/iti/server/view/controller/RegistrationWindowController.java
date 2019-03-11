/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.server.view.controller;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.RegistrationDuplicateException;
import com.iti.server.admin.modal.AdminModel;
import com.iti.server.admin.modal.implementation.AdminModelImplementation;
import com.iti.server.view.MainClass;
import com.iti.server.view.factory.DataFactory;
import com.iti.server.view.factory.ParentFactory;
import com.iti.server.view.validation.ValidationInterface;
import com.iti.server.view.validation.implementation.ValidationImplementation;
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
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

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
    private Button backBtn;
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
    private ValidationInterface validator = new ValidationImplementation();

    public RegistrationWindowController(MainClass mainClass) {
        this.mainClass = mainClass;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        countries = DataFactory.getCountriesList();
        genders = DataFactory.getgendersList();
        countryCB.setItems(countries);
        genderCB.setItems(genders);
        try {
            URL imageURL = getClass().getResource("default_image.jpg");
            BufferedImage bImage = ImageIO.read(imageURL);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "jpg", bos );
            imageByte = bos.toByteArray();
            profileImage.setImage(new Image(new ByteArrayInputStream(imageByte)));

        } catch (IOException e) {
            e.printStackTrace();
        }
        signUpBtn.setOnAction(this::signUp);
        uploadImageBtn.setOnAction(this::uploadImage);
        backBtn.setOnAction(event->
        {
            Parent root = ParentFactory.getServerHomeWindowRoot(mainClass);
            mainClass.changeScene(root);
        });
    }

    private void signUp(ActionEvent actionEvent) {
        // no image wait for image
        User user = new User();
        user.setPhno(phoneTF.getText());
        user.setFirstName(firstNameTF.getText());
        user.setLastName(lastNameTF.getText());
        user.setEmail(emailTF.getText());
        user.setPassword(passwordTF.getText());
        user.setImage(imageByte);
        user.setStatus("onlineAvailable");
        if (countryCB.getValue() == null) {
            user.setCountry("");
        } else {
            user.setCountry(countryCB.getValue().toString());
        }

        if (genderCB.getValue() == null) {
            user.setGender("");
        } else {
            user.setGender(genderCB.getValue().toString());
        }


        //user.setDatBirth(new Date(1999, 11, 28));
        java.sql.Date gettedDatePickerDate=java.sql.Date.valueOf(datePicker.getValue());
        user.setDateBirth(gettedDatePickerDate);

        user.setBio(bioTA.getText());
        if (validator.validate(user, confirmationTF.getText())) {
            AdminModel adminModel = new AdminModelImplementation();
            try {
                adminModel.addClientByAdmin(user);
                alertMessage("User Added");
            } catch (RegistrationDuplicateException ex) {
                ex.printStackTrace();
                alertMessage("Oops Error Ocurred Duplicated Phone Number");
            }
        }
    }

    private void alertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(message);
        alert.showAndWait();
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
