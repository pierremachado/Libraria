package test.java.com.uefs.libraria.businesslogic;

import main.java.com.uefs.libraria.services.LendingService;
import main.java.com.uefs.libraria.services.LoginService;
import main.java.com.uefs.libraria.services.FinesService;
import main.java.com.uefs.libraria.services.TimeService;
import main.java.com.uefs.libraria.dao.DAO;
import main.java.com.uefs.libraria.exceptions.*;
import main.java.com.uefs.libraria.model.*;
import main.java.com.uefs.libraria.model.enums.LoanStatus;
import main.java.com.uefs.libraria.model.enums.ReaderStatus;
import main.java.com.uefs.libraria.model.enums.UserPermission;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class LoanManagementTest {
    private Administrator adm;
    private Librarian bib;
    private Reader reader;
    private Book book;
    @BeforeEach
    public void setUp(){
        adm = new Administrator("admin", "", "admin", "admin");
        bib = new Librarian("sérgio", "sérgio", "sergiosergio", "senha");
        reader = new Reader("amanda", "amanda", "amandaamanda", "ahnes", "", "");
        book = new Book("Você é lindo", "Fulano de Tal", "Editora", "123456", Year.of(2013), "Auto-ajuda", 5, 0);

        DAO.getAdministradorDAO().create(adm);
        DAO.getBibliotecarioDAO().create(bib);
        DAO.getLeitorDAO().create(reader);
        DAO.getLivroDAO().create(book);
    }
    @AfterEach
    public void tearDown(){
        LoginService.logoff();
        DAO.limparDadosDAO();
    }

    @Test
    public void criarEmprestimo(){
        try {
            LoginService.login("sergiosergio", "senha", UserPermission.BIBLIOTECARIO);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        TimeService.setCurrentLocalDateTime(LocalDateTime.now());

        Loan loan = null;
        try {
            loan = LendingService.criarEmprestimo(this.reader, this.book);
        } catch (NotEnoughPermissionException | UserIsBlockedException | LoanException e) {
            e.printStackTrace();
        }

        assertNotNull(loan);
        assertEquals(loan.getIdLeitor(), reader.getId());
        assertEquals(loan.getIdLivro(), book.getIsbn());
        assertEquals(loan.getStatus(), LoanStatus.PENDENTE);

        try {
            LendingService.confirmarRecebimentoEmprestimo(loan);
        } catch (NotEnoughPermissionException | LoanException e) {
            throw new RuntimeException(e);
        }

        FinesService.aplicarMultas();
        FinesService.desbloquearMultas();

        assertEquals(loan.getStatus(), LoanStatus.CONCLUIDO);
        assertEquals(DAO.getLeitorDAO().findID(loan.getIdLeitor()).getStatus(), ReaderStatus.LIBERADO);
    }

    @Test
    public void cancelarEmprestimo(){
        try {
            LoginService.login("sergiosergio", "senha", UserPermission.BIBLIOTECARIO);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        TimeService.setCurrentLocalDateTime(LocalDateTime.now());

        Loan loan = null;
        try {
            loan = LendingService.criarEmprestimo(this.reader, this.book);
        } catch (NotEnoughPermissionException | UserIsBlockedException | LoanException e) {
            e.printStackTrace();
        }

        try {
            LendingService.cancelarEmprestimo(loan, true);
        } catch (NotEnoughPermissionException | LoanException e) {
            e.printStackTrace();
        }

        assertEquals(loan.getStatus(), LoanStatus.CANCELADO);
    }

    @Test
    public void verificarMultaTest(){
        try {
            LoginService.login("sergiosergio", "senha", UserPermission.BIBLIOTECARIO);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        TimeService.setCurrentLocalDateTime(LocalDateTime.now());

        Loan loan = null;
        try {
            loan = LendingService.criarEmprestimo(this.reader, this.book);
        } catch (NotEnoughPermissionException | UserIsBlockedException | LoanException e) {
            e.printStackTrace();
        }

        TimeService.setCurrentLocalDateTime(LocalDateTime.now().plusDays(10));

        FinesService.aplicarMultas();
        FinesService.desbloquearMultas();

        try {
            LendingService.confirmarRecebimentoEmprestimo(loan);
        } catch (NotEnoughPermissionException | LoanException e) {
            throw new RuntimeException(e);
        }

        FinesService.aplicarMultas();
        FinesService.desbloquearMultas();

        assertEquals(loan.getStatus(), LoanStatus.CONCLUIDO);
        assertEquals(DAO.getLeitorDAO().findID(loan.getIdLeitor()).getStatus(), ReaderStatus.MULTADO);

        TimeService.setCurrentLocalDateTime(LocalDateTime.now().plusDays(300));

        FinesService.aplicarMultas();
        FinesService.desbloquearMultas();

        assertEquals(DAO.getLeitorDAO().findID(loan.getIdLeitor()).getStatus(), ReaderStatus.LIBERADO);
    }
}
