package br.com.cancado.customer.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("Já existe um cliente cadastrado com o e-mail: " + email);
    }
}
