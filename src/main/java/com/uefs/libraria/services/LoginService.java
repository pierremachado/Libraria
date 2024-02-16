package com.uefs.libraria.services;

import com.uefs.libraria.dao.DAO;
import com.uefs.libraria.exceptions.IncorrectCredentialsException;
import com.uefs.libraria.exceptions.MustLogoutException;
import com.uefs.libraria.model.Guest;
import com.uefs.libraria.model.User;
import com.uefs.libraria.model.enums.UserPermission;
import com.uefs.libraria.util.UserPermissionValidator;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class LoginService {
    private static User currentLoggedUser;

    public static User getCurrentLoggedUser() {
        return currentLoggedUser;
    }

    /**
     * Método de realizar login no sistema, tanto como operador, como leitor ou como convidado
     *
     * @param id        ID do usuário que irá realizar o login
     * @param senha     A senha do usuário
     * @param permissao O nível de acesso do usuário
     * @throws MustLogoutException           Caso já tenha um usuário logado
     * @throws IncorrectCredentialsException Caso o usuário ou a senha informados estejam errados
     */
    public static void login(String id, String senha, UserPermission permissao) throws MustLogoutException, IncorrectCredentialsException {
        if (currentLoggedUser != null) {
            throw new MustLogoutException("Necessário fazer logout");
        }

        User user = null;

        switch (permissao) {
            case CONVIDADO -> {
                currentLoggedUser = new Guest();
                return;
            }
            case LEITOR -> user = DAO.getLeitorDAO().findID(id);
            case BIBLIOTECARIO -> user = DAO.getBibliotecarioDAO().findID(id);
            case ADMINISTRADOR -> user = DAO.getAdministradorDAO().findID(id);
        }

        if (user == null || !user.getSenha().equals(senha)) {
            throw new IncorrectCredentialsException();
        }

        currentLoggedUser = user;
    }


    /**
     * Método de realizar logoff do sistema.
     */
    public static void logoff() {
        currentLoggedUser = null;
    }


    /**
     * Método de verificar a permissão do usuário logado atualmente.
     *
     * @param expected A permissão esperada
     * @return Um valor verdadeiro ou falso caso a permissão seja a esperada ou não, respectivamente
     */
    public static boolean validatePermissao(UserPermission expected) {
        return UserPermissionValidator.validate(currentLoggedUser.getPermissao(), expected);
    }


    /**
     * Método para verificar se o usuário atual está logado ou não
     *
     * @return Um valor booleano que diz se o usuário está logado ou não
     */
    public static boolean verificarLogin() {
        return currentLoggedUser != null;
    }

    /**
     * Verificação se o usuário atual é um administrador
     *
     * @return Verdadeiro ou falso
     */
    public static boolean verificarAdministrador() {
        return verificarLogin() && validatePermissao(UserPermission.ADMINISTRADOR);
    }

    /**
     * Verificação se o usuário atual é um administrador ou um bibliotecário
     *
     * @return Verdadeiro ou falso
     */
    public static boolean verificarOperador() {
        return verificarLogin() && (validatePermissao(UserPermission.ADMINISTRADOR) || validatePermissao(UserPermission.BIBLIOTECARIO));
    }

    /**
     * Verificação se o usuário atual é um leitor
     *
     * @return Verdadeiro ou falso
     */
    public static boolean verificarLeitor() {
        return verificarLogin() && validatePermissao(UserPermission.LEITOR);
    }

    /**
     * Verificação se o usuário atual é um convidado
     *
     * @return Verdadeiro ou falso
     */
    public static boolean verificarConvidado() {
        return verificarLogin() && validatePermissao(UserPermission.CONVIDADO);
    }
}
