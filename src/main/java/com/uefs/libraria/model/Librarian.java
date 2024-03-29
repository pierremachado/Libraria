package com.uefs.libraria.model;

import com.uefs.libraria.model.enums.UserPermission;

import java.io.Serializable;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class Librarian extends User implements Serializable {
    public Librarian(String nome, String sobrenome, String id, String senha, String endereco, String telefone) {
        super(nome, sobrenome, id, "Bibliotecário", senha, UserPermission.BIBLIOTECARIO, endereco, telefone);
    }

    public Librarian(String nome, String sobrenome, String id, String senha) {
        super(nome, sobrenome, id, "Bibliotecário", senha, UserPermission.BIBLIOTECARIO);
    }

    @Override
    public String toString() {
        return "Bibliotecario{" +
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
