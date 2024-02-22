package com.uefs.libraria.businesslogic;

import com.uefs.libraria.model.*;
import com.uefs.libraria.model.enums.*;
import com.uefs.libraria.dao.*;
import com.uefs.libraria.services.*;
import com.uefs.libraria.exceptions.*;

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
    private Administrator adm;
    private Librarian bib;
    private Reader reader;
    private Guest guest;
    @BeforeEach
    public void setUp(){
        adm = new Administrator("admin", "", "admin", "admin");
        bib = new Librarian("sérgio", "sérgio", "sergiosergio", "senha");
        reader = new Reader("amanda", "amanda", "amandaamanda", "ahnes", "", "");
        guest = new Guest();

        DAO.getAdministradorDAO().create(adm);
        DAO.getBibliotecarioDAO().create(bib);
        DAO.getLeitorDAO().create(reader);
    }
    @AfterEach
    public void tearDown(){
        LoginService.logoff();
        DAO.limparDadosDAO();
    }

    @Test
    public void criarLivroTest(){
        try {
            LoginService.login("admin", "admin", UserPermission.ADMINISTRADOR);
        } catch (IncorrectCredentialsException | MustLogoutException e){
            e.printStackTrace();
        }

        Book book = new Book("The Fault in Our Stars", "John Green", "Editora", "12345", Year.of(2014), "Romance", 10, 0);

        Book book1 = null;
        try {
            book1 = BookService.criarLivro("The Fault in Our Stars", "John Green", "Editora", "12345", Year.of(2014), "Romance", 10);
        } catch (NotEnoughPermissionException | BookAmountUnderZeroException | IdAlreadyExistsException e) {
            e.printStackTrace();
        }

        assertThrows(IdAlreadyExistsException.class, () -> {
           Book book2 = BookService.criarLivro("The Fault in Our Stars", "John Green", "Editora", "12345", Year.of(2014), "Romance", 10);
        });

        assertNotNull(book1);
        assertEquals(book1, book);

        Book book3 = null;
        try {
            book3 = BookService.criarLivro("A Culpa é das Estrelas", "John Verde", "Editora", "12346", Year.of(2014), "Romance", 10);
        } catch (NotEnoughPermissionException | BookAmountUnderZeroException | IdAlreadyExistsException e) {
            e.printStackTrace();
        }

        assertNotNull(book3);
        assertNotEquals(book1, book3);
    }

    @Test
    public void pesquisarLivroTest(){
        try {
            LoginService.login("admin", "admin", UserPermission.ADMINISTRADOR);
        } catch (IncorrectCredentialsException | MustLogoutException e){
            e.printStackTrace();
        }

        Book book1 = null;
        try {
            book1 = BookService.criarLivro("The Fault in Our Stars", "John Green", "Editora", "12345", Year.of(2014), "Romance", 10);
        } catch (NotEnoughPermissionException | BookAmountUnderZeroException | IdAlreadyExistsException e) {
            e.printStackTrace();
        }

        Book book3 = null;
        try {
            book3 = BookService.criarLivro("A Culpa é das Estrelas", "John Verde", "Editora", "12346", Year.of(2014), "Romance", 10);
        } catch (NotEnoughPermissionException | BookAmountUnderZeroException | IdAlreadyExistsException e) {
            e.printStackTrace();
        }

        List<Book> livrosPesquisados = BookService.pesquisarLivroPorChave("John");
        assertEquals(2, livrosPesquisados.size());

        livrosPesquisados = BookService.pesquisarLivroPorChave("Romance");
        assertEquals(2, livrosPesquisados.size());

        livrosPesquisados = BookService.pesquisarLivroPorChave("A Culpa é");
        assertEquals(1, livrosPesquisados.size());
        assertEquals(3, livrosPesquisados.get(0).getVezesPesquisado());

        Book book = BookService.pesquisarLivroPorIsbn("12346");
        assertEquals(book, livrosPesquisados.get(0));

        LoginService.logoff();

        try {
            LoginService.login("amandaamanda", "ahnes", UserPermission.LEITOR);
        } catch (IncorrectCredentialsException | MustLogoutException e){
            e.printStackTrace();
        }

        book = BookService.pesquisarLivroPorIsbn("12346");
        assertNotNull(book);
    }

    @Test
    public void removerLivroTest(){
        try {
            LoginService.login("admin", "admin", UserPermission.ADMINISTRADOR);
        } catch (IncorrectCredentialsException | MustLogoutException e){
            e.printStackTrace();
        }

        Book book1 = null;
        try {
            book1 = BookService.criarLivro("The Fault in Our Stars", "John Green", "Editora", "12345", Year.of(2014), "Romance", 10);
        } catch (NotEnoughPermissionException | BookAmountUnderZeroException | IdAlreadyExistsException e) {
            e.printStackTrace();
        }

        Book book3 = null;
        try {
            book3 = BookService.criarLivro("A Culpa é das Estrelas", "John Verde", "Editora", "12346", Year.of(2014), "Romance", 10);
        } catch (NotEnoughPermissionException | BookAmountUnderZeroException | IdAlreadyExistsException e) {
            e.printStackTrace();
        }

        try {
            BookService.removerLivro(BookService.pesquisarLivroPorIsbn("12346"));
        } catch (NotEnoughPermissionException e){
            e.printStackTrace();
        } catch (OngoingLoansException e) {
            throw new RuntimeException(e);
        }

        assertNull(BookService.pesquisarLivroPorIsbn("12346"));
    }
}
