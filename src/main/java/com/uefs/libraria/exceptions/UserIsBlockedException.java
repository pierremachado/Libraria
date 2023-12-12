package main.java.com.uefs.libraria.exceptions;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class UserIsBlockedException extends Exception {
    public UserIsBlockedException() {
        super();
    }

    public UserIsBlockedException(String mensagem) {
        super(mensagem);
    }
}
