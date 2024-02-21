package com.uefs.libraria.controllers;

import com.uefs.libraria.exceptions.NotEnoughPermissionException;
import com.uefs.libraria.model.Book;
import com.uefs.libraria.services.BookService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class BookProfileEditController {

    private static Book bookToEdit;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField amountAvailableField;

    @FXML
    private TextField authorField;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField idField;

    @FXML
    private ImageView lvImagem;

    @FXML
    private TextField publisherField;

    @FXML
    private Button saveEditButton;

    @FXML
    private TextField titleField;

    @FXML
    private TextField yearOfPublishingField;

    @FXML
    private Label errorWarningLabel;

    @FXML
    void cancelAction(ActionEvent event) {
        AdministratorHomeController.administratorHomeController.closeRightPaneOperation();
        AdministratorHomeController.setCurrentSelectedBook(null);
    }

    @FXML
    void saveBookEditAction(ActionEvent event) {
        String title = titleField.getText();
        String author = authorField.getText();
        String publisher = publisherField.getText();
        String id = idField.getText();
        String yearOfPublishing = yearOfPublishingField.getText();

        Year year = null;
        try {
            year = Year.parse(yearOfPublishing);
        } catch (DateTimeParseException e) {
            errorWarningLabel.setText("Ano de publicação inválido.");
            return;
        }

        String category = categoryField.getText();
        String amountAvailable = amountAvailableField.getText();

        int amount = 0;
        try {
            amount = Integer.parseInt(amountAvailable);
        } catch (NumberFormatException e) {
            errorWarningLabel.setText("Quantidade disponível inválida.");
            return;
        }

        bookToEdit.setTitulo(title);
        bookToEdit.setAutor(author);
        bookToEdit.setCategoria(category);
        bookToEdit.setEditora(publisher);
        bookToEdit.setIsbn(id);
        bookToEdit.setQuantidadeDisponiveis(amount);
        bookToEdit.setAnoPublicacao(year);


        MainWindowController.mainWindowController.callAdministratorHomeScreen();
    }

    @FXML
    void initialize() {
        try {
            bookToEdit = BookService.updateLivro(AdministratorHomeController.getCurrentSelectedBook());

            errorWarningLabel.setText(null);

            assert bookToEdit != null;
            titleField.setText(bookToEdit.getTitulo());
            authorField.setText(bookToEdit.getAutor());
            idField.setText(bookToEdit.getIsbn());
            publisherField.setText(bookToEdit.getEditora());
            yearOfPublishingField.setText(bookToEdit.getAnoPublicacao().toString());
            categoryField.setText(bookToEdit.getCategoria());
            amountAvailableField.setText(Integer.valueOf(bookToEdit.getQuantidadeDisponiveis()).toString());

        } catch (NotEnoughPermissionException e) {
            throw new RuntimeException(e);
        }
    }

}
