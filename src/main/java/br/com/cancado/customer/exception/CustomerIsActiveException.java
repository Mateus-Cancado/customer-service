package br.com.cancado.customer.exception;

public class CustomerIsActiveException extends RuntimeException {

    public CustomerIsActiveException() {
        super("Usuário está ativo.");
    }

    public CustomerIsActiveException(String message) {
        super(message);
    }
}
