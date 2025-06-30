package com.tienda_api_rest.controller.producto;

import com.tienda_api_rest.dto.request.ProductoRequestDTO;
import com.tienda_api_rest.dto.response.PagedResponseDTO;
import com.tienda_api_rest.dto.response.ProductoResponseDTO;
import com.tienda_api_rest.service.producto.ProductoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tienda/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // endpoint para crear un registro de producto
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(
            @ModelAttribute @Valid ProductoRequestDTO productoRequestDTO) {
        return ResponseEntity.ok(productoService.crearProducto(productoRequestDTO));
    }
    // endpoint para listar los productos
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar(){
        return ResponseEntity.ok(productoService.listarProductos());
    }

    // endpoint para obtener un registro específico de producto
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtener(@PathVariable Long id){
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    // endpoint para actualizar un registro de producto
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id, @ModelAttribute ProductoRequestDTO productoRequestDTO){
        return ResponseEntity.ok(productoService.actualizarProducto(id, productoRequestDTO));
    }
    // endpoint para eliminar un registro de producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        return ResponseEntity.noContent().build();
    }
    // endpoint para listar productos con paginado
    @GetMapping("/pagina")
    public ResponseEntity<PagedResponseDTO<ProductoResponseDTO>> listarPorPaginado(Pageable pageable){
        return ResponseEntity.ok(productoService.listarProductosPorPaginado(pageable));
    }
}
