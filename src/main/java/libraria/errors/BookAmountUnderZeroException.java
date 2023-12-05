package main.java.libraria.errors;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class BookAmountUnderZeroException extends Exception {
    public BookAmountUnderZeroException() {
        super();
    }

    public BookAmountUnderZeroException(String mensagem) {
        super(mensagem);
    }
}
