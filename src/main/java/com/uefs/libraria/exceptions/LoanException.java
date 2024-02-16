package com.uefs.libraria.exceptions;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class LoanException extends Exception {
    public LoanException() {
        super();
    }

    public LoanException(String mensagem) {
        super(mensagem);
    }
}
