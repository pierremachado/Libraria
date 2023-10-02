package main.java.libraria.model;

import main.java.libraria.model.enums.UserPermissao;

public class Convidado extends Usuario {
    public Convidado() {
        super("Convidado", null, null, "Convidado", null, UserPermissao.CONVIDADO);
    }
}
