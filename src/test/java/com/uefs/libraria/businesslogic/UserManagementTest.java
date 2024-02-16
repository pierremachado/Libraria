package com.uefs.libraria.businesslogic;

import com.uefs.libraria.model.*;
import com.uefs.libraria.model.enums.*;
import com.uefs.libraria.dao.*;
import com.uefs.libraria.services.*;
import com.uefs.libraria.exceptions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class UserManagementTest {

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
        DAO.limparDadosDAO();
        LoginService.logoff();
    }

    @Test
    public void testLoginAndLogoff(){
        try {
            LoginService.login("admin", "admin", UserPermission.ADMINISTRADOR);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        assertNotNull(LoginService.getCurrentLoggedUser());
        assertEquals(LoginService.getCurrentLoggedUser(), adm);
        assertTrue(LoginService.verificarAdministrador());
        assertTrue(LoginService.verificarOperador());
        assertFalse(LoginService.verificarLeitor());
        assertFalse(LoginService.verificarConvidado());

        LoginService.logoff();

        assertNull(LoginService.getCurrentLoggedUser());

        try {
            LoginService.login("sergiosergio", "senha", UserPermission.BIBLIOTECARIO);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        assertNotNull(LoginService.getCurrentLoggedUser());
        assertEquals(LoginService.getCurrentLoggedUser(), bib);
        assertFalse(LoginService.verificarAdministrador());
        assertTrue(LoginService.verificarOperador());
        assertFalse(LoginService.verificarLeitor());
        assertFalse(LoginService.verificarConvidado());

        LoginService.logoff();

        assertNull(LoginService.getCurrentLoggedUser());

        try {
            LoginService.login("amandaamanda", "ahnes", UserPermission.LEITOR);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        assertNotNull(LoginService.getCurrentLoggedUser());
        assertEquals(LoginService.getCurrentLoggedUser(), reader);
        assertFalse(LoginService.verificarAdministrador());
        assertFalse(LoginService.verificarOperador());
        assertTrue(LoginService.verificarLeitor());
        assertFalse(LoginService.verificarConvidado());

        LoginService.logoff();

        assertNull(LoginService.getCurrentLoggedUser());

        try {
            LoginService.login("", "", UserPermission.CONVIDADO);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        assertNotNull(LoginService.getCurrentLoggedUser());
        assertEquals(LoginService.getCurrentLoggedUser(), guest);
        assertFalse(LoginService.verificarAdministrador());
        assertFalse(LoginService.verificarOperador());
        assertFalse(LoginService.verificarLeitor());
        assertTrue(LoginService.verificarConvidado());

        assertThrows(MustLogoutException.class, () -> {
            LoginService.login("admin", "admin", UserPermission.ADMINISTRADOR);
        });

        LoginService.logoff();

        assertNull(LoginService.getCurrentLoggedUser());

        assertThrows(IncorrectCredentialsException.class, () -> {
            LoginService.login("admin", "admon", UserPermission.ADMINISTRADOR);
        });

        assertThrows(IncorrectCredentialsException.class, () -> {
            LoginService.login("admid", "admin", UserPermission.ADMINISTRADOR);
        });
    }

    @Test
    public void userCrudTest() {
        try {
            LoginService.login("admin", "admin", UserPermission.ADMINISTRADOR);
        } catch (MustLogoutException | IncorrectCredentialsException e) {
            e.printStackTrace();
        }

        assertNotNull(LoginService.getCurrentLoggedUser());
        assertTrue(LoginService.verificarAdministrador());

        assertDoesNotThrow(() -> {
            Reader reader1 = (Reader) UserService.criarUsuario("rafael", "ribeiro", "rafaelribeiro", "psswrd", "", "", UserPermission.LEITOR);
        });

        assertThrows(IdAlreadyExistsException.class, () -> {
            Reader reader1 = (Reader) UserService.criarUsuario("amanda", "amanda", "amandaamanda", "ahnes", "", "", UserPermission.LEITOR);
        });

        Reader reader1 = null;
        try {
            reader1 = (Reader) UserService.getUsuario("rafaelribeiro", UserPermission.LEITOR);
        } catch (NotEnoughPermissionException | IdNotFoundException e) {
            e.printStackTrace();
        }

        assertNotNull(reader1);

        User user1 = null;
        try {
            user1 = UserService.getUsuario("amandaamanda", UserPermission.LEITOR);
        } catch (IdNotFoundException | NotEnoughPermissionException e) {
            e.printStackTrace();
        }

        assertNotNull(user1);

        try {
            UserService.removerUsuario(user1);
        } catch (NotEnoughPermissionException | OngoingReaderLoansException | RemoveSelfAttemptException e) {
            e.printStackTrace();
        }

        assertThrows(IdNotFoundException.class, () -> {
            UserService.getUsuario("amandaamanda", UserPermission.LEITOR);
        });

        try{
            UserService.bloquearLeitor(reader1);
        }
        catch (UserIsBlockedException | NotEnoughPermissionException e){
            e.printStackTrace();
        }

        assertSame(reader1.getStatus(), ReaderStatus.BANIDO);

        Reader finalReader = reader1;
        assertThrows(UserIsBlockedException.class, () -> {
            UserService.bloquearLeitor(finalReader);
        });

        try{
            UserService.desbloquearLeitor(reader1);
        } catch(NotEnoughPermissionException e){
            e.printStackTrace();
        }

        assertSame(reader1.getStatus(), ReaderStatus.LIBERADO);

        assertThrows(RemoveSelfAttemptException.class, () -> {
           UserService.removerUsuario(adm);
        });

        LoginService.logoff();

        try{
            LoginService.login("sergiosergio", "senha", UserPermission.BIBLIOTECARIO);
        } catch (MustLogoutException | IncorrectCredentialsException e){
            e.printStackTrace();
        }

        assertThrows(NotEnoughPermissionException.class, () -> {
            UserService.bloquearLeitor(finalReader);
        });
    }
}
