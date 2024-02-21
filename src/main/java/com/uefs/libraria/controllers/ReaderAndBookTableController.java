package com.uefs.libraria.controllers;

import com.uefs.libraria.dao.DAO;
import com.uefs.libraria.model.Book;
import com.uefs.libraria.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ReaderAndBookTableController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Book> bookTable;

    @FXML
    private TableView<User> userTable;

    @FXML
    void setBookTable() {
        ObservableList<Book> bookData = FXCollections.observableArrayList(DAO.getLivroDAO().findAll());

        BookTableController.createBookTable(this.bookTable, bookData);
    }

    @FXML
    void setUserTable() {
        ObservableList<User> userData = FXCollections.observableArrayList(DAO.getLeitorDAO().findAll());

        UserTableController.createUserTable(this.userTable, userData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBookTable();
        setUserTable();

        userTable.setOnMouseClicked(mouseEvent -> {
            LibrarianHomeController.setCurrentSelectedUser(userTable.getSelectionModel().getSelectedItem());
            LibrarianHomeController.librarianHomeController.profileCheck();
        });

        bookTable.setOnMouseClicked(mouseEvent -> {
            LibrarianHomeController.setCurrentSelectedBook(bookTable.getSelectionModel().getSelectedItem());
            LibrarianHomeController.librarianHomeController.bookCheck();});
    }
}
