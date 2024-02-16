package com.uefs.libraria.exceptions;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class IncorrectCredentialsException extends Exception {
    public IncorrectCredentialsException() {
        super();
    }

    public IncorrectCredentialsException(String mensagem) {
        super(mensagem);
    }
}
