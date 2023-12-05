package main.java.libraria.controllers;

import main.java.libraria.dao.DAO;
import main.java.libraria.errors.NotEnoughPermissionException;
import main.java.libraria.model.Emprestimo;
import main.java.libraria.model.Livro;
import main.java.libraria.model.Relatorio;
import main.java.libraria.model.Reserva;
import main.java.libraria.model.enums.EmprestimoStatus;
import main.java.libraria.model.enums.ReservaStatus;

import java.util.List;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class RelatorioController {

    /**
     * Método que gera um relatório contendo algumas estatísticas envolvendo o sistema
     *
     * @return Um objeto da classe Relatório
     * @throws NotEnoughPermissionException Caso o usuário não seja um administrador
     */
    public static Relatorio gerarRelatorio() throws NotEnoughPermissionException {
        if (!LoginController.verificarAdministrador()) {
            throw new NotEnoughPermissionException("Sem permissão necessária");
        }

        Relatorio relatorio = new Relatorio(LoginController.getCurrentLoggedUser().getId(), TimeController.getCurrentLocalDateTime(), 0, 0, 0, null);

        for (Emprestimo emprestimo : DAO.getEmprestimoDAO().findAll()) {
            if (emprestimo.getStatus() != EmprestimoStatus.CONCLUIDO && emprestimo.getStatus() != EmprestimoStatus.CANCELADO) {
                relatorio.setnLivrosEmprestados(relatorio.getnLivrosEmprestados() + 1);
                if (emprestimo.getStatus() == EmprestimoStatus.ATRASADO) {
                    relatorio.setnLivrosAtrasados(relatorio.getnLivrosAtrasados() + 1);
                }
            }
        }

        for (Reserva reserva : DAO.getReservaDAO().findAll()) {
            if (reserva.getStatus() == ReservaStatus.ESPERA || reserva.getStatus() == ReservaStatus.LIBERADO) {
                relatorio.setnLivrosReservados(relatorio.getnLivrosReservados() + 1);
            }
        }

        List<Livro> livrosPopulares = LivroController.ordenarPorVezesPesquisado(DAO.getLivroDAO().findAll());
        relatorio.setLivrosPopulares(livrosPopulares);

        return relatorio;
    }
}
