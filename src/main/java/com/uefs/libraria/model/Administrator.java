package com.uefs.libraria.model;

import java.io.Serializable;

import static com.uefs.libraria.model.enums.UserPermission.*;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class Administrator extends User implements Serializable {
    public Administrator(String nome, String sobrenome, String id, String senha, String endereco, String telefone) {
        super(nome, sobrenome, id, "Administrador", senha, ADMINISTRADOR, endereco, telefone);
    }

    public Administrator(String nome, String sobrenome, String id, String senha) {
        super(nome, sobrenome, id, "Administrador", senha, ADMINISTRADOR);
    }

    @Override
    public String toString() {
        return "Administrador{" +
                ", nome='" + getNome() + '\'' +
                ", sobrenome=" + getSobrenome() + '\'' +
                ", id=" + getId() +
                ", cargo='" + getCargo() + '\'' +
                ", senha='" + getSenha() + '\'' +
                ", endereco='" + getEndereco() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                '}';
    }
}

