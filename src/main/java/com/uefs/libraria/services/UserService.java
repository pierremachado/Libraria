package main.java.com.uefs.libraria.services;

import main.java.com.uefs.libraria.dao.DAO;
import main.java.com.uefs.libraria.exceptions.*;
import main.java.com.uefs.libraria.model.*;
import main.java.com.uefs.libraria.model.enums.LoanStatus;
import main.java.com.uefs.libraria.model.enums.ReaderStatus;
import main.java.com.uefs.libraria.model.enums.ReservationStatus;
import main.java.com.uefs.libraria.model.enums.UserPermission;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class UserService {
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
                Librarian librarian = new Librarian(nome, sobrenome, id, senha);
                DAO.getBibliotecarioDAO().create(librarian);
                return librarian;
            }
            case ADMINISTRADOR -> {
                if (DAO.getAdministradorDAO().findID(id) != null) {
                    throw new IdAlreadyExistsException("Administrador já cadastrado");
                }
                Administrator admin = new Administrator(nome, sobrenome, id, senha);
                DAO.getAdministradorDAO().create(admin);
                return admin;
            }
        }

        return null;
    }

    public static void removerUsuario(User user) throws NotEnoughPermissionException, RemoveSelfAttemptException, OngoingReaderLoansException {
        if (!LoginService.verificarAdministrador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        switch (user.getPermissao()) {
            case LEITOR -> {
                for (Loan loan : DAO.getEmprestimoDAO().findIdLeitor(user.getId())) {
                    if (loan.getStatus() == LoanStatus.PENDENTE) {
                        throw new OngoingReaderLoansException("Leitor ainda possui empréstimos pendentes");
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

    public static User bloquearLeitor(Reader reader) throws NotEnoughPermissionException, UserIsBlockedException {
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
        return reader;
    }

    public static User desbloquearLeitor(Reader reader) throws NotEnoughPermissionException {
        if (!LoginService.verificarAdministrador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        reader.setStatus(ReaderStatus.LIBERADO);
        return reader;
    }
}
