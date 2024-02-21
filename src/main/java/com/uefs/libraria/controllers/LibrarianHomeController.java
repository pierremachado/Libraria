package com.uefs.libraria.controllers;

import com.uefs.libraria.model.Book;
import com.uefs.libraria.model.User;
import com.uefs.libraria.services.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.uefs.libraria.controllers.MainWindowController.openPage;

public class LibrarianHomeController {

    public static LibrarianHomeController librarianHomeController;

    private static User currentSelectedUser;
    private static Book currentSelectedBook;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addBookButton;

    @FXML
    private Button addLoanButton;

    @FXML
    private ChoiceBox<String> bookReaderChoiceBox;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label greetingLabel;

    @FXML
    private ImageView home;

    @FXML
    private ImageView iconImage;

    @FXML
    private Button logoutButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button selfProfileCheckButton;

    @FXML
    private Label usernameLabel;

    @FXML
    void addBookAction(ActionEvent event) {
        //todo
    }

    @FXML
    void addLoanAction(ActionEvent event) {
        //todo
    }

    @FXML
    void goToHome(MouseEvent event) {
        this.refreshCenterTable();
        this.closeRightPaneOperation();
        this.searchTextField.clear();
    }

    @FXML
    void logoutAction(ActionEvent event) {
        LoginService.logoff();
        MainWindowController.mainWindowController.callLoginScreen();
    }

    @FXML
    void selfProfileCheckButton(ActionEvent event) {
        LibrarianHomeController.setCurrentSelectedUser(LoginService.getCurrentLoggedUser());
        this.profileCheck();
    }

    public void refreshCenterTable() {
        borderPane.setCenter(openPage("/com/uefs/libraria/ReaderAndBookTable.fxml"));
    }

    public static User getCurrentSelectedUser() {
        return currentSelectedUser;
    }

    public static void setCurrentSelectedUser(User currentSelectedUser) {
        LibrarianHomeController.currentSelectedUser = currentSelectedUser;
    }

    public static Book getCurrentSelectedBook() {
        return currentSelectedBook;
    }

    public static void setCurrentSelectedBook(Book currentSelectedBook) {
        LibrarianHomeController.currentSelectedBook = currentSelectedBook;
    }

    @FXML
    void initialize() {
        librarianHomeController = this;

        greetingLabel.setText("Boas vindas," +
                " " +
                LoginService.getCurrentLoggedUser().getNome());

        bookReaderChoiceBox.getItems().addAll("Livro", "Leitor");
        bookReaderChoiceBox.setValue("Livro");

        usernameLabel.setText("@" + LoginService.getCurrentLoggedUser().getId());

        borderPane.setCenter(openPage("/com/uefs/libraria/ReaderAndBookTable.fxml"));

    }

    public void closeRightPaneOperation(){
        currentSelectedUser = null;
        currentSelectedBook = null;
        borderPane.setRight(null);
    }

    public void bookCheck() {
        borderPane.setRight(openPage("/com/uefs/libraria/LibrarianBookProfile.fxml"));
    }

    void profileCheck(){
        borderPane.setRight(openPage("/com/uefs/libraria/LibrarianProfileCheck.fxml"));
    }
}
