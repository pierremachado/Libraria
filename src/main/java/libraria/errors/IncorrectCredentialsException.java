package main.java.libraria.errors;

public class IncorrectCredentialsException extends Exception {
    public IncorrectCredentialsException() {
        super();
    }

    public IncorrectCredentialsException(String mensagem) {
        super(mensagem);
    }
}
