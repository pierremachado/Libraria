package main.java.libraria.model;

import main.java.libraria.model.enums.LeitorStatus;
import main.java.libraria.model.enums.UserPermissao;

import java.time.LocalDateTime;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class Leitor extends Usuario {
    private String endereco;
    private String telefone;
    private LeitorStatus status;
    private LocalDateTime dataLimiteMulta;

    public Leitor(String nome, String sobrenome, String id, String senha, String endereco, String telefone) {
        super(nome, sobrenome, id, "Leitor", senha, UserPermissao.LEITOR);
        this.endereco = endereco;
        this.telefone = telefone;
        this.status = LeitorStatus.LIBERADO;
        this.dataLimiteMulta = null;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LeitorStatus getStatus() {
        return status;
    }

    public void setStatus(LeitorStatus status) {
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
                ", id=" + getId() +
                ", cargo='" + getCargo() + '\'' +
                ", senha='" + getSenha() + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
