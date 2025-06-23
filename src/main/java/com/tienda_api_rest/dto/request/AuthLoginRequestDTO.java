package com.tienda_api_rest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthLoginRequestDTO(
        @NotBlank
        @Email(message = "El email no es válido")
        @Size(max = 100)
        String email,
        @NotBlank String password
) {
}
