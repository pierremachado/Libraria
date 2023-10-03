package test;

import main.java.libraria.controllers.LivroController;
import main.java.libraria.controllers.LoginController;
import main.java.libraria.controllers.ReservaController;
import main.java.libraria.controllers.TimeController;
import main.java.libraria.dao.DAO;
import main.java.libraria.errors.*;
import main.java.libraria.model.*;
import main.java.libraria.model.enums.ReservaStatus;
import main.java.libraria.model.enums.UserPermissao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class ReservaManagementTest {
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
        DAO.getLivroDAO().create(new Livro("Eu sei o que você fez verão passado", "Fulano de Tal", "Editora", "123456", Year.of(2022), "Mistério", 2, 10));
        DAO.getLivroDAO().create(new Livro("Eu sei o que você fez nesse verão", "Fulano de Tal", "Editora", "654321", Year.of(2023), "Mistério", 1, 28));
        DAO.getLivroDAO().create(new Livro("Eu sei o que você vai fazer verão que vem", "Fulano de Tal", "Editora", "123123", Year.of(2024), "Mistério", 0, 3));
    }
    @AfterEach
    public void tearDown(){
        LoginController.logoff();
        DAO.limparDadosDAO();
    }

    @Test
    public void criarReserva(){
        try {
            LoginController.login("amandaamanda", "ahnes", UserPermissao.LEITOR);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        assertEquals(UserPermissao.LEITOR, LoginController.getCurrentLoggedUser().getPermissao());
        assertTrue(LoginController.verificarLeitor());

        assertThrows(BookException.class, () -> {
            ReservaController.criarReserva(LivroController.pesquisarLivroPorChave("Eu sei o que você").get(0));
        });

        Reserva reserva = null;
        try{
            reserva = ReservaController.criarReserva(LivroController.pesquisarLivroPorChave("Eu sei o que você").get(2));
        }
        catch (UserIsBlockedException | NotEnoughPermissionException | BookException | ReservaException e){
            e.printStackTrace();
        }

        assertNotNull(reserva);
        assertEquals(reserva.getStatus(), ReservaStatus.ESPERA);

        LoginController.logoff();

        try{
            LoginController.login("sergiosergio", "senha", UserPermissao.BIBLIOTECARIO);
        } catch (IncorrectCredentialsException | MustLogoutException e) {
            e.printStackTrace();
        }

        try{
            LivroController.livroAumentarQuantidade(LivroController.pesquisarLivroPorChave("Eu sei o que você").get(2), 2);
        } catch (NotEnoughPermissionException e){
            e.printStackTrace();
        }

        TimeController.setCurrentLocalDateTime(LocalDateTime.now());
        ReservaController.atualizarReservas();

        assertEquals(reserva.getStatus(), ReservaStatus.LIBERADO);
    }

    @Test
    public void cancelarReserva(){
        try {
            LoginController.login("amandaamanda", "ahnes", UserPermissao.LEITOR);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        assertEquals(UserPermissao.LEITOR, LoginController.getCurrentLoggedUser().getPermissao());
        assertTrue(LoginController.verificarLeitor());

        assertThrows(BookException.class, () -> {
            ReservaController.criarReserva(LivroController.pesquisarLivroPorChave("Eu sei o que você").get(0));
        });

        Reserva reserva = null;
        try{
            reserva = ReservaController.criarReserva(LivroController.pesquisarLivroPorChave("Eu sei o que você").get(2));
        }
        catch (UserIsBlockedException | NotEnoughPermissionException | BookException | ReservaException e){
            e.printStackTrace();
        }

        try {
            ReservaController.cancelarReserva(reserva);
        } catch (NotEnoughPermissionException | ReservaException e) {
            e.printStackTrace();
        }

        assertNotNull(reserva);
        assertEquals(reserva.getStatus(), ReservaStatus.CANCELADO);
        assertEquals(0, LivroController.pesquisarLivroPorChave("Eu sei o que você").get(2).getQuantidadeDisponiveis());
    }
}
