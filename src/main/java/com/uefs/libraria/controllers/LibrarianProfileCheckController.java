package com.uefs.libraria.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class LibrarianProfileCheckController implements Initializable {

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
        LibrarianHomeController.librarianHomeController.closeRightPaneOperation();
        LibrarianHomeController.setCurrentSelectedUser(null);
    }

    @FXML
    void setOnProfile() {
        nameLabel.setText(LibrarianHomeController.getCurrentSelectedUser().getNome());
        surnameLabel.setText(LibrarianHomeController.getCurrentSelectedUser().getSobrenome());
        usernameLabel.setText(LibrarianHomeController.getCurrentSelectedUser().getId());
        accountTypeLabel.setText(LibrarianHomeController.getCurrentSelectedUser().getCargo());

        if (LibrarianHomeController.getCurrentSelectedUser().getEndereco() == null ||
                LibrarianHomeController.getCurrentSelectedUser().getEndereco().isEmpty()) {
            addressLabel.setText("Nada informado.");
        }
        else {
            addressLabel.setText(LibrarianHomeController.getCurrentSelectedUser().getEndereco());
        }

        if (LibrarianHomeController.getCurrentSelectedUser().getTelefone() == null ||
                LibrarianHomeController.getCurrentSelectedUser().getTelefone().isEmpty()) {
            phoneLabel.setText("Nada informado.");
        }
        else {
            phoneLabel.setText(LibrarianHomeController.getCurrentSelectedUser().getTelefone());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOnProfile();
    }
}
