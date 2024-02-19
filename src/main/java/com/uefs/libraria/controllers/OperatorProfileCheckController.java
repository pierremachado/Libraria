package com.uefs.libraria.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class OperatorProfileCheckController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView accountIcon;

    @FXML
    private Label accountTypeLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Button editButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    void cancelProfileCheck(ActionEvent event) {
        AdministratorHomeController.administratorHomeController.cancelRightPaneOperation();
    }

    @FXML
    void editOperatorProfile(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert accountIcon != null : "fx:id=\"accountIcon\" was not injected: check your FXML file 'OperatorProfileCheck.fxml'.";
        assert accountTypeLabel != null : "fx:id=\"accountTypeLabel\" was not injected: check your FXML file 'OperatorProfileCheck.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'OperatorProfileCheck.fxml'.";
        assert editButton != null : "fx:id=\"editButton\" was not injected: check your FXML file 'OperatorProfileCheck.fxml'.";
        assert nameLabel != null : "fx:id=\"nameLabel\" was not injected: check your FXML file 'OperatorProfileCheck.fxml'.";
        assert surnameLabel != null : "fx:id=\"surnameLabel\" was not injected: check your FXML file 'OperatorProfileCheck.fxml'.";
        assert usernameLabel != null : "fx:id=\"usernameLabel\" was not injected: check your FXML file 'OperatorProfileCheck.fxml'.";

    }

}
