package com.tienda_api_rest.controller.categoria;

import com.tienda_api_rest.dto.request.CategoriaRequestDTO;
import com.tienda_api_rest.dto.response.CategoriaResponseDTO;
import com.tienda_api_rest.service.categoria.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tienda/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> crear(@RequestBody @Valid CategoriaRequestDTO categoriaRequestDTO){
        return ResponseEntity.ok(categoriaService.crearCategoria(categoriaRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listar(){
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtener(@PathVariable Long id){
        return ResponseEntity.ok(categoriaService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizar(@PathVariable Long id, @RequestBody CategoriaRequestDTO categoriaRequestDTO){
        return ResponseEntity.ok(categoriaService.actualizarCategoria(id, categoriaRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }

}
