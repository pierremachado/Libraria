package main.java.libraria.model;

import main.java.libraria.dao.DAO;
import main.java.libraria.error.IncorrectCredentials;
import main.java.libraria.model.enums.UserPermissao;

import java.time.LocalDateTime;

public class Sistema {
    private Usuario currentLoggedUser;

    private LocalDateTime currentDateTime;

    public void login(String id, String senha, UserPermissao permissao) throws IncorrectCredentials{
        Usuario user = null;

        switch (permissao) {
            case CONVIDADO -> {
                this.currentLoggedUser = new Convidado();
                return;
            }
            case LEITOR -> user = DAO.getLeitorDAO().findID(id);
            case BIBLIOTECARIO -> user = DAO.getBibliotecarioDAO().findID(id);
            case ADMINISTRADOR -> user = DAO.getAdministradorDAO().findID(id);
        }

        if (user == null || !user.getSenha().equals(senha)){
            throw new IncorrectCredentials();
        }

        this.currentLoggedUser = user;
    }

    public void logoff(){
        this.currentLoggedUser = null;
    }
}
