package main.java.com.uefs.libraria.exceptions;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class IdAlreadyExistsException extends Exception {
    public IdAlreadyExistsException() {
        super();
    }

    public IdAlreadyExistsException(String mensagem) {
        super(mensagem);
    }
}
