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

public class LibrarianBookProfileController {

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
        LibrarianHomeController.librarianHomeController.closeRightPaneOperation();
        LibrarianHomeController.setCurrentSelectedBook(null);
    }

    @FXML
    void editBook(ActionEvent event) {
        //todo
    }

    @FXML
    void removeBook(ActionEvent event) {
        try {
            BookService.removerLivro(LibrarianHomeController.getCurrentSelectedBook());
            LibrarianHomeController.setCurrentSelectedBook(null);
            MainWindowController.mainWindowController.refreshMainWindow("/com/uefs/libraria/LibrarianHome.fxml");
        } catch (NotEnoughPermissionException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        lvTitulo.setText(LibrarianHomeController.getCurrentSelectedBook().getTitulo());
        lvAutor.setText(LibrarianHomeController.getCurrentSelectedBook().getAutor());
        lvEditora.setText(LibrarianHomeController.getCurrentSelectedBook().getEditora());
        lvISBN.setText(LibrarianHomeController.getCurrentSelectedBook().getIsbn());
        lvCategoria.setText(LibrarianHomeController.getCurrentSelectedBook().getCategoria());
        lvAnoPubli.setText(LibrarianHomeController.getCurrentSelectedBook().getAnoPublicacao().toString());
        amountAvailable.setText(Integer.valueOf(LibrarianHomeController.getCurrentSelectedBook().getQuantidadeDisponiveis()).toString());
    }

}
