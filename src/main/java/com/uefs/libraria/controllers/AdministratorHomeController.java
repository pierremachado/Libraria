package com.uefs.libraria.controllers;

import com.uefs.libraria.model.Book;
import com.uefs.libraria.model.User;
import com.uefs.libraria.services.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.uefs.libraria.controllers.MainWindowController.mainWindowController;
import static com.uefs.libraria.controllers.MainWindowController.openPage;

public class AdministratorHomeController implements Initializable {

    static AdministratorHomeController administratorHomeController;
    private static User currentSelectedUser;
    private static Book currentSelectedBook;
    private static String search;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label greetingLabel;

    @FXML
    private ImageView iconImage;

    @FXML
    private Button searchButton;

    @FXML
    private Button selfProfileCheckButton;

    @FXML
    private Button addUserButton;

    @FXML
    private Button addBookButton;

    @FXML
    private Button generateReportButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private ChoiceBox<String> bookReaderChoiceBox;

    @FXML
    private TextField searchTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        administratorHomeController = this;
        this.setScreen();
    }

    @FXML
    private void goToHome(MouseEvent event){
        this.refreshCenterTable();
        this.closeRightPaneOperation();
        this.searchTextField.clear();
    }

    public static User getCurrentSelectedUser() {
        return currentSelectedUser;
    }

    public static void setCurrentSelectedUser(User currentSelectedUser) {
        AdministratorHomeController.currentSelectedUser = currentSelectedUser;
    }

    public static String getSearch(){
        return search;
    }

    public static void setSearch(String key){
        AdministratorHomeController.search = key;
    }

    @FXML
    void setScreen() {
        borderPane.setCenter(openPage("/com/uefs/libraria/UserAndBookTable.fxml"));

        greetingLabel.setText("Boas vindas," +
                " " +
                LoginService.getCurrentLoggedUser().getNome());

        bookReaderChoiceBox.getItems().addAll("Livro", "Usuário");
        bookReaderChoiceBox.setValue("Livro");

        usernameLabel.setText("@" + LoginService.getCurrentLoggedUser().getId());

        selfProfileCheckButton.setOnAction(actionEvent -> selfProfileCheck());

        addUserButton.setOnAction(actionEvent -> {addUser();});

        logoutButton.setOnAction(event -> handleLogout());

        searchButton.setOnAction(ActionEvent -> handleSearch());
    }

    private void handleSearch(){
        AdministratorHomeController.setSearch(searchTextField.getText());
        try {
            switch(bookReaderChoiceBox.getValue()){
                case "Livro" -> {borderPane.setCenter(openPage("/com/uefs/libraria/BookTable.fxml"));}
                case "Usuário" -> {borderPane.setCenter(openPage("/com/uefs/libraria/UserTable.fxml"));}
            }

        } catch (Exception e){
            // todo
        }
    }

    private void addUser(){
        borderPane.setRight(openPage("/com/uefs/libraria/UserRegister.fxml"));
    }

    private void selfProfileCheck(){
        currentSelectedUser = LoginService.getCurrentLoggedUser();
        borderPane.setRight(openPage("/com/uefs/libraria/OperatorProfileCheck.fxml"));
    }

    public void closeRightPaneOperation(){
        currentSelectedUser = null;
        currentSelectedBook = null;
        borderPane.setRight(null);
    }

    public void refreshCenterTable() {
        borderPane.setCenter(openPage("/com/uefs/libraria/UserAndBookTable.fxml"));
    }

    private void handleLogout(){
        LoginService.logoff();
        mainWindowController.callLoginScreen();
    }
}
