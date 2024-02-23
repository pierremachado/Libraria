package com.uefs.libraria.controllers;

import com.uefs.libraria.exceptions.LoanException;
import com.uefs.libraria.exceptions.NotEnoughPermissionException;
import com.uefs.libraria.model.enums.LoanStatus;
import com.uefs.libraria.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static com.uefs.libraria.controllers.MainWindowController.wipeSelections;

public class ReaderLoanProfileController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label bookName;

    @FXML
    private Button cancelLoanButton;

    @FXML
    private Button closeWindowButton;

    @FXML
    private Button finishLoanButton;

    @FXML
    private ImageView lvImagem;

    @FXML
    private Label readerName;

    @FXML
    private Label renovationLabel;

    @FXML
    private Label loanDate;

    @FXML
    private Label loanLimit;

    @FXML
    private Label loanReturnDate;
    @FXML
    private Label RenovationLabel;

    @FXML
    private Label loanStatus;

    @FXML
    private Button renewLoanButton;

    @FXML
    private void renewLoanAction(ActionEvent event){
        try {
            LendingService.renovarEmprestimo(LendingService.getSelectedLoan());
        } catch (NotEnoughPermissionException e) {
            throw new RuntimeException(e);
        } catch (LoanException e) {
            throw new RuntimeException(e);
        }

        wipeSelections();
        MainWindowController.mainWindowController.callReaderHomeScreen();
    }

    @FXML
    void closeWindowAction(ActionEvent event) {
        ReaderHomeController.readerHomeController.closeRightPaneOperation();
    }

    @FXML
    void showBookProfile(MouseEvent event) {
        ReaderHomeController.readerHomeController.bookCheck();
    }

    @FXML
    void showReaderProfile(MouseEvent event) {
        ReaderHomeController.readerHomeController.profileCheck();
    }

    @FXML
    void initialize() {

        BookService.setSelectedBook(BookService.pesquisarLivroPorIsbn(LendingService
                .getSelectedLoan().getIdLivro()));

        bookName.setText(BookService.getSelectedBook().getTitulo());

        UserService.setSelectedUser(UserService.pesquisarUsuarioPorUsername(LendingService
                .getSelectedLoan().getIdLeitor()));

        readerName.setText(UserService.getSelectedUser().getNomeCompleto());

        loanDate.setText(LendingService.getSelectedLoan().getDataEmprestimo()
                .format(TimeService.getFormatter()));

        loanLimit.setText(LendingService.getSelectedLoan().getDataLimite()
                .format(TimeService.getFormatter()));

        if (LendingService.getSelectedLoan().getDataDeRetorno() == null){
            loanReturnDate.setText("Não retornado");
        } else{
            loanReturnDate.setText(LendingService.getSelectedLoan().getDataDeRetorno()
                    .format(TimeService.getFormatter()));
        }


        loanStatus.setText(LendingService.getSelectedLoan().getStatus().toString());

        if(LendingService.getSelectedLoan().getVezesRenovado() == 0){
            renovationLabel.setText("Não");
        } else{
            renewLoanButton.setDisable(true);
            renovationLabel.setText("Sim");
        }

    }

}
