package main.java.com.uefs.libraria.exceptions;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class OngoingReaderLoansException extends Exception {
    public OngoingReaderLoansException() {
        super();
    }

    public OngoingReaderLoansException(String mensagem) {
        super(mensagem);
    }
}
