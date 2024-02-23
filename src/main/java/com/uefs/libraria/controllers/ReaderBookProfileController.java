package com.uefs.libraria.controllers;

import com.uefs.libraria.dao.DAO;
import com.uefs.libraria.exceptions.*;
import com.uefs.libraria.model.Reader;
import com.uefs.libraria.model.Reservation;
import com.uefs.libraria.model.enums.ReservationStatus;
import com.uefs.libraria.services.BookService;
import com.uefs.libraria.services.LoginService;
import com.uefs.libraria.services.ReservationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.uefs.libraria.controllers.MainWindowController.wipeSelections;

public class ReaderBookProfileController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label amountAvailable;

    @FXML
    private Button cancelButton;

    @FXML
    private Label lvAnoPubli;

    @FXML
    private Label lvAutor;

    @FXML
    private Label lvCategoria;

    @FXML
    private Label lvEditora;

    @FXML
    private Label lvISBN;

    @FXML
    private ImageView lvImagem;

    @FXML
    private Label lvTitulo;

    @FXML
    private Label errorWarningLabel;

    @FXML
    private Button reserveBookButton;

    @FXML
    void cancelAction(ActionEvent event) {
        ReaderHomeController.readerHomeController.closeRightPaneOperation();
        wipeSelections();
    }

    @FXML
    void reserveBookAction(){
        try {
            ReservationService.criarReserva(BookService.getSelectedBook());
        } catch (NotEnoughPermissionException e) {
            throw new RuntimeException(e);
        } catch (ReservationException e) {
            errorWarningLabel.setText("Você tem reservas demais. Máximo: 3");
            return;
        } catch (UserIsBlockedException e) {
            errorWarningLabel.setText("Você está bloqueado. Consulte um administrador do sistema.");
            return;
        }

        wipeSelections();
        MainWindowController.mainWindowController.callReaderHomeScreen();
    }

    @FXML
    void initialize() {
        reserveBookButton.setDisable(false);
        errorWarningLabel.setText(null);

        lvTitulo.setText(BookService.getSelectedBook().getTitulo());
        lvAutor.setText(BookService.getSelectedBook().getAutor());
        lvEditora.setText(BookService.getSelectedBook().getEditora());
        lvISBN.setText(BookService.getSelectedBook().getIsbn());
        lvCategoria.setText(BookService.getSelectedBook().getCategoria());
        lvAnoPubli.setText(BookService.getSelectedBook().getAnoPublicacao().toString());
        amountAvailable.setText(Integer.valueOf(BookService.getSelectedBook().getQuantidadeDisponiveis()).toString());

        List<Reservation> reservationList = ReservationService.pesquisarReservaPorLeitor((Reader) LoginService.getCurrentLoggedUser());for (Reservation reservation : reservationList){
            if (Objects.equals(reservation.getIdLivro(), BookService.getSelectedBook().getIsbn()) && reservation.getStatus() != ReservationStatus.CANCELADO){
                reserveBookButton.setDisable(true);
            }
        }
    }

}
