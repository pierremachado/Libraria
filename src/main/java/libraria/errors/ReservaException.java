package main.java.libraria.errors;

/**
 * @author      José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version     1.0
 */
public class ReservaException extends Exception {
    public ReservaException() {
        super();
    }

    public ReservaException(String mensagem) {
        super(mensagem);
    }
}
