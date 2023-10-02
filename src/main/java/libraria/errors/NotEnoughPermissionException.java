package main.java.libraria.errors;

public class NotEnoughPermissionException extends Exception {
    public NotEnoughPermissionException() {
        super();
    }

    public NotEnoughPermissionException(String mensagem) {
        super(mensagem);
    }
}
