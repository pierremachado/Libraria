package com.uefs.libraria.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.uefs.libraria.dao.DAO;
import com.uefs.libraria.model.Administrator;
import com.uefs.libraria.model.Book;
import com.uefs.libraria.model.Librarian;
import com.uefs.libraria.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserAndBookTableController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Book> bookTable;

    private ObservableList<Book> bookData;

    @FXML
    private TableView<User> userTable;

    private ObservableList<User> userData;

    @FXML
    void setBookTable() {
        this.bookData = FXCollections.observableArrayList(DAO.getLivroDAO().findAll());

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

        this.bookTable.getColumns().setAll(titleCol, authorCol, publisherCol, categoryCol, idCol, yearOfCol, timesSearched);
        this.bookTable.setItems(bookData);
    }

    @FXML
    void setUserTable() {
        List<User> allUsers = new ArrayList<>(DAO.getAdministradorDAO().findAll());
        allUsers.addAll(DAO.getBibliotecarioDAO().findAll());
        allUsers.addAll(DAO.getLeitorDAO().findAll());

        this.userData = FXCollections.observableArrayList(allUsers);

        TableColumn name = new TableColumn<>("Nome completo");
        TableColumn username = new TableColumn<>("Nome de usuário");
        TableColumn role = new TableColumn<>("Cargo");

        name.setCellValueFactory(new PropertyValueFactory<User, String>("nomeCompleto"));
        username.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        role.setCellValueFactory(new PropertyValueFactory<User, String>("cargo"));

        this.userTable.getColumns().setAll(name, username, role);
        this.userTable.setItems(userData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBookTable();
        setUserTable();
    }
}
