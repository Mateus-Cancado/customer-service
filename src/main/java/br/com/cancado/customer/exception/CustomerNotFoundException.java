package br.com.cancado.customer.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException() {
        super("Usuário não encontrado");
    }
}
