package main.java.libraria.errors;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class RemoveSelfAttemptException extends Exception {
    public RemoveSelfAttemptException() {
        super();
    }

    public RemoveSelfAttemptException(String mensagem) {
        super(mensagem);
    }
}
