package com.uefs.libraria.controllers;

import com.uefs.libraria.exceptions.NotEnoughPermissionException;
import com.uefs.libraria.model.User;
import com.uefs.libraria.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class OperatorProfileEditController {

    private static User userToEdit;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView accountIcon;

    @FXML
    private TextField addressField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button editButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField usernameField;

    @FXML
    void cancelEdit(ActionEvent event) {
        AdministratorHomeController.administratorHomeController.closeRightPaneOperation();
        UserService.setSelectedUser(null);
    }

    @FXML
    void saveEdit(ActionEvent event) {
        String nome = nameField.getText();
        String sobrenome = surnameField.getText();
        String senha = passwordField.getText();
        String telefone = phoneField.getText();
        String endereco = addressField.getText();
        String id = usernameField.getText();

        userToEdit.setNome(nome);
        userToEdit.setSobrenome(sobrenome);
        userToEdit.setSenha(senha);
        userToEdit.setId(id);
        userToEdit.setTelefone(telefone);
        userToEdit.setEndereco(endereco);

        MainWindowController.mainWindowController.callAdministratorHomeScreen();
    }

    @FXML
    void initialize() {
        try {
            userToEdit = UserService.updateUsuario(UserService.getSelectedUser());

            assert userToEdit != null;
            nameField.setText(userToEdit.getNome());
            surnameField.setText(userToEdit.getSobrenome());
            passwordField.setText(userToEdit.getSenha());
            phoneField.setText(userToEdit.getTelefone());
            usernameField.setText(userToEdit.getId());
            addressField.setText(userToEdit.getEndereco());

        } catch (NotEnoughPermissionException e) {
            throw new RuntimeException(e);
        }
    }

}
