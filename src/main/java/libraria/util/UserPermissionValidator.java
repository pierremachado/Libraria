package main.java.libraria.util;

import main.java.libraria.model.enums.UserPermissao;

public class UserPermissionValidator {
    public static boolean validate(UserPermissao expected, UserPermissao received) {
        return expected == received;
    }
}
