package com.uefs.libraria.services;

import com.uefs.libraria.dao.DAO;
import com.uefs.libraria.exceptions.*;
import com.uefs.libraria.model.*;
import com.uefs.libraria.model.enums.LoanStatus;
import com.uefs.libraria.model.enums.ReaderStatus;
import com.uefs.libraria.model.enums.ReservationStatus;
import com.uefs.libraria.model.enums.UserPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class UserService {

    private static User selectedUser;

    private static String search;

    public static User getSelectedUser() {
        return selectedUser;
    }

    public static void setSelectedUser(User selectedUser) {
        UserService.selectedUser = selectedUser;
    }

    public static String getSearch() {
        return search;
    }

    public static void setSearch(String search) {
        UserService.search = search;
    }

    public static User criarUsuario(String nome, String sobrenome, String id, String senha, String endereco, String telefone, UserPermission tipo) throws NotEnoughPermissionException, IdAlreadyExistsException {
        if (!LoginService.verificarAdministrador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        switch (tipo) {
            case LEITOR -> {
                if (DAO.getLeitorDAO().findID(id) != null) {
                    throw new IdAlreadyExistsException("Leitor já cadastrado");
                }
                Reader reader = new Reader(nome, sobrenome, id, senha, endereco, telefone);
                DAO.getLeitorDAO().create(reader);
                return reader;
            }
            case BIBLIOTECARIO -> {
                if (DAO.getBibliotecarioDAO().findID(id) != null) {
                    throw new IdAlreadyExistsException("Bibliotecário já cadastrado");
                }
                Librarian librarian = new Librarian(nome, sobrenome, id, senha, endereco, telefone);
                DAO.getBibliotecarioDAO().create(librarian);
                return librarian;
            }
            case ADMINISTRADOR -> {
                if (DAO.getAdministradorDAO().findID(id) != null) {
                    throw new IdAlreadyExistsException("Administrador já cadastrado");
                }
                Administrator admin = new Administrator(nome, sobrenome, id, senha, endereco, telefone);
                DAO.getAdministradorDAO().create(admin);
                return admin;
            }
        }

        return null;
    }

    public static void removerUsuario(User user) throws NotEnoughPermissionException, RemoveSelfAttemptException, OngoingLoansException {
        if (!LoginService.verificarAdministrador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        switch (user.getPermissao()) {
            case LEITOR -> {
                for (Loan loan : DAO.getEmprestimoDAO().findIdLeitor(user.getId())) {
                    if (loan.getStatus() == LoanStatus.PENDENTE) {
                        throw new OngoingLoansException("Leitor ainda possui empréstimos pendentes");
                    }
                }
                DAO.getReservaDAO().deleteAllByLeitor(user.getId());
                DAO.getLeitorDAO().deleteID(user.getId());
            }
            case BIBLIOTECARIO -> {
                DAO.getBibliotecarioDAO().deleteID(user.getId());
            }
            case ADMINISTRADOR -> {
                if (DAO.getAdministradorDAO().findID(user.getId()).equals(LoginService.getCurrentLoggedUser())) {
                    throw new RemoveSelfAttemptException("Não é possivel remover o usuário atual");
                }
                DAO.getAdministradorDAO().deleteID(user.getId());
            }
        }
    }

    public static User getUsuario(String id, UserPermission tipo) throws NotEnoughPermissionException, IdNotFoundException {
        if (!LoginService.verificarAdministrador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        switch (tipo) {
            case LEITOR -> {
                Reader reader = DAO.getLeitorDAO().findID(id);
                if (reader == null) {
                    throw new IdNotFoundException("Leitor não cadastrado");
                }

                return reader;
            }
            case BIBLIOTECARIO -> {
                Librarian librarian = DAO.getBibliotecarioDAO().findID(id);
                if (librarian == null) {
                    throw new IdNotFoundException("Bibliotecário não cadastrado");
                }

                return librarian;
            }
            case ADMINISTRADOR -> {
                Administrator administrator = DAO.getAdministradorDAO().findID(id);
                if (administrator == null) {
                    throw new IdNotFoundException("Administrador não cadastrado");
                }

                return administrator;
            }
        }
        return null;
    }

    public static User updateUsuario(User user) throws NotEnoughPermissionException {
        if (!LoginService.verificarAdministrador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        switch (user.getPermissao()) {
            case LEITOR -> {
                return DAO.getLeitorDAO().update((Reader) user);
            }
            case BIBLIOTECARIO -> {
                return DAO.getBibliotecarioDAO().update((Librarian) user);
            }
            case ADMINISTRADOR -> {
                return DAO.getAdministradorDAO().update((Administrator) user);
            }
        }
        return null;
    }

    public static void bloquearLeitor(Reader reader) throws NotEnoughPermissionException, UserIsBlockedException {
        if (!LoginService.verificarAdministrador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        if (reader.getStatus() == ReaderStatus.BANIDO) {
            throw new UserIsBlockedException("Leitor já está bloqueado");
        }

        for (Reservation reservation : DAO.getReservaDAO().findLeitor(reader.getId())) {
            Reservation reservationUpdate = DAO.getReservaDAO().update(reservation);
            reservationUpdate.setStatus(ReservationStatus.CANCELADO);
        }

        reader.setStatus(ReaderStatus.BANIDO);
    }

    public static void desbloquearLeitor(Reader reader) throws NotEnoughPermissionException {
        if (!LoginService.verificarAdministrador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        reader.setStatus(ReaderStatus.LIBERADO);
    }

    public static List<User> getAllUsers(){
        List<User> allUsers = new ArrayList<>(DAO.getAdministradorDAO().findAll());
        allUsers.addAll(DAO.getBibliotecarioDAO().findAll());
        allUsers.addAll(DAO.getLeitorDAO().findAll());

        return allUsers;
    }

    public static List<User> pesquisarUsuarioPorKey(String key){
        List<User> allUsers = getAllUsers();
        List<User> userListById = new ArrayList<>();

        for (User user : allUsers) {
            String userKey =
                    user.getNome() +
                            " " +
                            user.getSobrenome() +
                            " " +
                            user.getId() +
                            " " +
                            user.getCargo();

            if (userKey.toLowerCase().strip().contains(key.toLowerCase().strip())){
                userListById.add(user);
            }
        }

        return userListById;
    }

    public static List<User> pesquisarLeitorPorKey(String key){
        List<User> userListById = new ArrayList<>();

        for (User user : DAO.getLeitorDAO().findAll()) {
            String userKey =
                    user.getNome() +
                            " " +
                            user.getSobrenome() +
                            " " +
                            user.getId() +
                            " " +
                            user.getCargo();

            if (userKey.toLowerCase().strip().contains(key.toLowerCase().strip())){
                userListById.add(user);
            }
        }

        return userListById;
    }

    public static User pesquisarUsuarioPorUsername(String username){
        List<User> allUsers = getAllUsers();

        for (User user : allUsers) {
            if (Objects.equals(user.getId(), username)){
                return user;
            }
        }

        return null;
    }
}
