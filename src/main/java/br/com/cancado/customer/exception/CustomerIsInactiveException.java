package br.com.cancado.customer.exception;

public class CustomerIsInactiveException extends RuntimeException {

    public CustomerIsInactiveException() {
        super("Usuário está inativo.");
    }

    public CustomerIsInactiveException(String message) {
        super(message);
    }
}
