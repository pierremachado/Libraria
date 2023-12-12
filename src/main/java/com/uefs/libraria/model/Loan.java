package main.java.com.uefs.libraria.model;

import main.java.com.uefs.libraria.model.enums.LoanStatus;
import main.java.com.uefs.libraria.model.enums.UserPermission;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class Loan implements Serializable {
    private String idEmprestimo;
    private String idOperador;
    private UserPermission permissaoOperador; // pesquisar no DAO de administrador ou de bibliotecário
    private String idLeitor;
    private String idLivro;
    private String idReserva;
    private LocalDateTime dataEmprestimo;
    private LocalDateTime dataLimite;
    private LocalDateTime dataDeRetorno;
    private int vezesRenovado;
    private LoanStatus status;

    public Loan(String idOperador, UserPermission permissaoOperador, String idLeitor, String idLivro, String idReserva, LocalDateTime dataEmprestimo, LocalDateTime dataLimite, LocalDateTime dataDeRetorno, int vezesRenovado, LoanStatus status) {
        this.idEmprestimo = null;
        this.idOperador = idOperador;
        this.permissaoOperador = permissaoOperador;
        this.idLeitor = idLeitor;
        this.idLivro = idLivro;
        this.idReserva = idReserva;
        this.dataEmprestimo = dataEmprestimo;
        this.dataLimite = dataLimite;
        this.dataDeRetorno = dataDeRetorno;
        this.vezesRenovado = vezesRenovado;
        this.status = status;
    }

    public String getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(String idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public String getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(String idOperador) {
        this.idOperador = idOperador;
    }

    public UserPermission getPermissaoOperador() {
        return permissaoOperador;
    }

    public void setPermissaoOperador(UserPermission permissaoOperador) {
        this.permissaoOperador = permissaoOperador;
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

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
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

    public int getVezesRenovado() {
        return vezesRenovado;
    }

    public void setVezesRenovado(int vezesRenovado) {
        this.vezesRenovado = vezesRenovado;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan that = (Loan) o;
        return Objects.equals(idEmprestimo, that.idEmprestimo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmprestimo);
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "idEmprestimo='" + idEmprestimo + '\'' +
                ", idOperador='" + idOperador + '\'' +
                ", permissaoOperador=" + permissaoOperador +
                ", idLeitor='" + idLeitor + '\'' +
                ", idLivro='" + idLivro + '\'' +
                ", idReserva='" + idReserva + '\'' +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataLimite=" + dataLimite +
                ", dataDeRetorno=" + dataDeRetorno +
                ", vezesRenovado=" + vezesRenovado +
                ", status=" + status +
                '}';
    }
}
