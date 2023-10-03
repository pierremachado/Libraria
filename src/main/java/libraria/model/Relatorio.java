package main.java.libraria.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class Relatorio {
    private Administrador administrador;
    private LocalDateTime dataGerado;
    private int nLivrosEmprestados;
    private int nLivrosAtrasados;
    private int nLivrosReservados;
    private List<Livro> livrosPopulares;

    public Relatorio(Administrador administrador, LocalDateTime dataGerado) {
        this.administrador = administrador;
        this.dataGerado = dataGerado;
        this.nLivrosAtrasados = 0;
        this.nLivrosEmprestados = 0;
        this.nLivrosReservados = 0;
        this.livrosPopulares = new ArrayList<>();
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public LocalDateTime getDataGerado() {
        return dataGerado;
    }

    public void setDataGerado(LocalDateTime dataGerado) {
        this.dataGerado = dataGerado;
    }

    public int getnLivrosEmprestados() {
        return nLivrosEmprestados;
    }

    public void setnLivrosEmprestados(int nLivrosEmprestados) {
        this.nLivrosEmprestados = nLivrosEmprestados;
    }

    public int getnLivrosAtrasados() {
        return nLivrosAtrasados;
    }

    public void setnLivrosAtrasados(int nLivrosAtrasados) {
        this.nLivrosAtrasados = nLivrosAtrasados;
    }

    public int getnLivrosReservados() {
        return nLivrosReservados;
    }

    public void setnLivrosReservados(int nLivrosReservados) {
        this.nLivrosReservados = nLivrosReservados;
    }

    public List<Livro> getLivrosPopulares() {
        return livrosPopulares;
    }

    public void setLivrosPopulares(List<Livro> livrosPopulares) {
        this.livrosPopulares = livrosPopulares;
    }

    @Override
    public String toString() {
        return "Relatorio{" +
                "administrador=" + administrador +
                ", dataGerado=" + dataGerado +
                ", nLivrosEmprestados=" + nLivrosEmprestados +
                ", nLivrosAtrasados=" + nLivrosAtrasados +
                ", nLivrosReservados=" + nLivrosReservados +
                ", livrosPopulares=" + livrosPopulares +
                '}';
    }
}
