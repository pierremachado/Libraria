package main.java.com.uefs.libraria.services;

import main.java.com.uefs.libraria.dao.DAO;
import main.java.com.uefs.libraria.exceptions.LoanException;
import main.java.com.uefs.libraria.exceptions.NotEnoughPermissionException;
import main.java.com.uefs.libraria.exceptions.UserIsBlockedException;
import main.java.com.uefs.libraria.model.*;
import main.java.com.uefs.libraria.model.enums.LoanStatus;
import main.java.com.uefs.libraria.model.enums.ReaderStatus;
import main.java.com.uefs.libraria.model.enums.ReservationStatus;

import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */

public class LendingService {
    /**
     * Método para criar um empréstimo e adicionaro o empréstimo ao DAO.
     *
     * @param reader O leitor que receberá o livro.
     * @param book  O livro que será emprestado.
     * @return Objeto da classe Empréstimo com as informações acrescidas
     * @throws NotEnoughPermissionException Caso o usuário não possua permissão adequada.
     * @throws UserIsBlockedException       Caso o leitor esteja bloqueado ou multado.
     * @throws LoanException          Caso o leitor possua empréstimos demais, já esteja com um exemplar do livro ou não tenha uma reserva associada ao livro
     */
    public static Loan criarEmprestimo(Reader reader, Book book) throws NotEnoughPermissionException, UserIsBlockedException, LoanException {
        if (!LoginService.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        if (reader.getStatus() == ReaderStatus.BANIDO || reader.getStatus() == ReaderStatus.MULTADO) {
            throw new UserIsBlockedException("Leitor está multado ou banido");
        }

        List<Loan> loanList = DAO.getEmprestimoDAO().findIdLeitor(reader.getId());

        if (loanList.size() == 3) {
            throw new LoanException("Leitor possui empréstimos demais");
        }

        for (Loan loan : loanList) {
            if (loan.getIdLivro().equals(book.getIsbn())) {
                throw new LoanException("Leitor já está com um exemplar do livro");
            }
        }

        List<Reservation> reservationList = DAO.getReservaDAO().findCurrentLeitor(reader.getId());
        for (Reservation reservation : reservationList) {
            if (reservation.getIdLivro().equals(book.getIsbn()) && reservation.getStatus() == ReservationStatus.LIBERADO) {
                reservation.setStatus(ReservationStatus.EMPRESTADO);
                return DAO.getEmprestimoDAO().create(new Loan(LoginService.getCurrentLoggedUser().getId(), LoginService.getCurrentLoggedUser().getPermissao(), reader.getId(), book.getIsbn(), reservation.getIdReserva(), TimeService.getCurrentLocalDateTime(), TimeService.getCurrentLocalDateTime().plusDays(7), null, 0, LoanStatus.PENDENTE));
            }
        }

        if (book.getQuantidadeDisponiveis() == 0) {
            throw new LoanException("Não há exemplares disponíveis para empréstimo");
        }

        book.reduzirQuantidade(1);
        DAO.getLivroDAO().update(book);
        return DAO.getEmprestimoDAO().create(new Loan(LoginService.getCurrentLoggedUser().getId(), LoginService.getCurrentLoggedUser().getPermissao(), reader.getId(), book.getIsbn(), null, TimeService.getCurrentLocalDateTime(), TimeService.getCurrentLocalDateTime().plusDays(7), null, 0, LoanStatus.PENDENTE));
    }

    /**
     * Método para confirmar o recebimento de um livro e aplicar uma multa ao usuário em caso de atraso
     *
     * @param loan O objeto empréstimo a ser confirmado
     * @return O empréstimo atualizado
     * @throws NotEnoughPermissionException Caso o usuário logado não possua permissão
     * @throws LoanException          Caso o empréstimo já tenha sido cancelado ou concluído
     */
    public static Loan confirmarRecebimentoEmprestimo(Loan loan) throws NotEnoughPermissionException, LoanException {
        if (!LoginService.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        switch (loan.getStatus()) {
            case ATRASADO -> {
                Reader reader = DAO.getLeitorDAO().findID(loan.getIdLeitor());
                long diasDeMulta = ChronoUnit.DAYS.between(loan.getDataLimite(), TimeService.getCurrentLocalDateTime());
                reader.setDataLimiteMulta(TimeService.getCurrentLocalDateTime().plusDays(diasDeMulta * 2));
                DAO.getLeitorDAO().update(reader);
            }
            case CANCELADO, CONCLUIDO -> {
                throw new LoanException("Empréstimo já cancelado ou concluído");
            }
        }

        loan.setDataDeRetorno(TimeService.getCurrentLocalDateTime());
        loan.setStatus(LoanStatus.CONCLUIDO);
        Book book = DAO.getLivroDAO().findID(loan.getIdLivro());
        book.aumentarQuantidade(1);
        DAO.getLivroDAO().update(book);
        return loan;
    }

    /**
     * Renovar o empréstimo caso possua exemplares disponíveis ou o limite de vezes renovado não tenha sido atingido
     *
     * @param loan Empréstimo a ser renovado
     * @return O empréstimo renovado
     * @throws NotEnoughPermissionException Caso o usuário não seja um leitor
     * @throws LoanException          Caso não haja livros disponíveis ou o limite de vezes renovado tenha sido atingido
     */
    public static Loan renovarEmprestimo(Loan loan) throws NotEnoughPermissionException, LoanException {
        if (!LoginService.verificarLeitor()) {
            throw new NotEnoughPermissionException("Apenas leitores podem renovar o empréstimo");
        }

        if (DAO.getLivroDAO().findID(loan.getIdLivro()).getQuantidadeDisponiveis() == 0 || loan.getVezesRenovado() > 0) {
            throw new LoanException("Não há disponibilidade de renovação");
        }

        loan.setDataLimite(TimeService.getCurrentLocalDateTime().plusDays(7));
        loan.setVezesRenovado(loan.getVezesRenovado() + 1);
        return DAO.getEmprestimoDAO().update(loan);
    }


    /**
     * Pesquisar o empréstimo pelo seu id
     *
     * @param id ID do empréstimo
     * @return O empréstimo com ID correspondente
     * @throws NotEnoughPermissionException Caso o usuário não seja um operador do sistema
     */
    public static Loan pesquisarEmprestimoPorId(String id) throws NotEnoughPermissionException {
        if (!LoginService.verificarOperador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }
        return DAO.getEmprestimoDAO().findID(id);
    }

    /**
     * Pesquisar os empréstimos associados a um leitor
     *
     * @param reader Leitor a ser pesquisado
     * @return Os empréstimos de um leitor
     */
    public static List<Loan> pesquisarEmprestimoPorLeitor(Reader reader) {
        return DAO.getEmprestimoDAO().findIdLeitor(reader.getId());
    }

    /**
     * Pesquisar os empréstimos associados a um Livro
     *
     * @param book Livro a ser pesquisado
     * @return Os empréstimos de um livro
     * @throws NotEnoughPermissionException Caso o usuário logado não seja um operador do sistema
     */
    public static List<Loan> pesquisarEmprestimoPorLivro(Book book) throws NotEnoughPermissionException {
        if (!LoginService.verificarOperador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        return DAO.getEmprestimoDAO().findIdLivro(book.getIsbn());
    }

    /**
     * Pesquisar os empréstimos associados a um operador
     *
     * @param user O operador a ser pesquisado
     * @return Os empréstimos de um operador
     * @throws NotEnoughPermissionException Caso o usuário logado não seja um operador do sistema
     */
    public static List<Loan> pesquisarEmprestimoPorOperador(User user) throws NotEnoughPermissionException {
        if (!LoginService.verificarOperador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        return DAO.getEmprestimoDAO().findIdOperador(user.getId());
    }

    /**
     * Método que cancela um empréstimo
     *
     * @param loan O empréstimo a ser cancelado
     * @throws NotEnoughPermissionException Caso o usuário logado não seja um operador do sistema
     * @throws LoanException          Caso o empréstimo já tenha sido cancelado ou concluído
     */
    public static void cancelarEmprestimo(Loan loan, boolean devolvido) throws NotEnoughPermissionException, LoanException {
        if (!LoginService.verificarOperador()) {
            throw new NotEnoughPermissionException("Permissão insuficiente");
        }

        if (loan.getStatus().equals(LoanStatus.CANCELADO) || loan.getStatus().equals(LoanStatus.CONCLUIDO)) {
            throw new LoanException("Empréstimo já cancelado ou concluído");
        }

        loan.setStatus(LoanStatus.CANCELADO);
        DAO.getEmprestimoDAO().update(loan);

        if (devolvido) {
            Book book = DAO.getLivroDAO().findID(loan.getIdLivro());
            book.aumentarQuantidade(1);
            DAO.getLivroDAO().update(book);
        }
    }
}