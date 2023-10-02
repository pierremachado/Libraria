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

import java.util.List;

public class ReservaController {
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

        reservaUpdate.setStatus(ReservaStatus.CANCELADO);
        Livro livroUpdate = DAO.getLivroDAO().update(reservaUpdate.getLivro());
        livroUpdate.aumentarQuantidade(1);
    }

    public static void atualizarReservas() {
        for (Reserva reserva : DAO.getReservaDAO().findAll()) {
            Reserva reservaUpdate = DAO.getReservaDAO().update(reserva);
            Livro livroUpdate = DAO.getLivroDAO().update(reservaUpdate.getLivro());
            switch (reserva.getStatus()) {
                case ESPERA -> {
                    if (reserva.getLivro().getQuantidadeDisponiveis() > 0) {
                        reservaUpdate.setStatus(ReservaStatus.LIBERADO);
                        reservaUpdate.setDataLimite(TimeController.getCurrentLocalDateTime().plusDays(2).with(TimeController.getHorarioFechamento()));
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
