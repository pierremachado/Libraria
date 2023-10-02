package main.java.libraria.controllers;

import main.java.libraria.dao.DAO;
import main.java.libraria.errors.IncorrectCredentialsException;
import main.java.libraria.model.Convidado;
import main.java.libraria.model.Usuario;
import main.java.libraria.model.enums.UserPermissao;
import main.java.libraria.util.UserPermissionValidator;

public class LoginController {
    private static Usuario currentLoggedUser;

    public static Usuario getCurrentLoggedUser() {
        return currentLoggedUser;
    }

    public static void login(String id, String senha, UserPermissao permissao) throws IncorrectCredentialsException {
        Usuario user = null;

        switch (permissao) {
            case CONVIDADO -> {
                currentLoggedUser = new Convidado();
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

    public static void logoff() {
        currentLoggedUser = null;
    }

    public static boolean validatePermissao(UserPermissao expected) {
        return UserPermissionValidator.validate(currentLoggedUser.getPermissao(), expected);
    }

    public static boolean verificarLogin() {
        return currentLoggedUser != null;
    }

    public static boolean verificarAdministrador() {
        return verificarLogin() && validatePermissao(UserPermissao.ADMINISTRADOR);
    }

    public static boolean verificarOperador() {
        return verificarLogin() && (validatePermissao(UserPermissao.ADMINISTRADOR) || validatePermissao(UserPermissao.BIBLIOTECARIO));
    }

    public static boolean verificarLeitor() {
        return verificarLogin() && validatePermissao(UserPermissao.LEITOR);
    }

    public static boolean verificarConvidado() {
        return verificarLogin() && validatePermissao(UserPermissao.CONVIDADO);
    }
}
