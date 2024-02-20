package com.uefs.libraria.controllers;

import com.uefs.libraria.dao.DAO;
import com.uefs.libraria.model.Book;
import com.uefs.libraria.model.User;
import com.uefs.libraria.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserAndBookTableController implements Initializable {

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
        List<User> allUsers = new ArrayList<>(UserService.getAllUsers());

        ObservableList<User> userData = FXCollections.observableArrayList(allUsers);

        UserTableController.createUserTable(this.userTable, userData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBookTable();
        setUserTable();
    }
}
