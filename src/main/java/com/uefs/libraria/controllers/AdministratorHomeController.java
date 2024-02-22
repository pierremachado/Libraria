package com.uefs.libraria.controllers;

import com.uefs.libraria.services.LoginService;
import com.uefs.libraria.services.UserService;
import javafx.event.ActionEvent;
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

import static com.uefs.libraria.controllers.MainWindowController.*;

public class AdministratorHomeController implements Initializable {

    static AdministratorHomeController administratorHomeController;

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
    private Button showAllBooksButton;

    @FXML
    private Button showAllUsersButton;

    @FXML
    private Button showAllReservationsButton;

    @FXML
    private Button showAllLoansButton;

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
    private void openAllBooksAction(ActionEvent event){
        wipeSelections();
        this.borderPane.setCenter(openPage("/com/uefs/libraria/BookTable.fxml"));
    }

    @FXML
    private void showAllUsersAction(ActionEvent event){
        wipeSelections();
        this.borderPane.setCenter(openPage("/com/uefs/libraria/UserTable.fxml"));
    }

    @FXML
    private void showAllReservationsAction(ActionEvent event){
        // todo
    }

    @FXML
    private void showAllLoansAction(ActionEvent event){
        // todo
    }

    @FXML
    private void goToHome(MouseEvent event){
        wipeSelections();
        this.refreshCenterTable();
        this.closeRightPaneOperation();
        this.searchTextField.clear();
    }

    @FXML
    void setScreen() {
        borderPane.setCenter(openPage("/com/uefs/libraria/BookTable.fxml"));

        greetingLabel.setText("Boas vindas," +
                " " +
                LoginService.getCurrentLoggedUser().getNome());

        bookReaderChoiceBox.getItems().addAll("Livro", "Usuário");
        bookReaderChoiceBox.setValue("Livro");

        usernameLabel.setText("@" + LoginService.getCurrentLoggedUser().getId());

        selfProfileCheckButton.setOnAction(actionEvent -> {
            UserService.setSelectedUser((LoginService.getCurrentLoggedUser()));
            profileCheck();
        });

        addUserButton.setOnAction(actionEvent -> {addUser();});

        addBookButton.setOnAction(actionEvent -> addBook());

        logoutButton.setOnAction(event -> handleLogout());

        searchButton.setOnAction(ActionEvent -> handleSearch());

        generateReportButton.setOnAction(ActionEvent -> handleReport());
    }



    private void handleSearch(){
        UserService.setSearch(searchTextField.getText());
        try {
            switch(bookReaderChoiceBox.getValue()){
                case "Livro" -> {borderPane.setCenter(openPage("/com/uefs/libraria/BookTable.fxml"));}
                case "Usuário" -> {borderPane.setCenter(openPage("/com/uefs/libraria/UserTable.fxml"));}
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleReport(){
        closeRightPaneOperation();
        borderPane.setRight(openPage("/com/uefs/libraria/Report.fxml"));
    }

    private void addUser(){
        closeRightPaneOperation();
        borderPane.setRight(openPage("/com/uefs/libraria/UserRegister.fxml"));
    }

    private void addBook() {
        closeRightPaneOperation();
        borderPane.setRight(openPage("/com/uefs/libraria/BookRegister.fxml"));
    }

    public void profileCheck(){
        borderPane.setRight(openPage("/com/uefs/libraria/OperatorProfileCheck.fxml"));
    }

    public void bookCheck() {
        borderPane.setRight(openPage("/com/uefs/libraria/BookProfile.fxml"));
    }

    public void openRightPanel(String url){
        borderPane.setRight(openPage(url));
    }

    public void closeRightPaneOperation(){
        wipeSelections();
        borderPane.setRight(null);
    }

    public void refreshCenterTable() {
        borderPane.setCenter(openPage("/com/uefs/libraria/BookTable.fxml"));
    }

    private void handleLogout(){
        LoginService.logoff();
        mainWindowController.callLoginScreen();
    }
}
