package com.uefs.libraria.exceptions;

public class EditIdWithOngoingLoansException extends Exception{

    public EditIdWithOngoingLoansException() {
        super();
    }

    public EditIdWithOngoingLoansException(String mensagem) {
        super(mensagem);
    }
}
