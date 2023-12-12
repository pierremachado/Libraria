package test.java.com.uefs.libraria.businesslogic;

import main.java.com.uefs.libraria.services.LoginService;
import main.java.com.uefs.libraria.services.FinesService;
import main.java.com.uefs.libraria.services.ReportService;
import main.java.com.uefs.libraria.services.TimeService;
import main.java.com.uefs.libraria.dao.DAO;
import main.java.com.uefs.libraria.exceptions.IncorrectCredentialsException;
import main.java.com.uefs.libraria.exceptions.MustLogoutException;
import main.java.com.uefs.libraria.exceptions.NotEnoughPermissionException;
import main.java.com.uefs.libraria.model.*;
import main.java.com.uefs.libraria.model.enums.LoanStatus;
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
public class RelatorioTest {
    private Administrator adm;
    private Librarian bib;
    private Reader reader;
    private Book book;
    private Loan loan;
    @BeforeEach
    public void setUp(){
        adm = new Administrator("admin", "", "admin", "admin");
        bib = new Librarian("sérgio", "sérgio", "sergiosergio", "senha");
        reader = new Reader("amanda", "amanda", "amandaamanda", "ahnes", "", "");
        book = new Book("Você é lindo", "Fulano de Tal", "Editora", "123456", Year.of(2013), "Auto-ajuda", 5, 0);
        loan = new Loan(adm.getId(), adm.getPermissao(), reader.getId(), book.getIsbn(), null, LocalDateTime.now(), LocalDateTime.now().plusDays(7), null, 0, LoanStatus.PENDENTE);

        DAO.getAdministradorDAO().create(adm);
        DAO.getBibliotecarioDAO().create(bib);
        DAO.getLeitorDAO().create(reader);
        DAO.getLivroDAO().create(book);
        DAO.getEmprestimoDAO().create(loan);
    }
    @AfterEach
    public void tearDown(){
        LoginService.logoff();
        DAO.limparDadosDAO();
    }

    @Test
    public void criarRelatorio(){
        try {
            LoginService.login("admin", "admin", UserPermission.ADMINISTRADOR);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        TimeService.setCurrentLocalDateTime(LocalDateTime.now());

        Relatorio relatorio = null;
        try {
            relatorio = ReportService.gerarRelatorio();
        } catch (NotEnoughPermissionException e) {
            e.printStackTrace();
        }

        assertNotNull(relatorio);
        assertEquals(relatorio.getnLivrosReservados(), 0);
        assertEquals(relatorio.getnLivrosEmprestados(), 1);
        assertEquals(relatorio.getnLivrosAtrasados(), 0);
        assertEquals(relatorio.getIdAdministrador(), adm.getId());
        assertEquals(relatorio.getLivrosPopulares().get(0), book);

        TimeService.setCurrentLocalDateTime(LocalDateTime.now().plusDays(10));

        FinesService.aplicarMultas();
        FinesService.desbloquearMultas();

        relatorio = null;
        try {
            relatorio = ReportService.gerarRelatorio();
        } catch (NotEnoughPermissionException e) {
            e.printStackTrace();
        }

        assertNotNull(relatorio);
        assertEquals(relatorio.getnLivrosReservados(), 0);
        assertEquals(relatorio.getnLivrosEmprestados(), 1);
        assertEquals(relatorio.getnLivrosAtrasados(), 1);
    }
}
