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
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */

public class EmprestimoController {
    /**
     * Método para criar um empréstimo e adicionaro o empréstimo ao DAO.
     *
     * @param leitor O leitor que receberá o livro.
     * @param livro O livro que será emprestado.
     * @throws NotEnoughPermissionException Caso o usuário não possua permissão adequada.
     * @throws UserIsBlockedException Caso o leitor esteja bloqueado ou multado.
     * @throws EmprestimoException Caso o leitor possua empréstimos demais, já esteja com um exemplar do livro ou não tenha uma reserva associada ao livro
     * @return Objeto da classe Empréstimo com as informações acrescidas
     */
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

        return DAO.getEmprestimoDAO().create(new Emprestimo(LoginController.getCurrentLoggedUser(), leitor, livro, TimeController.getCurrentLocalDateTime(), TimeController.getCurrentLocalDateTime().plusDays(7), null));
    }

    /**
     * Método para confirmar o recebimento de um livro e aplicar uma multa ao usuário em caso de atraso
     *
     * @param emprestimo O objeto empréstimo a ser confirmado
     * @throws NotEnoughPermissionException Caso o usuário logado não possua permissão
     * @throws EmprestimoException Caso o empréstimo já tenha sido cancelado ou concluído
     * @return O empréstimo atualizado
     */
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

    /** Renovar o empréstimo caso possua exemplares disponíveis ou o limite de vezes renovado não tenha sido atingido
     * @param emprestimo Empréstimo a ser renovado
     * @return O empréstimo renovado
     * @throws NotEnoughPermissionException Caso o usuário não seja um leitor
     * @throws EmprestimoException Caso não haja livros disponíveis ou o limite de vezes renovado tenha sido atingido
     */
    public static Emprestimo renovarEmprestimo(Emprestimo emprestimo) throws NotEnoughPermissionException, EmprestimoException{
        if(!LoginController.verificarLeitor()){
            throw new NotEnoughPermissionException("Apenas leitores podem renovar o empréstimo");
        }

        if(emprestimo.getLivro().getQuantidadeDisponiveis() == 0 || emprestimo.getVezesRenovado() > 0){
            throw new EmprestimoException("Não há disponibilidade de renovação");
        }

        Emprestimo emprestimoUpdate = DAO.getEmprestimoDAO().update(emprestimo);
        emprestimoUpdate.setDataLimite(TimeController.getCurrentLocalDateTime().plusDays(7).with(TimeController.getHorarioFechamento()));
        emprestimoUpdate.setVezesRenovado(emprestimoUpdate.getVezesRenovado() + 1);
        return emprestimoUpdate;
    }


    /** Pesquisar o empréstimo pelo seu id
     * @param id ID do empréstimo
     * @return O empréstimo com ID correspondente
     * @throws NotEnoughPermissionException Caso o usuário não seja um operador do sistema
     */
    public static Emprestimo pesquisarEmprestimoPorId(String id) throws NotEnoughPermissionException{
        if(!LoginController.verificarOperador()){
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }
        return DAO.getEmprestimoDAO().findID(id);
    }

    /** Pesquisar os empréstimos associados a um leitor
     * @param leitor Leitor a ser pesquisado
     * @return Os empréstimos de um leitor
     */
    public static List<Emprestimo> pesquisarEmprestimoPorLeitor(Leitor leitor){
        return DAO.getEmprestimoDAO().findLeitor(leitor);
    }

    /** Pesquisar os empréstimos associados a um Livro
     * @param livro Livro a ser pesquisado
     * @return Os empréstimos de um livro
     * @throws NotEnoughPermissionException Caso o usuário logado não seja um operador do sistema
     */
    public static List<Emprestimo> pesquisarEmprestimoPorLivro(Livro livro) throws NotEnoughPermissionException{
        if(!LoginController.verificarOperador()){
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        return DAO.getEmprestimoDAO().findLivro(livro);
    }

    /** Pesquisar os empréstimos associados a um operador
     * @param usuario O operador a ser pesquisado
     * @return Os empréstimos de um operador
     * @throws NotEnoughPermissionException Caso o usuário logado não seja um operador do sistema
     */
    public static List<Emprestimo> pesquisarEmprestimoPorOperador(Usuario usuario) throws NotEnoughPermissionException{
        if(!LoginController.verificarOperador()){
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        return DAO.getEmprestimoDAO().findUsuario(usuario);
    }

    /** Método que cancela um empréstimo
     * @param emprestimo O empréstimo a ser cancelado
     * @throws NotEnoughPermissionException Caso o usuário logado não seja um operador do sistema
     */
    public static void cancelarEmprestimo(Emprestimo emprestimo) throws NotEnoughPermissionException {
        if (!LoginController.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        emprestimo.setStatus(EmprestimoStatus.CANCELADO);
        Livro livroUpdate = DAO.getLivroDAO().update(emprestimo.getLivro());
        livroUpdate.aumentarQuantidade(1);
    }
}