package com.uefs.libraria.services;

import com.uefs.libraria.dao.DAO;
import com.uefs.libraria.exceptions.NotEnoughPermissionException;
import com.uefs.libraria.model.Loan;
import com.uefs.libraria.model.Book;
import com.uefs.libraria.model.Report;
import com.uefs.libraria.model.Reservation;
import com.uefs.libraria.model.enums.LoanStatus;
import com.uefs.libraria.model.enums.ReservationStatus;

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
    public static Report gerarRelatorio() throws NotEnoughPermissionException {
        if (!LoginService.verificarAdministrador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        Report report = new Report(LoginService.getCurrentLoggedUser().getId(), TimeService.getCurrentLocalDateTime(), 0, 0, 0, null);

        for (Loan loan : DAO.getEmprestimoDAO().findAll()) {
            if (loan.getStatus() != LoanStatus.CONCLUIDO && loan.getStatus() != LoanStatus.CANCELADO) {
                report.setnLivrosEmprestados(report.getnLivrosEmprestados() + 1);
                if (loan.getStatus() == LoanStatus.ATRASADO) {
                    report.setnLivrosAtrasados(report.getnLivrosAtrasados() + 1);
                }
            }
        }

        for (Reservation reservation : DAO.getReservaDAO().findAll()) {
            if (reservation.getStatus() == ReservationStatus.RESERVADO || reservation.getStatus() == ReservationStatus.LIBERADO) {
                report.setnLivrosReservados(report.getnLivrosReservados() + 1);
            }
        }

        List<Book> livrosPopulares = BookService.ordenarPorVezesPesquisado(DAO.getLivroDAO().findAll());
        report.setLivrosPopulares(livrosPopulares);

        return report;
    }
}
