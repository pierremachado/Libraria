package model;

import model.enums.EmprestimoStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class Emprestimo {
    private Bibliotecario bibliotecario;
    private Leitor leitor;
    private Livro livro;
    private Reserva reserva;
    private LocalDateTime dataEmprestimo;
    private LocalDateTime dataDeRetorno;
    private int vezesRenovado;
    private EmprestimoStatus status;

    public Emprestimo(Bibliotecario bibliotecario, Leitor leitor, Livro livro, LocalDateTime dataEmprestimo, LocalDateTime dataDeRetorno, int vezesRenovado, EmprestimoStatus status, Reserva reserva) {
        this.bibliotecario = bibliotecario;
        this.leitor = leitor;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDeRetorno = dataDeRetorno;
        this.vezesRenovado = vezesRenovado;
        this.status = status;
        this.reserva = reserva;
    }

    public Emprestimo(Bibliotecario bibliotecario, Leitor leitor, Livro livro, LocalDateTime dataEmprestimo, LocalDateTime dataDeRetorno, int vezesRenovado, EmprestimoStatus status) {
        this.bibliotecario = bibliotecario;
        this.leitor = leitor;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDeRetorno = dataDeRetorno;
        this.vezesRenovado = vezesRenovado;
        this.status = status;
        this.reserva = null;
    }

    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprestimo that = (Emprestimo) o;
        return Objects.equals(bibliotecario, that.bibliotecario) && Objects.equals(leitor, that.leitor) && Objects.equals(livro, that.livro) && Objects.equals(dataEmprestimo, that.dataEmprestimo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bibliotecario, leitor, livro, dataEmprestimo);
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "bibliotecario=" + bibliotecario +
                ", leitor=" + leitor +
                ", livro=" + livro +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDeRetorno=" + dataDeRetorno +
                ", vezesRenovado=" + vezesRenovado +
                '}';
    }
}
