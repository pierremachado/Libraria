package com.uefs.libraria.controllers;

import com.uefs.libraria.exceptions.IncorrectCredentialsException;
import com.uefs.libraria.exceptions.MustLogoutException;
import com.uefs.libraria.model.enums.UserPermission;
import com.uefs.libraria.services.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

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

        try {
            LoginService.login(username, password, accountType);

            System.out.println(LoginService.getCurrentLoggedUser().getNome());
            System.out.println(LoginService.getCurrentLoggedUser().getCargo());
            System.out.println("@" + LoginService.getCurrentLoggedUser().getId());
            System.out.println(LoginService.getCurrentLoggedUser().getSenha());
            System.out.println(LoginService.getCurrentLoggedUser().getPermissao());

            LoginService.logoff(); // transformar em comentário após terminar os testes
        }
        catch(MustLogoutException | IncorrectCredentialsException e){
            loginStatus.setText("Usuário ou senha incorretos.");
        }
    }

    private void handleGuestLogin() {
        LoginService.guestLogin();

        System.out.println(LoginService.getCurrentLoggedUser().getId());
        System.out.println(LoginService.getCurrentLoggedUser().getSenha());
        System.out.println(LoginService.getCurrentLoggedUser().getPermissao());

        LoginService.logoff(); // transformar em comentário após terminar os testes
    }
}