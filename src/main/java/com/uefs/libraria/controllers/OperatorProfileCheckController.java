package com.uefs.libraria.controllers;

import com.uefs.libraria.exceptions.NotEnoughPermissionException;
import com.uefs.libraria.exceptions.OngoingLoansException;
import com.uefs.libraria.exceptions.RemoveSelfAttemptException;
import com.uefs.libraria.services.LoginService;
import com.uefs.libraria.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class OperatorProfileCheckController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView accountIcon;

    @FXML
    private Label accountTypeLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Button editButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Button removeButton;

    @FXML
    private Label errorWarningLabel;

    @FXML
    private void removeUser(ActionEvent event){
        try {
            UserService.removerUsuario(UserService.getSelectedUser());
            UserService.setSelectedUser(null);
            MainWindowController.mainWindowController.refreshMainWindow("/com/uefs/libraria/AdministratorHome.fxml");
        } catch (NotEnoughPermissionException e) {
            throw new RuntimeException(e);
        } catch (RemoveSelfAttemptException e) {
            throw new RuntimeException(e);
        } catch (OngoingLoansException e) {
            errorWarningLabel.setText("Usuário ainda possui empréstimos pendentes.");
        }
    }

    @FXML
    void cancelProfileCheck(ActionEvent event) {
        AdministratorHomeController.administratorHomeController.closeRightPaneOperation();
        UserService.setSelectedUser(null);
    }

    @FXML
    void editOperatorProfile(ActionEvent event) {
        AdministratorHomeController.administratorHomeController.openRightPanel("/com/uefs/libraria/OperatorProfileEdit.fxml");
    }

    @FXML
    void setOnProfile() {
        errorWarningLabel.setText(null);
        nameLabel.setText(UserService.getSelectedUser().getNome());
        surnameLabel.setText(UserService.getSelectedUser().getSobrenome());
        usernameLabel.setText(UserService.getSelectedUser().getId());
        accountTypeLabel.setText(UserService.getSelectedUser().getCargo());

        if (UserService.getSelectedUser().getEndereco() == null ||
                UserService.getSelectedUser().getEndereco().isEmpty()) {
            addressLabel.setText("Nada informado.");
        }
        else {
            addressLabel.setText(UserService.getSelectedUser().getEndereco());
        }

        if (UserService.getSelectedUser().getTelefone() == null ||
                UserService.getSelectedUser().getTelefone().isEmpty()) {
            phoneLabel.setText("Nada informado.");
        }
        else {
            phoneLabel.setText(UserService.getSelectedUser().getTelefone());
        }

        if(LoginService.getCurrentLoggedUser() == UserService.getSelectedUser()){
            removeButton.setDisable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOnProfile();
    }
}
