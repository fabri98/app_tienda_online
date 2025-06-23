package com.tienda_api_rest.mapper.categoria;

import com.tienda_api_rest.dto.request.CategoriaRequestDTO;
import com.tienda_api_rest.dto.response.CategoriaResponseDTO;
import com.tienda_api_rest.model.Categoria;

public class CategoriaMapper {

    public static Categoria toEntity(CategoriaRequestDTO categoriaRequestDTO){
        Categoria categoria = Categoria.builder()
                .nombre(categoriaRequestDTO.nombre())
                .build();

        return categoria;
    }

    public static CategoriaResponseDTO toResponseDTO(Categoria categoria){
        return new CategoriaResponseDTO(
                categoria.getNombre()
        );
    }
}
