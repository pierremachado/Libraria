package com.uefs.libraria.controllers;

import com.uefs.libraria.exceptions.IncorrectCredentialsException;
import com.uefs.libraria.exceptions.MustLogoutException;
import com.uefs.libraria.model.enums.UserPermission;
import com.uefs.libraria.services.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import static com.uefs.libraria.controllers.MainWindowController.mainWindowController;

public class LoginController{

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private Button loginButton;

    @FXML
    private Button guestLoginButton;

    @FXML
    private Label loginStatus;

    @FXML
    private BorderPane borderPane;

    @FXML
    public void initialize() {
        choiceBox.getItems().addAll("Administrador", "Bibliotecário", "Leitor");
        choiceBox.setValue("Administrador");

        // inicialização do status de login do usuário
        loginStatus.setText(null);

        // login padrão
        loginButton.setOnAction(event -> handleLogin());

        // login do convidado (não é necessário inserir nenhum dado)
        guestLoginButton.setOnAction(event -> handleGuestLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        UserPermission accountType = getUserPermission();

        try {
            LoginService.login(username, password, accountType);
            loginStatus.setText(null);

            switch(accountType) {
                case ADMINISTRADOR -> {mainWindowController.callAdministratorHomeScreen();} // replace Login.fxml with AdministratorHome.fxml in MainWindow
                case BIBLIOTECARIO -> {} // replace Login.fxml with LibrarianHome.fxml in MainWindow
                case LEITOR -> {} // replace Login.fxml with ReaderHome.fxml in MainWindow
                default -> throw new IllegalStateException("Unexpected value: " + accountType);
            }
        }
        catch(IncorrectCredentialsException e){
            loginStatus.setText("Usuário ou senha incorretos.");
            passwordField.clear();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private UserPermission getUserPermission() {
        String selectedOption = choiceBox.getValue();

        UserPermission accountType = null;

        // necessário para pesquisar no DAO correspondente
        switch(selectedOption){
            case "Administrador" -> {
                accountType = UserPermission.ADMINISTRADOR;
            }
            case "Bibliotecário" -> {
                accountType = UserPermission.BIBLIOTECARIO;
            }
            case "Leitor" -> {
                accountType = UserPermission.LEITOR;
            }
        }
        return accountType;
    }

    private void handleGuestLogin() {
        LoginService.guestLogin();
        // replace Login.fxml with GuestHome.fxml in MainWindow
    }
}

