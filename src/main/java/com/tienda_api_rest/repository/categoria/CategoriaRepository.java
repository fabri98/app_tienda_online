package com.tienda_api_rest.repository.categoria;

import com.tienda_api_rest.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
//    @Query(
//            "SELECT c FROM Categoria c WHERE c.idCategoria = :id OR c.nombre = :nombre"
//    )
    Optional<Categoria> findByIdCategoriaOrNombreIgnoreCase(Long id,String nombre);
}
