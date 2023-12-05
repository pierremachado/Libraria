package main.java.libraria.errors;

/**
 * @author José Alberto da Silva Porto Júnior e Pierre Machado Mendes Novaes
 * @version 1.0
 */
public class IdNotFoundException extends Exception {
    public IdNotFoundException() {
        super();
    }

    public IdNotFoundException(String mensagem) {
        super(mensagem);
    }
}
