package com.uefs.libraria.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.uefs.libraria.dao.DAO;
import com.uefs.libraria.exceptions.NotEnoughPermissionException;
import com.uefs.libraria.model.Loan;
import com.uefs.libraria.model.Reader;
import com.uefs.libraria.model.enums.LoanStatus;
import com.uefs.libraria.model.enums.UserPermission;
import com.uefs.libraria.services.LendingService;
import com.uefs.libraria.services.LoginService;
import com.uefs.libraria.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import static com.uefs.libraria.model.enums.UserPermission.ADMINISTRADOR;

public class LoanTableController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label contentLabel;

    @FXML
    private TableView<Loan> loanTable;

    @FXML
    void initialize() {
        setLoanTable();

        loanTable.setOnMouseClicked(MouseEvent -> {
            LendingService.setSelectedLoan(loanTable.getSelectionModel().getSelectedItem());

            switch (LoginService.getCurrentLoggedUser().getPermissao()) {
                case UserPermission.ADMINISTRADOR -> {
                    AdministratorHomeController.administratorHomeController.loanCheck();
                }
                case UserPermission.BIBLIOTECARIO -> {
                    LibrarianHomeController.librarianHomeController.loanCheck();
                }
                case LEITOR -> {
                    ReaderHomeController.readerHomeController.loanCheck();
                }
                case CONVIDADO -> {
                }
            }
        });
    }

    private void setLoanTable() {
        ObservableList<Loan> loanData = null;

        switch(LoginService.getCurrentLoggedUser().getPermissao()){
            case ADMINISTRADOR, BIBLIOTECARIO -> {
                if (UserService.getSearch() != null){
                    try {
                        loanData = FXCollections.observableArrayList(LendingService.pesquisarEmprestimoPorId(UserService.getSearch()));
                    } catch (NotEnoughPermissionException e) {
                        return;
                    }
                }
                else{
                    loanData = FXCollections.observableArrayList(DAO.getEmprestimoDAO().findAll());

                }
            }
            case LEITOR -> {
                loanData = FXCollections.observableArrayList(DAO.getEmprestimoDAO().findIdLeitor(LoginService.getCurrentLoggedUser().getId()));
            }
        }

        createLoanTable(this.loanTable, loanData);
    }

    private void createLoanTable(TableView<Loan> loanTable, ObservableList<Loan> loanData){
        TableColumn id = new TableColumn("ID");
        TableColumn idOperador = new TableColumn<>("ID Operador");
        TableColumn permissaoOperador = new TableColumn("Cargo");
        TableColumn nameLeitor = new TableColumn("Leitor(a)");
        TableColumn idLeitor = new TableColumn("Nome de usuário");
        TableColumn nameLivro = new TableColumn("Livro");
        TableColumn idLivro = new TableColumn("ISBN");
        TableColumn status = new TableColumn("Status");
        TableColumn dataReservado = new TableColumn("Data de empréstimo");
        TableColumn dataLimite = new TableColumn("Data limite");
        TableColumn dataDeRetorno = new TableColumn("Data de retorno");
        TableColumn qtdRenovacao = new TableColumn<>("Vezes renovado");

        id.setCellValueFactory(new PropertyValueFactory<Loan, String>("idEmprestimo"));
        idOperador.setCellValueFactory(new PropertyValueFactory<Loan, String>("idOperador"));
        permissaoOperador.setCellValueFactory(new PropertyValueFactory<Loan, UserPermission>("permissaoOperador"));
        idLeitor.setCellValueFactory(new PropertyValueFactory<Loan, String>("idLeitor"));
        idLivro.setCellValueFactory(new PropertyValueFactory<Loan, String>("idLivro"));
        status.setCellValueFactory(new PropertyValueFactory<Loan, LoanStatus>("status"));
        dataReservado.setCellValueFactory(new PropertyValueFactory<Loan, LocalDateTime>("dataEmprestimo"));
        dataLimite.setCellValueFactory(new PropertyValueFactory<Loan, LocalDateTime>("dataLimite"));
        dataDeRetorno.setCellValueFactory(new PropertyValueFactory<Loan, LocalDateTime>("dataDeRetorno"));
        qtdRenovacao.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("vezesRenovado"));

        loanTable.getColumns().setAll(id, idOperador, permissaoOperador, idLeitor, idLivro, status, dataReservado,
                dataLimite, dataDeRetorno, qtdRenovacao);

        loanTable.setItems(loanData);
    }
}
