package main.java.com.uefs.libraria.services;

import main.java.com.uefs.libraria.dao.DAO;
import main.java.com.uefs.libraria.exceptions.BookException;
import main.java.com.uefs.libraria.exceptions.NotEnoughPermissionException;
import main.java.com.uefs.libraria.exceptions.ReservationException;
import main.java.com.uefs.libraria.exceptions.UserIsBlockedException;
import main.java.com.uefs.libraria.model.Reader;
import main.java.com.uefs.libraria.model.Book;
import main.java.com.uefs.libraria.model.Reservation;
import main.java.com.uefs.libraria.model.enums.ReaderStatus;
import main.java.com.uefs.libraria.model.enums.ReservationStatus;

import java.util.List;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class ReservationService {
    /**
     * Método que cria uma reserva e adiciona a reserva no DAO. Apenas leitores podem fazer reservas
     *
     * @param book O livro a ser reservado
     * @return A reserva criada
     * @throws NotEnoughPermissionException Caso o usuário logado não seja um leitor
     * @throws BookException                Caso haja unidades disponíveis do exemplar
     * @throws ReservationException             Caso o leitor já tenha ultrapassado o limite de reservas em seu nome
     * @throws UserIsBlockedException       Caso o leitor esteja bloqueado ou multado
     */
    public static Reservation criarReserva(Book book) throws NotEnoughPermissionException, BookException, ReservationException, UserIsBlockedException {
        if (!LoginService.verificarLeitor()) {
            throw new NotEnoughPermissionException("Apenas leitores podem fazer reservas");
        }

        Reader reader = (Reader) LoginService.getCurrentLoggedUser();

        if (reader.getStatus() == ReaderStatus.BANIDO || reader.getStatus() == ReaderStatus.MULTADO) {
            throw new UserIsBlockedException("Leitor está bloqueado ou multado");
        }

        if (book.getQuantidadeDisponiveis() > 0) {
            throw new BookException("Há unidades disponíveis do exemplar");
        }

        List<Reservation> reservasAtuais = DAO.getReservaDAO().findCurrentLeitor(reader.getId());

        if (reservasAtuais.size() == 3) {
            throw new ReservationException("Leitor possui reservas demais");
        }

        for (Reservation reservation : reservasAtuais) {
            if (reservation.getIdLivro().equals(book.getIsbn())) {
                throw new ReservationException("Livro já reservado");
            }
        }

        return DAO.getReservaDAO().create(new Reservation(null, reader.getId(), book.getIsbn(), ReservationStatus.RESERVADO, TimeService.getCurrentLocalDateTime(), null));
    }

    /**
     * Método para cancelar a reserva de um livro
     *
     * @param reservation A reserva a ser cancelada
     * @throws NotEnoughPermissionException Caso o usuário logado seja um convidado. Convidados não podem fazer ou cancelar reservas
     * @throws ReservationException             Caso a reserva já tenha sido cancelada ou emprestada
     */
    public static void cancelarReserva(Reservation reservation) throws NotEnoughPermissionException, ReservationException {
        if (LoginService.verificarConvidado()) {
            throw new NotEnoughPermissionException("Convidados não podem cancelar reservas");
        }

        switch (reservation.getStatus()) {
            case CANCELADO -> {
                throw new ReservationException("Reserva já cancelada");
            }
            case EMPRESTADO -> {
                throw new ReservationException("Reserva já emprestada");
            }
        }

        if (reservation.getStatus() == ReservationStatus.LIBERADO) {
            Book book = DAO.getLivroDAO().findID(reservation.getIdLivro());
            book.aumentarQuantidade(1);
            DAO.getLivroDAO().update(book);
        }
        reservation.setStatus(ReservationStatus.CANCELADO);
        DAO.getReservaDAO().update(reservation);
    }

    public static Reservation pesquisarReservaPorId(String id) throws NotEnoughPermissionException {
        if (!LoginService.verificarOperador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        return DAO.getReservaDAO().findID(id);
    }

    public static List<Reservation> pesquisarReservaPorLeitor(Reader reader) {
        return DAO.getReservaDAO().findLeitor(reader.getId());
    }

    public static List<Reservation> pesquisarReservaPorLivro(Book book) throws NotEnoughPermissionException {
        if (!LoginService.verificarOperador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        return DAO.getReservaDAO().findLivro(book.getIsbn());
    }

    public static List<Reservation> pesquisarReservasAtuaisPorLeitor(Reader reader) {
        return DAO.getReservaDAO().findCurrentLeitor(reader.getId());
    }

    public static void atualizarReservas() {
        for (Reservation reservation : DAO.getReservaDAO().findAll()) {
            Book book = DAO.getLivroDAO().findID(reservation.getIdLivro());
            switch (reservation.getStatus()) {
                case RESERVADO -> {
                    if (book.getQuantidadeDisponiveis() > 0) {
                        reservation.setStatus(ReservationStatus.LIBERADO);
                        reservation.setDataLimite(TimeService.getCurrentLocalDateTime().plusDays(2));
                        book.reduzirQuantidade(1);

                        DAO.getReservaDAO().update(reservation);
                        DAO.getLivroDAO().update(book);
                    }
                }
                case LIBERADO -> {
                    if (reservation.getDataLimite().isBefore(TimeService.getCurrentLocalDateTime())) {
                        reservation.setStatus(ReservationStatus.CANCELADO);
                        book.aumentarQuantidade(1);

                        DAO.getReservaDAO().update(reservation);
                        DAO.getLivroDAO().update(book);
                    }
                }
            }
        }
    }
}
