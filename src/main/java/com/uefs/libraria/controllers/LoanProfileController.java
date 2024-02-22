package com.uefs.libraria.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.uefs.libraria.Main;
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

import static com.uefs.libraria.controllers.MainWindowController.wipeSelections;

public class LoanProfileController {

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
    void cancelLoanAction(ActionEvent event) {
        try {
            LendingService.cancelarEmprestimo(LendingService.getSelectedLoan(), false);
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
        } catch (LoanException e) {
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
        }
    }

    @FXML
    void finishLoanAction(ActionEvent event) {
        try {
            LendingService.confirmarRecebimentoEmprestimo(LendingService.getSelectedLoan());
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
            }
            case CONVIDADO -> {
            }
        }
    }

    @FXML
    void initialize() {
        cancelLoanButton.setDisable(LendingService.getSelectedLoan().getStatus() == LoanStatus.CANCELADO ||
                LendingService.getSelectedLoan().getStatus() == LoanStatus.CONCLUIDO);

        finishLoanButton.setDisable(LendingService.getSelectedLoan().getStatus() == LoanStatus.CANCELADO || LendingService.getSelectedLoan()
                .getStatus() == LoanStatus.CONCLUIDO);

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
            renovationLabel.setText("Sim");
        }

    }

}
