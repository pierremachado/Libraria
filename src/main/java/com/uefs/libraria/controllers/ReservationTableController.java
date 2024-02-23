package com.uefs.libraria.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.uefs.libraria.dao.DAO;
import com.uefs.libraria.exceptions.NotEnoughPermissionException;
import com.uefs.libraria.model.Book;
import com.uefs.libraria.model.Reader;
import com.uefs.libraria.model.Reservation;
import com.uefs.libraria.model.enums.ReservationStatus;
import com.uefs.libraria.model.enums.UserPermission;
import com.uefs.libraria.services.BookService;
import com.uefs.libraria.services.LoginService;
import com.uefs.libraria.services.ReservationService;
import com.uefs.libraria.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReservationTableController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private Label contentLabel;

    @FXML
    void initialize() {
        setReservationTable();

        reservationTable.setOnMouseClicked(MouseEvent -> {
            ReservationService.setSelectedReservation(reservationTable.getSelectionModel().getSelectedItem());

            switch (LoginService.getCurrentLoggedUser().getPermissao()) {
                case UserPermission.ADMINISTRADOR -> {
                    AdministratorHomeController.administratorHomeController.reservationCheck();
                }
                case UserPermission.BIBLIOTECARIO -> {
                    LibrarianHomeController.librarianHomeController.reservationCheck();
                }
                case LEITOR -> {
                    ReaderHomeController.readerHomeController.reservationCheck();
                }
                case CONVIDADO -> {
                }
            }
        });
    }

    private void setReservationTable() {
        ObservableList<Reservation> reservationData = null;

        switch(LoginService.getCurrentLoggedUser().getPermissao()) {
            case ADMINISTRADOR, BIBLIOTECARIO -> {
                if (UserService.getSearch() != null){
                    try {
                        reservationData = FXCollections.observableArrayList(ReservationService.pesquisarReservaPorId(UserService.getSearch()));
                    } catch (NotEnoughPermissionException e) {
                        return;
                    }
                }
                else{
                    reservationData = FXCollections.observableArrayList(DAO.getReservaDAO().findAll());
                }
            }
            case LEITOR -> {
                reservationData = FXCollections.observableArrayList(DAO.getReservaDAO().findLeitor(LoginService.getCurrentLoggedUser().getId()));
            }
        }

        createReservationTable(this.reservationTable, reservationData);
    }

    private void createReservationTable(TableView<Reservation> reservationTable, ObservableList<Reservation> reservationData){
        TableColumn id = new TableColumn("ID");

        TableColumn nameLeitor = new TableColumn("Leitor(a)");
        TableColumn idLeitor = new TableColumn("Nome de usuário");
        TableColumn nameLivro = new TableColumn("Livro");
        TableColumn idLivro = new TableColumn("ISBN");
        TableColumn status = new TableColumn("Status");
        TableColumn dataReservado = new TableColumn("Data de reserva");
        TableColumn dataLimite = new TableColumn("Data limite para empréstimo");


        id.setCellValueFactory(new PropertyValueFactory<Reservation, String>("idReserva"));
        idLeitor.setCellValueFactory(new PropertyValueFactory<Reservation, String>("idLeitor"));
        idLivro.setCellValueFactory(new PropertyValueFactory<Reservation, String>("idLivro"));
        status.setCellValueFactory(new PropertyValueFactory<Reservation, ReservationStatus>("status"));
        dataReservado.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDateTime>("dataReservado"));
        dataLimite.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDateTime>("dataLimite"));

        reservationTable.getColumns().setAll(id, idLeitor, idLivro, status, dataReservado, dataLimite);
        reservationTable.setItems(reservationData);
    }
}
