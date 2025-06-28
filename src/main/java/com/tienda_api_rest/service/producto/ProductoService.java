package com.tienda_api_rest.service.producto;

import com.tienda_api_rest.dto.request.ProductoRequestDTO;
import com.tienda_api_rest.dto.response.PagedResponseDTO;
import com.tienda_api_rest.dto.response.ProductoResponseDTO;
import com.tienda_api_rest.exception.ElementNotFoundException;
import com.tienda_api_rest.mapper.producto.ProductoMapper;
import com.tienda_api_rest.model.Producto;
import com.tienda_api_rest.repository.producto.ProductoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public ProductoResponseDTO crearProducto(ProductoRequestDTO productoRequestDTO){
        Producto productoEntity = ProductoMapper.toEntity(productoRequestDTO);
        return ProductoMapper.toResponseDTO(productoRepository.save(productoEntity));
    }

    // Retorna una lista con todos los registro de productos
    public List<ProductoResponseDTO> listarProductos(){
        return productoRepository.findAll().stream()
                .map(ProductoMapper::toResponseDTO)
                .toList();
    }

    // Recibe un id (Long) y retorna un producto específico
    public ProductoResponseDTO obtenerPorId(Long id){
        Producto productoEntity = productoRepository.findById(id)
                .orElseThrow(
                        ()-> new ElementNotFoundException("No se encontraron coincidencias con ID: " + id)
                );
        return ProductoMapper.toResponseDTO(productoEntity);
    }

    public ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO productoRequestDTO){
        Producto productoEntity = productoRepository.findById(id)
                .orElseThrow(
                        ()-> new ElementNotFoundException("No se encontraron coincidencias con ID: " + id)
                );
        productoEntity.setNumProducto(productoRequestDTO.numProducto());
        productoEntity.setNombre(productoRequestDTO.nombre());
        productoEntity.setPrecio(productoRequestDTO.precio());
        productoEntity.setStock(productoRequestDTO.stock());
        productoEntity.setCategoria(productoRequestDTO.categoria());

        return ProductoMapper.toResponseDTO(productoEntity);
    }

    // Recibe un id (Long) y elimina un registro de producto
    public void eliminarProducto(Long id){
        if (!productoRepository.existsById(id)) throw new ElementNotFoundException("No se encontraron coincidencias con ID: " + id);
        productoRepository.deleteById(id);
    }

    // Recibe un Pageable y devuelve un paginado de productos
    public PagedResponseDTO<ProductoResponseDTO> listarProductosPorPaginado(Pageable pageable){
        Page<Producto> productosPage = productoRepository.findAll(pageable);

        List<ProductoResponseDTO> productoResponseDTOList = productosPage.getContent()
                .stream()
                .map(ProductoMapper::toResponseDTO)
                .toList();

        return new PagedResponseDTO<>(
                productoResponseDTOList,
                productosPage.getNumber(),
                productosPage.getSize(),
                productosPage.getTotalElements(),
                productosPage.getTotalPages(),
                productosPage.isLast()
        );
    }
}
