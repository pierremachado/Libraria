package main.java.com.uefs.libraria.exceptions;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class NotEnoughPermissionException extends Exception {
    public NotEnoughPermissionException() {
        super();
    }

    public NotEnoughPermissionException(String mensagem) {
        super(mensagem);
    }
}
