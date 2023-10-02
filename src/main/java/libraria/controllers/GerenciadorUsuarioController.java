package main.java.libraria.controllers;

import main.java.libraria.dao.DAO;
import main.java.libraria.errors.*;
import main.java.libraria.model.*;
import main.java.libraria.model.enums.EmprestimoStatus;
import main.java.libraria.model.enums.LeitorStatus;
import main.java.libraria.model.enums.ReservaStatus;
import main.java.libraria.model.enums.UserPermissao;

public class GerenciadorUsuarioController {
    public static Usuario criarUsuario(String nome, String sobrenome, String id, String senha, String endereco, String telefone, UserPermissao tipo) throws NotEnoughPermissionException, IdAlreadyExistsException {
        if (!LoginController.verificarAdministrador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        switch (tipo) {
            case LEITOR -> {
                if (DAO.getLeitorDAO().findID(id) != null) {
                    throw new IdAlreadyExistsException("Leitor já cadastrado");
                }
                Leitor leitor = new Leitor(nome, sobrenome, id, senha, endereco, telefone);
                DAO.getLeitorDAO().create(leitor);
                return leitor;
            }
            case BIBLIOTECARIO -> {
                if (DAO.getBibliotecarioDAO().findID(id) != null) {
                    throw new IdAlreadyExistsException("Bibliotecário já cadastrado");
                }
                Bibliotecario bibliotecario = new Bibliotecario(nome, sobrenome, id, senha);
                DAO.getBibliotecarioDAO().create(bibliotecario);
                return bibliotecario;
            }
            case ADMINISTRADOR -> {
                if (DAO.getAdministradorDAO().findID(id) != null) {
                    throw new IdAlreadyExistsException("Administrador já cadastrado");
                }
                Administrador admin = new Administrador(nome, sobrenome, id, senha);
                DAO.getAdministradorDAO().create(admin);
                return admin;
            }
        }

        return null;
    }

    public static void removerUsuario(Usuario usuario) throws NotEnoughPermissionException, RemoveSelfAttemptException, OngoingReaderLoansException {
        if (!LoginController.verificarAdministrador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        switch (usuario.getPermissao()) {
            case LEITOR -> {
                for (Emprestimo emprestimo : DAO.getEmprestimoDAO().findLeitor((Leitor) usuario)) {
                    if (emprestimo.getStatus() == EmprestimoStatus.PENDENTE) {
                        throw new OngoingReaderLoansException("Leitor ainda possui empréstimos pendentes");
                    }
                }
                DAO.getReservaDAO().deleteAllByLeitor((Leitor) usuario);
                DAO.getLeitorDAO().deleteID(usuario.getId());
            }
            case BIBLIOTECARIO -> {
                DAO.getBibliotecarioDAO().deleteID(usuario.getId());
            }
            case ADMINISTRADOR -> {
                if (DAO.getAdministradorDAO().findID(usuario.getId()).equals(LoginController.getCurrentLoggedUser())) {
                    throw new RemoveSelfAttemptException("Não é possivel remover o usuário atual");
                }
                DAO.getAdministradorDAO().deleteID(usuario.getId());
            }
        }
    }

    public static Usuario getUsuario(String id, UserPermissao tipo) throws NotEnoughPermissionException, IdNotFoundException {
        if (!LoginController.verificarAdministrador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        switch (tipo) {
            case LEITOR -> {
                Leitor leitor = DAO.getLeitorDAO().findID(id);
                if (leitor == null) {
                    throw new IdNotFoundException("Leitor não cadastrado");
                }

                return leitor;
            }
            case BIBLIOTECARIO -> {
                Bibliotecario bibliotecario = DAO.getBibliotecarioDAO().findID(id);
                if (bibliotecario == null) {
                    throw new IdNotFoundException("Bibliotecário não cadastrado");
                }

                return bibliotecario;
            }
            case ADMINISTRADOR -> {
                Administrador administrador = DAO.getAdministradorDAO().findID(id);
                if (administrador == null) {
                    throw new IdNotFoundException("Administrador não cadastrado");
                }

                return administrador;
            }
        }
        return null;
    }

    public static Usuario updateUsuario(Usuario usuario) throws NotEnoughPermissionException {
        if (!LoginController.verificarAdministrador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        switch (usuario.getPermissao()) {
            case LEITOR -> {
                return DAO.getLeitorDAO().update((Leitor) usuario);
            }
            case BIBLIOTECARIO -> {
                return DAO.getBibliotecarioDAO().update((Bibliotecario) usuario);
            }
            case ADMINISTRADOR -> {
                return DAO.getAdministradorDAO().update((Administrador) usuario);
            }
        }
        return null;
    }

    public static Usuario bloquearLeitor(Leitor leitor) throws NotEnoughPermissionException, UserIsBlockedException {
        if (!LoginController.verificarAdministrador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        if (leitor.getStatus() == LeitorStatus.BANIDO) {
            throw new UserIsBlockedException("Leitor já está bloqueado");
        }

        for (Reserva reserva : DAO.getReservaDAO().findLeitor(leitor)) {
            Reserva reservaUpdate = DAO.getReservaDAO().update(reserva);
            reservaUpdate.setStatus(ReservaStatus.CANCELADO);
        }

        leitor.setStatus(LeitorStatus.BANIDO);
        return leitor;
    }

    public static Usuario desbloquearLeitor(Leitor leitor) throws NotEnoughPermissionException {
        if (!LoginController.verificarAdministrador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        leitor.setStatus(LeitorStatus.LIBERADO);
        return leitor;
    }
}
