package com.tienda_api_rest.mapper.producto;

import com.tienda_api_rest.dto.request.ProductoRequestDTO;
import com.tienda_api_rest.dto.response.ProductoResponseDTO;
import com.tienda_api_rest.model.Producto;

public class ProductoMapper {

    public static Producto toEntity(ProductoRequestDTO productoRequestDTO){
        return Producto.builder()
                .numProducto(productoRequestDTO.numProducto())
                .nombre(productoRequestDTO.nombre())
                .precio(productoRequestDTO.precio())
                .stock(productoRequestDTO.stock())
                .categoria(productoRequestDTO.categoria())
                .build();
    }

    public static ProductoResponseDTO toResponseDTO(Producto producto){
        return new ProductoResponseDTO(
                producto.getNumProducto(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getCategoria().getNombre(),
                producto.getImagenUrl()
        );
    }
}
