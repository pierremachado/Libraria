package com.uefs.libraria.model;

import com.uefs.libraria.model.enums.UserPermission;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class Guest extends User {
    public Guest() {
        super("Convidado", null, null, "Convidado", null, UserPermission.CONVIDADO);
    }
}
