package com.uefs.libraria.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class OperatorProfileCheckController implements Initializable {

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
    private Label addressLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    void cancelProfileCheck(ActionEvent event) {
        AdministratorHomeController.administratorHomeController.closeRightPaneOperation();
        AdministratorHomeController.setCurrentSelectedUser(null);
    }

    @FXML
    void editOperatorProfile(ActionEvent event) {

    }

    @FXML
    void setOnProfile() {
        nameLabel.setText(AdministratorHomeController.getCurrentSelectedUser().getNome());
        surnameLabel.setText(AdministratorHomeController.getCurrentSelectedUser().getSobrenome());
        usernameLabel.setText(AdministratorHomeController.getCurrentSelectedUser().getId());
        accountTypeLabel.setText(AdministratorHomeController.getCurrentSelectedUser().getCargo());

        if (AdministratorHomeController.getCurrentSelectedUser().getEndereco() == null ||
                AdministratorHomeController.getCurrentSelectedUser().getEndereco().isEmpty()) {
            addressLabel.setText("Nada informado.");
        }
        else {
            addressLabel.setText(AdministratorHomeController.getCurrentSelectedUser().getEndereco());
        }

        if (AdministratorHomeController.getCurrentSelectedUser().getTelefone() == null ||
                AdministratorHomeController.getCurrentSelectedUser().getTelefone().isEmpty()) {
            phoneLabel.setText("Nada informado.");
        }
        else {
            phoneLabel.setText(AdministratorHomeController.getCurrentSelectedUser().getTelefone());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOnProfile();
    }
}
