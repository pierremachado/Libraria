package com.uefs.libraria.controllers;

import com.uefs.libraria.services.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.uefs.libraria.controllers.MainWindowController.mainWindowController;
import static com.uefs.libraria.controllers.MainWindowController.openPage;

public class AdministratorHomeController implements Initializable {

    public static AdministratorHomeController administratorHomeController;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label greetingLabel;

    @FXML
    private ImageView iconImage;

    @FXML
    private Button searchButton;

    @FXML
    private Button addUserButton;

    @FXML
    private Button addBookButton;

    @FXML
    private Button generateReportButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label usernameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        administratorHomeController = this;
        this.setScreen();
    }

    @FXML
    void setScreen() {
        greetingLabel.setText("Boas vindas," +
                " " +
                LoginService.getCurrentLoggedUser().getNome());

        usernameLabel.setText("@" + LoginService.getCurrentLoggedUser().getId());

        addUserButton.setOnAction(actionEvent -> {addUser();});

        logoutButton.setOnAction(event -> handleLogout());
    }

    private void addUser(){
        borderPane.setRight(openPage("/com/uefs/libraria/userRegister.fxml"));
    }

    public void cancelAddOperation(){
        borderPane.setRight(null);
    }

    private void handleLogout(){
        LoginService.logoff();
        mainWindowController.callLoginScreen();
    }
}
