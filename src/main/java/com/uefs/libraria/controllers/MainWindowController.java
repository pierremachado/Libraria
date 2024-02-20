package com.uefs.libraria.controllers;

import com.uefs.libraria.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    public static MainWindowController mainWindowController;

    @FXML
    private BorderPane mainBorderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainWindowController = this;

        try {
            callLoginScreen();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void callLoginScreen(){
        this.refreshMainWindow("/com/uefs/libraria/Login.fxml");
    }

    @FXML
    public void callAdministratorHomeScreen(){
        this.refreshMainWindow("/com/uefs/libraria/AdministratorHome.fxml");
    }

    @FXML
    public void callLibrarianHomeScreen() {this.refreshMainWindow("/com/uefs/libraria/LibrarianHome.fxml");}

    @FXML
    public void callReaderHomeScreen() {this.refreshMainWindow("/com/uefs/libraria/ReaderHome.fxml");}

    public void refreshMainWindow(String url){
        this.mainBorderPane.setCenter(openPage(url));
    }

    public static Parent openPage(String url){
        Parent root = null;
        try{
            root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(url)));
        } catch(Exception e){
            e.printStackTrace();
        }
        return root;
    }
}