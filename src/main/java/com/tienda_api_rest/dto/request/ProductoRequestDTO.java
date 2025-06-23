package com.tienda_api_rest.dto.request;

import com.tienda_api_rest.model.Categoria;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record ProductoRequestDTO(
        @NotNull(message = "El numero de producto no puede ser null")
        String numProducto,
        @NotBlank(message = "El nombre no puede estar vacio")
        @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
        String nombre,
        @Positive(message = "El precio debe ser un valor positivo")
        double precio,
        @Min(value = 0, message = "El valor minimo del stock es 0")
        int stock,

        @Valid
        @NotNull(message = "La categoria no puede ser null")
        Categoria categoria
) {
}
