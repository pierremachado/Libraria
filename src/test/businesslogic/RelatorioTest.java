package test.businesslogic;

import main.java.libraria.controllers.LoginController;
import main.java.libraria.controllers.MultaController;
import main.java.libraria.controllers.RelatorioController;
import main.java.libraria.controllers.TimeController;
import main.java.libraria.dao.DAO;
import main.java.libraria.errors.IncorrectCredentialsException;
import main.java.libraria.errors.MustLogoutException;
import main.java.libraria.errors.NotEnoughPermissionException;
import main.java.libraria.model.*;
import main.java.libraria.model.enums.EmprestimoStatus;
import main.java.libraria.model.enums.UserPermissao;
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
    private Administrador adm;
    private Bibliotecario bib;
    private Leitor leitor;
    private Livro livro;
    private Emprestimo emprestimo;
    @BeforeEach
    public void setUp(){
        adm = new Administrador("admin", "", "admin", "admin");
        bib = new Bibliotecario("sérgio", "sérgio", "sergiosergio", "senha");
        leitor = new Leitor("amanda", "amanda", "amandaamanda", "ahnes", "", "");
        livro = new Livro("Você é lindo", "Fulano de Tal", "Editora", "123456", Year.of(2013), "Auto-ajuda", 5, 0);
        emprestimo = new Emprestimo(adm.getId(), adm.getPermissao(), leitor.getId(), livro.getIsbn(), null, LocalDateTime.now(), LocalDateTime.now().plusDays(7), null, 0, EmprestimoStatus.PENDENTE);

        DAO.getAdministradorDAO().create(adm);
        DAO.getBibliotecarioDAO().create(bib);
        DAO.getLeitorDAO().create(leitor);
        DAO.getLivroDAO().create(livro);
        DAO.getEmprestimoDAO().create(emprestimo);
    }
    @AfterEach
    public void tearDown(){
        LoginController.logoff();
        DAO.limparDadosDAO();
    }

    @Test
    public void criarRelatorio(){
        try {
            LoginController.login("admin", "admin", UserPermissao.ADMINISTRADOR);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        TimeController.setCurrentLocalDateTime(LocalDateTime.now());

        Relatorio relatorio = null;
        try {
            relatorio = RelatorioController.gerarRelatorio();
        } catch (NotEnoughPermissionException e) {
            e.printStackTrace();
        }

        assertNotNull(relatorio);
        assertEquals(relatorio.getnLivrosReservados(), 0);
        assertEquals(relatorio.getnLivrosEmprestados(), 1);
        assertEquals(relatorio.getnLivrosAtrasados(), 0);
        assertEquals(relatorio.getIdAdministrador(), adm.getId());
        assertEquals(relatorio.getLivrosPopulares().get(0), livro);

        TimeController.setCurrentLocalDateTime(LocalDateTime.now().plusDays(10));

        MultaController.aplicarMultas();
        MultaController.desbloquearMultas();

        relatorio = null;
        try {
            relatorio = RelatorioController.gerarRelatorio();
        } catch (NotEnoughPermissionException e) {
            e.printStackTrace();
        }

        assertNotNull(relatorio);
        assertEquals(relatorio.getnLivrosReservados(), 0);
        assertEquals(relatorio.getnLivrosEmprestados(), 1);
        assertEquals(relatorio.getnLivrosAtrasados(), 1);
    }
}
