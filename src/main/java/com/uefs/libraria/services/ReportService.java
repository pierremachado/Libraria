package main.java.com.uefs.libraria.services;

import main.java.com.uefs.libraria.dao.DAO;
import main.java.com.uefs.libraria.exceptions.NotEnoughPermissionException;
import main.java.com.uefs.libraria.model.Loan;
import main.java.com.uefs.libraria.model.Book;
import main.java.com.uefs.libraria.model.Relatorio;
import main.java.com.uefs.libraria.model.Reservation;
import main.java.com.uefs.libraria.model.enums.LoanStatus;
import main.java.com.uefs.libraria.model.enums.ReservationStatus;

import java.util.List;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class ReportService {

    /**
     * Método que gera um relatório contendo algumas estatísticas envolvendo o sistema
     *
     * @return Um objeto da classe Relatório
     * @throws NotEnoughPermissionException Caso o usuário não seja um administrador
     */
    public static Relatorio gerarRelatorio() throws NotEnoughPermissionException {
        if (!LoginService.verificarAdministrador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        Relatorio relatorio = new Relatorio(LoginService.getCurrentLoggedUser().getId(), TimeService.getCurrentLocalDateTime(), 0, 0, 0, null);

        for (Loan loan : DAO.getEmprestimoDAO().findAll()) {
            if (loan.getStatus() != LoanStatus.CONCLUIDO && loan.getStatus() != LoanStatus.CANCELADO) {
                relatorio.setnLivrosEmprestados(relatorio.getnLivrosEmprestados() + 1);
                if (loan.getStatus() == LoanStatus.ATRASADO) {
                    relatorio.setnLivrosAtrasados(relatorio.getnLivrosAtrasados() + 1);
                }
            }
        }

        for (Reservation reservation : DAO.getReservaDAO().findAll()) {
            if (reservation.getStatus() == ReservationStatus.RESERVADO || reservation.getStatus() == ReservationStatus.LIBERADO) {
                relatorio.setnLivrosReservados(relatorio.getnLivrosReservados() + 1);
            }
        }

        List<Book> livrosPopulares = BookService.ordenarPorVezesPesquisado(DAO.getLivroDAO().findAll());
        relatorio.setLivrosPopulares(livrosPopulares);

        return relatorio;
    }
}
