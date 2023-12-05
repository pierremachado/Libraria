package test.businesslogic;

import main.java.libraria.controllers.GerenciadorUsuarioController;
import main.java.libraria.controllers.LoginController;
import main.java.libraria.dao.DAO;
import main.java.libraria.errors.*;
import main.java.libraria.model.*;
import main.java.libraria.model.enums.LeitorStatus;
import main.java.libraria.model.enums.UserPermissao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class UserManagementTest {

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
        DAO.limparDadosDAO();
        LoginController.logoff();
    }

    @Test
    public void testLoginAndLogoff(){
        try {
            LoginController.login("admin", "admin", UserPermissao.ADMINISTRADOR);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        assertNotNull(LoginController.getCurrentLoggedUser());
        assertEquals(LoginController.getCurrentLoggedUser(), adm);
        assertTrue(LoginController.verificarAdministrador());
        assertTrue(LoginController.verificarOperador());
        assertFalse(LoginController.verificarLeitor());
        assertFalse(LoginController.verificarConvidado());

        LoginController.logoff();

        assertNull(LoginController.getCurrentLoggedUser());

        try {
            LoginController.login("sergiosergio", "senha", UserPermissao.BIBLIOTECARIO);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        assertNotNull(LoginController.getCurrentLoggedUser());
        assertEquals(LoginController.getCurrentLoggedUser(), bib);
        assertFalse(LoginController.verificarAdministrador());
        assertTrue(LoginController.verificarOperador());
        assertFalse(LoginController.verificarLeitor());
        assertFalse(LoginController.verificarConvidado());

        LoginController.logoff();

        assertNull(LoginController.getCurrentLoggedUser());

        try {
            LoginController.login("amandaamanda", "ahnes", UserPermissao.LEITOR);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        assertNotNull(LoginController.getCurrentLoggedUser());
        assertEquals(LoginController.getCurrentLoggedUser(), leitor);
        assertFalse(LoginController.verificarAdministrador());
        assertFalse(LoginController.verificarOperador());
        assertTrue(LoginController.verificarLeitor());
        assertFalse(LoginController.verificarConvidado());

        LoginController.logoff();

        assertNull(LoginController.getCurrentLoggedUser());

        try {
            LoginController.login("", "", UserPermissao.CONVIDADO);
        } catch (MustLogoutException | IncorrectCredentialsException e1){
            e1.printStackTrace();
        }

        assertNotNull(LoginController.getCurrentLoggedUser());
        assertEquals(LoginController.getCurrentLoggedUser(), convidado);
        assertFalse(LoginController.verificarAdministrador());
        assertFalse(LoginController.verificarOperador());
        assertFalse(LoginController.verificarLeitor());
        assertTrue(LoginController.verificarConvidado());

        assertThrows(MustLogoutException.class, () -> {
            LoginController.login("admin", "admin", UserPermissao.ADMINISTRADOR);
        });

        LoginController.logoff();

        assertNull(LoginController.getCurrentLoggedUser());

        assertThrows(IncorrectCredentialsException.class, () -> {
            LoginController.login("admin", "admon", UserPermissao.ADMINISTRADOR);
        });

        assertThrows(IncorrectCredentialsException.class, () -> {
            LoginController.login("admid", "admin", UserPermissao.ADMINISTRADOR);
        });
    }

    @Test
    public void userCrudTest() {
        try {
            LoginController.login("admin", "admin", UserPermissao.ADMINISTRADOR);
        } catch (MustLogoutException | IncorrectCredentialsException e) {
            e.printStackTrace();
        }

        assertNotNull(LoginController.getCurrentLoggedUser());
        assertTrue(LoginController.verificarAdministrador());

        assertDoesNotThrow(() -> {
            Leitor leitor1 = (Leitor) GerenciadorUsuarioController.criarUsuario("rafael", "ribeiro", "rafaelribeiro", "psswrd", "", "", UserPermissao.LEITOR);
        });

        assertThrows(IdAlreadyExistsException.class, () -> {
            Leitor leitor1 = (Leitor) GerenciadorUsuarioController.criarUsuario("amanda", "amanda", "amandaamanda", "ahnes", "", "", UserPermissao.LEITOR);
        });

        Leitor leitor1 = null;
        try {
            leitor1 = (Leitor) GerenciadorUsuarioController.getUsuario("rafaelribeiro", UserPermissao.LEITOR);
        } catch (NotEnoughPermissionException | IdNotFoundException e) {
            e.printStackTrace();
        }

        assertNotNull(leitor1);

        Usuario usuario1 = null;
        try {
            usuario1 = GerenciadorUsuarioController.getUsuario("amandaamanda", UserPermissao.LEITOR);
        } catch (IdNotFoundException | NotEnoughPermissionException e) {
            e.printStackTrace();
        }

        assertNotNull(usuario1);

        try {
            GerenciadorUsuarioController.removerUsuario(usuario1);
        } catch (NotEnoughPermissionException | OngoingReaderLoansException | RemoveSelfAttemptException e) {
            e.printStackTrace();
        }

        assertThrows(IdNotFoundException.class, () -> {
            GerenciadorUsuarioController.getUsuario("amandaamanda", UserPermissao.LEITOR);
        });

        try{
            GerenciadorUsuarioController.bloquearLeitor(leitor1);
        }
        catch (UserIsBlockedException | NotEnoughPermissionException e){
            e.printStackTrace();
        }

        assertSame(leitor1.getStatus(), LeitorStatus.BANIDO);

        Leitor finalLeitor = leitor1;
        assertThrows(UserIsBlockedException.class, () -> {
            GerenciadorUsuarioController.bloquearLeitor(finalLeitor);
        });

        try{
            GerenciadorUsuarioController.desbloquearLeitor(leitor1);
        } catch(NotEnoughPermissionException e){
            e.printStackTrace();
        }

        assertSame(leitor1.getStatus(), LeitorStatus.LIBERADO);

        assertThrows(RemoveSelfAttemptException.class, () -> {
           GerenciadorUsuarioController.removerUsuario(adm);
        });

        LoginController.logoff();

        try{
            LoginController.login("sergiosergio", "senha", UserPermissao.BIBLIOTECARIO);
        } catch (MustLogoutException | IncorrectCredentialsException e){
            e.printStackTrace();
        }

        assertThrows(NotEnoughPermissionException.class, () -> {
            GerenciadorUsuarioController.bloquearLeitor(finalLeitor);
        });
    }
}
