package test;

import main.java.libraria.controllers.EmprestimoController;
import main.java.libraria.controllers.LoginController;
import main.java.libraria.controllers.MultaController;
import main.java.libraria.controllers.TimeController;
import main.java.libraria.dao.DAO;
import main.java.libraria.errors.*;
import main.java.libraria.model.*;
import main.java.libraria.model.enums.EmprestimoStatus;
import main.java.libraria.model.enums.LeitorStatus;
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
public class EmprestimoManagementTest {
    private Administrador adm;
    private Bibliotecario bib;
    private Leitor leitor;
    private Livro livro;
    @BeforeEach
    public void setUp(){
        adm = new Administrador("admin", "", "admin", "admin");
        bib = new Bibliotecario("sérgio", "sérgio", "sergiosergio", "senha");
        leitor = new Leitor("amanda", "amanda", "amandaamanda", "ahnes", "", "");
        livro = new Livro("Você é lindo", "Fulano de Tal", "Editora", "123456", Year.of(2013), "Auto-ajuda", 5, 0);

        DAO.getAdministradorDAO().create(adm);
        DAO.getBibliotecarioDAO().create(bib);
        DAO.getLeitorDAO().create(leitor);
        DAO.getLivroDAO().create(livro);
    }
    @AfterEach
    public void tearDown(){
        LoginController.logoff();
        DAO.limparDadosDAO();
    }

    @Test
    public void criarEmprestimo(){
        try {
            LoginController.login("sergiosergio", "senha", UserPermissao.BIBLIOTECARIO);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        TimeController.setCurrentLocalDateTime(LocalDateTime.now());

        Emprestimo emprestimo = null;
        try {
            emprestimo = EmprestimoController.criarEmprestimo(this.leitor, this.livro);
        } catch (NotEnoughPermissionException | UserIsBlockedException | EmprestimoException e) {
            e.printStackTrace();
        }

        assertNotNull(emprestimo);
        assertEquals(emprestimo.getLeitor(), leitor);
        assertEquals(emprestimo.getLivro(), livro);
        assertEquals(emprestimo.getStatus(), EmprestimoStatus.PENDENTE);

        try {
            EmprestimoController.confirmarRecebimentoEmprestimo(emprestimo);
        } catch (NotEnoughPermissionException | EmprestimoException e) {
            throw new RuntimeException(e);
        }

        MultaController.aplicarMultas();
        MultaController.desbloquearMultas();

        assertEquals(emprestimo.getStatus(), EmprestimoStatus.CONCLUIDO);
        assertEquals(emprestimo.getLeitor().getStatus(), LeitorStatus.LIBERADO);
    }

    @Test
    public void cancelarEmprestimo(){
        try {
            LoginController.login("sergiosergio", "senha", UserPermissao.BIBLIOTECARIO);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        TimeController.setCurrentLocalDateTime(LocalDateTime.now());

        Emprestimo emprestimo = null;
        try {
            emprestimo = EmprestimoController.criarEmprestimo(this.leitor, this.livro);
        } catch (NotEnoughPermissionException | UserIsBlockedException | EmprestimoException e) {
            e.printStackTrace();
        }

        try {
            EmprestimoController.cancelarEmprestimo(emprestimo);
        } catch (NotEnoughPermissionException e) {
            e.printStackTrace();
        }

        assertEquals(emprestimo.getStatus(), EmprestimoStatus.CANCELADO);
    }

    @Test
    public void verificarMultaTest(){
        try {
            LoginController.login("sergiosergio", "senha", UserPermissao.BIBLIOTECARIO);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        TimeController.setCurrentLocalDateTime(LocalDateTime.now());

        Emprestimo emprestimo = null;
        try {
            emprestimo = EmprestimoController.criarEmprestimo(this.leitor, this.livro);
        } catch (NotEnoughPermissionException | UserIsBlockedException | EmprestimoException e) {
            e.printStackTrace();
        }

        TimeController.setCurrentLocalDateTime(LocalDateTime.now().plusDays(10));

        MultaController.aplicarMultas();
        MultaController.desbloquearMultas();

        try {
            EmprestimoController.confirmarRecebimentoEmprestimo(emprestimo);
        } catch (NotEnoughPermissionException | EmprestimoException e) {
            throw new RuntimeException(e);
        }

        MultaController.aplicarMultas();
        MultaController.desbloquearMultas();

        assertEquals(emprestimo.getStatus(), EmprestimoStatus.CONCLUIDO);
        assertEquals(emprestimo.getLeitor().getStatus(), LeitorStatus.MULTADO);

        TimeController.setCurrentLocalDateTime(LocalDateTime.now().plusDays(300));

        MultaController.aplicarMultas();
        MultaController.desbloquearMultas();

        assertEquals(emprestimo.getLeitor().getStatus(), LeitorStatus.LIBERADO);
    }
}
