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
        BookService.setSelectedBook(null);
    }

    @FXML
    void editBook(ActionEvent event) {
        //todo
    }

    @FXML
    void removeBook(ActionEvent event) {
        try {
            BookService.removerLivro(BookService.getSelectedBook());
            BookService.setSelectedBook(null);
            MainWindowController.mainWindowController.refreshMainWindow("/com/uefs/libraria/LibrarianHome.fxml");
        } catch (NotEnoughPermissionException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        lvTitulo.setText(BookService.getSelectedBook().getTitulo());
        lvAutor.setText(BookService.getSelectedBook().getAutor());
        lvEditora.setText(BookService.getSelectedBook().getEditora());
        lvISBN.setText(BookService.getSelectedBook().getIsbn());
        lvCategoria.setText(BookService.getSelectedBook().getCategoria());
        lvAnoPubli.setText(BookService.getSelectedBook().getAnoPublicacao().toString());
        amountAvailable.setText(Integer.valueOf(BookService.getSelectedBook().getQuantidadeDisponiveis()).toString());
    }

}
