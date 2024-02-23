package com.uefs.libraria.businesslogic;

import com.uefs.libraria.model.*;
import com.uefs.libraria.model.enums.*;
import com.uefs.libraria.dao.*;
import com.uefs.libraria.services.*;
import com.uefs.libraria.exceptions.*;

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
public class ReservationManagementTest {
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
        DAO.getLivroDAO().create(new Book("Eu sei o que você fez verão passado", "Fulano de Tal", "Editora", "123456", Year.of(2022), "Mistério", 2, 10));
        DAO.getLivroDAO().create(new Book("Eu sei o que você fez nesse verão", "Fulano de Tal", "Editora", "654321", Year.of(2023), "Mistério", 1, 28));
        DAO.getLivroDAO().create(new Book("Eu sei o que você vai fazer verão que vem", "Fulano de Tal", "Editora", "123123", Year.of(2024), "Mistério", 0, 3));
    }
    @AfterEach
    public void tearDown(){
        LoginService.logoff();
        DAO.limparDadosDAO();
    }

    @Test
    public void criarReserva(){
        try {
            LoginService.login("amandaamanda", "ahnes", UserPermission.LEITOR);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        assertEquals(UserPermission.LEITOR, LoginService.getCurrentLoggedUser().getPermissao());
        assertTrue(LoginService.verificarLeitor());

        assertThrows(BookException.class, () -> {
            ReservationService.criarReserva(BookService.pesquisarLivroPorChave("Eu sei o que você").get(0));
        });

        Reservation reservation = null;
        try{
            reservation = ReservationService.criarReserva(BookService.pesquisarLivroPorChave("Eu sei o que você").get(2));
        }
        catch (UserIsBlockedException | NotEnoughPermissionException | ReservationException e){
            e.printStackTrace();
        }

        assertNotNull(reservation);
        assertEquals(reservation.getStatus(), ReservationStatus.RESERVADO);

        LoginService.logoff();

        try{
            LoginService.login("sergiosergio", "senha", UserPermission.BIBLIOTECARIO);
        } catch (IncorrectCredentialsException | MustLogoutException e) {
            e.printStackTrace();
        }

        try{
            BookService.livroAumentarQuantidade(BookService.pesquisarLivroPorChave("Eu sei o que você").get(2), 2);
        } catch (NotEnoughPermissionException e){
            e.printStackTrace();
        }

        TimeService.setCurrentLocalDateTime(LocalDateTime.now());
        ReservationService.atualizarReservas();

        assertEquals(reservation.getStatus(), ReservationStatus.LIBERADO);
    }

    @Test
    public void cancelarReserva(){
        try {
            LoginService.login("amandaamanda", "ahnes", UserPermission.LEITOR);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        assertEquals(UserPermission.LEITOR, LoginService.getCurrentLoggedUser().getPermissao());
        assertTrue(LoginService.verificarLeitor());

        assertThrows(BookException.class, () -> {
            ReservationService.criarReserva(BookService.pesquisarLivroPorChave("Eu sei o que você").get(0));
        });

        Reservation reservation = null;
        try{
            reservation = ReservationService.criarReserva(BookService.pesquisarLivroPorChave("Eu sei o que você").get(2));
        }
        catch (UserIsBlockedException | NotEnoughPermissionException | ReservationException e){
            e.printStackTrace();
        }

        try {
            ReservationService.cancelarReserva(reservation);
        } catch (NotEnoughPermissionException | ReservationException e) {
            e.printStackTrace();
        }

        assertNotNull(reservation);
        assertEquals(reservation.getStatus(), ReservationStatus.CANCELADO);
        assertEquals(0, BookService.pesquisarLivroPorChave("Eu sei o que você").get(2).getQuantidadeDisponiveis());
    }
}
