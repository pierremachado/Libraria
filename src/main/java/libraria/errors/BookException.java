package main.java.libraria.errors;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class BookException extends Exception {
    public BookException() {
        super();
    }

    public BookException(String mensagem) {
        super(mensagem);
    }
}
