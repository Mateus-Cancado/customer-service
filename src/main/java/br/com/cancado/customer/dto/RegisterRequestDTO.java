package br.com.cancado.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO (

        @NotBlank(message = "O nome não pode ser em branco.")
        @Size(min = 3, max = 150, message = "O nome deve ter entre 3 e 150 caracteres.")
        String name,

        @NotBlank(message = "O e-mail não pode ser em branco.")
        @Email(message = "O e-mail informado é inválido.")
        @Size(max = 255, message = "O email deve ter no máximo 255 caracteres.")
        String email,

        @NotBlank(message = "A senha não pode ser em branco.")
        @Size(min = 8, max = 72, message = "A senha deve ter entre 8 e 72 caracteres.")
        String password
) {
    @Override
    public String toString() {
        return "RegisterRequestDTO[name="
                + name
                + ", email="
                + email
                + ", password=****]";
    }
}
