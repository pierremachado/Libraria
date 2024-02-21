package com.uefs.libraria.controllers;

import com.uefs.libraria.model.User;
import com.uefs.libraria.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class UserTableController {

    private ObservableList<User> userData;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label userSearchLabel;

    @FXML
    private TableView<User> userTable;

    @FXML
    void initialize() {
        setUserTable();
    }

    private void setUserTable(){
        userData = FXCollections.observableArrayList(UserService.pesquisarUsuarioPorKey
                (AdministratorHomeController.getSearch()));

        createUserTable(this.userTable, userData);

        userTable.setOnMouseClicked(MouseEvent -> {
            AdministratorHomeController.setCurrentSelectedUser(userTable.getSelectionModel().getSelectedItem());
            AdministratorHomeController.administratorHomeController.profileCheck();
        });

        AdministratorHomeController.setSearch(null);
    }

    static void createUserTable(TableView<User> userTable, ObservableList<User> userData) {
        TableColumn name = new TableColumn<>("Nome completo");
        TableColumn username = new TableColumn<>("Nome de usuário");
        TableColumn role = new TableColumn<>("Cargo");
        TableColumn phoneNumber = new TableColumn<>("Telefone");
        TableColumn address = new TableColumn("Endereço");

        name.setCellValueFactory(new PropertyValueFactory<User, String>("nomeCompleto"));
        username.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
        role.setCellValueFactory(new PropertyValueFactory<User, String>("cargo"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<User, String>("telefone"));
        address.setCellValueFactory(new PropertyValueFactory<User, String>("endereco"));

        userTable.getColumns().setAll(name, username, role, phoneNumber, address);
        userTable.setItems(userData);
    }

}
