package com.uefs.libraria.controllers;

import com.uefs.libraria.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.util.Objects;

public class MainWindowController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    public void initialize() throws Exception {
        this.openPage("/com/uefs/libraria/Login.fxml");
    }

    private void openPage(String url){
        Parent root = null;
        try{
            root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(url)));
            this.mainBorderPane.setCenter(root);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}