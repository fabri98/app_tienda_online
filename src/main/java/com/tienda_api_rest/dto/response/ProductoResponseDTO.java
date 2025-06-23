package com.tienda_api_rest.dto.response;

import com.tienda_api_rest.model.Categoria;


public record ProductoResponseDTO(
        String numProducto,
        String nombre,
        double precio,
        int stock,
        Categoria categoria
) {
}
