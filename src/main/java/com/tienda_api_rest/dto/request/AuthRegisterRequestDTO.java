package com.tienda_api_rest.dto.request;

import com.tienda_api_rest.model.enums.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthRegisterRequestDTO(
        @NotBlank
        String nombre,
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotNull RoleEnum role
) {
}
