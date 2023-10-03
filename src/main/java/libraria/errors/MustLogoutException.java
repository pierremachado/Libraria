package main.java.libraria.errors;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class MustLogoutException extends Exception {
    public MustLogoutException() {
        super();
    }

    public MustLogoutException(String mensagem) {
        super(mensagem);
    }
}
