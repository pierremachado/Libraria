package main.java.libraria.controllers;

import main.java.libraria.dao.DAO;
import main.java.libraria.errors.EmprestimoException;
import main.java.libraria.errors.NotEnoughPermissionException;
import main.java.libraria.errors.UserIsBlockedException;
import main.java.libraria.model.*;
import main.java.libraria.model.enums.EmprestimoStatus;
import main.java.libraria.model.enums.LeitorStatus;
import main.java.libraria.model.enums.ReservaStatus;

import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */

public class EmprestimoController {
    /**
     * Método para criar um empréstimo e adicionaro o empréstimo ao DAO.
     *
     * @param leitor O leitor que receberá o livro.
     * @param livro  O livro que será emprestado.
     * @return Objeto da classe Empréstimo com as informações acrescidas
     * @throws NotEnoughPermissionException Caso o usuário não possua permissão adequada.
     * @throws UserIsBlockedException       Caso o leitor esteja bloqueado ou multado.
     * @throws EmprestimoException          Caso o leitor possua empréstimos demais, já esteja com um exemplar do livro ou não tenha uma reserva associada ao livro
     */
    public static Emprestimo criarEmprestimo(Leitor leitor, Livro livro) throws NotEnoughPermissionException, UserIsBlockedException, EmprestimoException {
        if (!LoginController.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        if (leitor.getStatus() == LeitorStatus.BANIDO || leitor.getStatus() == LeitorStatus.MULTADO) {
            throw new UserIsBlockedException("Leitor está multado ou banido");
        }

        List<Emprestimo> emprestimoList = DAO.getEmprestimoDAO().findIdLeitor(leitor.getId());

        if (emprestimoList.size() == 3) {
            throw new EmprestimoException("Leitor possui empréstimos demais");
        }

        for (Emprestimo emprestimo : emprestimoList) {
            if (emprestimo.getIdLivro().equals(livro.getIsbn())) {
                throw new EmprestimoException("Leitor já está com um exemplar do livro");
            }
        }

        List<Reserva> reservaList = DAO.getReservaDAO().findCurrentLeitor(leitor.getId());
        for (Reserva reserva : reservaList) {
            if (reserva.getIdLivro().equals(livro.getIsbn()) && reserva.getStatus() == ReservaStatus.LIBERADO) {
                reserva.setStatus(ReservaStatus.EMPRESTADO);
                return DAO.getEmprestimoDAO().create(new Emprestimo(LoginController.getCurrentLoggedUser().getId(), LoginController.getCurrentLoggedUser().getPermissao(), leitor.getId(), livro.getIsbn(), reserva.getIdReserva(), TimeController.getCurrentLocalDateTime(), TimeController.getCurrentLocalDateTime().plusDays(7), null, 0, EmprestimoStatus.PENDENTE));
            }
        }

        if (livro.getQuantidadeDisponiveis() == 0) {
            throw new EmprestimoException("Não há exemplares disponíveis para empréstimo");
        }

        livro.reduzirQuantidade(1);
        DAO.getLivroDAO().update(livro);
        return DAO.getEmprestimoDAO().create(new Emprestimo(LoginController.getCurrentLoggedUser().getId(), LoginController.getCurrentLoggedUser().getPermissao(), leitor.getId(), livro.getIsbn(), null, TimeController.getCurrentLocalDateTime(), TimeController.getCurrentLocalDateTime().plusDays(7), null, 0, EmprestimoStatus.PENDENTE));
    }

    /**
     * Método para confirmar o recebimento de um livro e aplicar uma multa ao usuário em caso de atraso
     *
     * @param emprestimo O objeto empréstimo a ser confirmado
     * @return O empréstimo atualizado
     * @throws NotEnoughPermissionException Caso o usuário logado não possua permissão
     * @throws EmprestimoException          Caso o empréstimo já tenha sido cancelado ou concluído
     */
    public static Emprestimo confirmarRecebimentoEmprestimo(Emprestimo emprestimo) throws NotEnoughPermissionException, EmprestimoException {
        if (!LoginController.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        switch (emprestimo.getStatus()) {
            case ATRASADO -> {
                Leitor leitor = DAO.getLeitorDAO().findID(emprestimo.getIdLeitor());
                long diasDeMulta = ChronoUnit.DAYS.between(emprestimo.getDataLimite(), TimeController.getCurrentLocalDateTime());
                leitor.setDataLimiteMulta(TimeController.getCurrentLocalDateTime().plusDays(diasDeMulta * 2));
                DAO.getLeitorDAO().update(leitor);
            }
            case CANCELADO, CONCLUIDO -> {
                throw new EmprestimoException("Empréstimo já cancelado ou concluído");
            }
        }

        emprestimo.setDataDeRetorno(TimeController.getCurrentLocalDateTime());
        emprestimo.setStatus(EmprestimoStatus.CONCLUIDO);
        Livro livro = DAO.getLivroDAO().findID(emprestimo.getIdLivro());
        livro.aumentarQuantidade(1);
        DAO.getLivroDAO().update(livro);
        return emprestimo;
    }

    /**
     * Renovar o empréstimo caso possua exemplares disponíveis ou o limite de vezes renovado não tenha sido atingido
     *
     * @param emprestimo Empréstimo a ser renovado
     * @return O empréstimo renovado
     * @throws NotEnoughPermissionException Caso o usuário não seja um leitor
     * @throws EmprestimoException          Caso não haja livros disponíveis ou o limite de vezes renovado tenha sido atingido
     */
    public static Emprestimo renovarEmprestimo(Emprestimo emprestimo) throws NotEnoughPermissionException, EmprestimoException {
        if (!LoginController.verificarLeitor()) {
            throw new NotEnoughPermissionException("Apenas leitores podem renovar o empréstimo");
        }

        if (DAO.getLivroDAO().findID(emprestimo.getIdLivro()).getQuantidadeDisponiveis() == 0 || emprestimo.getVezesRenovado() > 0) {
            throw new EmprestimoException("Não há disponibilidade de renovação");
        }

        emprestimo.setDataLimite(TimeController.getCurrentLocalDateTime().plusDays(7));
        emprestimo.setVezesRenovado(emprestimo.getVezesRenovado() + 1);
        return DAO.getEmprestimoDAO().update(emprestimo);
    }


    /**
     * Pesquisar o empréstimo pelo seu id
     *
     * @param id ID do empréstimo
     * @return O empréstimo com ID correspondente
     * @throws NotEnoughPermissionException Caso o usuário não seja um operador do sistema
     */
    public static Emprestimo pesquisarEmprestimoPorId(String id) throws NotEnoughPermissionException {
        if (!LoginController.verificarOperador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }
        return DAO.getEmprestimoDAO().findID(id);
    }

    /**
     * Pesquisar os empréstimos associados a um leitor
     *
     * @param leitor Leitor a ser pesquisado
     * @return Os empréstimos de um leitor
     */
    public static List<Emprestimo> pesquisarEmprestimoPorLeitor(Leitor leitor) {
        return DAO.getEmprestimoDAO().findIdLeitor(leitor.getId());
    }

    /**
     * Pesquisar os empréstimos associados a um Livro
     *
     * @param livro Livro a ser pesquisado
     * @return Os empréstimos de um livro
     * @throws NotEnoughPermissionException Caso o usuário logado não seja um operador do sistema
     */
    public static List<Emprestimo> pesquisarEmprestimoPorLivro(Livro livro) throws NotEnoughPermissionException {
        if (!LoginController.verificarOperador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        return DAO.getEmprestimoDAO().findIdLivro(livro.getIsbn());
    }

    /**
     * Pesquisar os empréstimos associados a um operador
     *
     * @param usuario O operador a ser pesquisado
     * @return Os empréstimos de um operador
     * @throws NotEnoughPermissionException Caso o usuário logado não seja um operador do sistema
     */
    public static List<Emprestimo> pesquisarEmprestimoPorOperador(Usuario usuario) throws NotEnoughPermissionException {
        if (!LoginController.verificarOperador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        return DAO.getEmprestimoDAO().findIdOperador(usuario.getId());
    }

    /**
     * Método que cancela um empréstimo
     *
     * @param emprestimo O empréstimo a ser cancelado
     * @throws NotEnoughPermissionException Caso o usuário logado não seja um operador do sistema
     * @throws EmprestimoException          Caso o empréstimo já tenha sido cancelado ou concluído
     */
    public static void cancelarEmprestimo(Emprestimo emprestimo, boolean devolvido) throws NotEnoughPermissionException, EmprestimoException {
        if (!LoginController.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        if (emprestimo.getStatus().equals(EmprestimoStatus.CANCELADO) || emprestimo.getStatus().equals(EmprestimoStatus.CONCLUIDO)) {
            throw new EmprestimoException("Empréstimo já cancelado ou concluído");
        }

        emprestimo.setStatus(EmprestimoStatus.CANCELADO);
        DAO.getEmprestimoDAO().update(emprestimo);

        if (devolvido) {
            Livro livro = DAO.getLivroDAO().findID(emprestimo.getIdLivro());
            livro.aumentarQuantidade(1);
            DAO.getLivroDAO().update(livro);
        }
    }
}