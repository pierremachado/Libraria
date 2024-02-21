package com.uefs.libraria.controllers;

import com.uefs.libraria.exceptions.NotEnoughPermissionException;
import com.uefs.libraria.services.BookService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class BookProfileController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label amountAvailable;

    @FXML
    private Button cancelButton;

    @FXML
    private Button editBookButton;

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
    private Button removeBookButton;

    @FXML
    void cancelAction(ActionEvent event) {
        AdministratorHomeController.administratorHomeController.closeRightPaneOperation();
        AdministratorHomeController.setCurrentSelectedBook(null);
    }

    @FXML
    void editBook(ActionEvent event) {
        AdministratorHomeController.administratorHomeController.openRightPanel("/com/uefs/libraria/BookProfileEdit.fxml");
    }

    @FXML
    void removeBook(ActionEvent event) {
        try {
            BookService.removerLivro(AdministratorHomeController.getCurrentSelectedBook());
            AdministratorHomeController.setCurrentSelectedBook(null);
            MainWindowController.mainWindowController.refreshMainWindow("/com/uefs/libraria/AdministratorHome.fxml");
        } catch (NotEnoughPermissionException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        lvTitulo.setText(AdministratorHomeController.getCurrentSelectedBook().getTitulo());
        lvAutor.setText(AdministratorHomeController.getCurrentSelectedBook().getAutor());
        lvEditora.setText(AdministratorHomeController.getCurrentSelectedBook().getEditora());
        lvISBN.setText(AdministratorHomeController.getCurrentSelectedBook().getIsbn());
        lvCategoria.setText(AdministratorHomeController.getCurrentSelectedBook().getCategoria());
        lvAnoPubli.setText(AdministratorHomeController.getCurrentSelectedBook().getAnoPublicacao().toString());
        amountAvailable.setText(Integer.valueOf(AdministratorHomeController.getCurrentSelectedBook().getQuantidadeDisponiveis()).toString());
    }

}
