package com.tienda_api_rest.service.categoria;

import com.tienda_api_rest.dto.request.CategoriaRequestDTO;
import com.tienda_api_rest.dto.response.CategoriaResponseDTO;
import com.tienda_api_rest.exception.ElementNotFoundException;
import com.tienda_api_rest.mapper.categoria.CategoriaMapper;
import com.tienda_api_rest.model.Categoria;
import com.tienda_api_rest.repository.categoria.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaResponseDTO crearCategoria(CategoriaRequestDTO categoriaRequestDTO){
        Categoria categoriaEntity = CategoriaMapper.toEntity(categoriaRequestDTO);
        return CategoriaMapper.toResponseDTO(categoriaRepository.save(categoriaEntity));
    }

    public List<CategoriaResponseDTO> listarCategorias(){
        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaMapper::toResponseDTO)
                .toList();
    }

    public CategoriaResponseDTO obtenerPorId(Long id){
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(
                        () -> new ElementNotFoundException("No se encontraron coincidencias con ID: " + id)
                );
        return CategoriaMapper.toResponseDTO(categoria);
    }

    public CategoriaResponseDTO actualizarCategoria(Long id, CategoriaRequestDTO categoriaRequestDTO){
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(
                        ()-> new ElementNotFoundException("No se encontraron coincidencias con ID: " + id)
                );
        categoria.setNombre(categoriaRequestDTO.nombre());

        return CategoriaMapper.toResponseDTO(categoria);
    }

    public void eliminarCategoria(Long id){
        if (!categoriaRepository.existsById(id)) throw new ElementNotFoundException("No se encontraron coincidencias con ID: " + id);
        categoriaRepository.deleteById(id);
    }
}
