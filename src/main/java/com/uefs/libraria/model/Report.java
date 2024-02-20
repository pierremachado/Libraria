package com.uefs.libraria.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class Report implements Serializable {

    private String idAdministrador;
    private LocalDateTime dataGerado;
    private int nLivrosEmprestados;
    private int nLivrosAtrasados;
    private int nLivrosReservados;
    private List<Book> livrosPopulares;

    public Report(String idAdministrador, LocalDateTime dataGerado, int nLivrosEmprestados, int nLivrosAtrasados, int nLivrosReservados, List<Book> livrosPopulares) {
        this.idAdministrador = idAdministrador;
        this.dataGerado = dataGerado;
        this.nLivrosEmprestados = nLivrosEmprestados;
        this.nLivrosAtrasados = nLivrosAtrasados;
        this.nLivrosReservados = nLivrosReservados;
        this.livrosPopulares = livrosPopulares;
    }

    public String getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(String idAdministrador) {
        this.idAdministrador = idAdministrador;
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

    public List<Book> getLivrosPopulares() {
        return livrosPopulares;
    }

    public void setLivrosPopulares(List<Book> livrosPopulares) {
        this.livrosPopulares = livrosPopulares;
    }
}
