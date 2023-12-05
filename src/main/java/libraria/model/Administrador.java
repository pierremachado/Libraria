package main.java.libraria.model;

import main.java.libraria.model.enums.UserPermissao;

import java.io.Serializable;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class Administrador extends Usuario implements Serializable {
    public Administrador(String nome, String sobrenome, String id, String senha) {
        super(nome, sobrenome, id, "Administrador", senha, UserPermissao.ADMINISTRADOR);
    }

    @Override
    public String toString() {
        return "Administrador{" +
                ", nome='" + getNome() + '\'' +
                ", id=" + getId() +
                ", cargo='" + getCargo() + '\'' +
                ", senha='" + getSenha() + '\'' +
                '}';
    }
}

