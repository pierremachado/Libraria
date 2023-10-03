package test;

import main.java.libraria.controllers.LivroController;
import main.java.libraria.controllers.LoginController;
import main.java.libraria.dao.DAO;
import main.java.libraria.errors.*;
import main.java.libraria.model.*;
import main.java.libraria.model.enums.UserPermissao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class BookManagementTest {
    private Administrador adm;
    private Bibliotecario bib;
    private Leitor leitor;
    private Convidado convidado;
    @BeforeEach
    public void setUp(){
        adm = new Administrador("admin", "", "admin", "admin");
        bib = new Bibliotecario("sérgio", "sérgio", "sergiosergio", "senha");
        leitor = new Leitor("amanda", "amanda", "amandaamanda", "ahnes", "", "");
        convidado = new Convidado();

        DAO.getAdministradorDAO().create(adm);
        DAO.getBibliotecarioDAO().create(bib);
        DAO.getLeitorDAO().create(leitor);
    }
    @AfterEach
    public void tearDown(){
        LoginController.logoff();
        DAO.limparDadosDAO();
    }

    @Test
    public void criarLivroTest(){
        try {
            LoginController.login("admin", "admin", UserPermissao.ADMINISTRADOR);
        } catch (IncorrectCredentialsException | MustLogoutException e){
            e.printStackTrace();
        }

        Livro livro = new Livro("The Fault in Our Stars", "John Green", "Editora", "12345", Year.of(2014), "Romance", 10, 0);

        Livro livro1 = null;
        try {
            livro1 = LivroController.criarLivro("The Fault in Our Stars", "John Green", "Editora", "12345", Year.of(2014), "Romance", 10);
        } catch (NotEnoughPermissionException | BookAmountUnderZeroException | IdAlreadyExistsException e) {
            e.printStackTrace();
        }

        assertThrows(IdAlreadyExistsException.class, () -> {
           Livro livro2 = LivroController.criarLivro("The Fault in Our Stars", "John Green", "Editora", "12345", Year.of(2014), "Romance", 10);
        });

        assertNotNull(livro1);
        assertEquals(livro1, livro);

        Livro livro3 = null;
        try {
            livro3 = LivroController.criarLivro("A Culpa é das Estrelas", "John Verde", "Editora", "12346", Year.of(2014), "Romance", 10);
        } catch (NotEnoughPermissionException | BookAmountUnderZeroException | IdAlreadyExistsException e) {
            e.printStackTrace();
        }

        assertNotNull(livro3);
        assertNotEquals(livro1, livro3);
    }

    @Test
    public void pesquisarLivroTest(){
        try {
            LoginController.login("admin", "admin", UserPermissao.ADMINISTRADOR);
        } catch (IncorrectCredentialsException | MustLogoutException e){
            e.printStackTrace();
        }

        Livro livro1 = null;
        try {
            livro1 = LivroController.criarLivro("The Fault in Our Stars", "John Green", "Editora", "12345", Year.of(2014), "Romance", 10);
        } catch (NotEnoughPermissionException | BookAmountUnderZeroException | IdAlreadyExistsException e) {
            e.printStackTrace();
        }

        Livro livro3 = null;
        try {
            livro3 = LivroController.criarLivro("A Culpa é das Estrelas", "John Verde", "Editora", "12346", Year.of(2014), "Romance", 10);
        } catch (NotEnoughPermissionException | BookAmountUnderZeroException | IdAlreadyExistsException e) {
            e.printStackTrace();
        }

        List<Livro> livrosPesquisados = LivroController.pesquisarLivroPorChave("John");
        assertEquals(2, livrosPesquisados.size());

        livrosPesquisados = LivroController.pesquisarLivroPorChave("Romance");
        assertEquals(2, livrosPesquisados.size());

        livrosPesquisados = LivroController.pesquisarLivroPorChave("A Culpa é");
        assertEquals(1, livrosPesquisados.size());
        assertEquals(3, livrosPesquisados.get(0).getVezesPesquisado());

        Livro livro = LivroController.pesquisarLivroPorIsbn("12346");
        assertEquals(livro, livrosPesquisados.get(0));

        LoginController.logoff();

        try {
            LoginController.login("amandaamanda", "ahnes", UserPermissao.LEITOR);
        } catch (IncorrectCredentialsException | MustLogoutException e){
            e.printStackTrace();
        }

        livro = LivroController.pesquisarLivroPorIsbn("12346");
        assertNotNull(livro);
    }

    @Test
    public void removerLivroTest(){
        try {
            LoginController.login("admin", "admin", UserPermissao.ADMINISTRADOR);
        } catch (IncorrectCredentialsException | MustLogoutException e){
            e.printStackTrace();
        }

        Livro livro1 = null;
        try {
            livro1 = LivroController.criarLivro("The Fault in Our Stars", "John Green", "Editora", "12345", Year.of(2014), "Romance", 10);
        } catch (NotEnoughPermissionException | BookAmountUnderZeroException | IdAlreadyExistsException e) {
            e.printStackTrace();
        }

        Livro livro3 = null;
        try {
            livro3 = LivroController.criarLivro("A Culpa é das Estrelas", "John Verde", "Editora", "12346", Year.of(2014), "Romance", 10);
        } catch (NotEnoughPermissionException | BookAmountUnderZeroException | IdAlreadyExistsException e) {
            e.printStackTrace();
        }

        try {
            LivroController.removerLivro(LivroController.pesquisarLivroPorIsbn("12346"));
        } catch (NotEnoughPermissionException e){
            e.printStackTrace();
        }

        assertNull(LivroController.pesquisarLivroPorIsbn("12346"));
    }
}
