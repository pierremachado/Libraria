package com.uefs.libraria.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class ReaderLoanAndReservationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<?> interEmprest;

    @FXML
    private TableView<?> interReserva;

    @FXML
    void initialize() {
        assert interEmprest != null : "fx:id=\"interEmprest\" was not injected: check your FXML file 'ReaderLoanAndReservation.fxml'.";
        assert interReserva != null : "fx:id=\"interReserva\" was not injected: check your FXML file 'ReaderLoanAndReservation.fxml'.";

    }

}
