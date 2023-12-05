package main.java.libraria.util;

import main.java.libraria.model.enums.UserPermissao;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class UserPermissionValidator {
    public static boolean validate(UserPermissao expected, UserPermissao received) {
        return expected == received;
    }
}
