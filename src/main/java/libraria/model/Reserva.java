package main.java.libraria.model;

import main.java.libraria.model.enums.ReservaStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class Reserva implements Serializable {
    private String idReserva;
    private String idLeitor;
    private String idLivro;
    private ReservaStatus status;

    private LocalDateTime dataReservado;

    private LocalDateTime dataLimite;

    public Reserva(String idReserva, String idLeitor, String idLivro, ReservaStatus status, LocalDateTime dataReservado, LocalDateTime dataLimite) {
        this.idReserva = idReserva;
        this.idLeitor = idLeitor;
        this.idLivro = idLivro;
        this.status = status;
        this.dataReservado = dataReservado;
        this.dataLimite = dataLimite;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getIdLeitor() {
        return idLeitor;
    }

    public void setIdLeitor(String idLeitor) {
        this.idLeitor = idLeitor;
    }

    public String getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(String idLivro) {
        this.idLivro = idLivro;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(idReserva, reserva.idReserva);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReserva);
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva='" + idReserva + '\'' +
                ", idLeitor='" + idLeitor + '\'' +
                ", idLivro='" + idLivro + '\'' +
                ", status=" + status +
                ", dataReservado=" + dataReservado +
                ", dataLimite=" + dataLimite +
                '}';
    }
}
