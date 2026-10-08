package br.com.cancado.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordDTO(

        @NotBlank(message = "A senha não pode ser em branco.")
        @Size(min = 8, max = 72, message = "A senha deve ter entre 8 e 72 caracteres.")
        String currentPassword,

        @NotBlank(message = "A nova senha não pode ser em branco.")
        @Size(min = 8, max = 72, message = "A nova senha deve ter entre 8 e 72 caracteres.")
        String newPassword
) {
}
