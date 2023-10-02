package main.java.libraria.errors;

public class IdAlreadyExistsException extends Exception {
    public IdAlreadyExistsException() {
        super();
    }

    public IdAlreadyExistsException(String mensagem) {
        super(mensagem);
    }
}
