package main.java.libraria.model;

import main.java.libraria.model.enums.EmprestimoStatus;

import java.time.LocalDateTime;

public class Emprestimo {
    private String id;
    private Usuario usuario;
    private Leitor leitor;
    private Livro livro;
    private Reserva reserva;
    private LocalDateTime dataEmprestimo;
    private LocalDateTime dataLimite;
    private LocalDateTime dataDeRetorno;
    private int vezesRenovado;
    private EmprestimoStatus status;

    public Emprestimo(Usuario usuario, Leitor leitor, Livro livro, LocalDateTime dataEmprestimo, LocalDateTime dataLimite, Reserva reserva) {
        this.usuario = usuario;
        this.leitor = leitor;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataLimite = dataLimite;
        this.dataDeRetorno = null;
        this.vezesRenovado = 0;
        this.status = EmprestimoStatus.PENDENTE;
        this.reserva = reserva;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public void setLeitor(Leitor leitor) {
        this.leitor = leitor;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public LocalDateTime getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDateTime dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDateTime getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDateTime dataLimite) {
        this.dataLimite = dataLimite;
    }

    public LocalDateTime getDataDeRetorno() {
        return dataDeRetorno;
    }

    public void setDataDeRetorno(LocalDateTime dataDeRetorno) {
        this.dataDeRetorno = dataDeRetorno;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public void setVezesRenovado(int vezesRenovado) {
        this.vezesRenovado = vezesRenovado;
    }

    public int getVezesRenovado() {
        return vezesRenovado;
    }

    public EmprestimoStatus getStatus() {
        return status;
    }

    public void setStatus(EmprestimoStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                "usuario=" + usuario +
                ", leitor=" + leitor +
                ", livro=" + livro +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDeRetorno=" + dataDeRetorno +
                ", vezesRenovado=" + vezesRenovado +
                '}';
    }
}
