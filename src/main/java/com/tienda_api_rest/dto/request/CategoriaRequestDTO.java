package com.tienda_api_rest.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequestDTO(
        @NotBlank(message = "El nombre de la categoria no puede estar vacio")
        String nombre
) {
}
