package com.uefs.libraria.controllers;

import com.uefs.libraria.services.LoginService;
import com.uefs.libraria.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.uefs.libraria.controllers.MainWindowController.openPage;
import static com.uefs.libraria.controllers.MainWindowController.wipeSelections;

public class GuestHomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button logoutButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;

    @FXML
    void goToHome(MouseEvent event) {
        wipeSelections();
        this.refreshCenterTable();
        this.closeRightPaneOperation();
        this.searchTextField.clear();
    }

    @FXML
    void logoutAction(ActionEvent event) {
        LoginService.logoff();
        MainWindowController.mainWindowController.callLoginScreen();
    }

    @FXML
    void searchBookAction(ActionEvent event) {
        UserService.setSearch(searchTextField.getText());
        borderPane.setCenter(openPage("/com/uefs/libraria/BookTable.fxml"));
    }

    @FXML
    void initialize() {
        borderPane.setCenter(openPage("/com/uefs/libraria/BookTable.fxml"));
    }

    public void closeRightPaneOperation(){
        wipeSelections();
        borderPane.setRight(null);
    }

    public void refreshCenterTable() {
        borderPane.setCenter(openPage("/com/uefs/libraria/BookTable.fxml"));
    }

}
