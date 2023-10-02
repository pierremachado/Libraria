package main.java.libraria.errors;

public class RemoveSelfAttemptException extends Exception {
    public RemoveSelfAttemptException() {
        super();
    }

    public RemoveSelfAttemptException(String mensagem) {
        super(mensagem);
    }
}
