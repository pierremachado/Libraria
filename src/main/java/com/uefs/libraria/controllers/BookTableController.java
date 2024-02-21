package com.uefs.libraria.controllers;

import com.uefs.libraria.model.Book;
import com.uefs.libraria.services.BookService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BookTableController {

    private ObservableList<Book> bookData;

    @FXML
    private TableView<Book> bookTable;

    @FXML
    private Label contentLabel;

    @FXML
    void initialize(){
        setBookTable();
    }

    private void setBookTable(){
        bookData = FXCollections.observableArrayList(BookService.pesquisarLivroPorChave(AdministratorHomeController.getSearch()));

        createBookTable(this.bookTable, bookData);

        bookTable.setOnMouseClicked(MouseEvent -> {
            AdministratorHomeController.setCurrentSelectedBook(bookTable.getSelectionModel().getSelectedItem());
            AdministratorHomeController.administratorHomeController.bookCheck();
        });

        AdministratorHomeController.setSearch(null);
    }

    static void createBookTable(TableView<Book> bookTable, ObservableList<Book> bookData) {
        TableColumn titleCol = new TableColumn("Título");
        TableColumn authorCol = new TableColumn("Autor(a)");
        TableColumn publisherCol = new TableColumn("Editora");
        TableColumn categoryCol = new TableColumn("Categoria");
        TableColumn idCol = new TableColumn("ISBN");
        TableColumn yearOfCol = new TableColumn("Ano de publicação");
        TableColumn amountAvailable = new TableColumn("Quantidade disponíveis");
        TableColumn timesSearched = new TableColumn("Vezes pesquisado");

        titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("titulo"));
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("autor"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<Book, String>("editora"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Book, String>("categoria"));
        idCol.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
        yearOfCol.setCellValueFactory(new PropertyValueFactory<Book, String>("anoPublicacao"));
        amountAvailable.setCellValueFactory(new PropertyValueFactory<Book, String>("quantidadeDisponiveis"));
        timesSearched.setCellValueFactory(new PropertyValueFactory<Book, String>("vezesPesquisado"));

        bookTable.getColumns().setAll(titleCol, authorCol, publisherCol, categoryCol, idCol, yearOfCol, timesSearched);
        bookTable.setItems(bookData);
    }

}
