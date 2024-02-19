package com.uefs.libraria.controllers;

import com.uefs.libraria.services.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class AdministratorHomeController {

    private MainWindowController mainWindowController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label greetingLabel;

    @FXML
    private ImageView iconImage;

    @FXML
    private Button logoutButton;

    @FXML
    private Label usernameLabel;

    @FXML
    void initialize() {
        logoutButton.setOnAction(event -> handleLogout());

        greetingLabel.setText("Boas vindas," +
                "\n" +
                LoginService.getCurrentLoggedUser().getNome() +
                " " +
                LoginService.getCurrentLoggedUser().getSobrenome());

        usernameLabel.setText("@" + LoginService.getCurrentLoggedUser().getId());
    }

    private void handleLogout(){
        LoginService.logoff();
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }
}
