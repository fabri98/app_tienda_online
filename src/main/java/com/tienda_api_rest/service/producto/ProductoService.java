package com.tienda_api_rest.service.producto;

import com.tienda_api_rest.dto.request.ProductoRequestDTO;
import com.tienda_api_rest.dto.response.PagedResponseDTO;
import com.tienda_api_rest.dto.response.ProductoResponseDTO;
import com.tienda_api_rest.exception.CloudinaryUploadException;
import com.tienda_api_rest.exception.ElementNotFoundException;
import com.tienda_api_rest.mapper.producto.ProductoMapper;
import com.tienda_api_rest.model.Categoria;
import com.tienda_api_rest.model.Producto;
import com.tienda_api_rest.repository.categoria.CategoriaRepository;
import com.tienda_api_rest.repository.producto.ProductoRepository;
import com.tienda_api_rest.service.cloudinary.CloudinaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final CloudinaryService cloudinaryService;

    //Inyeccion de dependencia por constructor
    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, CloudinaryService cloudinaryService) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.cloudinaryService = cloudinaryService;
    }

    // crea un registro de producto
    @Transactional
    public ProductoResponseDTO crearProducto(ProductoRequestDTO productoRequestDTO) {

        Producto productoEntity = ProductoMapper.toEntity(productoRequestDTO);

        // Verificamos si la categoria ya existe, de ser así establecemos la relacion.
        // Caso contrario, creamos la categoria en cascada
        Optional<Categoria> categoriaOptional = categoriaRepository.findByIdCategoriaOrNombreIgnoreCase(
                productoRequestDTO.categoria().getIdCategoria(),
                productoRequestDTO.categoria().getNombre());
        categoriaOptional.ifPresent(productoEntity::setCategoria);

        // Verificamos que la imagen no sea null o que su contenido este vacio
        // Si esto se cumple cargamos la imagen
        if (productoRequestDTO.imagen() != null && !productoRequestDTO.imagen().isEmpty()) {
            try {
                String imagenUrl = cloudinaryService.subirImagen(productoRequestDTO.imagen());
                productoEntity.setImagenUrl(imagenUrl);
            } catch (IOException e) {
                throw new CloudinaryUploadException("Error al subir la imagen a Cloudinary");
            }
        }
        return ProductoMapper.toResponseDTO(productoRepository.save(productoEntity));
    }

    // Retorna una lista con todos los registro de productos
    public List<ProductoResponseDTO> listarProductos() {
        return productoRepository.findAll().stream()
                .map(ProductoMapper::toResponseDTO)
                .toList();
    }

    // Recibe un id (Long) y retorna un producto específico
    public ProductoResponseDTO obtenerPorId(Long id) {
        Producto productoEntity = productoRepository.findById(id)
                .orElseThrow(
                        () -> new ElementNotFoundException("No se encontraron coincidencias con ID: " + id)
                );
        return ProductoMapper.toResponseDTO(productoEntity);
    }

    // Recibe un id(Long) y un ProductoRequestDTO, actualiza y retorna un ProductoResponseDTO
    @Transactional
    public ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO productoRequestDTO) {
        Producto productoEntity = productoRepository.findById(id)
                .orElseThrow(
                        () -> new ElementNotFoundException("No se encontraron coincidencias con ID: " + id)
                );
        productoEntity.setNumProducto(productoRequestDTO.numProducto());
        productoEntity.setNombre(productoRequestDTO.nombre());
        productoEntity.setPrecio(productoRequestDTO.precio());
        productoEntity.setStock(productoRequestDTO.stock());
        productoEntity.setCategoria(productoRequestDTO.categoria());

        if (productoRequestDTO.imagen() != null && !productoRequestDTO.imagen().isEmpty()) {
            try {
                String imagenUrl = cloudinaryService.subirImagen(productoRequestDTO.imagen());
                productoEntity.setImagenUrl(imagenUrl);
            } catch (IOException e) {
                throw new CloudinaryUploadException("Error al subir la imagen a Cloudinary");
            }
        }
        return ProductoMapper.toResponseDTO(productoEntity);
    }

    // Recibe un id (Long) y elimina un registro de producto
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id))
            throw new ElementNotFoundException("No se encontraron coincidencias con ID: " + id);
        productoRepository.deleteById(id);
    }

    // Recibe un Pageable y devuelve un paginado de productos
    public PagedResponseDTO<ProductoResponseDTO> listarProductosPorPaginado(Pageable pageable) {
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
