package com.uefs.libraria.controllers;

import com.uefs.libraria.services.UserService;
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
        UserService.setSelectedUser(null);
    }

    @FXML
    void setOnProfile() {
        nameLabel.setText(UserService.getSelectedUser().getNome());
        surnameLabel.setText(UserService.getSelectedUser().getSobrenome());
        usernameLabel.setText(UserService.getSelectedUser().getId());
        accountTypeLabel.setText(UserService.getSelectedUser().getCargo());

        if (UserService.getSelectedUser().getEndereco() == null ||
                UserService.getSelectedUser().getEndereco().isEmpty()) {
            addressLabel.setText("Nada informado.");
        }
        else {
            addressLabel.setText(UserService.getSelectedUser().getEndereco());
        }

        if (UserService.getSelectedUser().getTelefone() == null ||
                UserService.getSelectedUser().getTelefone().isEmpty()) {
            phoneLabel.setText("Nada informado.");
        }
        else {
            phoneLabel.setText(UserService.getSelectedUser().getTelefone());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOnProfile();
    }
}
