package main.java.libraria.model;

import main.java.libraria.model.enums.UserPermissao;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class Convidado extends Usuario {
    public Convidado() {
        super("Convidado", null, null, "Convidado", null, UserPermissao.CONVIDADO);
    }
}
