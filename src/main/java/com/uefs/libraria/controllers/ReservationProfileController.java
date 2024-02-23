package com.uefs.libraria.controllers;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.uefs.libraria.exceptions.LoanException;
import com.uefs.libraria.exceptions.NotEnoughPermissionException;
import com.uefs.libraria.exceptions.ReservationException;
import com.uefs.libraria.exceptions.UserIsBlockedException;
import com.uefs.libraria.model.Reader;
import com.uefs.libraria.model.enums.LoanStatus;
import com.uefs.libraria.model.enums.ReservationStatus;
import com.uefs.libraria.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import static com.uefs.libraria.controllers.MainWindowController.wipeSelections;
import static com.uefs.libraria.model.enums.UserPermission.LEITOR;

public class ReservationProfileController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label bookName;

    @FXML
    private Button cancelReservationButton;

    @FXML
    private Button loanButton;

    @FXML
    private Button closeWindowButton;

    @FXML
    private ImageView lvImagem;

    @FXML
    private Label readerName;

    @FXML
    private Label reservationDate;

    @FXML
    private Label reservationLimit;

    @FXML
    private Label reservationStatus;

    @FXML
    void cancelReservationAction(ActionEvent event) {
        try {
            ReservationService.cancelarReserva(ReservationService.getSelectedReservation());
            switch (LoginService.getCurrentLoggedUser().getPermissao()){
                case ADMINISTRADOR -> {
                    MainWindowController.mainWindowController.callAdministratorHomeScreen();
                }
                case BIBLIOTECARIO -> {
                    MainWindowController.mainWindowController.callLibrarianHomeScreen();
                }
                case LEITOR -> {
                    MainWindowController.mainWindowController.callReaderHomeScreen();
                }
            }
        } catch (NotEnoughPermissionException e) {
            throw new RuntimeException(e);
        } catch (ReservationException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void closeWindowAction(ActionEvent event) {
        switch (LoginService.getCurrentLoggedUser().getPermissao()){
            case ADMINISTRADOR -> {
                AdministratorHomeController.administratorHomeController.closeRightPaneOperation();
            }
            case BIBLIOTECARIO -> {
                LibrarianHomeController.librarianHomeController.closeRightPaneOperation();
            }
            case LEITOR -> {
                ReaderHomeController.readerHomeController.closeRightPaneOperation();
            }
        }
    }

    @FXML
    void createLoanAction(ActionEvent event) {
        try {
            LendingService.criarEmprestimo((Reader) UserService.getSelectedUser(), BookService.getSelectedBook());
            wipeSelections();

            switch (LoginService.getCurrentLoggedUser().getPermissao()){
                case ADMINISTRADOR -> {
                    MainWindowController.mainWindowController.callAdministratorHomeScreen();
                }
                case BIBLIOTECARIO -> {
                    MainWindowController.mainWindowController.callLibrarianHomeScreen();
                }
            }

        } catch (NotEnoughPermissionException e) {
            throw new RuntimeException(e);
        } catch (UserIsBlockedException e) {
            throw new RuntimeException(e);
        } catch (LoanException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void showBookProfile(MouseEvent event) {
        switch (LoginService.getCurrentLoggedUser().getPermissao()){
            case ADMINISTRADOR -> {
                AdministratorHomeController.administratorHomeController.bookCheck();
            }
            case BIBLIOTECARIO -> {
                LibrarianHomeController.librarianHomeController.bookCheck();
            }
            case LEITOR -> {
                ReaderHomeController.readerHomeController.bookCheck();
            }
            case CONVIDADO -> {
            }
        }
    }

    @FXML
    void showReaderProfile(MouseEvent event) {
        switch(LoginService.getCurrentLoggedUser().getPermissao()){
            case ADMINISTRADOR -> {
                AdministratorHomeController.administratorHomeController.profileCheck();
            }
            case BIBLIOTECARIO -> {
                LibrarianHomeController.librarianHomeController.profileCheck();
            }
            case LEITOR -> {
                ReaderHomeController.readerHomeController.profileCheck();
            }
            case CONVIDADO -> {
            }
        }
    }

    @FXML
    void initialize() {
        loanButton.setDisable(ReservationService.getSelectedReservation().getStatus() == ReservationStatus.CANCELADO ||
                ReservationService.getSelectedReservation().getStatus() == ReservationStatus.EMPRESTADO);

        loanButton.setVisible(!(LoginService.getCurrentLoggedUser().getPermissao() == LEITOR));

        cancelReservationButton.setDisable(ReservationService.getSelectedReservation().getStatus() == ReservationStatus.CANCELADO ||
                ReservationService.getSelectedReservation().getStatus() == ReservationStatus.EMPRESTADO);


        BookService.setSelectedBook(BookService.pesquisarLivroPorIsbn(ReservationService
                .getSelectedReservation().getIdLivro()));

        bookName.setText(BookService.getSelectedBook().getTitulo());

        UserService.setSelectedUser(UserService.pesquisarUsuarioPorUsername(ReservationService
                .getSelectedReservation().getIdLeitor()));

        readerName.setText(UserService.getSelectedUser().getNomeCompleto());

        reservationDate.setText(ReservationService.getSelectedReservation().getDataReservado()
                .format(TimeService.getFormatter()));
        reservationLimit.setText(ReservationService.getSelectedReservation().getDataLimite()
                .format(TimeService.getFormatter()));
        reservationStatus.setText(ReservationService.getSelectedReservation().getStatus().toString());
    }

}
