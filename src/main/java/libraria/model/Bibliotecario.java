package main.java.libraria.model;

import main.java.libraria.model.enums.UserPermissao;

public class Bibliotecario extends Usuario {
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
