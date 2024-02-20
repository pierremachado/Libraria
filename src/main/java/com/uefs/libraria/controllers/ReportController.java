package com.uefs.libraria.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.uefs.libraria.exceptions.NotEnoughPermissionException;
import com.uefs.libraria.model.Book;
import com.uefs.libraria.model.Report;
import com.uefs.libraria.services.ReportService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import static java.lang.Integer.valueOf;

public class ReportController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button closeButton;

    @FXML
    private Label relatAtrasados;

    @FXML
    private Label relatEmprestados;

    @FXML
    private TableView<Book> relatPopulares;

    @FXML
    private Label relatReservados;

    @FXML
    void closeWindow(ActionEvent event) {
        AdministratorHomeController.administratorHomeController.closeRightPaneOperation();
    }

    @FXML
    void initialize() {
        Report report = null;

        try {
            report = ReportService.gerarRelatorio();
            relatEmprestados.setText(Integer.valueOf(report.getnLivrosEmprestados()).toString());
            relatAtrasados.setText(Integer.valueOf(report.getnLivrosAtrasados()).toString());
            relatReservados.setText(Integer.valueOf(report.getnLivrosReservados()).toString());

            BookTableController.createBookTable(relatPopulares, FXCollections.observableArrayList(report.getLivrosPopulares()));

        } catch (NotEnoughPermissionException e) {
            throw new RuntimeException(e);
        }
    }

}
