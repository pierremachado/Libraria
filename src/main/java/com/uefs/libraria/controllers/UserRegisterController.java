package com.uefs.libraria.controllers;

import com.uefs.libraria.exceptions.IdAlreadyExistsException;
import com.uefs.libraria.exceptions.NotEnoughPermissionException;
import com.uefs.libraria.model.enums.UserPermission;
import com.uefs.libraria.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static com.uefs.libraria.controllers.AdministratorHomeController.administratorHomeController;

public class UserRegisterController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<UserPermission> accountTypeSelect;

    @FXML
    private Button addButton;

    @FXML
    private TextField addressField;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField dddField;

    @FXML
    private Label errorWarningLabel;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField usernameField;

    @FXML
    void addUser(ActionEvent event) {
        try {
            handleAddUser();
            administratorHomeController.refreshCenterTable();
            administratorHomeController.closeRightPaneOperation();
        } catch (NotEnoughPermissionException e) {
            errorWarningLabel.setText("Sem permissão suficiente.");
        } catch (IdAlreadyExistsException e) {
            errorWarningLabel.setText("Nome de usuário já cadastrado com o cargo selecionado.");
        }
    }

    @FXML
    void cancelRegister(ActionEvent event) {
        administratorHomeController.closeRightPaneOperation();
    }

    @FXML
    void setScreenAction() {
        accountTypeSelect.getItems().addAll(UserPermission.values());
        accountTypeSelect.setValue(UserPermission.ADMINISTRADOR);

        errorWarningLabel.setText(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setScreenAction();
    }

    private void handleAddUser() throws NotEnoughPermissionException, IdAlreadyExistsException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();
        String surname = surnameField.getText();
        String address = addressField.getText();
        String phone = phoneNumberField.getText();
        String ddd = dddField.getText();

        if (!phone.isEmpty() && !ddd.isEmpty()) {
            phone = "(" + ddd + ")" + " " + phone;
        }

        UserService.criarUsuario(name, surname, username, password, address, phone, accountTypeSelect.getValue());
    }
}
