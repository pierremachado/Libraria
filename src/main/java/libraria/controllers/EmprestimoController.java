package main.java.libraria.controllers;

import main.java.libraria.dao.DAO;
import main.java.libraria.errors.EmprestimoException;
import main.java.libraria.errors.NotEnoughPermissionException;
import main.java.libraria.errors.UserIsBlockedException;
import main.java.libraria.model.Emprestimo;
import main.java.libraria.model.Leitor;
import main.java.libraria.model.Livro;
import main.java.libraria.model.Reserva;
import main.java.libraria.model.enums.EmprestimoStatus;
import main.java.libraria.model.enums.LeitorStatus;
import main.java.libraria.model.enums.ReservaStatus;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class EmprestimoController {
    public static Emprestimo criarEmprestimo(Leitor leitor, Livro livro) throws NotEnoughPermissionException, UserIsBlockedException, EmprestimoException {
        if (!LoginController.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        if (leitor.getStatus() == LeitorStatus.BANIDO || leitor.getStatus() == LeitorStatus.MULTADO) {
            throw new UserIsBlockedException("Leitor está multado ou banido");
        }

        List<Emprestimo> emprestimoList = DAO.getEmprestimoDAO().findLeitor(leitor);

        if (emprestimoList.size() == 3) {
            throw new EmprestimoException("Leitor possui empréstimos demais");
        }

        for (Emprestimo emprestimo : emprestimoList) {
            if (emprestimo.getLivro().equals(livro)) {
                throw new EmprestimoException("Leitor já está com um exemplar do livro");
            }
        }

        List<Reserva> reservaList = DAO.getReservaDAO().findCurrentLeitor(leitor);
        for (Reserva reserva : reservaList) {
            if (reserva.getLivro().equals(livro) && reserva.getStatus() == ReservaStatus.LIBERADO) {
                reserva.setStatus(ReservaStatus.EMPRESTADO);
                return DAO.getEmprestimoDAO().create(new Emprestimo(LoginController.getCurrentLoggedUser(), leitor, livro, TimeController.getCurrentLocalDateTime(), TimeController.getCurrentLocalDateTime().plusDays(7).with(TimeController.getHorarioFechamento()), reserva));
            }
        }

        if (livro.getQuantidadeDisponiveis() == 0) {
            throw new EmprestimoException("Não há exemplares disponíveis para empréstimo");
        }

        return DAO.getEmprestimoDAO().create(new Emprestimo(LoginController.getCurrentLoggedUser(), leitor, livro, TimeController.getCurrentLocalDateTime(), TimeController.getCurrentLocalDateTime().plusDays(7).with(TimeController.getHorarioFechamento()), null));
    }

    public static Emprestimo confirmarRecebimentoEmprestimo(Emprestimo emprestimo) throws NotEnoughPermissionException, EmprestimoException {
        if (!LoginController.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        switch (emprestimo.getStatus()) {
            case ATRASADO -> {
                Leitor leitorUpdate = DAO.getLeitorDAO().update(emprestimo.getLeitor());
                long diasDeMulta = ChronoUnit.DAYS.between(emprestimo.getDataLimite(), TimeController.getCurrentLocalDateTime());
                leitorUpdate.setDataLimiteMulta(TimeController.getCurrentLocalDateTime().plusDays(diasDeMulta * 2));
            }
            case CANCELADO, CONCLUIDO -> {
                throw new EmprestimoException("Empréstimo já cancelado ou concluído");
            }
        }

        emprestimo.setDataDeRetorno(TimeController.getCurrentLocalDateTime());
        emprestimo.setStatus(EmprestimoStatus.CONCLUIDO);
        Livro livroUpdate = DAO.getLivroDAO().update(emprestimo.getLivro());
        livroUpdate.aumentarQuantidade(1);
        return emprestimo;
    }

    public static Emprestimo cancelarEmprestimo(Emprestimo emprestimo) throws NotEnoughPermissionException {
        if (!LoginController.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        emprestimo.setStatus(EmprestimoStatus.CANCELADO);
        Livro livroUpdate = DAO.getLivroDAO().update(emprestimo.getLivro());
        livroUpdate.aumentarQuantidade(1);
        return emprestimo;
    }
}