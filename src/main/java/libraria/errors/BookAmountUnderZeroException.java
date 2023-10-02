package main.java.libraria.errors;

public class BookAmountUnderZeroException extends Exception {
    public BookAmountUnderZeroException() {
        super();
    }

    public BookAmountUnderZeroException(String mensagem) {
        super(mensagem);
    }
}
