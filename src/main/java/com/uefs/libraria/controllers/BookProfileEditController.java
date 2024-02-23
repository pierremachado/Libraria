package com.uefs.libraria.controllers;

import com.uefs.libraria.dao.DAO;
import com.uefs.libraria.exceptions.EditIdWithOngoingLoansException;
import com.uefs.libraria.exceptions.NotEnoughPermissionException;
import com.uefs.libraria.model.Book;
import com.uefs.libraria.model.Loan;
import com.uefs.libraria.model.Reservation;
import com.uefs.libraria.services.BookService;
import com.uefs.libraria.services.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.uefs.libraria.dao.DAO.*;

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
        switch (LoginService.getCurrentLoggedUser().getPermissao()){
            case ADMINISTRADOR -> AdministratorHomeController.administratorHomeController.closeRightPaneOperation();
            case BIBLIOTECARIO -> LibrarianHomeController.librarianHomeController.closeRightPaneOperation();
        }

        BookService.setSelectedBook(null);
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

        Book findEqualBook = BookService.pesquisarLivroPorIsbn(id);
        if(findEqualBook != null && !findEqualBook.equals(bookToEdit)){
            errorWarningLabel.setText("ISBN já cadastrado.");
            return;
        }

        DAO.getReservaDAO().updateReservationIsbn(bookToEdit, id);
        DAO.getEmprestimoDAO().updateLoanIsbn(bookToEdit, id);

        bookToEdit.setTitulo(title);
        bookToEdit.setAutor(author);
        bookToEdit.setCategoria(category);
        bookToEdit.setEditora(publisher);
        bookToEdit.setIsbn(id);
        bookToEdit.setQuantidadeDisponiveis(amount);
        bookToEdit.setAnoPublicacao(year);

        bookToEdit = null;

        switch (LoginService.getCurrentLoggedUser().getPermissao()){
            case ADMINISTRADOR -> MainWindowController.mainWindowController.callAdministratorHomeScreen();
            case BIBLIOTECARIO -> MainWindowController.mainWindowController.callLibrarianHomeScreen();
        }
    }

    @FXML
    void initialize() {
        titleField.setText(BookService.getSelectedBook().getTitulo());
        authorField.setText(BookService.getSelectedBook().getAutor());
        idField.setText(BookService.getSelectedBook().getIsbn());
        publisherField.setText(BookService.getSelectedBook().getEditora());
        yearOfPublishingField.setText(BookService.getSelectedBook().getAnoPublicacao().toString());
        categoryField.setText(BookService.getSelectedBook().getCategoria());
        amountAvailableField.setText(Integer.valueOf(BookService.getSelectedBook().getQuantidadeDisponiveis()).toString());

        try {
            bookToEdit = BookService.updateLivro(BookService.getSelectedBook());

            errorWarningLabel.setText(null);

            assert bookToEdit != null;

            saveEditButton.setDisable(false);

        } catch (NotEnoughPermissionException e) {
            throw new RuntimeException(e);
        } catch (EditIdWithOngoingLoansException e) {
            errorWarningLabel.setText("Há empréstimos pendentes para este livro.");
            saveEditButton.setDisable(true);
        }
    }

}
