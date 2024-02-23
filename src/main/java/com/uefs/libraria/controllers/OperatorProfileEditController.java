package com.uefs.libraria.controllers;

import com.uefs.libraria.Main;
import com.uefs.libraria.dao.DAO;
import com.uefs.libraria.exceptions.NotEnoughPermissionException;
import com.uefs.libraria.model.Loan;
import com.uefs.libraria.model.Reservation;
import com.uefs.libraria.model.User;
import com.uefs.libraria.services.BookService;
import com.uefs.libraria.services.LoginService;
import com.uefs.libraria.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.uefs.libraria.dao.DAO.getEmprestimoDAO;
import static com.uefs.libraria.dao.DAO.getReservaDAO;

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
    private Label errorWarningLabel;

    @FXML
    void cancelEdit(ActionEvent event) {
        switch(LoginService.getCurrentLoggedUser().getPermissao()){
            case ADMINISTRADOR -> {AdministratorHomeController.administratorHomeController.closeRightPaneOperation();}
            case BIBLIOTECARIO -> LibrarianHomeController.librarianHomeController.closeRightPaneOperation();
        }
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

        User findEqualUser = UserService.pesquisarUsuarioPorUsername(id);
        if(findEqualUser != null && !findEqualUser.equals(userToEdit)){
            errorWarningLabel.setText("Nome de usuário já cadastrado.");
            return;
        }

        DAO.getReservaDAO().updateReservationId(userToEdit, id);
        DAO.getEmprestimoDAO().updateLoanId(userToEdit, id);

        userToEdit.setNome(nome);
        userToEdit.setSobrenome(sobrenome);
        userToEdit.setSenha(senha);
        userToEdit.setId(id);
        userToEdit.setTelefone(telefone);
        userToEdit.setEndereco(endereco);

        switch(LoginService.getCurrentLoggedUser().getPermissao()){
            case ADMINISTRADOR -> MainWindowController.mainWindowController.callAdministratorHomeScreen();
            case BIBLIOTECARIO -> MainWindowController.mainWindowController.callLibrarianHomeScreen();
        }

        userToEdit = null;
    }

    @FXML
    void initialize() {
        errorWarningLabel.setText(null);

        try {
            userToEdit = UserService.updateUsuario(UserService.getSelectedUser());

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
