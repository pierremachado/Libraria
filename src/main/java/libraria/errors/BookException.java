package main.java.libraria.errors;

public class BookException extends Exception {
    public BookException() {
        super();
    }

    public BookException(String mensagem) {
        super(mensagem);
    }
}
