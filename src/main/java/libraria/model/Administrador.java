package main.java.libraria.model;

import main.java.libraria.model.enums.UserPermissao;

public class Administrador extends Usuario{
    public Administrador(String nome, String sobrenome, String id, String senha){
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

