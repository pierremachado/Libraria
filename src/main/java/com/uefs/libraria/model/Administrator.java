package main.java.com.uefs.libraria.model;

import main.java.com.uefs.libraria.model.enums.UserPermission;

import java.io.Serializable;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class Administrator extends User implements Serializable {
    public Administrator(String nome, String sobrenome, String id, String senha) {
        super(nome, sobrenome, id, "Administrador", senha, UserPermission.ADMINISTRADOR);
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

