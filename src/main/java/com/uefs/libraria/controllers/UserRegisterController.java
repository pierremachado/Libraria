package com.uefs.libraria.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static com.uefs.libraria.controllers.AdministratorHomeController.administratorHomeController;

public class UserRegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<?> accountTypeSelect;

    @FXML
    private Button addButton;

    @FXML
    private TextField addressField;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField dddField;

    @FXML
    private Label errorWarningLabel;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField usernameField;

    @FXML
    void addUser(ActionEvent event) {

    }

    @FXML
    void cancelRegister(ActionEvent event) {
        administratorHomeController.cancelRightPaneOperation();
    }

    @FXML
    void initialize() {
        assert accountTypeSelect != null : "fx:id=\"accountTypeSelect\" was not injected: check your FXML file 'UserRegister.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'UserRegister.fxml'.";
        assert addressField != null : "fx:id=\"addressField\" was not injected: check your FXML file 'UserRegister.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'UserRegister.fxml'.";
        assert dddField != null : "fx:id=\"dddField\" was not injected: check your FXML file 'UserRegister.fxml'.";
        assert errorWarningLabel != null : "fx:id=\"errorWarningLabel\" was not injected: check your FXML file 'UserRegister.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'UserRegister.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'UserRegister.fxml'.";
        assert phoneNumberField != null : "fx:id=\"phoneNumberField\" was not injected: check your FXML file 'UserRegister.fxml'.";
        assert surnameField != null : "fx:id=\"surnameField\" was not injected: check your FXML file 'UserRegister.fxml'.";
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'UserRegister.fxml'.";

    }

}
