package br.com.cancado.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(

        @NotBlank(message = "O e-mail não pode ser em branco.")
        @Size(max = 255, message = "O email deve ter no máximo 255 caracteres.")
        String email,

        @NotBlank(message = "A senha não pode ser em branco.")
        @Size(max = 72, message = "A senha deve ter no máximo 72 caracteres.")
        String password
) {
    @Override
    public String toString() {
        return "LoginRequestDTO[email="
                + email
                + ", password=****]";
    }
}
