package main.java.libraria.errors;

public class IdNotFoundException extends Exception {
    public IdNotFoundException() {
        super();
    }

    public IdNotFoundException(String mensagem) {
        super(mensagem);
    }
}
