package br.com.cancado.customer.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("E-mail ou senha inválidos.");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    };
}
