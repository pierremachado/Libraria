package main.java.libraria.errors;

public class EmprestimoException extends Exception {
    public EmprestimoException() {
        super();
    }

    public EmprestimoException(String mensagem) {
        super(mensagem);
    }
}
