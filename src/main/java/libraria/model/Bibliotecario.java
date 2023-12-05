package main.java.libraria.model;

import main.java.libraria.model.enums.UserPermissao;

import java.io.Serializable;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class Bibliotecario extends Usuario implements Serializable {
    public Bibliotecario(String nome, String sobrenome, String id, String senha) {
        super(nome, sobrenome, id, "Bibliotecário", senha, UserPermissao.BIBLIOTECARIO);
    }

    @Override
    public String toString() {
        return "Bibliotecario{" +
                ", nome='" + getNome() + '\'' +
                ", id=" + getId() +
                ", cargo='" + getCargo() + '\'' +
                ", senha='" + getSenha() + '\'' +
                '}';
    }
}
