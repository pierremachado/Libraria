package com.uefs.libraria.model;

import com.uefs.libraria.model.enums.ReaderStatus;
import com.uefs.libraria.model.enums.UserPermission;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class Reader extends User implements Serializable {
    private ReaderStatus status;
    private LocalDateTime dataLimiteMulta;

    public Reader(String nome, String sobrenome, String id, String senha) {
        super(nome, sobrenome, id, "Leitor", senha, UserPermission.LEITOR);
        this.status = ReaderStatus.LIBERADO;
        this.dataLimiteMulta = null;
    }

    public Reader(String nome, String sobrenome, String id, String senha, String endereco, String telefone) {
        super(nome, sobrenome, id, "Leitor", senha, UserPermission.LEITOR, endereco, telefone);
        this.status = ReaderStatus.LIBERADO;
        this.dataLimiteMulta = null;
    }

    public ReaderStatus getStatus() {
        return status;
    }

    public void setStatus(ReaderStatus status) {
        this.status = status;
    }

    public LocalDateTime getDataLimiteMulta() {
        return dataLimiteMulta;
    }

    public void setDataLimiteMulta(LocalDateTime dataLimiteMulta) {
        this.dataLimiteMulta = dataLimiteMulta;
    }

    @Override
    public String toString() {
        return "Leitor{" +
                ", nome='" + getNome() + '\'' +
                ", sobrenome=" + getSobrenome() + '\'' +
                ", id=" + getId() +
                ", cargo='" + getCargo() + '\'' +
                ", senha='" + getSenha() + '\'' +
                ", endereco='" + getEndereco() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
