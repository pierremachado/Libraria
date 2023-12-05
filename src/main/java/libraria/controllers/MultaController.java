package main.java.libraria.controllers;

import main.java.libraria.dao.DAO;
import main.java.libraria.model.Emprestimo;
import main.java.libraria.model.Leitor;
import main.java.libraria.model.enums.EmprestimoStatus;
import main.java.libraria.model.enums.LeitorStatus;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class MultaController {

    /**
     * Método que automaticamente varrerá todos os empréstimos e irá aplicar multas aos empréstimos atrasados
     */
    public static void aplicarMultas(){
        for(Emprestimo emprestimo : DAO.getEmprestimoDAO().findAll()){
            if(emprestimo.getStatus() == EmprestimoStatus.PENDENTE && emprestimo.getDataLimite().isBefore(TimeController.getCurrentLocalDateTime())){
                emprestimo.setStatus(EmprestimoStatus.ATRASADO);
                Leitor leitor = DAO.getLeitorDAO().findID(emprestimo.getIdLeitor());
                leitor.setStatus(LeitorStatus.MULTADO);
                DAO.getLeitorDAO().update(leitor);
            }
        }
    }


    /**
     * Método que automaticamente varrerá todos os leitores e irá desbloqueá-los caso a data atual tenha ultrapassado a data de término da multa
     */
    public static void desbloquearMultas(){
        for(Leitor leitor : DAO.getLeitorDAO().findAll()){
            if(leitor.getStatus() == LeitorStatus.MULTADO && leitor.getDataLimiteMulta() != null && leitor.getDataLimiteMulta().isBefore(TimeController.getCurrentLocalDateTime())){
                leitor.setStatus(LeitorStatus.LIBERADO);
                leitor.setDataLimiteMulta(null);
                DAO.getLeitorDAO().update(leitor);
            }
        }
    }
}
