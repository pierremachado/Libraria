package com.uefs.libraria.controllers;

import com.uefs.libraria.dao.DAO;
import com.uefs.libraria.model.User;
import com.uefs.libraria.services.LoginService;
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
        switch (LoginService.getCurrentLoggedUser().getPermissao()) {
            case ADMINISTRADOR -> {
                if (UserService.getSearch() != null) {
                    userData = FXCollections.observableArrayList(UserService.pesquisarUsuarioPorKey
                            (UserService.getSearch()));
                }
                else {
                    userData = FXCollections.observableArrayList(UserService.getAllUsers());
                }
            }

            case BIBLIOTECARIO -> {
                if (UserService.getSearch() != null) {
                    userData = FXCollections.observableArrayList(UserService.pesquisarLeitorPorKey
                            (UserService.getSearch()));
                } else {
                    userData = FXCollections.observableArrayList(DAO.getLeitorDAO().findAll());
                }
            }
        }


        createUserTable(this.userTable, userData);

        userTable.setOnMouseClicked(MouseEvent -> {
            UserService.setSelectedUser(userTable.getSelectionModel().getSelectedItem());

            switch(LoginService.getCurrentLoggedUser().getPermissao()){
                case ADMINISTRADOR -> AdministratorHomeController.administratorHomeController.profileCheck();
                case BIBLIOTECARIO -> LibrarianHomeController.librarianHomeController.profileCheck();
            }
        });

        UserService.setSelectedUser(null);
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
