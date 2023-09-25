package main.model;

import main.model.enums.ReservaStatus;

import java.time.LocalDateTime;

public class Reserva {
    private String id;
    private Leitor leitor;
    private Livro livro;
    private ReservaStatus status;

    private LocalDateTime dataReservado;

    private LocalDateTime dataLimite;

    public Reserva(String id, Leitor leitor, Livro livro, ReservaStatus status, LocalDateTime dataReservado) {
        this.id = id;
        this.leitor = leitor;
        this.livro = livro;
        this.status = status;
        this.dataReservado = dataReservado;
        this.dataLimite = null;
    }

    public Reserva(String id, Leitor leitor, Livro livro, ReservaStatus status, LocalDateTime dataReservado, LocalDateTime dataLimite) {
        this.id = id;
        this.leitor = leitor;
        this.livro = livro;
        this.status = status;
        this.dataReservado = dataReservado;
        this.dataLimite = dataLimite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ReservaStatus getStatus() {
        return status;
    }

    public void setStatus(ReservaStatus status) {
        this.status = status;
    }

    public LocalDateTime getDataReservado() {
        return dataReservado;
    }

    public void setDataReservado(LocalDateTime dataReservado) {
        this.dataReservado = dataReservado;
    }

    public LocalDateTime getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDateTime dataLimite) {
        this.dataLimite = dataLimite;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                "leitor=" + leitor +
                ", livro=" + livro +
                ", status=" + status +
                ", dataReservado=" + dataReservado +
                ", dataLimite=" + dataLimite +
                '}';
    }
}
