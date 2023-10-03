package main.java.libraria.controllers;

import main.java.libraria.dao.DAO;
import main.java.libraria.errors.BookException;
import main.java.libraria.errors.NotEnoughPermissionException;
import main.java.libraria.errors.ReservaException;
import main.java.libraria.errors.UserIsBlockedException;
import main.java.libraria.model.Leitor;
import main.java.libraria.model.Livro;
import main.java.libraria.model.Reserva;
import main.java.libraria.model.enums.LeitorStatus;
import main.java.libraria.model.enums.ReservaStatus;

import java.sql.Time;
import java.util.List;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class ReservaController {
    /** Método que cria uma reserva e adiciona a reserva no DAO. Apenas leitores podem fazer reservas
     * @param livro O livro a ser reservado
     * @return A reserva criada
     * @throws NotEnoughPermissionException Caso o usuário logado não seja um leitor
     * @throws BookException Caso haja unidades disponíveis do exemplar
     * @throws ReservaException Caso o leitor já tenha ultrapassado o limite de reservas em seu nome
     * @throws UserIsBlockedException Caso o leitor esteja bloqueado ou multado
     */
    public static Reserva criarReserva(Livro livro) throws NotEnoughPermissionException, BookException, ReservaException, UserIsBlockedException {
        if (!LoginController.verificarLeitor()) {
            throw new NotEnoughPermissionException("Apenas leitores podem fazer reservas");
        }

        Leitor leitor = (Leitor) LoginController.getCurrentLoggedUser();

        if (leitor.getStatus() == LeitorStatus.BANIDO || leitor.getStatus() == LeitorStatus.MULTADO) {
            throw new UserIsBlockedException("Leitor está bloqueado ou multado");
        }

        if (livro.getQuantidadeDisponiveis() > 0) {
            throw new BookException("Há unidades disponíveis do exemplar");
        }

        List<Reserva> reservasAtuais = DAO.getReservaDAO().findCurrentLeitor(leitor);

        if (reservasAtuais.size() == 3) {
            throw new ReservaException("Leitor possui reservas demais");
        }

        for (Reserva reserva : reservasAtuais) {
            if (reserva.getLivro().equals(livro)) {
                throw new ReservaException("Livro já reservado");
            }
        }

        return DAO.getReservaDAO().create(new Reserva(leitor, livro, ReservaStatus.ESPERA, TimeController.getCurrentLocalDateTime()));
    }

    /** Método para cancelar a reserva de um livro
     * @param reserva A reserva a ser cancelada
     * @throws NotEnoughPermissionException Caso o usuário logado seja um convidado. Convidados não podem fazer ou cancelar reservas
     * @throws ReservaException Caso a reserva já tenha sido cancelada ou emprestada
     */
    public static void cancelarReserva(Reserva reserva) throws NotEnoughPermissionException, ReservaException {
        if (LoginController.verificarConvidado()) {
            throw new NotEnoughPermissionException("Convidados não podem cancelar reservas");
        }

        Reserva reservaUpdate = DAO.getReservaDAO().update(reserva);

        switch (reserva.getStatus()) {
            case CANCELADO -> {
                throw new ReservaException("Reserva já cancelada");
            }
            case EMPRESTADO -> {
                throw new ReservaException("Reserva já emprestada");
            }
        }

        Livro livroUpdate = DAO.getLivroDAO().update(reservaUpdate.getLivro());
        if(reservaUpdate.getStatus() == ReservaStatus.LIBERADO){
            livroUpdate.aumentarQuantidade(1);
        }
        reservaUpdate.setStatus(ReservaStatus.CANCELADO);
    }

    public static Reserva pesquisarReservaPorId(String id) throws NotEnoughPermissionException{
        if(!LoginController.verificarOperador()){
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        return DAO.getReservaDAO().findID(id);
    }

    public static List<Reserva> pesquisarReservaPorLeitor(Leitor leitor){
        return DAO.getReservaDAO().findLeitor(leitor);
    }

    public static List<Reserva> pesquisarReservaPorLivro(Livro livro) throws NotEnoughPermissionException{
        if(!LoginController.verificarOperador()){
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        return DAO.getReservaDAO().findLivro(livro);
    }

    public static List<Reserva> pesquisarReservasAtuaisPorLeitor(Leitor leitor){
        return DAO.getReservaDAO().findCurrentLeitor(leitor);
    }

    public static void atualizarReservas() {
        for (Reserva reserva : DAO.getReservaDAO().findAll()) {
            Reserva reservaUpdate = DAO.getReservaDAO().update(reserva);
            Livro livroUpdate = DAO.getLivroDAO().update(reservaUpdate.getLivro());
            switch (reserva.getStatus()) {
                case ESPERA -> {
                    if (reserva.getLivro().getQuantidadeDisponiveis() > 0) {
                        reservaUpdate.setStatus(ReservaStatus.LIBERADO);
                        reservaUpdate.setDataLimite(TimeController.getCurrentLocalDateTime().plusDays(2));
                        livroUpdate.reduzirQuantidade(1);
                    }
                }
                case LIBERADO -> {
                    if (reserva.getDataLimite().isBefore(TimeController.getCurrentLocalDateTime())) {
                        reservaUpdate.setStatus(ReservaStatus.CANCELADO);
                        livroUpdate.aumentarQuantidade(1);
                    }
                }
            }
        }
    }
}