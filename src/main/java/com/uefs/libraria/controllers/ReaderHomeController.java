package com.uefs.libraria.controllers;

import com.uefs.libraria.services.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.uefs.libraria.controllers.MainWindowController.openPage;

public class ReaderHomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label greetingLabel;

    @FXML
    private ImageView home;

    @FXML
    private ImageView iconImage;

    @FXML
    private Button logoutButton;

    @FXML
    private Button reserveBook;

    @FXML
    private Button searchBookButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button selfProfileCheckButton;

    @FXML
    private Label usernameLabel;

    @FXML
    void goToHome(MouseEvent event) {
        this.searchTextField.clear();
    }

    @FXML
    void logoutAction(ActionEvent event) {
        LoginService.logoff();
        MainWindowController.mainWindowController.callLoginScreen();
    }

    @FXML
    void reserveBookAction(ActionEvent event) {
        //todo
    }

    @FXML
    void searchBookAction(ActionEvent event) {
        //todo
    }

    @FXML
    void selfProfileCheckAction(ActionEvent event) {
        //todo
    }

    @FXML
    void initialize() {
        greetingLabel.setText("Boas vindas," +
                " " +
                LoginService.getCurrentLoggedUser().getNome());

        usernameLabel.setText("@" + LoginService.getCurrentLoggedUser().getId());

        borderPane.setCenter(openPage("/com/uefs/libraria/ReaderLoanAndReservation.fxml"));
    }

}
