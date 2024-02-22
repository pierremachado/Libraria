package com.uefs.libraria.exceptions;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class OngoingLoansException extends Exception {
    public OngoingLoansException() {
        super();
    }

    public OngoingLoansException(String mensagem) {
        super(mensagem);
    }
}
