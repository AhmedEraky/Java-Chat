/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.server.view.controller;

import com.iti.ChatCommanServices.model.entity.user.User;
import com.iti.ChatCommanServices.model.exception.RegistrationDuplicateException;
import com.iti.server.admin.modal.implementation.AdminModelImplementation;
import com.iti.server.view.MainClass;
import com.iti.server.view.factory.ParentFactory;
import com.iti.server.view.validation.ValidationInterface;
import com.iti.server.view.validation.implementation.ValidationImplementation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pc
 */
public class DisplayUsersController implements Initializable {

    @FXML
    TextField phoneNumberTF;
    @FXML
    TextField firstNameTF;
    @FXML
    TextField lastNameTF;
    @FXML
    TextField emailTF;
    @FXML
    TextField genderTF;
    @FXML
    TextField passwordTF;
    @FXML
    TextField dateOFBirthTF;
    @FXML
    TextField bioTF;
    @FXML
    TextField statusTF;
    @FXML
    TextField countryTF;
    @FXML
    DatePicker datePicker;
    @FXML
    Button UpdateBtn;
    @FXML
    Button deleteBtn;

    @FXML
    TableView displayUsersTV;
    @FXML
    TableColumn<User, String> F_nameCol;
    @FXML
    TableColumn<User, String> L_nameCol;
    @FXML
    TableColumn<User, String> emailCol;
    @FXML
    TableColumn<User, String> passwordCol;
    @FXML
    TableColumn<User, String> sataesCol;
    @FXML
    TableColumn<User, String> countryCol;
    @FXML
    TableColumn<User, String> genderCol;
    @FXML
    TableColumn<User, String> dateOfBirthCol;
    @FXML
    TableColumn<User, String> bioCol;
    @FXML
    Button backBtn;

    AdminModelImplementation adminModelImplementation = new AdminModelImplementation();

    MainClass mainClass;
    private ValidationInterface validator = new ValidationImplementation();

    public DisplayUsersController(MainClass mainClass) {
        this.mainClass = mainClass;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updateUsersTable();

        displayUsersTV.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    UpdateBtn.setDisable(false);
                    deleteBtn.setDisable(false);
                    User userRow = row.getItem();
                    printRow(userRow);
                }
            });
            return row;
        });

        UpdateBtn.setOnAction((event) -> {
            AdminModelImplementation adminModelImplementation1 = new AdminModelImplementation();
            User user = new User();
            user.setPhno(phoneNumberTF.getText());
            user.setFirstName(firstNameTF.getText());
            user.setLastName(lastNameTF.getText());
            user.setEmail(emailTF.getText());
            user.setPassword(passwordTF.getText());
            user.setGender(genderTF.getText());
            user.setStatus(statusTF.getText());
            user.setBio(bioTF.getText());
            user.setCountry(countryTF.getText());
            user.setPhoto(null);

            if (datePicker.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Invalid Date");
                alert.showAndWait();

            } else {
                if (validator.validate(user, passwordTF.getText())) {
                    java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(datePicker.getValue());
                    user.setDatBirth(gettedDatePickerDate);
                    try {
                        adminModelImplementation.updateUser(user);
                        alertMessage("Data Updated");
                        updateUsersTable();

                    } catch (RegistrationDuplicateException ex) {
                        Logger.getLogger(DisplayUsersController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        deleteBtn.setOnAction((event) -> {
            AdminModelImplementation adminModelImplementation1 = new AdminModelImplementation();
            adminModelImplementation.deleteUser(phoneNumberTF.getText());
            alertMessage("Data Deleted");
            updateUsersTable();
        });
        backBtn.setOnAction(event->
        {
            Parent root = ParentFactory.getServerHomeWindowRoot(mainClass);
            mainClass.changeScene(root);
        });
    }

    private void alertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Done");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void printRow(User user) {
        phoneNumberTF.setText(user.getPhno());
        firstNameTF.setText(user.getFirstName());
        lastNameTF.setText(user.getLastName());
        emailTF.setText(user.getEmail());
        genderTF.setText(user.getGender());
        passwordTF.setText(user.getPassword());
        bioTF.setText(user.getBio());
        countryTF.setText(user.getCountry());
        statusTF.setText(user.getStatus());
    }

    private void updateUsersTable() {
        ArrayList<User> userList = adminModelImplementation.displayUsers();
        ObservableList<User>observableList = FXCollections.observableArrayList(userList);
        F_nameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        L_nameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        sataesCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("datBirth"));
        bioCol.setCellValueFactory(new PropertyValueFactory<>("bio"));
        displayUsersTV.setItems(observableList);
    }

}
