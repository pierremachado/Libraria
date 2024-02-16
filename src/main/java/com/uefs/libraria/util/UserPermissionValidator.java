package com.uefs.libraria.util;

import com.uefs.libraria.model.enums.UserPermission;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class UserPermissionValidator {
    public static boolean validate(UserPermission expected, UserPermission received) {
        return expected == received;
    }
}
