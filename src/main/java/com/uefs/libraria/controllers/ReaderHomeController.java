package com.uefs.libraria.controllers;

import com.uefs.libraria.model.User;
import com.uefs.libraria.services.LoginService;
import com.uefs.libraria.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.uefs.libraria.controllers.MainWindowController.openPage;
import static com.uefs.libraria.controllers.MainWindowController.wipeSelections;

public class ReaderHomeController {

    static ReaderHomeController readerHomeController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private Button searchBookButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button selfProfileCheckButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private Button showAllBooksButton;

    @FXML
    private Button myReservationsButton;

    @FXML
    private Button myLoansButton;

    @FXML
    private void showAllBooksAction(ActionEvent event){
        borderPane.setCenter(openPage("/com/uefs/libraria/BookTable.fxml"));
    }

    @FXML
    private void myReservationsAction(ActionEvent event){
        borderPane.setCenter(openPage("/com/uefs/libraria/ReservationTable.fxml"));
    }

    @FXML
    private void myLoansAction(ActionEvent event) {
        borderPane.setCenter(openPage("/com/uefs/libraria/LoanTable.fxml"));
    }

    @FXML
    void goToHome(MouseEvent event) {
        this.searchTextField.clear();
        MainWindowController.mainWindowController.callReaderHomeScreen();
    }

    @FXML
    void logoutAction(ActionEvent event) {
        LoginService.logoff();
        MainWindowController.mainWindowController.callLoginScreen();
    }

    @FXML
    void searchBookAction(ActionEvent event) {
        UserService.setSearch(searchTextField.getText());
        borderPane.setCenter(openPage("/com/uefs/libraria/BookTable.fxml"));
    }

    @FXML
    void selfProfileCheckAction(ActionEvent event) {
        UserService.setSelectedUser(LoginService.getCurrentLoggedUser());
        borderPane.setRight(openPage("/com/uefs/libraria/LibrarianProfileCheck.fxml"));
    }

    @FXML
    void initialize() {
        readerHomeController = this;

        greetingLabel.setText("Boas vindas," +
                " " +
                LoginService.getCurrentLoggedUser().getNome());

        usernameLabel.setText("@" + LoginService.getCurrentLoggedUser().getId());

        borderPane.setCenter(openPage("/com/uefs/libraria/BookTable.fxml"));
    }

    public void bookCheck(){
        borderPane.setRight(openPage("/com/uefs/libraria/ReaderBookProfile.fxml"));
    }

    public void closeRightPaneOperation(){
        wipeSelections();
        borderPane.setRight(null);
    }

    public void reservationCheck() {borderPane.setRight(openPage("/com/uefs/libraria/ReservationProfile.fxml"));}

    public void profileCheck(){
        borderPane.setRight(openPage("/com/uefs/libraria/LibrarianProfileCheck.fxml"));
    }

    public void loanCheck() {borderPane.setRight(openPage("/com/uefs/libraria/ReaderLoanProfile.fxml"));}

    public void refreshCenterTable() {
        borderPane.setCenter(openPage("/com/uefs/libraria/BookTable.fxml"));
    }
}
