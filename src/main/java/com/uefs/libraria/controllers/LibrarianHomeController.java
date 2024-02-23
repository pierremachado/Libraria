package com.uefs.libraria.controllers;

import com.uefs.libraria.model.Book;
import com.uefs.libraria.model.User;
import com.uefs.libraria.services.LoginService;
import com.uefs.libraria.services.UserService;
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
import static com.uefs.libraria.controllers.MainWindowController.wipeSelections;

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
    private Button showAllBooksButton;

    @FXML
    private Button showAllReadersButton;

    @FXML
    private Button showAllReservationsButton;

    @FXML
    private Button showAllLoansButton;


    @FXML
    private Label usernameLabel;

    @FXML
    private void openAllBooksAction(ActionEvent event){
        wipeSelections();
        this.borderPane.setCenter(openPage("/com/uefs/libraria/BookTable.fxml"));
    }

    @FXML
    private void showAllReadersAction(ActionEvent event){
        wipeSelections();
        this.borderPane.setCenter(openPage("/com/uefs/libraria/UserTable.fxml"));
    }

    @FXML
    private void showAllReservationsAction(ActionEvent event){
        wipeSelections();
        this.borderPane.setCenter(openPage("/com/uefs/libraria/ReservationTable.fxml"));
    }

    @FXML
    private void showAllLoansAction(ActionEvent event){
        wipeSelections();
        this.borderPane.setCenter(openPage("/com/uefs/libraria/LoanTable.fxml"));
    }

    @FXML
    void addBookAction(ActionEvent event) {
        closeRightPaneOperation();
        borderPane.setRight(openPage("/com/uefs/libraria/BookRegister.fxml"));
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
        UserService.setSelectedUser(LoginService.getCurrentLoggedUser());
        this.profileCheck();
    }

    public void refreshCenterTable() {
        borderPane.setCenter(openPage("/com/uefs/libraria/BookTable.fxml"));
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

        bookReaderChoiceBox.getItems().addAll("Livro", "Leitor", "Reserva", "Empréstimo");
        bookReaderChoiceBox.setValue("Livro");

        usernameLabel.setText("@" + LoginService.getCurrentLoggedUser().getId());

        borderPane.setCenter(openPage("/com/uefs/libraria/BookTable.fxml"));

        searchButton.setOnAction(ActionEvent -> {handleSearch();});
    }

    private void handleSearch(){
        UserService.setSearch(searchTextField.getText());
        try {
            switch(bookReaderChoiceBox.getValue()){
                case "Livro" -> {borderPane.setCenter(openPage("/com/uefs/libraria/BookTable.fxml"));}
                case "Leitor" -> {borderPane.setCenter(openPage("/com/uefs/libraria/UserTable.fxml"));}
                case "Reserva" -> {borderPane.setCenter(openPage("/com/uefs/libraria/ReservationTable.fxml"));}
                case "Empréstimo" -> {borderPane.setCenter(openPage("/com/uefs/libraria/LoanTable.fxml"));}
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeRightPaneOperation(){
        wipeSelections();
        borderPane.setRight(null);
    }

    public void bookCheck() {
        borderPane.setRight(openPage("/com/uefs/libraria/BookProfile.fxml"));
    }

    void profileCheck(){
        borderPane.setRight(openPage("/com/uefs/libraria/LibrarianProfileCheck.fxml"));
    }

    void reservationCheck() {borderPane.setRight(openPage("/com/uefs/libraria/ReservationProfile.fxml"));}

    public void loanCheck() {borderPane.setRight(openPage("/com/uefs/libraria/LoanProfile.fxml"));}

    public void openRightPanel(String url){
        borderPane.setRight(openPage(url));
    }
}
