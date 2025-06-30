package com.tienda_api_rest.dto.response;


public record ProductoResponseDTO(
        String numProducto,
        String nombre,
        double precio,
        int stock,
        String categoria,
        String imageUrl
) {
}
