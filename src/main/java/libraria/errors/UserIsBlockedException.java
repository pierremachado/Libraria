package main.java.libraria.errors;

public class UserIsBlockedException extends Exception {
    public UserIsBlockedException() {
        super();
    }

    public UserIsBlockedException(String mensagem) {
        super(mensagem);
    }
}
