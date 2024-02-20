package com.uefs.libraria.controllers;

import java.net.URL;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

import com.uefs.libraria.exceptions.BookAmountUnderZeroException;
import com.uefs.libraria.exceptions.IdAlreadyExistsException;
import com.uefs.libraria.exceptions.NotEnoughPermissionException;
import com.uefs.libraria.services.BookService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static com.uefs.libraria.controllers.AdministratorHomeController.administratorHomeController;

public class BookRegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addBookButton;

    @FXML
    private TextField cAnoPublicacao;

    @FXML
    private TextField cAutor;

    @FXML
    private TextField cCategoria;

    @FXML
    private TextField cISBN;

    @FXML
    private TextField cQuantidadeDisp;

    @FXML
    private TextField cTitulo;

    @FXML
    private TextField cEditora;

    @FXML
    private Button cancelButton;

    @FXML
    private Label errorWarningLabel;

    @FXML
    void initialize() {
        addBookButton.setOnAction(event -> handleAddBook());
        cancelButton.setOnAction(event -> administratorHomeController.closeRightPaneOperation());
        errorWarningLabel.setText(null);
    }

    private void handleAddBook(){
        String title = cTitulo.getText();
        String author = cAutor.getText();
        String publisher = cEditora.getText();
        String id = cISBN.getText();
        String yearOfPublishing = cAnoPublicacao.getText();

        Year year = null;
        try {
            year = Year.parse(yearOfPublishing);
        } catch (DateTimeParseException e) {
            errorWarningLabel.setText("Ano de publicação inválido.");
            return;
        }

        String category = cCategoria.getText();
        String amountAvailable = cQuantidadeDisp.getText();

        int amount = 0;
        try {
            amount = Integer.parseInt(amountAvailable);
        } catch (NumberFormatException e) {
            errorWarningLabel.setText("Quantidade disponível inválida.");
        }

        try {
            BookService.criarLivro(title, author, publisher, id, year, category, amount);
        } catch (BookAmountUnderZeroException e) {
            errorWarningLabel.setText("Quantidade disponível inválida.");
            return;
        } catch (NotEnoughPermissionException e) {
            errorWarningLabel.setText("Sem permissão suficiente.");
            return;
        } catch (IdAlreadyExistsException e) {
            errorWarningLabel.setText("ISBN já cadastrado.");
            return;
        }

        administratorHomeController.closeRightPaneOperation();
        administratorHomeController.refreshCenterTable();
    }
}
