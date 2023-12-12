package main.java.com.uefs.libraria.services;

import main.java.com.uefs.libraria.dao.DAO;
import main.java.com.uefs.libraria.model.Loan;
import main.java.com.uefs.libraria.model.Reader;
import main.java.com.uefs.libraria.model.enums.LoanStatus;
import main.java.com.uefs.libraria.model.enums.ReaderStatus;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class FinesService {

    /**
     * Método que automaticamente varrerá todos os empréstimos e irá aplicar multas aos empréstimos atrasados
     */
    public static void aplicarMultas() {
        for (Loan loan : DAO.getEmprestimoDAO().findAll()) {
            if (loan.getStatus() == LoanStatus.PENDENTE && loan.getDataLimite().isBefore(TimeService.getCurrentLocalDateTime())) {
                loan.setStatus(LoanStatus.ATRASADO);
                Reader reader = DAO.getLeitorDAO().findID(loan.getIdLeitor());
                reader.setStatus(ReaderStatus.MULTADO);
                DAO.getLeitorDAO().update(reader);
            }
        }
    }


    /**
     * Método que automaticamente varrerá todos os leitores e irá desbloqueá-los caso a data atual tenha ultrapassado a data de término da multa
     */
    public static void desbloquearMultas() {
        for (Reader reader : DAO.getLeitorDAO().findAll()) {
            if (reader.getStatus() == ReaderStatus.MULTADO && reader.getDataLimiteMulta() != null && reader.getDataLimiteMulta().isBefore(TimeService.getCurrentLocalDateTime())) {
                reader.setStatus(ReaderStatus.LIBERADO);
                reader.setDataLimiteMulta(null);
                DAO.getLeitorDAO().update(reader);
            }
        }
    }
}
