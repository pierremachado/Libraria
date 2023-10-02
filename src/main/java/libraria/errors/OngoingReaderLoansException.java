package main.java.libraria.errors;

public class OngoingReaderLoansException extends Exception {
    public OngoingReaderLoansException() {
        super();
    }

    public OngoingReaderLoansException(String mensagem) {
        super(mensagem);
    }
}
