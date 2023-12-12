package main.java.com.uefs.libraria.exceptions;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class ReservationException extends Exception {
    public ReservationException() {
        super();
    }

    public ReservationException(String mensagem) {
        super(mensagem);
    }
}
